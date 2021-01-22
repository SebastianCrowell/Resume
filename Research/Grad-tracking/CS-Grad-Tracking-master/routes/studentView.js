// /routes/studentView.js
var express = require("express");
var router = express.Router();

var student = require("../controllers/StudentViewController.js");

router.use((req, res, next)=>{
    if(req.session.accessLevel == 1){
        next();
    }
    else{
        res.render("../views/error.ejs", {string: "You are not a student or have incorrectly authenticated."})
    }
})

router.get("/", student.get);

router.get("/courses", student.courses);

router.get("/forms", student.forms);

router.get("/forms/:title/:uploadSuccess", student.viewForm);

router.get("/jobs", student.jobs);

router.get("/downloadCourses", student.downloadCourses);

// router.post("/post", student.post);
router.post("/put", student.put);

router.post("/forms/update/:title", student.updateForm);

// router.post("/delete/:_id", student.delete);

module.exports = router;