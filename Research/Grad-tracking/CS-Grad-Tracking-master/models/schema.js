/* global reject */
var mongoose = require("mongoose");
var schema = {};

// Faculty
var facultySchema = mongoose.Schema({
  onyen: String,
  csid: String,
  email: String,
  firstName: String,
  lastName: String,
  pid: Number,
  sectionNumber: Number,
  active: Boolean,
  admin: Boolean
});

// Students
var studentSchema = mongoose.Schema({
  onyen: String,
  csid: String,
  email: String,
  firstName: String,
  lastName: String,
  pronouns: {
  	type: String,
  	enum: ["she, her", "he, him", "they, them", "ze, zie", "hir, hirs", "xe, xem", "pe, per", "e/ey, em", "(f)ae, (f)aer", "None"],
  	default: "None"
  },
  pid: Number,
  status: {
    type: String,
    enum: ["Active", "Inactive", "Leave", "Graduated", "Ineligible"],
    default: "Active"
  },
  alternativeName: String,
  gender: {
    type: String,
    enum: ["MALE", "FEMALE", "OTHER"],
    default: "OTHER"
  },
  ethnicity: {
    type: String,
    enum: ["ASIAN", "BLACK", "HISPANIC", "PACIFIC", "WHITE", "OTHER"],
    default: "OTHER"
  },
  residency: {
    type: String,
    enum: ["YES", "NO", "APPLIED"],
    default: "NO"
  },
  enteringStatus: String,
  researchArea: String,
  leaveExtension: String,
  intendedDegree: {
    type: String,
    enum: ["MASTERS", "PHD", "BOTH"],
    default: "MASTERS"
  },
  hoursCompleted: Number,
  citizenship: Boolean,
  fundingEligibility: {
	  type: String,
	  enum: ["NOT GUARANTEED", "GUARANTEED", "PROBATION"],
	  default: "NOT GUARANTEED"
  },
  semestersOnLeave: Number,
  backgroundApproved: String,
  mastersAwarded: String,
  prpPassed: String,
  technicalWritingApproved: String,
  backgroundPrepWorksheetApproved: String,
  programOfStudyApproved: String,
  researchPlanningMeeting: String,
  programProductRequirement: String,
  committeeCompApproved: String,
  phdProposalApproved: String,
  phdAwarded: String,
  oralExamPassed: String,
  dissertationDefencePassed: String,
  dissertationSubmitted: String,
  jobHistory: [{type: mongoose.Schema.Types.ObjectId, ref: "Job"}],
  semesterStarted: { type: mongoose.Schema.Types.ObjectId, ref: "Semester" },
  advisor: { type: mongoose.Schema.Types.ObjectId, ref: "Faculty" },
  grades: [{type:mongoose.Schema.Types.ObjectId, ref: "Grade"}]
});

// Semesters
var semesterSchema = mongoose.Schema({
  year: Number,
  season: {
    type: String,
    enum: ["FA", "SP", "S1", "S2"]
  }
});

// Courses
var courseSchema = mongoose.Schema({
  department: String,
  number: String,
  univNumber: Number,
  name: String,
  category: {
    type: String,
    enum: ["NA", "Theory", "Systems", "Appls"]
  },
  topic: String,
  hours: Number,
  section: String,
  faculty: {type: mongoose.Schema.Types.ObjectId, ref: "Faculty"},
  semester: {type: mongoose.Schema.Types.ObjectId, ref: "Semester"}
});

var courseInfoSchema = mongoose.Schema({
  number: String,
  name: String,
  hours: Number
})

// Jobs
var jobSchema = mongoose.Schema({
  position: {
    type: String,
    enum: ["RA", "TA", "OTHER"]
  },
  supervisor: {type: mongoose.Schema.Types.ObjectId, ref: "Faculty"},
  semester: {type: mongoose.Schema.Types.ObjectId, ref: "Semester"},
  course: {type: mongoose.Schema.Types.ObjectId, ref: "Course"},
  description: String,
  hours: Number,
  fundingSource: {type: mongoose.Schema.Types.ObjectId, ref: "Grant"}
});

// Grades
var gradeSchema = mongoose.Schema({
  grade: {
    type: String,
    enum: ["H+", "H", "H-", "P+", "P", "P-", "L+", "L", "L-", "NA"],
    default: "NA"
  },
  course: {type: mongoose.Schema.Types.ObjectId, ref: "Course"}
});

