angular.module(appTeclo).service("claveService", function($http, config) {

    this.claves = function() {
        return $http.get(config.baseUrl + "/clave");
    };

    this.clavesSugerido = function() {
        return $http.get(config.baseUrl + "/clave/sugerido");
    };

    this.nueva = function(data) {
        return $http.post(config.baseUrl + "/clave", data);
    };

    this.st = function() {
        return $http.get(config.baseUrl + "/clave/st");
    };

    this.activar = function(vo) {
        return $http.post(config.baseUrl + "/clave/activar", vo);
    };
});