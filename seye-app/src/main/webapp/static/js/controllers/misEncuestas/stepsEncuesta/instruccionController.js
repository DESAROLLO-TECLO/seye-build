angular.module(appTeclo).controller('instruccionController', function(encuestaService, $controller, $scope, $rootScope, $routeParams, $timeout, $location, $localStorage, WizardHandler, showAlert, growl, configAppService, ModalService, encuestaService, growl) {
		
	$scope.saveStepOne = function(idEncuesta) {	
		encuestaService.cargarEncuesta(idEncuesta).success(function(data) {
			if (data) {
				var wizardExample = WizardHandler.wizard('wizardExample');
				wizardExample.next();
				$scope.cambiarPregunta(null,$scope.encuestaDetalle.encuesta.secciones[0]);
				$scope.iniciarConteo();
				//reloj();
			}
		}).error(function(data) {
			growl.warning(data.message);
		});
	}

$scope.reanudarEncuesta = function(idEncuesta) {
				if(!$scope.paramConfigPage.tiempoReanuda){
					$scope.reanudar();	
				}else{
						var wizardExample = WizardHandler.wizard('wizardExample');
						wizardExample.next();
						var pocision=$scope.posicionActual==-1?0:$scope.posicionActual;
						$scope.cambiarPregunta(null,$scope.encuestaDetalle.encuesta.secciones[pocision],1);
				}	
	};
	
});