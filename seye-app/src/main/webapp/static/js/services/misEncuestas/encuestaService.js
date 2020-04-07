angular.module(appTeclo).service("encuestaService", function($http, config) {
	
	const END_POINT="/encuesta";
	
	this.getEncuestaUsuario = function (idEncuesta, idUsuarioEncuesta) {
		return $http.get(config.baseUrl + END_POINT + "/detalle", 
		{params:{"idEncuesta": idEncuesta,"idUsuarioEncuesta": idUsuarioEncuesta}});
	};

	this.saveTiempo = function (idIntento, nuConsumidos) {
		return $http.get(config.baseUrl + END_POINT + "/pausar", 
		{params:{"idIntento": idIntento,"nuConsumidos": nuConsumidos}});
	};


	this.getNumPreguntasPorSeccion = function (cdParametro) {
		return $http.get(config.baseUrl +"/catalogo/parametroCd", 
		{params:{"cdParametro": cdParametro}});
	};
	
	this.saveRespuestaEncuesta=function(listRespuestasVO){
		return $http.post(config.baseUrl + END_POINT+"/respuestas",listRespuestasVO);
	};


	this.finalizaEncuesta=function(usuarioEncuestaVO){
		return $http.put(config.baseUrl + END_POINT+"/finalizar",usuarioEncuestaVO);
	};

	this.cargarEncuesta = function (idEncuesta) {
			return $http.get(config.baseUrl + END_POINT + "/cargar", 
			{params:{"idEncuesta": idEncuesta}});
			};

	this.validaTiempoIntento = function (idUsuarioEncuesta) {
				return $http.get(config.baseUrl + END_POINT + "/validaTiempoIntento", 
				{params:{"idUsuarioEncuesta": idUsuarioEncuesta}});
				};
});