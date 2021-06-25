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



/*
docker run -d --name db -p 8091-8094:8091-8094 -p 11210:11210 couchbase
admin 123456

function OnUpdate(doc, meta) {
    var request = {
        path: '/notify',
        body: {
            id : meta.id,
            value: doc
        }
    };
    var response = curl("POST", notifyApi, request);
    if(response.status != 200){
        log('request failed', response);
    }
}
function OnDelete(meta, options) {
}

UPDATE `beer-sample`
SET abv = 14
WHERE category = "Irish Ale"

INSERT INTO `beer-sample` (KEY, VALUE)
VALUES ("key3", {
  "abv": 5.8,
  "brewery_id": "21st_amendment_brewery_cafe",
  "category": "North American Ale",
  "description": "Deep amber color. Subtle hop floral nose intertwined with sweet crystal malt aromas. Rich malt flavors supporting a slight bitterness finish.",
  "ibu": 0,
  "name": "North Star Red",
  "srm": 0,
  "style": "American-Style Amber/Red Ale",
  "type": "beer",
  "upc": 0,
  "updated": "2010-07-22 20:00:20"
})
*/