angular.module(appTeclo).controller('preguntaController', function($controller, $interval,$scope, $rootScope, $routeParams, $timeout, $location, $localStorage, WizardHandler, showAlert, growl, configAppService, ModalService,encuestaService) {

	$scope.listPregunta = new Array();
	$scope.totalPreguntas=0;
	$scope.valorAnterior="";

	$scope.view = new Object({
		rowsPerPageTwo:0
		});

	$scope.flagSeccion=undefined;
	$scope.saveStepTwo = function() {
		var wizardExample = WizardHandler.wizard('wizardExample');
		wizardExample.next();
		
	};

$(function() {
	$(document).keydown(function(e){
	 var code = (e.keyCode ? e.keyCode : e.which);
	 if(code == 116 && $scope.controllerActual==='encuestaController') {
	  e.preventDefault();
	  growl.warning("No puedes recargar la página");
	 }
	});
   });
   
	$scope.getNumPreguntasPorSeccion=function(cdParametro){
		encuestaService.getNumPreguntasPorSeccion(cdParametro).success(function(data) {
		 var a=parseInt(data.cdValorPConfig);
		 $scope.paramConfigPage.itemsPerPage=a;
		}).error(function(data) {
		  growl.warning(data.message);
		});
	};

	$scope.getNumMaxPaginacion=function(cdParametro){
		encuestaService.getNumPreguntasPorSeccion(cdParametro).success(function(data) {
		 var b=parseInt(data.cdValorPConfig);
		 $scope.paramConfigPage.maxSize=b;
		}).error(function(data) {
		  growl.warning(data.message);
		});
	};


	$scope.guardaFinalizaEncuesta=function(numPagina){
		$scope.guardaAvancePorPagina(numPagina);
		$timeout(() => {
			$scope.finalizaEncuesta();
		}, 500);
	};


$scope.checkPregunta =function(opcion,respuesta){
	var evaluaContestadas=$scope.encuestaDetalle.encuesta.secciones[$scope.posicionActual].nuPreguntasContestadas;
	var preguntasCont=evaluaContestadas!=undefined?evaluaContestadas:0;
	var cambio=false;

	if(respuesta.stMarcado==undefined || respuesta.stMarcado==0  || respuesta.stMarcado==null){
		$scope.encuestaDetalle.encuesta.secciones[$scope.posicionActual].nuPreguntasContestadas=preguntasCont;
		$scope.encuestaDetalle.encuesta.secciones[$scope.posicionActual].nuPreguntasContestadas++
		$scope.preguntasContestadasEncuesta++;
	}
	for (let i in respuesta.opciones) {
		respuesta.opciones[i].stMarcado=0;
	}

	for (let i in respuesta.opciones) {
		if (angular.equals(respuesta.opciones[i],opcion)) {
		respuesta.opciones[i].stMarcado=1;	
		respuesta.stMarcado=1;
	}
}
	
};



$scope.mostarTooltip = function(){
	$("[data-toggle='tooltipinfo']").tooltip('show');
	
	if ($('#checked').is(":checked"))
	{
		$("#guardarAvance").attr('disabled','disabled');
	}else{
		$("#guardarAvance").removeAttr("disabled");
	}
}

$scope.getNumPreguntasPorSeccion('TEE019P_NU_PAGINACION');
$scope.getNumMaxPaginacion('TEE019P_NU_MAX_PAG');

});