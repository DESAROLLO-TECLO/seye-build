angular.module(appTeclo).config(function($routeProvider, $locationProvider) {

    $routeProvider.when("/", {
        templateUrl: "login.html",
        controller: "loginController"
    });

    $routeProvider.when("/login", {
        templateUrl: "login.html",
        controller: "loginController"
    });

    $routeProvider.when("/error", {
        templateUrl: "views/error.html",
    });

    $routeProvider.when("/index", {
        templateUrl: "views/index.html",
    });

    $routeProvider.when("/accesoNegado", {
        templateUrl: "views/accesoNegado.html",
    });

    $routeProvider.otherwise({ redirectTo: "/index" });

    //	Encuestado
    $routeProvider.when("/encuestas", {
        templateUrl: "views/misEncuestas/encuestas.html",
        controller: "encuestasController"
    });
    $routeProvider.when("/encuesta/:idEncuesta/:idUsuarioEncuesta/:idUsuEncuIntento", {
        templateUrl: "views/misEncuestas/encuesta.html",
        controller: "encuestaController",
        resolve: {
            idEncuesta: function($route) { return $route.current.params.idEncuesta; },
            idUsuarioEncuesta: function($route) { return $route.current.params.idUsuarioEncuesta; },
            idUsuEncuIntento: function($route) { return $route.current.params.idUsuEncuIntento; }
        }
    });

    /*___________________________________________________________
	// Perfiles
	$routeProvider.when("/perfiles", {
		templateUrl : "views/administracion/perfiles.html",
		controller: "perfilesController"
    });		
	________** INICIO -> ADMINISTRACIÓN CONTROLLERS ** ________*/
    $routeProvider.when("/administracionModificaClave", {
        templateUrl: "views/administracion/administracionModificaClave.html",
        controller: "administracionModificaClaveController"
    });

    //	Configurar Aplicación
    $routeProvider.when("/configuracion", {
        templateUrl: "views/administracion/configuracionApp.html",
        controller: "configuracionAppController"
    });

    //	Componentes Web
    $routeProvider.when("/componentesWeb", {
        templateUrl: "views/administracion/resources/pluginsWeb.html",
        controller: "pluginsWebController"
    });

    //	Usuarios
    $routeProvider.when("/users", {
        templateUrl: "views/administracion/users.html",
        controller: "usersController",
        resolve: {
            predata: async function(userService) {

                let response = new Object();

                try {
                    let typeSearch = await userService.getTypeSearch();
                    let searchForAll = await userService.getTypeSearchForAll();
                    let profiles = await userService.getProfiles();

                    response.typeSearch = typeSearch.data;
                    response.searchForAll = searchForAll.data;
                    response.profiles = profiles.data;

                    return response;
                } catch (e) {
                    return console.log("Ocurrió un error");
                }
            }
        }
    });
    /*___________________________________________________________
    ________** FIN -> ADMINISTRACIÓN CONTROLLERS ** ___________*/


    /*___________________________________________________________
    ________** INICIO -> MODULO ENCUESTAS ** ________*/

    $routeProvider.when("/evaluacionesEvaluador", {
        templateUrl: "views/evaluaciones/evaluacionesEvaluador.html",
        controller: "evaluacionesEvaluadorController"
    });

    $routeProvider.when("/evaluacionEvaluado/:idEvaluacion", {
        templateUrl: "views/evaluaciones/evaluacionesEvaluados.html",
        controller: "evaluacionesEvaluadosController",
        resolve: {
            idEvaluacion: function($route) { return $route.current.params.idEvaluacion; }
        }
    });


    // RUTA PARA VISUALIZAR LA CONSTRASEÑA ACTUAL
    $routeProvider.when("/clave", {
        templateUrl: "views/administracion/clave/clave.html",
        controller: "claveController"
    });


    /*___________________________________________________________
    ________** FIN -> ADMINISTRACIÓN CONTROLLERS ** ___________*/
});