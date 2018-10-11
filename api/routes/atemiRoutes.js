'use strict';

module.exports = function(app) {
  const atemiController = require('../controllers/atemiController.js');
  app.route('/')
    .get(atemiController.get);  
};