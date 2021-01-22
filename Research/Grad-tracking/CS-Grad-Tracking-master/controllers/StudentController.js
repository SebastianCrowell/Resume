var schema = require("../models/schema.js");
var util = require("./util.js");
var formidable = require("formidable");
var fs = require("fs");
var path = require("path");
var XLSX = require("xlsx");
var formidable = require("formidable");
var mongoose = require("mongoose");
var studentController = {}


studentController.post = function (req, res) {
  var input = req.body;
  input = verifyBoolean(input);
  //verify that the required fields are not null
  if(input.onyen != null && input.email != null && input.advisor != null){
    //try to find a student by unique identifiers: onyen or PID, display error page if one found
    schema.Student.findOne({$or: [{onyen: input.onyen}, {pid: input.pid}]}).exec().then(function (result) {
      if (result != null){
        res.render("../views/error.ejs", {string: "That student already exists."});
      }
      else if(input.pid.length != 9){
        res.render("../views/error.ejs", {string: "PID needs to be of length 9"});
      }
      else {
        input.onyen = input.onyen.toLowerCase();
        var inputStudent = new schema.Student(util.validateModelData(input, schema.Student));
        console.log(inputStudent);
        /*use the then function because save() is asynchronous. If you only have inputStudent.save(); res.redirect...
        it is possible that the data does not save in time (or load in time if performing queries that return data
        that is to be sent to a view) before the view loads which can cause errors. So put view rendering code which is
        reliant on database operations inside of the then function of those operations*/
        inputStudent.save().then(function(result){
          res.redirect("/student/edit/"+result._id);
        });
      }
    }).catch(function (err) {
      res.json({"error": err.message, "origin": "student.post"})
    });
  }
  else{
    res.render("../views/error.ejs", {string: "Either onyen, firstName, lastName, pid, or advisor is null"});
  }
}

studentController.get = function (req, res) {
  var input = req.query;
  input = util.validateModelData(input, schema.Student); //remove fields that are empty/not part of Student definition
  var search = util.listObjectToString(input);
  var temp = input.status;
  input = util.makeRegexp(input); //make all text fields regular expressions with ignore case
  if(temp != "" && temp != null && temp != undefined){
   input.status = temp;
  }
  var admin;
  if(req.session.accessLevel == 3){
    admin = true;
  } else {
    admin = false;
  }

  schema.Faculty.findOne({pid: req.session.userPID}).exec().then(function(result){
    schema.Student.find(input).sort({lastName:1, firstName:1}).exec().then(function (result) {
      res.render("../views/student/index.ejs", {students: result, admin: admin, search: search});
    }).catch(function (err) {
      res.json({"error": err.message, "origin": "student.get"})
    });
  });
}

studentController.put = function (req, res) {
  var input = req.body;
  input = verifyBoolean(input);
  if(input.phdAwardedDate != ""){
    input.status = "Graduated";
  }
  
  var input = util.validateModelData(input, schema.Student);
  if (input.onyen != null && input.firstName != null && input.lastName != null && input.pid != null && input.pid != NaN) {
    schema.Student.findOneAndUpdate({_id: input._id}, input).exec().then(function(result){
      if(result != null){
        res.redirect("/student/edit/"+result._id);
      }
      else res.render("../views/error.ejs", {string: "StudentNotFound"});
    });
  }
  else{
    res.render("../views/error.ejs", {string: "RequiredParamNotFound"});
  }
}

studentController.delete = function (req, res) {
  var id = req.params._id;
  if (id != null) {
    /*Documents reference students; since documents are
    personal student documents, just delete the documents. 
    */
    schema.Student.findOneAndRemove({_id: id}).exec().then(function(result){
      if(result){
        res.redirect('/student');
      }
      else{
        res.render("../views/error.ejs", {string: "StudentNotFound"});
      }
    });
  }
}

