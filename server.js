require('newrelic');
'use strict';

const express = require('express');
const bodyParser = require('body-parser');

const atemiRoutes = require('./api/routes/atemiRoutes'); 

var atemiService = express();

atemiService.use(bodyParser.json());

atemiRoutes(atemiService);  

atemiService.listen(8080, function () {
  console.log('Atemi API listening on port 8080!');
});