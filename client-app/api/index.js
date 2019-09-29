// Source: https://github.com/nuxt-community/express-template/

const express = require('express');
const bodyParser = require('body-parser');
// Create express instnace
const app = express();


//Increasing size for transactions
app.use(bodyParser.json({type: '*/*',limit: '50mb', extended: true}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));



// Require API routes
const {Router} = require('express');
const router = Router();

// Mock Users
// const Model = require('./models');


// MONGO DB
// const mongoose = require('mongoose');
// const db = uri + '/test';
//
// console.log('Connected to ' + db);
// mongoose.connect(db, (err, res) => {
//   if (err) {
//     console.log('Failed to connected to ' + db)
//   } else {
//     console.log('Connected to ' + db)
//   }
// });


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


router.post('/hackathons', function (req, res) {
  console.log('Posted Body:', req.body);
  let test_call = 0;

  request(options, function (error, response, body) {
    if (error) {
      test_call = error;
    } else {
      test_call = body;

    }
    console.log(test_call);
  });
  res.status(200).send({
    body: req.body,
    test_call: test_call

  });

});


// Import API Routes
app.use(router);

// Export the server middleware
module.exports = {
  path: '/api',
  handler: app
};