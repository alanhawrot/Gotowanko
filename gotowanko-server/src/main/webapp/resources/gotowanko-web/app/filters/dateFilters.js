var m = angular.module('dateFilters', []);

m.filter('msToDateTime', function() {
    return function(millseconds) {
        var seconds = Math.floor(millseconds / 1000);
        var days = Math.floor(seconds / 86400);
        var hours = Math.floor((seconds % 86400) / 3600);
        var minutes = Math.floor(((seconds % 86400) % 3600) / 60);
        var timeString = '';
        if(days > 0) timeString += (days > 1) ? (days + " days ") : (days + " day ");
        if(hours > 0) timeString += (hours > 1) ? (hours + " hours ") : (hours + " hour ");
        if(minutes >= 0) timeString += (minutes != 1) ? (minutes + " minutes ") : (minutes + " minute ");
        return timeString;
    }
});

m.filter('msToDateTimeWithSeconds', function() {
    return function(millseconds) {
        var totalSeconds = Math.floor(millseconds / 1000);
        var days = Math.floor(totalSeconds / 86400);
        var hours = Math.floor((totalSeconds % 86400) / 3600);
        var minutes = Math.floor(((totalSeconds % 86400) % 3600) / 60);
        var seconds = Math.floor(((totalSeconds % 86400) % 3600) % 60);
        var timeString = '';
        if(days > 0) timeString += (days > 1) ? (days + " days ") : (days + " day ");
        if(hours > 0) timeString += (hours > 1) ? (hours + " hours ") : (hours + " hour ");
        if(minutes > 0) timeString += (minutes != 1) ? (minutes + " minutes ") : (minutes + " minute ");
        if(seconds >= 0) timeString += (seconds != 1) ? (seconds + " seconds ") : (seconds + " second ");
        return timeString;
    }
});