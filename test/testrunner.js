var expect = require('chai').expect;
var atemiController = require('../api/controllers/atemiController.js');

class AtemiCallback {
  constructor() {
    this.result = "failed";
    AtemiCallback.prototype.send = function (result) {
      this.result = result;
    };
  }
}

describe('atemiController.get()', function () {
  it('should return a random atemi', function () {
    var atemiCallback = new AtemiCallback();
    atemiController.get(null, atemiCallback);
    console.log(atemiCallback.result);
    expect(atemiCallback.result.match(/uke attacks using.*/).length).to.be.equal(1);
  });
});