var semesterReferenceSchema = mongoose.Schema({
   name: String,
   semester: {type: mongoose.Schema.Types.ObjectId, ref:"Semester"}
 });

var grantSchema = mongoose.Schema({
  name: String
})

var noteSchema = mongoose.Schema({
  student: {type: mongoose.Schema.Types.ObjectId, ref:"Student"},
  title: String,
  date: String,
  note: String
})

//form schemas
var CS01Schema = mongoose.Schema({
  student: {type: mongoose.Schema.Types.ObjectId, ref:"Student"},
  name: String, pid: Number,
  comp283Covered: String, comp283Date: String,
  comp410Covered: String, comp410Date: String,
  comp411Covered: String, comp411Date: String,
  comp455Covered: String, comp455Date: String,
  comp521Covered: String, comp521Date: String,
  comp520Covered: String, comp520Date: String,
  comp530Covered: String, comp530Date: String,
  comp524Covered: String, comp524Date: String,
  comp541Covered: String, comp541Date: String,
  comp550Covered: String, comp550Date: String,
  math233Covered: String, math233Date: String,
  math381Covered: String, math381Date: String,
  math547Covered: String, math547Date: String,
  math661Covered: String, math661Date: String,
  stat435Covered: String, stat435Date: String,
  studentSignature: String, studentDateSigned: String,
  advisorSignature: String, advisorDateSigned: String
});

var CS01BSMSSchema = mongoose.Schema({
  student: {type: mongoose.Schema.Types.ObjectId, ref:"Student"},
  name: String, pid: Number,
  comp521Covered: String, comp521Date: String,
  comp520Covered: String, comp520Date: String,
  comp530Covered: String, comp530Date: String,
  comp524Covered: String, comp524Date: String,
  comp541Covered: String, comp541Date: String,
  math661Covered: String, math661Date: String,
  studentSignature: String, studentDateSigned: String,
  advisorSignature: String, advisorDateSigned: String
});

var CS02Schema = mongoose.Schema({
  student: {type: mongoose.Schema.Types.ObjectId, ref:"Student"},
  name: String, pid: Number, dateSubmitted: String,
  courseNumber: String,
  basisWaiver: String,
  advisorSignature: String, advisorDateSigned: String,
  instructorSignature: String, instructorDateSigned: String
})

var CS03Schema = mongoose.Schema({
  student: {type: mongoose.Schema.Types.ObjectId, ref:"Student"},
  name: String, pid: Number, dateSubmitted: String,
  DR: [String],
  university: [String],
  dept: [String],
  course: [String],
  hours: [Number],
  semester: [String],
  title: [String],
  backgroundPrep: Boolean,
  programProduct: Boolean,
  comprehensivePaper: Boolean,
  thesis: Boolean,
  outsideReview: Boolean,
  comprehensiveExam: String,
  studentSignature: String,
  advisorSignature: String,
  approved: String,
  approvalReason: String,
  directorSignature: String, directorDateSigned: String
})

var CS04Schema = mongoose.Schema({
  student: {type: mongoose.Schema.Types.ObjectId, ref:"Student"},
  name: String, pid: Number, dateSubmitted: String,
  projectDescription: String,
  docProprietary: Boolean,
  studentSignature: String, studentDateSigned: String,
  chairmanSignature: String, chairmanDateSigned: String,
  approved: Boolean
})

var CS05Schema = mongoose.Schema({
  student: {type: mongoose.Schema.Types.ObjectId, ref:"Student"},
  name: String, pid: Number, dateSubmitted: String,
  oralComprehensiveExam: Boolean,
  thesis: Boolean,
  nominees: [String],
  nomineeDepartments: [String],
  nomineeStatuses: [String],
  thesisadvisor: String,
  committeeChairman: String,
  directorSignature: String, directorDateSigned: String
}) 

var CS06Schema = mongoose.Schema({
  student: {type: mongoose.Schema.Types.ObjectId, ref:"Student"},
  name: String, pid: Number, dateSubmitted: String, dateEntered: String,
  dissTitle: String,
  comp915: Boolean,
  breadthCourseCategory: [String],
  breadthCourseInfo: [String],
  breadthCourseDate: [String],
  breadthCourseGrade: [String],
  concentrationCourseInfo: [String],
  concentrationCourseDate: [String],
  concentrationCourseHours: [Number],
  otherCourseInfo: [String],
  otherCourseHours: [Number],
  note: String,
  otherCourses: String,
  minor: String,
  backgroundPrepWorkSheet: Boolean,
  programProductRequirement: Boolean,
  PHDWrittenExam: Boolean,
  PHDOralExam: Boolean,
  committee: [String],
  advisor: String, chairman: String,
  chairSignature: String,
  approved: String,
  reasonApproved: String,
  directorSignature: String
})