studentController.create = function(req, res){
  var pronouns, genders, ethnicities, residencies, degrees, semesters;
  pronouns = schema.Student.schema.path("pronouns").enumValues;
  genders = schema.Student.schema.path("gender").enumValues;
  ethnicities = schema.Student.schema.path("ethnicity").enumValues;
  residencies = schema.Student.schema.path("residency").enumValues;
  degrees = schema.Student.schema.path("intendedDegree").enumValues;    
  eligibility = schema.Student.schema.path("fundingEligibility").enumValues;
  
  schema.Semester.find().sort({year:1, season:1}).exec().then(function(result){
    semesters = result;
    schema.Faculty.find({}).sort({lastName:1, firstName:1}).exec().then(function(result){
      res.render("../views/student/create", {faculty: result, semesters: semesters, degrees: degrees, residencies: residencies, ethnicities: ethnicities, genders: genders, eligibility: eligibility, pronouns: pronouns});
    });
  });
}

studentController.edit = function(req, res){
  if(req.params._id){
    schema.Student.findOne({_id: req.params._id}).populate("semesterStarted").populate("advisor").exec().then(function(result){
      if(result != null){
        var pronouns, genders, ethnicities, residencies, degrees, semesters, student;
        student = result;
        pronouns = schema.Student.schema.path("pronouns").enumValues;
        genders = schema.Student.schema.path("gender").enumValues;
        ethnicities = schema.Student.schema.path("ethnicity").enumValues;
        residencies = schema.Student.schema.path("residency").enumValues;
        degrees = schema.Student.schema.path("intendedDegree").enumValues;
		    eligibility = schema.Student.schema.path("fundingEligibility").enumValues;
        schema.Semester.find({}).sort({year:1, season:1}).exec().then(function(result){
          semesters = result;
          schema.Faculty.find({}).sort({lastName:1, firstName:1}).exec().then(function(result){
            res.render("../views/student/edit", {student: student, faculty: result, semesters: semesters, degrees: degrees, residencies: residencies, ethnicities: ethnicities, genders: genders, eligibility: eligibility, pronouns: pronouns});
          });
        });
      }
      else{
        res.render("../views/error.ejs", {string: "Student not found"});
      }
    });
  }
  else{
    res.render("../views/error.ejs", {string: "RequiredParamNotFound"});
  }
}

studentController.jobs = function(req, res){
  if(req.params._id){
    var jobs;

    schema.Job.find().populate("supervisor").populate("course").populate("semester").sort({position:1}).exec().then(function(result){
      result.sort(function(a, b){
        if(a.semester.year == b.semester.year){
          if(a.semester.season < b.semester.season){
            return -1;
          }
          if(a.semester.season > b.semester.season){
            return 1;
          }
          return 0;
        }
        else{
          return a.semester.year - b.semester.year;
        }
      });
      jobs = result;

      schema.Student.findOne({_id: req.params._id}).populate("jobHistory").populate({path:"jobHistory", populate:{path:"supervisor"}})
      .populate({path:"jobHistory", populate:{path:"semester"}}).populate({path:"jobHistory", populate:{path:"course"}}).exec().then(function(result){
        result.jobHistory.sort(function(a, b){
          if(a.semester.year == b.semester.year){
            if(a.semester.season < b.semester.season){
              return -1;
            }
            if(a.semester.season > b.semester.season){
              return 1;
            }
            return 0;
          }
          else{
            return a.semester.year - b.semester.year;
          }
        });
        res.render("../views/student/jobs", {student: result, jobs: jobs});
      });
    });
  }
  else{
    //this shouldn't happen if frontend done correctly
    res.render("../views/error.ejs", {string: "RequiredParamNotFound"});
  }
}

studentController.deleteJob = function(req, res){
  var input = req.body;
  if(input.studentId != null && input.jobId != null){
    schema.Student.update({_id:input.studentId}, {$pull:{jobHistory: input.jobId}}).exec().then(function(result){
      res.redirect("/student/jobs/"+input.studentId);
    }).catch(function(err){
      res.render("../views/error.ejs", {string:"Student was not found."});
    });
  } 
  else{
    res.render("../views/error.ejs", {string: "Either studentId or jobId is missing."});
  }
}

studentController.addJobs = function(req, res){
  var input = req.body;
  if(input.studentId != null){
    schema.Student.findOne({_id: input.studentId}).exec().then(function(result){
      if(result != null){
        if(typeof(input.jobs) == "string"){
          input.jobs = [input.jobs];
        }
        schema.Student.update({_id: input.studentId},{$addToSet: {jobHistory: {$each: input.jobs}}}).exec().then(function(result){
          res.redirect("/student/jobs/"+input.studentId);
        });
      }
      else{
        res.render("../views/error.ejs", {string: "Student not found"});
      }
    });
  }
  else{
    res.render("../views/error.ejs", {string: "Student id missing"});
  }
}

