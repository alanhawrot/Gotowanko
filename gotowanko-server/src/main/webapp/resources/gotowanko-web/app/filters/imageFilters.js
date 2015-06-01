var m = angular.module('imageFilters', []);

m.filter('defaultImageFilter', function() {
    return function(imgPath) {
        if(imgPath)
            return imgPath;

        return "/images/recipe/noImage.png"
    }
});