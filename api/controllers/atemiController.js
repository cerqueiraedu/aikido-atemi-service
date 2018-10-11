'use strict';
const getRandomItem = require('../helpers/getRandomItem.js');

exports.get = function(req, res) {
    let atemis = ["tsuki", "shomen uchi", "yokomen uchi"]

    let uke = "uke attacks using " + getRandomItem.get(atemis);
    res.send(uke);
};