var CS07Schema = mongoose.Schema({
  student: {type: mongoose.Schema.Types.ObjectId, ref:"Student"},
  name: String, pid: Number,
  comments: String,
  chairmanSignature: String, chairmanDateSigned: String
});

var CS08Schema = mongoose.Schema({
  student: {type: mongoose.Schema.Types.ObjectId, ref:"Student"},
  name: String, pid: Number,
  semester: String, year: Number,
  title: String,
  primaryReader: String, primaryDate: String,
  secondaryReader: String, secondaryDate: String,
  primarySignature: String, primarySignedDate: String,
  secondarySignature: String, secondarySignedDate: String
})

var CS09Schema = mongoose.Schema({
  student: {type: mongoose.Schema.Types.ObjectId, ref:"Student"},
  name: String, pid: Number,
  prpTitle: String,
  researchAdvisor: String,
  peerReviewed: String,
  authors: String,
  paperAccepted: String, paperNotifyDate: String,
  reviewsAvailable: String,
  researchResponsible: String,
  present: String,
  advisorSignature: String, advisorDateSigned: String,
  committeeSignature: [String],
  committeeDateSigned: [String],
  presentationDate: String,
  conceptIntegration: Number,
  creativity: Number,
  clarity: Number,
  abstractionFormality: Number,
  organization: Number,
  writing: Number,
  presentation: Number,
  answeringQuestion: Number,
  overallScore: Number,
  feedback: String
})

var CS11Schema = mongoose.Schema({
  student: {type: mongoose.Schema.Types.ObjectId, ref:"Student"},
  name: String, pid: Number,
  fullResponsibility: String,
  partialResponsibility: String,
  semester: String, year: Number,
  supervisor: String, supervisorSignature: String, supervisorDateSigned: String,
  other: String,
  approved: String,
  directorSignature: String, directorDateSigned: String
})

var CS12Schema = mongoose.Schema({
  student: {type: mongoose.Schema.Types.ObjectId, ref:"Student"},
  name: String, pid: Number, email: String, dateMet: String,
  committeeSignature: [String]
});

var CS13Schema = mongoose.Schema({
  student: {type: mongoose.Schema.Types.ObjectId, ref:"Student"},
  name: String, pid: Number, email: String, dateMet: String,
  comp523: Boolean,
  comp523Signature: String,
  comp523Name: String,
  hadJob: Boolean,
  jobInfo: String,
  advisorName: String,
  advisorSignature: String,
  alternative: Boolean,
  product: String,
  client: String,
  position: String,
  altSignature1: String,
  altSignature2: String,
  altPrint1: String,
  altPrint2: String
});

schema.Faculty = mongoose.model("Faculty", facultySchema);
schema.Student = mongoose.model("Student", studentSchema);
schema.Semester = mongoose.model("Semester", semesterSchema);
schema.Course = mongoose.model("Course", courseSchema);
schema.CourseInfo = mongoose.model("CourseInfo", courseInfoSchema);
schema.Job = mongoose.model("Job", jobSchema);
schema.Grade = mongoose.model("Grade", gradeSchema);
schema.Grant = mongoose.model("Grant", grantSchema);
schema.SemesterReference = mongoose.model("SemesterReference", semesterReferenceSchema);
schema.Note = mongoose.model("Note", noteSchema);
schema.CS01 = mongoose.model("CS01", CS01Schema);
schema.CS01BSMS = mongoose.model("CS01BSMS", CS01BSMSSchema);
schema.CS02 = mongoose.model("CS02", CS02Schema);
schema.CS03 = mongoose.model("CS03", CS03Schema);
schema.CS04 = mongoose.model("CS04", CS04Schema);
schema.CS05 = mongoose.model("CS05", CS05Schema);
schema.CS06 = mongoose.model("CS06", CS06Schema);
schema.CS07 = mongoose.model("CS07", CS07Schema);
schema.CS08 = mongoose.model("CS08", CS08Schema);
schema.CS09 = mongoose.model("CS09", CS09Schema);
schema.CS11 = mongoose.model("CS11", CS11Schema);
schema.CS12 = mongoose.model("CS12", CS12Schema);
schema.CS13 = mongoose.model("CS13", CS13Schema);

module.exports = schema;
