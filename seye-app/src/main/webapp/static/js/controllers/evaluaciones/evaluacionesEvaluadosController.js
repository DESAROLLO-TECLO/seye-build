angular.module(appTeclo).controller('evaluacionesEvaluadosController', 
	function($scope, $filter, $location, $route, idEvaluacion, ModalService, showAlert, $timeout, catalogoService, encuestasService, evaluacionesEvaluadorService, growl) {
	$scope.idEvaluacion = idEvaluacion;
	$scope.consultaEvaData = {};
	$scope.viewpanelConsulta = false;
	$scope.order ='stAplicaEncuesta';
	$scope.reverse = "reverse";
	
	$scope.generales = new Object({
    	txMarcaTodos: "Marcar todos"
    });
	
	$scope.flags = new Object({
        isModalUsers: false,
        isPromise: false,
        isGridMode: false,
        isListMode: true,
        isTypeSearchAll: true,
        isTypeSearchCdIdentifi: false,
        isTypeSearchInput: false,
        isModalUsersEdit: false,
        isFirstModal: true,
        isSearchTab: true,
        isNewTab: false,
        isDataPersonal: true,
        isDataUser: false,
        isDataContact: false,
        isPassword: false,
        isButtonMode: true,
        showMenuDataUser:false,
        verIntentos: false,
        todos: false,
        PDFMasivo: false
    });
	
	$scope.showAviso = function(messageTo, template, action) {
		ModalService.showModal({
			templateUrl: 'views/templatemodal/'+ template +'.html',
			controller: 'mensajeModalController',
			inputs:{ message: messageTo}
		}).then(function(modal) {
			modal.element.modal();
		});
	};
	
	$scope.showDialogIntentos = function(consultaEvaIntentos){
		ModalService.showModal({
			templateUrl: 'views/templatemodal/templateModalIntentos.html',
        	controller: 'modalIntentosController',
        	scope: $scope,
        	inputs: {consultaEvaIntentos : consultaEvaIntentos}
		}).then(function(modal) {
			modal.element.modal();
		});
	}
	
	$scope.showPdf = function (messageTo) {
        ModalService.showModal({
            templateUrl: 'views/templatemodal/templateModalPdf.html',
            controller: 'mensajeModalController',
            inputs: {message: messageTo}
        }).then(function (modal) {
            modal.element.modal();
        });
    };
	
	$scope.consultaEvaEvaluados = function(){
		if($scope.idEvaluacion == undefined || $scope.idEvaluacion == null){
			$scope.showAviso("No se encontraron registros", 'templateModalAviso');
			$scope.regresarBusq();
		}else{
			evaluacionesEvaluadorService.consultaEncuestaEvaluados(
				idEvaluacion
			).success(function(data) {
				if(data.length != 0){
					$scope.consultaEvaData = data;
					for (let i = 0; i < $scope.consultaEvaData[0].usuarioEncuesta.length; i++) {
						$scope.consultaEvaData[0].usuarioEncuesta[i].checked = false;
						$scope.consultaEvaData[0].usuarioEncuesta[i].txAgregar = "Seleccionar";
					}
					$scope.viewpanelConsulta = true;
				}else{
					$scope.showAviso("No se encontraron registros", 'templateModalAviso');
					$scope.consultaEvaData = {};
					$scope.viewpanelConsulta = false;
				}
			}).error(function(data){
				$scope.consultaEvaData = {};
				$scope.viewpanelConsulta = false;
			});
		}
	}
	
	$scope.activaUsuarioIntento = function(usuarioEncuestaVO) {
        evaluacionesEvaluadorService.activaUsuarioIntento(
            usuarioEncuestaVO
        ).success(function(data) {
            $scope.showAviso("Actualización exitosa", 'templateModalAviso');
            $scope.consultaEvaEvaluados();
        }).error(function(data) {
            if (data != null && data.status != null && data.status == 400) {
                growl.error(data.message, { ttl: 3000 });
                $scope.consultaEvaEvaluados();
            } else {
                growl.error('Ha ocurrido un error al tratar de activar la evaluación, favor de validar.', { ttl: 4000 });
            }
        });
    }
	
	$scope.activaUsuario = function(newValue, oldValue, idUsuarioEncuesta, idUsuEncuIntento,consultaEva) {
        var msgIntento = "";
        if (newValue == 1) {
            msgIntento = "¿Desea activar al usuario para un nuevo intento?";
        } else if (newValue == 0) {
            msgIntento = "¿Desea desactivar el intento actual del usuario?";
        }
        
        $timeout(() => {
        	consultaEva.stAplicaEncuesta = oldValue;
        }, 100);
        
        var usuarioEncuestaVO = { 
        		"idUsuarioEncuesta"	: idUsuarioEncuesta,
        		"valor"				: newValue ? 1 : 0,
    			"intento"			: idUsuEncuIntento
            };
        
        showAlert.confirmacion(msgIntento,
            confirm = () => {
               	consultaEva.stAplicaEncuesta = newValue;
                $scope.activaUsuarioIntento(usuarioEncuestaVO);
            }, cancelaNotificar = () => {
                return;
        });
        
        
        /*consultaEva.stAplicaEncuesta=true;
        
        var usuarioEncuestaVO = { 
    		"idUsuarioEncuesta"	: idUsuarioEncuesta,
    		"valor"				: newValue,
			"intento"			: idUsuEncuIntento
        };
        showAlert.confirmacion(msgIntento, $scope.activaUsuarioIntento, usuarioEncuestaVO);*/
    }
/*
	$scope.activaUsuario = function(newValue, oldValue,consultaEva){
		var usuarioEncuestaVO = { 
    		"idUsuarioEncuesta"	: consultaEva.idUsuarioEncuesta,
    		"valor"				: newValue,
			"intento"			: consultaEva.intentoMostar.idUsuEncuIntento
        };
		
        $timeout(() => {
            consultaEva.stAplicaEncuesta = oldValue;
        }, 100);

        if (newValue== 0) {
            showAlert.confirmacion('¿Desea desactivar al usuario para un nuevo intento?',
                confirm = () => {

                    consultaEva.stAplicaEncuesta= newValue;
					$scope.activaUsuarioIntento(usuarioEncuestaVO);
					
                }, cancelaNotificar = () => {
                    return;
                });
        } else {
            showAlert.confirmacion('¿Desea activar al usuario para un nuevo intento?',
                confirm = () => {

                    consultaEva.stAplicaEncuesta = newValue;
					$scope.activaUsuarioIntento(usuarioEncuestaVO);
					
                }, cancelaNotificar = () => {
                    return;
                });
          }
    };
*/
	$scope.descargarExcel = function() {
        let peticionReporteVO = new Object();
        peticionReporteVO.header = getCabeceras();
        peticionReporteVO.values = getContenido($scope.consultaEvaData[0].usuarioEncuesta);
		peticionReporteVO.titulo = "Reporte de Evaluados";
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
	
	getCabeceras = () => {
        let headers = new Array();
        headers.push("PLACA OFICIAL");
        headers.push("NOMBRE OFICIAL");
        headers.push("FECHA APLICACIÓN");
        headers.push("CALIFICACIÓN");
        headers.push("INTENTOS");
        headers.push("ESTATUS");
        headers.push("ACTIVO");
        return headers;
    };
    
    getContenido = (list) => {
        let array = new Array();
        for (let i = 0; i < list.length; i++) {
            let elemento = new Array();
            elemento.push(list[i].usuario.cdUsuario);
            elemento.push(list[i].usuario.nbUsuario + " " + list[i].usuario.nbApaterno + " " + list[i].usuario.nbAmaterno);
            elemento.push(list[i].intentoMostar!=null? list[i].intentoMostar.fhInicio : "N/A");
            elemento.push(list[i].intentoMostar != null ? list[i].intentoMostar.nuCalificacion : "SIN CALIFICACION");
            elemento.push(list[i].nuIntegerentos);
            elemento.push(list[i].intentoMostar!=null ? list[i].intentoMostar.stEncuesta.nbStEncuesta : "NO INICIADA");
            if(list[i].stAplicaEncuesta == 0){
            	elemento.push("Inactivo");
            }else if(list[i].stAplicaEncuesta == 1){
            	elemento.push("Activo");
            }
            array.push(elemento);
        }
        return array;
    };
	
    $scope.descargaPDFRecibida = function(cdEncuesta) {
    	archivo = "././static/dist/documentosEvaluaciones/" + cdEncuesta + ".pdf";
    	$scope.showPdf(archivo);
    }
    $scope.visualizarPDF = function(archivo){
    	let file = new Blob([archivo], { type: 'application/pdf' });
		var url = window.URL || window.webkitURL;
        var blobUrl = url.createObjectURL(file);
    	$scope.showPdf(blobUrl);
    }
    
    $scope.mostrarPDF = function(idUsuario, idUsuEncuIntento, idUsuarioEncuesta, opcion){
    	if(opcion == "C" || opcion == "P"){
	    	evaluacionesEvaluadorService.getDetallePDF(idUsuario, idUsuEncuIntento, opcion).success(
	    	function(archivo) {
	    		$scope.visualizarPDF(archivo);
	        }).error(function(e) {
	            if(e.descripcion != undefined){
	            	growl.error(e.descripcion,{ ttl: 5000 });
	            }else if(e.message != undefined) {
	            	growl.error(e.message,{ ttl: 5000 });
	            }else {showAlert.error('Falló la petición');}
	        });
    	}else if(opcion == "COM"){
    		evaluacionesEvaluadorService.getDetallePDFCom(idUsuarioEncuesta,idUsuEncuIntento, idUsuario).success(
    		function(archivo) {
    			$scope.visualizarPDF(archivo);
	        }).error(function(e) {
	            if(e.descripcion != undefined){
	            	growl.error(e.descripcion,{ ttl: 5000 });
	            }else if(e.message != undefined) {
	            	growl.error(e.message,{ ttl: 5000 });
	            }else {showAlert.error('Falló la petición');}
	        });
    	}
    }
    
    $scope.descargarPDF = function(idUsuario, idUsuEncuIntento, opcion, nbUsuario){
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
    
	$scope.descargarPDFMasivo = function(opcion){
		for (let i = 0; i < $scope.consultaEvaData[0].usuarioEncuesta.length; i++) {
			if($scope.consultaEvaData[0].usuarioEncuesta[i].checked == true  && $scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar != null){
				
				var idUsuario = $scope.consultaEvaData[0].usuarioEncuesta[i].usuario.idUsuario;
				var idUsuEncuIntento = $scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar.idUsuEncuIntento;
				 
				var nbUsuarioNombre = $scope.consultaEvaData[0].usuarioEncuesta[i].usuario.nbUsuario.replace(" ", "_");
				var nbUsuarioApaterno = $scope.consultaEvaData[0].usuarioEncuesta[i].usuario.nbApaterno.replace(" ", "_");
				var nbUsuarioAmaterno = $scope.consultaEvaData[0].usuarioEncuesta[i].usuario.nbAmaterno.replace(" ", "_");
				
				var nbUsuario =	nbUsuarioNombre + "_" + nbUsuarioApaterno + "_" + nbUsuarioAmaterno;
				
				if($scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar.stCalificacion !=null && opcion == "C" &&
						($scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar.stCalificacion.cdStCalificacion == "A" && 
						$scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar.nuPreguntasVacias == 0)){
					$scope.descargarPDF(idUsuario, idUsuEncuIntento, opcion, nbUsuario);
				}
				if(opcion=="P" && $scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar.fhInicio!=null){
					$scope.descargarPDF(idUsuario, idUsuEncuIntento, opcion, nbUsuario);
				}
			}
		}
	}
	
	$scope.descargarPDFMultiple = function(listaDescarga){
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
    
	$scope.descargarPDFMasivo2 = function(opcion){
		var listaDescarga = [];
		for (var i = 0; i < $scope.consultaEvaData[0].usuarioEncuesta.length; i++) {
			if($scope.consultaEvaData[0].usuarioEncuesta[i].checked == true  && $scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar != null){
				var EncuestaUsuarioIntentoPDFMVO = {};
				
				var idUsuario = $scope.consultaEvaData[0].usuarioEncuesta[i].usuario.idUsuario;
				var idUsuEncuIntento = $scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar.idUsuEncuIntento;
				 
				var nbUsuarioNombre = $scope.consultaEvaData[0].usuarioEncuesta[i].usuario.nbUsuario.replace(" ", "_");
				var nbUsuarioApaterno = $scope.consultaEvaData[0].usuarioEncuesta[i].usuario.nbApaterno.replace(" ", "_");
				var nbUsuarioAmaterno = $scope.consultaEvaData[0].usuarioEncuesta[i].usuario.nbAmaterno.replace(" ", "_");
				
				var nbUsuario =	nbUsuarioNombre + "_" + nbUsuarioApaterno + "_" + nbUsuarioAmaterno;
				
				if(opcion == "C" && $scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar.stCalificacion != null && 
					($scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar.stCalificacion.cdStCalificacion == "A" && 
					$scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar.nuPreguntasVacias == 0)
				){
					//$scope.descargarPDF(idUsuario, idUsuEncuIntento, opcion, nbUsuario);
					var EncuestaUsuarioIntentoPDFMVO = {
						"idUsuario"			: idUsuario,
						"idUsuEncuIntento"	: idUsuEncuIntento,
						"opcion"			: opcion
					};
					listaDescarga.push(EncuestaUsuarioIntentoPDFMVO);
				}
				if(opcion=="P" && $scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar.fhInicio!=null){
					//$scope.descargarPDF(idUsuario, idUsuEncuIntento, opcion, nbUsuario);
					var EncuestaUsuarioIntentoPDFMVO = {
						"idUsuario"			: idUsuario,
						"idUsuEncuIntento"	: idUsuEncuIntento,
						"opcion"			: opcion
					};
					listaDescarga.push(EncuestaUsuarioIntentoPDFMVO);
				}
			}
		}
		if(listaDescarga.length > 0){
			$scope.descargarPDFMultiple(listaDescarga);
		}else if(listaDescarga.length == 0 && opcion == "C"){
			growl.error("No se puede descargar la constancia de estos usuarios.",{ ttl: 5000 });
		}else if(listaDescarga.length == 0 && opcion == "P"){
			growl.error("No se puede descargar el detalle de estos usuarios.",{ ttl: 5000 });
		}
	}
	
	verificarMarcarTodos = function() {
		let count = 0;
		for (let i = 0; i < $scope.consultaEvaData[0].usuarioEncuesta.length; i++) {
			if($scope.consultaEvaData[0].usuarioEncuesta[i].checked){
				count++;
			}
		}
		if(count == $scope.consultaEvaData[0].usuarioEncuesta.length){
			$scope.flags.todos = true;
		} else {
			$scope.flags.todos = false;
		}
		
		if(count > 0){
			$scope.flags.PDFMasivo = true;
		}else{
			$scope.flags.PDFMasivo = false;
		}
		$scope.generales.txMarcaTodos = $scope.flags.todos ? 'Desmarcar todos' : 'Marcar todos';
	}
	
	$scope.checkManual = function(intento){
		intento.txAgregar = intento.checked ? 'Deseleccionar':'Seleccionar';
		verificarMarcarTodos();
	}
	
	$scope.marcarTodos = function() {
		$scope.generales.txMarcaTodos = $scope.flags.todos ? 'Desmarcar todos' : 'Marcar todos';
		let count = 0;
		for (let i = 0; i < $scope.consultaEvaData[0].usuarioEncuesta.length; i++) {
//			if($scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar != null 
//				&& $scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar.stCalificacion != null 
//				&& $scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar.stCalificacion.cdStCalificacion == 'A'
//				&& $scope.consultaEvaData[0].usuarioEncuesta[i].intentoMostar.nuPreguntasVacias < 1
//			){
				$scope.consultaEvaData[0].usuarioEncuesta[i].checked = $scope.flags.todos ? true : false;
				$scope.consultaEvaData[0].usuarioEncuesta[i].txAgregar = $scope.flags.todos ? 'Deseleccionar' : 'Seleccionar';
				$scope.flags.PDFMasivo = $scope.flags.todos ? true : false;
				
				if($scope.consultaEvaData[0].usuarioEncuesta[i].checked){
					count++;
				}
//			}
		}
		if(count > 0){
			$scope.flags.PDFMasivo = true;
		}else{
			$scope.flags.PDFMasivo = false;
		}
	};
	
	mostrarMsjTablaResulados = (cdParametro) => {
		catalogoService.buscarParametroPorCd(cdParametro).success(function(data) {
    		$scope.msjTabla = data.cdValorPConfig;
    		$scope.error = null;
        }).error(function(data) {
        	$scope.msjTabla = {};
    		$scope.error = data;
        });
    }
    
	ejecutaConsutaInicial = function(){
		$timeout(function() {
			$scope.consultaEvaEvaluados();
		});
	}
	
	$scope.regresarBusq = function(){
		window.history.back();
	}
	
	defaultValues = function(){
		ejecutaConsutaInicial();
		mostrarMsjTablaResulados("TEE019P_MSJ_TRESUL");
	}
	
	defaultValues();
});