const Express = require("express");
const BasicAuth = require("express-basic-auth");
const BodyParser = require("body-parser");

var app = Express();

app.use(BodyParser.json());
app.use(BodyParser.urlencoded({extended: true}));
//app.use(BasicAuth({users: { 'couchbase': 'password'}}));

app.post("/notify", (req, res) => {
	console.log(req.hostname);
	var id = req.body.id;
	console.log(req.body);
	res.send("OK");
});

app.post("/notify-auth", (req, res) => {
	console.log(req.hostname);
	var id = req.body.id;
	console.log(req.body);
	res.send("OK");
});

var server = app.listen(3000, () => {
	console.log("listening...");
});
