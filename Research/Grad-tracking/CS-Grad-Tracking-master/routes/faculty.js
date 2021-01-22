// /routes/faculty.js
var express = require("express");
var router = express.Router();
var util = require("../controllers/util");

var faculty = require("../controllers/FacultyController.js");

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

router.get("/", faculty.get);

router.get("/create", faculty.create);

router.get("/edit/:_id", faculty.edit);

router.get("/download", faculty.download);

router.get("/upload/:uploadSuccess", faculty.uploadPage);

router.post("/post", faculty.post);

router.post("/put", faculty.put);

router.post("/delete/:_id", faculty.delete);

router.post("/upload", faculty.upload);

module.exports = router;