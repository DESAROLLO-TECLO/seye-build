angular.module(appTeclo).controller('evaluacionesEvaluadorController', 
	function($scope, $filter, $location, $route, evaluacionesEvaluadorService, ModalService, catalogoService, showAlert, $timeout) {//, catalogoService, utileriaService
	$scope.tipoBusquedaEva = "Evaluaciones";
	$scope.stBusquedaDe = 0;
	$scope.busquedaDeDisabled = false;
	$scope.viewpanelConsulta = 0;
	$scope.parametrosConsultaRealizada = {};
	$scope.parametroBusqueda = {};
	$scope.consultaEvaData = {};

	$scope.finiRec = false;
	$scope.ffinRec = false;
	$scope.vbusReq = false;
	
	$scope.showAviso = function(messageTo, template, action) {
		ModalService.showModal({
			templateUrl: 'views/templatemodal/'+ template +'.html',
			controller: 'mensajeModalController',
			inputs:{ message: messageTo}
		}).then(function(modal) {
			modal.element.modal();
		});
	};
	
	$scope.filtroTipoBusquedaEva = function(){
		$scope.filtroTiposBusquedaEvaData = [];
		$scope.filtroTiposBusquedaEvaData = catalogoService.buscarFiltroTiposBusquedaEvaData();
	}
	
	$scope.filtroEstatusEva = function(){
		$scope.filtroEstatusEvaData = [];
		$scope.filtroEstatusEvaData = catalogoService.buscarFiltroCatalogosEstatusEncuesta();		
	}
	
	$scope.cambiarBusquedaDe = function(newValue, oldValue){
		if(newValue == 1){
			//Evaluaciones
			$scope.tipoBusquedaEva = "Evaluados";
		}else if(newValue == 0){
			//Evaluados
			$scope.tipoBusquedaEva = "Evaluaciones";
		}
	}
	
	$scope.realizaValidaciones = function(campo){
		if(campo == 1){
			if($scope.parametroBusqueda.startDate != undefined && $scope.parametroBusqueda.startDate.length > 0){
				$scope.ffinRec = true;
			}else{
				$scope.ffinRec = false;
				mascarRequeridos();
			}
		}else if(campo == 2){
			if($scope.parametroBusqueda.endDate != undefined && $scope.parametroBusqueda.endDate.length > 0){
				$scope.finiRec = true;
			}else{
				$scope.finiRec = false;
				mascarRequeridos();
			}
		}else if(campo == 3){
			$scope.parametroBusqueda.valorBusqueda = "";
			if($scope.parametroBusqueda.tipoBusqueda > 0){
				$scope.vbusReq = true;
			}else{
				$scope.vbusReq = false;
			}
		}
	}
	
	$scope.limpiarCampos = function(){
		var $dateFin = $('#fFin').datepicker();
		var $dateIni = $('#fInicio').datepicker();
		$dateFin.datepicker('setDate', null);
		$dateIni.datepicker('setDate', null);
		
		$scope.parametroBusqueda.startDate = "";
		$scope.parametroBusqueda.endDate = "";
		$scope.ffinRec = false;
		$scope.finiRec = false;

		$dateIni.datepicker('setStartDate', null);
		$dateFin.datepicker('setStartDate', null);
		$dateIni.datepicker('setEndDate', null);
		$dateFin.datepicker('setEndDate', null);
		
		$scope.tipoBusquedaEva = "Evaluaciones";
		$scope.stBusquedaDe = 0;
		
		$scope.parametroBusqueda.tipoBusqueda = 0;
		$scope.vbusReq = false;
		$scope.parametroBusqueda.valorBusqueda = "";
		
		$scope.parametroBusqueda.estatusEva = 0;
		//$scope.parametroBusqueda.selectMultipleOp1TipoArchivo = $scope.tipoArchivoReset;
		mascarRequeridos();
	}
	
	mascarRequeridos = function(){
		angular.forEach($scope.form.$error, function (field) {
			angular.forEach(field, function(errorField){
				errorField.$setDirty();
			})
		});
	}
	
	$scope.consultaEvaluacion = function(){
		if ($scope.form.$invalid) {
			angular.forEach($scope.form.$error, function (field) {
				angular.forEach(field, function(errorField){
					errorField.$setDirty();
				})
			});
			$scope.showAviso("Formulario Incompleto", 'templateModalAviso');
		}else {
			$scope.consultaEvaData = {};
			
			if($scope.parametroBusqueda.startDate == undefined && $scope.parametroBusqueda.endDate == undefined){
				$scope.parametroBusqueda.startDate = "";
				$scope.parametroBusqueda.endDate = "";
			}
			
			if($scope.parametroBusqueda.tipoBusqueda == undefined || $scope.parametroBusqueda.tipoBusqueda == null || $scope.parametroBusqueda.tipoBusqueda == ""){
				$scope.parametroBusqueda.tipoBusqueda = 0;
			}
			
			if($scope.parametroBusqueda.estatusEva == undefined || $scope.parametroBusqueda.estatusEva == null){
				$scope.parametroBusqueda.estatusEva = 0;
			}
			
			if($scope.stBusquedaDe == 1){
				//Evaluaciones
				$scope.tipoBusquedaEva = "Evaluados";
				$scope.parametrosConsultaRealizada.stBusquedaDe = 2;
				$scope.classTablaConsulta = "col-md-offset-2 col-md-8 col-md-offset-2";
				$scope.classFiltroConsulta = "col-md-offset-2 col-md-4 col-xs-10";
			}else if($scope.stBusquedaDe == 0){
				//Evaluados
				$scope.tipoBusquedaEva = "Evaluaciones";
				$scope.parametrosConsultaRealizada.stBusquedaDe = 1;
				$scope.classTablaConsulta = "col-md-12";
				$scope.classFiltroConsulta = "col-md-offset-3 col-md-3 col-xs-10";
			}
			
			$scope.parametrosConsultaRealizada.tipoBusquedaEva = $scope.tipoBusquedaEva;
			$scope.parametrosConsultaRealizada.startDate = $scope.parametroBusqueda.startDate;
			$scope.parametrosConsultaRealizada.endDate = $scope.parametroBusqueda.endDate;
			$scope.parametrosConsultaRealizada.tipoBusqueda = $scope.parametroBusqueda.tipoBusqueda;
			$scope.parametrosConsultaRealizada.valorBusqueda = $scope.parametroBusqueda.valorBusqueda;
			$scope.parametrosConsultaRealizada.estatusEva = $scope.parametroBusqueda.estatusEva;
			
			evaluacionesEvaluadorService.consultaEncuestaEvaluador(
				$scope.parametrosConsultaRealizada.tipoBusqueda,
				$scope.parametrosConsultaRealizada.valorBusqueda,
				$scope.parametrosConsultaRealizada.startDate,
				$scope.parametrosConsultaRealizada.endDate,
				$scope.parametrosConsultaRealizada.estatusEva
			).success(function(data) {
				if(data.length != 0){
					$scope.consultaEvaData = data;
					$scope.viewpanelConsulta = $scope.parametrosConsultaRealizada.stBusquedaDe;
				}else{
					$scope.consultaEvaData = {};
					$scope.showAviso("No se encontraron registros", 'templateModalAviso');
					$scope.consultaEvaData = {};
					$scope.viewpanelConsulta = 0;
				}
			}).error(function(data){
				$scope.consultaEvaData = {};
				$scope.viewpanelConsulta = 0;
			});
		}
	}
	
	$scope.descargarExcel = function() {
        let peticionReporteVO = new Object();
        peticionReporteVO.header = getCabeceras();
        peticionReporteVO.values = getContenido($scope.consultaEvaData);
		peticionReporteVO.titulo = "Reporte de Evaluaciones";
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
        headers.push("FOLIO");
        headers.push("NOMBRE");
        headers.push("FECHA INICIO");
        headers.push("FECHA FIN");
        headers.push("EVALUADOS");
        headers.push("PORCENTAJE APLICACIÓN");
        headers.push("PROMEDIO GENERAL");
        headers.push("EFECTIVIDAD");
        return headers;
    };
    
    getContenido = (list) => {
        let array = new Array();
        for (let i = 0; i < list.length; i++) {
            let elemento = new Array();
            elemento.push(list[i].cdEncuesta);
            elemento.push(list[i].nbEncuesta);
            elemento.push(list[i].fhVigIni);
            elemento.push(list[i].fhVigFin);
            elemento.push(list[i].nuEncuestados);
            elemento.push(list[i].aplicacionProm + "%");
            elemento.push(list[i].promedio + "%");
            elemento.push(list[i].efectividad);
            array.push(elemento);
        }
        return array;
    };
	
	ejecutaConsutaInicial = function(){
		$timeout(function() {
			$scope.consultaEvaluacion();
		});
	}
	
	defaultValues = function(){
		$scope.parametroBusqueda = {
			tipoBusqueda	: 0,
			valorBusqueda	: "",
			estatusEva		: 0,
			startDate		: "",
			endDate			: ""
		};
		
		$scope.filtroTipoBusquedaEva();
		$scope.filtroEstatusEva();
		ejecutaConsutaInicial();
	}
	
	defaultValues();
});
