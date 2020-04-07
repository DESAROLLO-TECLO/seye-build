angular.module(appTeclo).controller('modalIntentosController',
	function($scope, evaluacionesEvaluadorService, consultaEvaIntentos, ModalService,showAlert, $filter, $location, $controller, growl, encuestasService) {
	
	$scope.consultaEvaIntentos = consultaEvaIntentos;
	$scope.contadorCheck = 0;
	$scope.order ="nuCalificacion";
	$scope.reverse = "reverse";
	$scope.flags.todos=false;
	
	for (var i = 0; i < $scope.consultaEvaIntentos.usuarioEncuestaIntentos.length; i++) {
		var array_element = $scope.consultaEvaIntentos.usuarioEncuestaIntentos[i]; 
		
		var fInicioIntento = $scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].fhInicio;
		var fFinIntento = $scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].fhFin;
		if(fInicioIntento == undefined || fInicioIntento == null || fInicioIntento == ""
			|| fFinIntento == undefined || fFinIntento == null || fFinIntento == ""){
			// No calcular duracion
			$scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].duracionColor = $scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].stEncuesta.cdColor;
			$scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].duracion = $scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].stEncuesta.nbStEncuesta;
		}else{
			var fecha1 = moment(fInicioIntento, "DD/MM/YYYY HH:mm:ss");
			var fecha2 = moment(fFinIntento, "DD/MM/YYYY HH:mm:ss");
			
			var diff1 = fecha2.diff(fecha1, 's');
			
			var difference_ms = diff1*1000; //Diferencia en milisegundos
			
			difference_ms = difference_ms/1000;
			var segundos = Math.floor(difference_ms % 60); //Diferencia en Segundos
			difference_ms = difference_ms/60; 
			var minutos = Math.floor(difference_ms % 60); //Diferencia en Minutos
			difference_ms = difference_ms/60; 
			var horas = Math.floor(difference_ms % 24); //Diferencia en Horas
			var dias = Math.floor(difference_ms/24); //Diferencia en Dias
			
			$scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].duracionColor = -1;
			var duracion = "";
			if(dias > 0){
				duracion = dias + ' Dias, ';
				duracion += horas + ' Horas, '
				duracion += minutos + ' Minutos, '
				duracion += segundos + ' Segundos';
				$scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].duracion = duracion;
			}else if(horas > 0){
				duracion = horas + ' Horas, '
				duracion += minutos + ' Minutos, '
				duracion += segundos + ' Segundos';
				$scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].duracion = duracion;
			}else if(minutos > 0){
				duracion = minutos + ' Minutos, '
				duracion += segundos + ' Segundos';
				$scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].duracion = duracion;
			}else if(segundos > 0){
				duracion = segundos + ' Segundos';
				$scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].duracion = duracion;
			}else{
				$scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].duracionColor = $scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].stEncuesta.cdColor;
				$scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].duracion = $scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].stEncuesta.nbStEncuesta;
			}
			
			//console.log(days + ' days, ' + hours + ' hours, ' + minutes + ' minutes, and ' + seconds + ' seconds');
		}
	}
	
	$scope.descargarExcelInt = function() {
        let peticionReporteVO = new Object();
        peticionReporteVO.header = getCabeceras();
        peticionReporteVO.values = getContenido($scope.consultaEvaIntentos.usuarioEncuestaIntentos);
		peticionReporteVO.titulo = "Reporte de Intentos";
		evaluacionesEvaluadorService.descargarReporteExcel(peticionReporteVO).success(function(data, status, headers) {
            let filename = headers('filename');
            let file = new Blob([data], { type: 'application/vnd.ms-excel;base64,' });
            evaluacionesEvaluadorService.downloadfile(file, filename);
        }).error(function(e) {
            if(e.descripcion != undefined){
            	growl.error(e.descripcion,{ ttl: 5000 });
            }else if(e.message != undefined) {
            	growl.error(e.message,{ ttl: 5000 });
            }else {showAlert.error('Falló la petición');}
        });
    };
    
    $scope.checkManual = function(intento, valor){
    	if(!valor){
    		$scope.flags.todos = valor;
    		$scope.contadorCheck--;
    	}else{
    		$scope.contadorCheck++;
    	}
    }
    
    $scope.marcarTodos = function(valor){
    	$scope.contadorCheck=0;
    	for(var i in $scope.consultaEvaIntentos.usuarioEncuestaIntentos){
//    		if($scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].stCalificacion.cdStCalificacion == 'A' 
//    				&& $scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].nuPreguntasVacias == 0){
    			$scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].checked = valor;
    			if(valor)
    				$scope.contadorCheck++;
    			//else
    			//	$scope.contadorCheck--;
//    		}
    	}
    }
    
    $scope.descargarPDFMasivo = function(opcion){
    	
    	for(var i in $scope.consultaEvaIntentos.usuarioEncuestaIntentos){
    		if($scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].checked == true){
    			var idUsuario = $scope.consultaEvaIntentos.usuario.idUsuario;
				var idUsuEncuIntento = $scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].idUsuEncuIntento;
				 
				var nbUsuarioNombre = $scope.consultaEvaIntentos.usuario.nbUsuario.replace(" ", "_");
				var nbUsuarioApaterno = $scope.consultaEvaIntentos.usuario.nbApaterno.replace(" ", "_");
				var nbUsuarioAmaterno = $scope.consultaEvaIntentos.usuario.nbAmaterno.replace(" ", "_");
				
				var nbUsuario =	nbUsuarioNombre + "_" + nbUsuarioApaterno + "_" + nbUsuarioAmaterno;
				
				if($scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].stCalificacion.cdStCalificacion == "A" 
					&&  $scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].nuPreguntasVacias == 0 && opcion == "C"){
					$scope.descargarPDF(idUsuario, idUsuEncuIntento, opcion, nbUsuario);
    			}
				if(opcion == "P"){
					$scope.descargarPDF(idUsuario, idUsuEncuIntento, opcion, nbUsuario);
    			}
    		}
    	}
	}
    
    $scope.descargarPDF = (idUsuario, idUsuEncuIntento, opcion, nbUsuario) => {
    	evaluacionesEvaluadorService.getDetallePDF(
    		idUsuario, idUsuEncuIntento, opcion
    	).success(function(archivo) {
    		var filename = "";
    		
    		if(opcion == "C"){
    			filename = "CONSTANCIA_" + nbUsuario;
    		}else if(opcion == "P"){
    			filename = "DETALLE_" + nbUsuario;
    		}
    		let file = new Blob([archivo], { type: 'application/pdf' });
            encuestasService.downloadfile(file, filename);
        }).error(function(e) {
            if(e.descripcion != undefined){
            	growl.error(e.descripcion,{ ttl: 5000 });
            }else if(e.message != undefined) {
            	growl.error(e.message,{ ttl: 5000 });
            }else {showAlert.error('Falló la petición');}
        });
    }
	
	getCabeceras = () => {
        let headers = new Array();
        headers.push("FECHA APLICACIÓN");
        headers.push("CORRECTAS");
        headers.push("INCORRECTAS");
        headers.push("NO RESPONDIDAS");
        headers.push("CALIFICACÍON");
        headers.push("RESULTADO");
        headers.push("DURACIÓN");
        return headers;
    };
    
    getContenido = (list) => {
        let array = new Array();
        for (let i = 0; i < list.length; i++) {
            let elemento = new Array();
            elemento.push(list[i].fhInicio);
            elemento.push(list[i].nuPreguntasCorrectas);
            elemento.push(list[i].nuPreguntasIncorr);
            elemento.push(list[i].nuPreguntasNoRespondidas == null ? 0 : list[i].nuPreguntasNoRespondidas);
            elemento.push(list[i].nuCalificacion);
            elemento.push(list[i].stCalificacion.nbStCalificacion);
            elemento.push(list[i].duracion);
            array.push(elemento);
        }
        return array;
    };
    
    $scope.descargarPDFMultipleInt = function(listaDescarga){
    	evaluacionesEvaluadorService.descargarPDFMasivo(
    		listaDescarga
    	).success(function(archivo) {
    		let file = new Blob([archivo], { type: 'application/pdf' });
    		var url = window.URL || window.webkitURL;
            var blobUrl = url.createObjectURL(file);
        	$scope.showPdf(blobUrl);
        }).error(function(e) {
            if(e.descripcion != undefined){
            	growl.error(e.descripcion,{ ttl: 5000 });
            }else if(e.message != undefined) {
            	growl.error(e.message,{ ttl: 5000 });
            }else {showAlert.error('Falló la petición');}
        });
    }
    
	$scope.descargarPDFMasivoInt = function(opcion){
		var listaDescargaInt = [];
		for (var i = 0; i < $scope.consultaEvaIntentos.usuarioEncuestaIntentos.length; i++) {
		//for (var i in $scope.consultaEvaIntentos.usuarioEncuestaIntentos){
			if($scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].checked == true){
				var EncuestaUsuarioIntentoPDFMVOInt = {};
				
				var idUsuario = $scope.consultaEvaIntentos.usuario.idUsuario;
				var idUsuEncuIntento = $scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].idUsuEncuIntento;
				 
				var nbUsuarioNombre = $scope.consultaEvaIntentos.usuario.nbUsuario.replace(" ", "_");
				var nbUsuarioApaterno = $scope.consultaEvaIntentos.usuario.nbApaterno.replace(" ", "_");
				var nbUsuarioAmaterno = $scope.consultaEvaIntentos.usuario.nbAmaterno.replace(" ", "_");
				
				var nbUsuario =	nbUsuarioNombre + "_" + nbUsuarioApaterno + "_" + nbUsuarioAmaterno;
				
				if(opcion == "C" && $scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].stCalificacion != null && 
					($scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].stCalificacion.cdStCalificacion == "A" && 
					$scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].nuPreguntasVacias == 0)
				){
					//$scope.descargarPDF(idUsuario, idUsuEncuIntento, opcion, nbUsuario);
					var EncuestaUsuarioIntentoPDFMVOInt = {
						"idUsuario"			: idUsuario,
						"idUsuEncuIntento"	: idUsuEncuIntento,
						"opcion"			: opcion
					};
					listaDescargaInt.push(EncuestaUsuarioIntentoPDFMVOInt);
				}
				if(opcion=="P" && $scope.consultaEvaIntentos.usuarioEncuestaIntentos[i].fhInicio!=null){
					//$scope.descargarPDF(idUsuario, idUsuEncuIntento, opcion, nbUsuario);
					var EncuestaUsuarioIntentoPDFMVOInt = {
						"idUsuario"			: idUsuario,
						"idUsuEncuIntento"	: idUsuEncuIntento,
						"opcion"			: opcion
					};
					listaDescargaInt.push(EncuestaUsuarioIntentoPDFMVOInt);
				}
			}
		}
		if(listaDescargaInt.length > 0){
			$scope.descargarPDFMultipleInt(listaDescargaInt);
		}else if(listaDescargaInt.length == 0 && opcion == "C"){
			growl.error("No se puede descargar la constancia de estos usuarios.",{ ttl: 5000 });
		}else if(listaDescargaInt.length == 0 && opcion == "P"){
			growl.error("No se puede descargar el detalle de estos usuarios.",{ ttl: 5000 });
		}
	}
});