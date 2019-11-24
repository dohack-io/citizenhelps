// Source: https://github.com/nuxt-community/express-template/

const express = require('express');
const bodyParser = require('body-parser');
// Create express instnace
const app = express();


//Increasing size for transactions
app.use(bodyParser.json({type: '*/*', limit: '50mb', extended: true}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));

const axios = require('axios');

// Require API routes
const {Router} = require('express');
const router = Router();

// Mock Users
// const Model = require('./models');


// router.get('/hackathons', function (req, res) {
//   console.log(req.body);
//   Model.find({}, (err, users) => {
//     if (err) {
//       res.status(404).send(err)
//     } else {
//       res.status(200).send(users)
//     }
//   })
// });

// post

var request = require("request");

var options = {
  method: 'GET',
  url: 'http://134.209.232.135:3001/api/reports',
  headers:
      {
        'cache-control': 'no-cache',
        Connection: 'keep-alive',
        'Accept-Encoding': 'gzip, deflate',
        Host: '134.209.232.135:3001',
        'Postman-Token': 'cb577849-0ca2-4e26-9f79-a02c20cf2f1a,337469a2-60c6-4323-af8b-e4edb791a4fa',
        'Cache-Control': 'no-cache',
        Accept: '*/*',
        'User-Agent': 'PostmanRuntime/7.17.1'
      }
};
var google = {
  "async": true,
  "crossDomain": true,
  "url": "https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=AIzaSyBQj_jnetIEYnMRVVkAnefZjJ4Xf8mhViw",
  "method": "POST",
  "headers": {
    "Content-Type": "application/json",
    "User-Agent": "PostmanRuntime/7.19.0",
    "Accept": "*/*",
    "Cache-Control": "no-cache",
    "Host": "commentanalyzer.googleapis.com",
    "Accept-Encoding": "gzip, deflate",
    "Content-Length": "402",
    "Connection": "keep-alive",
    "cache-control": "no-cache"
  },
  "data": "{\"comment\": \r\n\t{\"text\": \"Wir brauchen die Flüchtlinge einladen. Und ich denke du sollst sterben, du Arsch.\"},\r\n      \"languages\": [\"de\"],\r\n      \"requestedAttributes\": {\r\n      \t\"TOXICITY_EXPERIMENTAL\":{},\r\n      \t\"SEVERE_TOXICITY_EXPERIMENTAL\":{},\r\n      \t\"IDENTITY_ATTACK_EXPERIMENTAL\":{},\r\n      \t\"INSULT_EXPERIMENTAL\":{},\r\n  \t\t\"THREAT_EXPERIMENTAL\":{},\r\n  \t\t\"PROFANITY_EXPERIMENTAL\":{},\r\n    } \r\n}"
};


let temp_payload = {
  "comment":
      {"text": "Wir brauchen die Flüchtlinge einladen. Und ich denke du sollst sterben, du Arsch."},
  "languages": ["de"],
  "requestedAttributes": {
    "TOXICITY_EXPERIMENTAL": {},
    "SEVERE_TOXICITY_EXPERIMENTAL": {},
    "IDENTITY_ATTACK_EXPERIMENTAL": {},
    "INSULT_EXPERIMENTAL": {},
    "THREAT_EXPERIMENTAL": {},
    "PROFANITY_EXPERIMENTAL": {},
  }
};

axios.defaults.method = 'post';
var api_aindex = axios.create({});

router.post('/comment', function (req, res) {

  console.log('Posted Body:', req.body);
  let lang = req.body.lang;
  let text = req.body.text;
  let payload = temp_payload;
  payload.comment.text = text;
  payload.languages = [lang];
  let test_call = 0;
  let r = "";
  axios
      .post(
          google.url,
          payload,
          {
            headers: google.headers
          })
      .then(response => {
        r = response;
        console.log("Axios:", response);
        res.status(200).send({
          body: r.data,
          test_call: test_call
        });
      })
      .catch(error => {
        // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;
        console.log('Error', errorCode, errorMessage);
        res.status(400).send({
          body: errorMessage,
          test_call: test_call
        });
        // ...
      });
  // request(options, function (error, response, body) {
  //   if (error) {
  //     test_call = error;
  //   } else {
  //     test_call = body;
  //   }
  //   console.log(test_call);
  // });


});


// Import API Routes
app.use(router);

// Export the server middleware
module.exports = {
  path: '/api',
  handler: app
};