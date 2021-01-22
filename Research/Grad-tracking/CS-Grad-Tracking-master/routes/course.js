var express = require("express");
var router = express.Router();
var util = require("../controllers/util");

var course = require("../controllers/CourseController");

router.use(function(req, res, next){
	if(req.session.accessLevel == 3){
		res.locals.admin = true;
		next();
	}
	else{
		res.locals.admin = false;
		res.render("../views/error.ejs", {string: "Not admin"});
	}
});


router.get("/", course.get);

router.get("/edit/:_id", course.edit);

router.get("/create", course.create);

router.get("/upload/:uploadSuccess", course.uploadPage);

router.get("/uploadInfo/:uploadSuccess", course.uploadInfoPage);

router.get("/download", course.download);

router.post("/post", course.post);

router.post("/put", course.put);

router.post("/delete/:_id", course.delete);

router.post("/upload", course.upload);

router.post("/uploadInfo", course.uploadInfo);

module.exports = router;
