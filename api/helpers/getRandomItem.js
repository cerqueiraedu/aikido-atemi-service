'use strict';

exports.get = function (col) {
    var min = Math.ceil(0);
    var max = Math.floor(col.length);
    
    return  col[Math.floor(Math.random() * (max - 0)) + min];
}