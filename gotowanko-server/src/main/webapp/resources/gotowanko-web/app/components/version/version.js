'use strict';

angular.module('gotowankoApp.version', [
  'gotowankoApp.version.interpolate-filter',
  'gotowankoApp.version.version-directive'
])

.value('version', '0.1');
