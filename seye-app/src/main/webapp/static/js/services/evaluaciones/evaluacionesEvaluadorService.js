angular.module(appTeclo).service("evaluacionesEvaluadorService", function ($http, config, $timeout) {
	
	this.consultaEncuestaEvaluador = function (
		tipoBusqueda,
		valorBusqueda,
		startDate,
		endDate,
		estatusEva
	) {
		return $http.get(config.baseUrl + "/encuesta/encuesta", {
			params : {
				"tipoBusqueda"	: tipoBusqueda,
				"valorBusqueda"	: valorBusqueda,
				"fhInicio"		: startDate,
				"fhFin"			: endDate,
				"stEncuesta"	: estatusEva
			}
		});
	};
	
	this.consultaEncuestaEvaluados = function (
		idEncuesta
	) {
		return $http.get(config.baseUrl + "/encuesta/encuestadosEncuesta", {
			params : {
				"idEncuesta"	: idEncuesta
			}
		});
	};
	
	this.activaUsuarioIntento = function(
		usuarioEncuestaVO
	){
		return $http.post(config.baseUrl + "/encuesta/intento", usuarioEncuestaVO); 
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
	
	this.getDetallePDF = function (idUsuario, idUsuEncuIntento, opcion){
		return $http({
            method: 'GET',
            url: config.baseUrl + "/reporte/pdf",
            params: {
            	idUsuEncuIntento: idUsuEncuIntento, 
            	idUsuario: idUsuario, 
            	opcion : opcion},
            dataType: "json",
            header: {
                "Content-type": "application/json",
                "Accept": "application/pdf"
            },
            responseType: 'arraybuffer'
        });
	};
	
	this.getDetallePDFCom = function (idUsuarioEncuesta, idUsuEncuIntento, idUsuario){
		return $http({
            method: 'GET',
            url: config.baseUrl + "/reporte/encuesta",
            params: { idUsuarioEncuesta: idUsuarioEncuesta, idIntento: idUsuEncuIntento, idUsuario:idUsuario },
            dataType: "json",
            header: {
                "Content-type": "application/json",
                "Accept": "application/pdf"
            },
            responseType: 'arraybuffer'
        });
	};
	
	this.descargarPDFMasivo = function (listaDescarga){
//		return $http.post(config.baseUrl + "/reporte/pdfMultiple",listaDescarga);
//		var json =  angular.toJson(listaDescarga);
		return $http({
            method: 'POST',
            url: config.baseUrl + "/reporte/pdfMultiple",
            data: listaDescarga,
            dataType: "json",
            header: {
                "Content-type": "application/json",
                "Accept": "application/pdf"
            },
            responseType: 'arraybuffer'
        });
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