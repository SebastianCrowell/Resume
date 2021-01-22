var schema = require('../models/schema.js');
var _ = {}

var regexSlashes = /\/*\//ig;

// Removes variables not defined in models and other undefined/null variables
/*
@param input document or object reprenting a document
@param model schema.{model} (the model reprenting the document)

@returns the document with undefined/null variables removed
*/
_.validateModelData = function (input, model) {
  var result = {};
  var m = model.schema.paths;
  for (var key in m) {
    if (input[key] !== undefined && input[key] !== null && input[key] !== NaN && input[key] !== "") {
      if (m[key].instance === "Array") {
        result[key] = input[key];
      } else if (m[key].instance === "Boolean") {
        result[key] = (input[key].toString().toLowerCase() == 'true');
      } else if (m[key].instance === "Number") {
        result[key] = parseInt(input[key]);
      } else if (m[key].instance === "ObjectID") {
        result[key] = input[key] === "" ? null : input[key];
      } else if (m[key].instance === "Date") {
        result[key] = new Date(input[key]);
      } else {
        result[key] = input[key];
      }
    }
  }
  return result;
}

/*
verifies that all fields for a given
document exist for the corresponding model

@param input document to be checked
@param model the model that represents that document

@return true or false
*/

_.allFieldsExist = function(input, model) {
  var m = model.schema.obj;
  for (var key in m){
    if(input[key] !== undefined && input[key] !== null && input[key] !== NaN && input[key] !== ""){
    }
    else{
      return false;
    }
  }
  return true; 
}

/*
  makeRegexp is used by the get function in the controllers to make it so that 
  text fields can be searched using regexp rather than exactly (eg, searching abc
  for username returns all entries in the database that contain abc rather than 
  only returning entries that are exactly abc)

  @param input document

  @return the document with text fields as regular expressions
*/

_.makeRegexp = function(input){
  for(var key in input){
    if(input[key].constructor == Array){
      if(input[key][0] == "string"){
        for(var i = 0; i < input[key].length; i++){
          input[key][i] = new RegExp(input[key][i], "i");
        }
      }
    }
    else{
      //only create regexp if the field is text
      if(typeof input[key] == "string"){
        input[key] = new RegExp(input[key], "i");
      }
    }
  }
  return input;
}

//used just once to initialize all possible semesters
_.initializeAllSemesters = function(){
  schema.Semester.find({}).deleteMany().exec();
  var seasons = schema.Semester.schema.path("season").enumValues;
  for(var i = 2018; i < 2040; i++){
    for(var j = 0; j < seasons.length; j++){
      var semester = new schema.Semester({year: i, season: seasons[j]});
      semester.save().then(function(result){}).catch(function(err){});
    }
  }
}

/*
  checkAdvisor is a promise that resolves as true if the user is an
  advisor of the passed in student, and resolves false otherwise.
*/
_.checkAdvisor = function(facultyID, studentID){
  return new Promise((resolve, reject)=>{
    schema.Student.findOne({_id: studentID}).exec().then(function(result){
      if(result != null){
        if(result.advisor != null){
          schema.Faculty.findOne({_id: result.advisor}).exec().then(function(result){
            if(result.pid == facultyID){
              resolve(true);
            } else {
              resolve(false);
            }
          });
        } else {
          resolve(false);
        }
      } else {
        resolve(false);
      }
    });
  });
}

/*
  checkAdvisorAdmin is a promise that resolves as true if the user is
  an admin, or if the user is an advisor of the passed in studentID, and
  resolves as false otherwise.
*/
_.checkAdvisorAdmin = function(facultyID, studentID){
  return new Promise((resolve, reject)=>{
    schema.Faculty.findOne({pid: facultyID}).exec().then(function(result){
      if(result.admin == true){
        resolve(true);
      }
      else{
        schema.Student.findOne({_id: studentID}).exec().then(function(result){
          if(result != null){
            if(result.advisor != null){
              schema.Faculty.findOne({_id: result.advisor}).exec().then(function(result){
                if(result.pid == facultyID){
                  resolve(true);
                } else {
                  resolve(false);
                }
              });
            } else {
              resolve(false);
            }
          } else {
            resolve(false);
          }
        });
      }
    });
  });
}

_.listObjectToString = function (input) {
  var result = "Search: ";
  for (var key in input) {
    result = result + key + " = ";
    result = result + input[key] + "; ";
  }
  return result;
}

_.checkFormCompletion = (studentID) => {
    return new Promise((resolve, reject) => {
        let completedForms = [];
        schema.CS01BSMS.findOne({ student: studentID }).exec().then((result) => {
            if (result != null && result.name != null) {
                completedForms.push("CS01BSMS");
            }
            for (var i = 1; i <= 13; i++) {
                let currentForm = "";
                if (i < 10) {
                    currentForm = "0" + i;
                }
                else {
                    currentForm = i;
                }
                if (i != 10) {
                    checkOneForm(currentForm, completedForms, studentID).then((result) => {
                        if ("13" == result) {
                            resolve(completedForms);
                        }
                    }).catch((error) => {
                        reject(error);
                    })
                }
            }
        })
        
    });
}

checkOneForm = (currentForm, completedForms, studentID) => {
    return new Promise((resolve, reject) => {
        console.log("CS" + currentForm);
        schema["CS" + currentForm].findOne({ student: studentID }).exec().then((result) => {
            if (result != null && result.name != null) {
                completedForms.push("CS" + currentForm);
            }
            resolve(currentForm);
        }).catch((error) => {
            reject(error);
        })
    });
}

module.exports = _;
