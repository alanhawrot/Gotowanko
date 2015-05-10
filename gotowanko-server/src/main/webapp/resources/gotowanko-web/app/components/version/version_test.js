'use strict';

describe('gotowankoApp.version module', function() {
  beforeEach(module('gotowankoApp.version'));

  describe('version service', function() {
    it('should return current version', inject(function(version) {
      expect(version).toEqual('0.1');
    }));
  });
});
