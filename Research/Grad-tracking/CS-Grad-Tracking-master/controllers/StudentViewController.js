var schema = require("../models/schema.js");
var util = require("./util.js");
var formidable = require("formidable");
var fs = require("fs");
var path = require("path");
var XLSX = require("xlsx");
var formidable = require("formidable");
var mongoose = require("mongoose");
var nodemailer = require('nodemailer');

var studentViewController = {};

var transport = nodemailer.createTransport({
  service: "Gmail",
  auth: {
    user: process.env.gmailUser,
    pass: process.env.gmailPass
  }
})

var mailOptions = {
  from: process.env.gmailUser, // sender address
  to: "", // list of receivers
  subject: "", // Subject line
  text: "No reply", // plaintext body
}

/*
Students only allowed to edit their:
firstName
lastName
alternativeName
gender
ethnicity
notes
*/
studentViewController.put = function (req, res) {
  var input = req.body;
  var editableFields = ["firstName", "lastName", "alternativeName", "gender", "ethnicity", "residency"];
  if (input.firstName != null && input.lastName != null && input._id != null) {
    schema.Student.findOne({ _id: input._id }).exec().then(function (result) {
      if (result != null) {
        for (var i = 0; i < editableFields.length; i++) {
          result[editableFields[i]] = input[editableFields[i]];
        }
        result.save(function (err, updated) {
          res.redirect("/studentView");
        });
      }
      else res.render("../views/error.ejs", { string: "StudentNotFound" });
    });
  }
  else {
    res.render("../views/error.ejs", { string: "RequiredParamNotFound" });
  }
}

studentViewController.get = function (req, res) {
  schema.Student.findOne({ pid: req.session.userPID }).populate("semesterStarted").populate("advisor").exec().then(function (result) {
    if (result != null) {
      result = result.toJSON();
      var genders, ethnicities, student;
      student = result;
      genders = schema.Student.schema.path("gender").enumValues;
      ethnicities = schema.Student.schema.path("ethnicity").enumValues;
      schema.Faculty.find({}).sort({ lastName: 1, firstName: 1 }).exec().then(function (result) {
        res.render("../views/studentView/index", { student: student, faculty: result, ethnicities: ethnicities, genders: genders });
      });
    }
    else {
      res.render("../views/error.ejs", { string: "Student not found" });
    }
  });
}

studentViewController.jobs = function (req, res) {

  schema.Student.findOne({ pid: req.session.userPID }).populate("jobHistory").populate({ path: "jobHistory", populate: { path: "supervisor" } })
    .populate({ path: "jobHistory", populate: { path: "semester" } }).populate({ path: "jobHistory", populate: { path: "course" } }).exec().then(function (result) {
      result.jobHistory.sort(function (a, b) {
        if (a.semester.year == b.semester.year) {
          if (a.semester.season < b.semester.season) {
            return -1;
          }
          if (a.semester.season > b.semester.season) {
            return 1;
          }
          return 0;
        }
        else {
          return a.semester.year - b.semester.year;
        }
      });
      res.render("../views/studentView/jobs", { student: result });
    });
}

studentViewController.forms = function (req, res) {
  schema.Student.findOne({ pid: req.session.userPID }).exec().then(function (result) {
    var student = result;
    res.render("../views/studentView/forms.ejs", { student: student });
  });
}

studentViewController.viewForm = function (req, res) {
  var signature = "In place of your signature, please type your full legal name:";
  if (req.params.title != null && req.params.uploadSuccess != null) {
    var uploadSuccess = false;
    if (req.params.uploadSuccess == "true") {
      uploadSuccess = true;
    }
    schema.Student.findOne({ pid: req.session.userPID }).exec().then(function (result) {
      if (result != null) {
        var student = result;
        schema[req.params.title].findOne({ student: result._id }).exec().then(function (result) {
          var form = {};
          if (result != null) {
            form = result;
          }
          var isStudent = true;
          var hasAccess = true;
          var postMethod = "/studentView/forms/update/" + req.params.title;
          /*Need both an administrator view of form and a student view of form,
          with varying levels of ability to update data fields, and with minor html
          changes, so I use EJS and the above two variables to load the correct
          form version depending on whether it is an administrator/faculty
          or student viewing the form.
          */
          res.render("../views/student/" + req.params.title, { student: student, form: form, signature: signature, uploadSuccess: uploadSuccess, isStudent: isStudent, postMethod: postMethod, hasAccess: hasAccess });
        });
      }
      else {
        res.render("..views/error.ejs", { string: "Student id not specified." });
      }
    });
  }
}