studentController.formPage = function(req, res){
  if(req.params._id != null){
    schema.Student.findOne({_id: req.params._id}).exec().then(function(result){
      var student = result;
      res.render("../views/student/forms.ejs", {student: student});
    });
  }
  else{
    res.render("../views/error.ejs", {string: "StudentId incorrect"});
  }
}

studentController.viewForm = function(req, res){
  var signature = "In place of your signature, please type your full legal name:";
  if(req.params.title != null && req.params._id != null && req.params.uploadSuccess != null){
    schema.Faculty.find({}).exec().then((result)=>{
      var faculty = result;
      var uploadSuccess = false;
      if(req.params.uploadSuccess == "true"){
        uploadSuccess = true;
      }
      schema.Student.findOne({_id: req.params._id}).exec().then(function(result){
        if(result != null){
          var student = result;
          schema[req.params.title].findOne({student: result._id}).exec().then(function(result){
            var form = {};
            if(result != null){
              form = result;
            }
            var isStudent = false;
            util.checkAdvisorAdmin(req.session.userPID, req.params._id).then(function(result){
              var hasAccess;
              if(result){hasAccess = true;}
              else{hasAccess = false;}
              var postMethod = "/student/forms/update/"+student._id+"/"+req.params.title;
              res.render("../views/student/"+req.params.title, {student: student, form: form, signature: signature, uploadSuccess: uploadSuccess, isStudent: isStudent, postMethod: postMethod, hasAccess: hasAccess, faculty: faculty});
            });
            
          });
        }
        else{
          res.render("..views/error.ejs", {string: "Student id not specified."});
        }
      })
    })
  }
}

studentController.updateForm = function(req, res){

  var input = req.body;
  if(req.params.title != null && req.params._id != null){
    schema.Student.findOne({_id: req.params._id}).exec().then(function(result){
      if(result != null){
        var studentId = result._id;

        
        schema[req.params.title].findOneAndUpdate({student: studentId}, input).exec().then(function(result){
          if(result != null){
            res.redirect("/student/forms/viewForm/"+studentId+"/"+req.params.title+"/true");
          }
          else{
            var inputModel = new schema[req.params.title](input);
            inputModel.save().then(function(result){
              res.redirect("/student/forms/viewForm/"+studentId+"/"+req.params.title+"/true");
            });
          }
        });
      }
      else{
        res.render("../views/error.ejs", {string: "Student not found"});
      }
    })
  }
  else{
    res.render("../views/error.ejs", {string: "Did not include student ID or title of form"});
  }
}

studentController.courses = function(req, res){
  if(req.params._id != null){
    schema.Student.findOne({_id: req.params._id}).populate({
      path:"grades",
      populate:{path:"course", populate:{path:"semester"}}
    }).populate({
      path:"grades",
      populate:{path:"course", populate:{path:"faculty"}}
    }).exec().then(function(result){
      result.grades.sort(function(a, b){
        if(a.course.semester.year == b.course.semester.year){
          if(a.course.semester.season < b.course.semester.season){
            return -1;
          }
          if(a.course.semester.season > b.course.semester.season){
            return 1;
          }
          return 0;
        }
        else{
          return a.course.semester.year - b.course.semester.year;
        }
      });
      res.render("../views/student/courses.ejs", {student: result});
    });
  }
  else{
    res.render("../views/error.ejs", {string: "Id missing."});
  }
}

studentController.uploadCoursePage = function(req, res){
  schema.Course.find().exec().then(function(result){
    var courses = result;
    var uploadSuccess = false;
    if(req.params.uploadSuccess == "true"){
     uploadSuccess = true;
    }
    res.render("../views/student/uploadCourses.ejs", {courses: courses ,uploadSuccess: uploadSuccess});
  });
}

