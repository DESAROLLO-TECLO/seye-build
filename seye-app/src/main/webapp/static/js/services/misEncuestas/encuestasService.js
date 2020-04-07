angular.module(appTeclo).service("encuestasService", function($http, config, $timeout) {
	
	const END_POINT="/encuesta";
	
	this.getEncuestas = function (){
		return $http.get(config.baseUrl + END_POINT + "/usuario");
	};
	
	this.getDetallePDF = function (idUsuarioEncuesta, idUsuEncuIntento){
		var idUsuario = 0;
		return $http({
            method: 'GET',
            url: config.baseUrl + "/reporte/encuesta",
            params: { idUsuarioEncuesta: idUsuarioEncuesta, idIntento: idUsuEncuIntento, idUsuario: idUsuario},
            dataType: "json",
            header: {
                "Content-type": "application/json",
                "Accept": "application/pdf"
            },
            responseType: 'arraybuffer'
        });
	};
	
	this.detalleEncuesta = function (idUsuEncuIntento){
		return $http.get(config.baseUrl + END_POINT + "/detalle/intento",{params:{"idIntento":idUsuEncuIntento}});
	};
	
	this.descargarReporteExcel = function (peticionReporteVO){
		return $http({
            method: 'POST',
            url: config.baseUrl + "/reporte/usuario",
            data: peticionReporteVO,
            dataType: "json",
            header: {
                "Content-type": "application/json",
                "Accept": "vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            },
            responseType: 'arraybuffer'
        });
	};
	
	this.updateUsuarioExterno = function (usrVO){
		return $http.put(config.baseUrl + END_POINT + "/updateUsuarioExterno", usrVO);
	};
	
	this.downloadfile = function(file, fileName) {
        var url = window.URL || window.webkitURL;
        var blobUrl = url.createObjectURL(file);
        var a = document.createElement('a');
        a.href = blobUrl;
        a.target = '_blank';
        a.download = fileName;
        document.body.appendChild(a);
        $timeout(function() {
            a.click();
        }, 100);
    }
});