studentViewController.updateForm = function (req, res) {
  var input = req.body;
  if (req.params.title != null) {
    schema.Student.findOne({ pid: req.session.userPID }).populate("advisor").exec().then(function (result) {
      if (result != null) {
        var studentId = result._id;
        var studentInfo = result;

        schema[req.params.title].findOneAndUpdate({ student: studentId }, input).exec().then(function (result) {
          if (result != null) {
            res.redirect("/studentView/forms" + "/" + req.params.title + "/true");
          }
          else {
            var inputModel = new schema[req.params.title](input);
            inputModel.save().then(function (result) {
              res.redirect("/studentView/forms/" + req.params.title + "/true");
            });
          }
          util.checkFormCompletion(studentId).then((result) => {
            //ADD DENISE/JASLEEN WHEN IN PRODUCTION FOR REAL
            mailOptions.to = studentInfo.advisor.email + "";
            mailOptions.subject = studentInfo.firstName + " " + studentInfo.lastName + " has submitted " + req.params.title;
            mailOptions.text = "The student has completed the following forms as of now: " + result;
            transport.sendMail(mailOptions, function (error, response) {
              if (error) {
                console.log(error);
              } else {
                console.log("Message sent");
              }
              // if you don't want to use this transport object anymore, uncomment following line
              transport.close(); // shut down the connection pool, no more messages
            });
          })


        });
      }
      else {
        res.render("../views/error.ejs", { string: "Student not found" });
      }
    })
  }
  else {
    res.render("../views/error.ejs", { string: "Did not include student ID or title of form" });
  }
}

studentViewController.courses = function (req, res) {
  schema.Student.findOne({ pid: req.session.userPID }).populate({
    path: "grades",
    populate: { path: "course", populate: { path: "semester" } }
  }).populate({
    path: "grades",
    populate: { path: "course", populate: { path: "faculty" } }
  }).exec().then(function (result) {
    result.grades.sort(function (a, b) {
      if (a.course.semester.year == b.course.semester.year) {
        if (a.course.semester.season < b.course.semester.season) {
          return -1;
        }
        if (a.course.semester.season > b.course.semester.season) {
          return 1;
        }
        return 0;
      }
      else {
        return a.course.semester.year - b.course.semester.year;
      }
    });
    res.render("../views/studentView/courses.ejs", { student: result });
  });
}

studentViewController.downloadCourses = function (req, res) {
  schema.Student.findOne({ pid: req.session.userPID }).populate({
    path: "grades",
    populate: { path: "course", populate: { path: "semester" } }
  }).populate({
    path: "grades",
    populate: { path: "course", populate: { path: "faculty" } }
  }).lean().exec().then(function (result) {
    result.grades.sort(function (a, b) {
      if (a.course.semester.year == b.course.semester.year) {
        if (a.course.semester.season < b.course.semester.season) {
          return -1;
        }
        if (a.course.semester.season > b.course.semester.season) {
          return 1;
        }
        return 0;
      }
      else {
        return a.course.semester.year - b.course.semester.year;
      }
    });
    var output = [];
    for (var i = 0; i < result.grades.length; i++) {
      var grade = {};
      grade.onyen = result.onyen;
      grade.grade = result.grades[i].grade;
      grade.department = result.grades[i].course.department;
      grade.number = result.grades[i].course.number;
      grade.section = result.grades[i].course.section;
      grade.semester = result.grades[i].course.semester.season + " " + result.grades[i].course.semester.year;
      grade.faculty = result.grades[i].course.faculty.lastName + ", " + result.grades[i].course.faculty.firstName;
      output[i] = grade;
    }
    var wb = XLSX.utils.book_new();
    var ws = XLSX.utils.json_to_sheet(output);
    XLSX.utils.book_append_sheet(wb, ws, "grades");
    var filePath = path.join(__dirname, "../data/gradeTemp.xlsx");
    XLSX.writeFile(wb, filePath);
    res.setHeader("Content-Disposition", "filename=" + result.onyen + " grades.xlsx");
    res.setHeader("Content-type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    fs.createReadStream(filePath).pipe(res);
  });

}

module.exports = studentViewController;