studentController.downloadCourses = function(req, res){
  if(req.params._id != null){
    schema.Student.findOne({_id: req.params._id}).populate({
      path:"grades",
      populate:{path:"course", populate:{path:"semester"}}
    }).populate({
      path:"grades",
      populate:{path:"course", populate:{path:"faculty"}}
    }).lean().exec().then(function(result){
      result.grades.sort(function(a, b){
        if(a.course.semester.year == b.course.semester.year){
          if(a.course.semester.season < b.course.semester.season){
            return -1;
          }
          if(a.course.semester.season > b.course.semester.season){
            return 1;
          }
          return 0;
        }
        else{
          return a.course.semester.year - b.course.semester.year;
        }
      });
      var output = [];
      for(var i = 0; i < result.grades.length; i++){
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
  else{
    res.render("../views/error.ejs", {string:"Student id wrong or missing"});
  }
  
}

studentController.uploadCourses = function(req, res){
  var form = new formidable.IncomingForm();
  form.parse(req, function(err, fields, files){
    var f = files[Object.keys(files)[0]];
    var workbook = XLSX.readFile(f.path, {cellDates:true});
    var worksheet = workbook.Sheets[workbook.SheetNames[0]];
    var headers = {};
    var data = [];
    headers[String.fromCharCode(65)] = "onyen"
    headers[String.fromCharCode(66)] = "grade";
    headers[String.fromCharCode(67)] = "department";
    headers[String.fromCharCode(68)] = "number";
    headers[String.fromCharCode(69)] = "section";
    headers[String.fromCharCode(70)] = "semester";
    headers[String.fromCharCode(71)] = "faculty";
    for(z in worksheet) {
        if(z[0] === '!') continue;
        //parse out the column, row, and value
        var tt = 0;
        for(var i = 0; i < z.length; i++){
          if(!isNaN(z[i])){
            tt = i;
            break;
          }
        }
        var col = z.substring(0,tt);
        var row = parseInt(z.substring(tt));
        var value = worksheet[z].v;
        if(!data[row]) data[row]={};
        data[row][headers[col]] = value;
    }
    //drop those first two rows which are empty
    data.shift();
    data.shift();
    //have to use foreach because of asynchronous nature of mongoose stuff (the loop would increment i before it could save the appropriate i)
    var count = 0;
    data.forEach(function(element){
      //verify that all fields exist
      if(element.onyen != null && element.department != null && element.number != null && element.section != null && element.semester != null && element.faculty != null){
        var spaceReg = /\s* \s*/;
        var commaReg = /\s*,\s*/;
        var semester = element.semester.split(spaceReg);
        semester[0] = semester[0].toUpperCase();
        semester[1] = parseInt(semester[1]);

        var faculty = element.faculty.split(commaReg);

        schema.Semester.findOne({season: semester[0], year: parseInt(semester[1])}).exec().then(function(result){
          if(result != null){
            element.semester = result._id;
          }
          else{
            res.render("../views/error.ejs", {string:"Semester not found."});
          }
          schema.Faculty.findOne({lastName: faculty[0], firstName: faculty[1]}).exec().then(function(result){
            if(result != null){
              element.faculty = result._id;
            }
            else{
              res.render("../views/error.ejs", {string:"Faculty not found."});
            }
            schema.Course.findOne({
              department: element.department,
              number: element.number,
              section: element.section,
              faculty: element.faculty,
              semester: element.semester
              }).exec().then(function(result){
                if(result != null){
                  var grade = {};
                  if(element.grade != null){
                    grade.grade = element.grade;
                  }
                  grade.course = result._id;
                  schema.Grade.findOne(grade).exec().then(function(result){
                    if(result == null){
                      var inputGrade = new schema.Grade(util.validateModelData(grade, schema.Grade));
                      inputGrade.save().then(function(result){
                        pushStudentCourse(element.onyen, result._id).then(function(result){
                          count++;
                          if(count == data.length){
                            res.redirect("/student/uploadCourses/true");
                          }
                        }).catch(function(err){
                          res.render("../views/error.ejs", {string: "Did not push to student grades."});
                        });
                      }).catch(function(err){
                        res.render("../views/error.ejs", {string: element.onyen+" "+element.department+" "+element.number+" did not save because something was wrong with it."});
                      });
                    }
                    else{
                      pushStudentCourse(element.onyen, result._id).then(function(result){
                        count++;
                        if(count == data.length){
                          res.redirect("/student/uploadCourses/true");
                        }
                      }).catch(function(err){
                        res.render("../views/error.ejs", {string: "Did not push to student grades."});
                      });
                    }
                  });
                }
                else{
                  res.render("../views/error.ejs", {string:"Course not found."});
                }
              });
          });
        });
        
      }
    });
  });
}

function pushStudentCourse(onyen, gradeId){
  return new Promise((resolve, reject)=>{
    schema.Student.findOne({onyen: input.onyen.toLowerCase()}).exec().then(function(result){
      if(result != null){
        schema.Student.update({onyen:onyen},{$addToSet: {grades: gradeId}}).exec();
        resolve(result);
      }
      else{
        reject(result);
      }
    });
  });
}

studentController.uploadPage = function(req, res){
  var uploadSuccess = false;
  if(req.params.uploadSuccess == "true"){
    uploadSuccess = true;
  }
  res.render("../views/student/upload.ejs", {uploadSuccess: uploadSuccess});
}

studentController.upload = function(req, res){
  var form = new formidable.IncomingForm();
  form.parse(req, function(err, fields, files){
    var f = files[Object.keys(files)[0]];
    var workbook = XLSX.readFile(f.path, {cellDates:true, cellNF: false, cellText:false});
    var worksheet = workbook.Sheets[workbook.SheetNames[0]];
    var data = XLSX.utils.sheet_to_json(worksheet, {dateNF:"YYYY-MM-DD"})

    //have to use foreach because of asynchronous nature of mongoose stuff (the loop would increment i before it could save the appropriate i)
    var count = 0;
    //for(let element of data){
    data.forEach(function(element){
      //verify that all fields exist
      if(element.onyen != null && element.csid != null && element.firstName != null && element.lastName != null && element.pid != null && element.advisor != null){
        var commaReg = /\s*,\s*/;
        var facultyName = [null, null];
        //if(element.advisor != null){
          facultyName = element.advisor.split(commaReg);
          facultyName[0] = new RegExp(facultyName[0], "i");
          facultyName[1] = new RegExp(facultyName[1], "i");
        //}
        var spaceReg = /\s* \s*/;
        var semester = [null, 0];
        if(element.semesterStarted != null){
          semester = element.semesterStarted.split(spaceReg);
          semester[0] = semester[0].toUpperCase();
          semester[1] = parseInt(semester[1]);
        }
        schema.Faculty.findOne({lastName: facultyName[0], firstName: facultyName[1]}).exec().then(function(result){
          if(result != null){
            element.advisor = result._id;
          } else {
            element.advisor = null;
          }
          schema.Semester.findOne({season: semester[0], year: parseInt(semester[1])}).exec().then(function(result){
            if(result != null){
              element.semesterStarted = result._id;
            } else {
              element.semesterStarted = null;
            }
            schema.Student.findOne({onyen: element.onyen, pid: element.pid}).exec().then(function(result){
              if(result == null){
                var stud1;
                schema.Student.findOne({onyen: element.onyen}).exec().then(function(result){
                  stud1 = result;
                  schema.Student.findOne({pid: element.pid}).exec().then(function(result){
                    if(stud1 != null || result != null){
                      res.render("../views/error.ejs", {string: element.lastName+" contains an onyen or pid that already exists."});
                      return;
                    } else {
                      var inputStudent = new schema.Student(util.validateModelData(element, schema.Student));
                      inputStudent.save().then(function(result){
                        count++;
                        if(count == data.length){
                          res.redirect("/student/upload/true");
                        }
                      }).catch(function(err){
                        res.render("../views/error.ejs", {string: element.lastName+" did not save because something was wrong with it."});
                        return;
                      });
                    }
                  });
                });
              } else {
                schema.Student.update({onyen: element.onyen, pid:element.pid}, util.validateModelData(element, schema.Student)).exec().then(function(result){
                  count++;
                  if(count == data.length){
                    res.redirect("/student/upload/true");
                  }
                }).catch(
                  function(err){
                    res.render("../views/error.ejs", {string: element.lastName+" did not update because something was wrong with it."});
                    return;
                  });
              }
            });
          });
        });
      } else {
        res.render("../views/error.ejs", {string: element.lastName+" did not save because it is missing a field"});
        return;
      }
    });
  });
}


studentController.download = function(req, res){
  schema.Student.find({}, "-_id -__v").populate("advisor").populate("semesterStarted").sort({lastName:1, firstName:1}).lean().exec().then(function(result){
    var m = schema.Student.schema.obj;
    var template = {};
    for(var key in m){
      if (result[0][key] == undefined || result[0][key] == null || result[0][key] == NaN || result[0][key] == ""){
        template[key] = null;
      }
      else{
        template[key] = result[0][key];
      }
    }
    result[0] = template;
    for(var i = 0; i < result.length; i++){
      result[i].jobHistory = null;
      result[i].courseHistory = null;
      if(result[i].advisor != null){
        result[i].advisor = result[i].advisor.lastName + " " + result[i].advisor.firstName;
      }
      if(result[i].semesterStarted != null){
        result[i].semesterStarted = result[i].semesterStarted.season + " " + result[i].semesterStarted.year;
      }
    }
    var wb = XLSX.utils.book_new();
    var ws = XLSX.utils.json_to_sheet(result);
    XLSX.utils.book_append_sheet(wb, ws, "Student");
    var filePath = path.join(__dirname, "../data/studentTemp.xlsx");
    XLSX.writeFile(wb, filePath);
    res.setHeader("Content-type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    fs.createReadStream(filePath).pipe(res);
  });
}

function verifyBoolean(input){
  var m = schema.Student.schema.paths
  for(var key in m){
    if(m[key].instance === "Boolean"){
      if(input[key] == null){
        input[key] = false;
      }
    }
  }
  return input;
}

studentController.notesPage = function(req, res){
  if(req.params._id) {
    schema.Student.findOne({_id: req.params._id}).exec().then(function(result){
      if(result != null) {
        var student = result;
        schema.Note.find({student: req.params._id}).exec().then(function(result){
          res.render("../views/student/notes", {student: student, notes: result});
        });
      } else {
        res.render("../views/error.ejs", {string: "Student not found"});
      }
    });
  } else {
    res.render("../views/error.ejs", {string: "RequiredParamNotFound"});
  }
}

studentController.updateNote = function (req, res) {
  var input = req.body;
  var _id = req.params.noteId;
  //verify that the required fields are not null
  if(req.params._id != null){
    //try to find a student by unique identifiers: onyen or PID, display error page if one found

    schema.Student.findOne({_id: req.params._id}).exec().then(function (result) {
      if (result != null){
        input.student = req.params._id;
        util.allFieldsExist(input, schema.Note);

        schema.Note.findOneAndUpdate({_id: _id}, input).exec().then(function(result){
          if(result != null){
            res.redirect("/student/notes/"+req.params._id);
          }
          else{
            var inputModel = new schema.Note(input);
            inputModel.save().then(function(result){
              res.redirect("/student/notes/"+req.params._id);
            })
          }
        });
      }
    }).catch(function (err) {
      res.json({"error": err.message, "origin": "student.post"})
    });
  }
  else{
    res.render("../views/error.ejs", {string: "RequiredParamNotFound"});
  }
}

studentController.addNewNote = function (req, res) {
  var input = req.body;
  //verify that the required fields are not null
  if(req.params._id != null){
    //try to find a student by unique identifiers: onyen or PID, display error page if one found

    schema.Student.findOne({_id: req.params._id}).exec().then(function (result) {
      if (result != null){
        input.student = req.params._id;
        util.allFieldsExist(input, schema.Note);


        var inputModel = new schema.Note(input);
        inputModel.save().then(function(result){
          res.redirect("/student/notes/"+req.params._id);
        })
      }
    }).catch(function (err) {
      res.json({"error": err.message, "origin": "student.post"})
    });
  }
  else{
    res.render("../views/error.ejs", {string: "RequiredParamNotFound"});
  }
}


studentController.deleteNotes = function(req, res){
  var noteID = req.body.noteID;
  schema.Note.findOneAndRemove({_id: noteID}).exec().then(function(result){
    res.redirect("/student/notes/"+req.params._id);
  }).catch(function(error){
    //handle error if note not delete
  });
}


module.exports = studentController;
