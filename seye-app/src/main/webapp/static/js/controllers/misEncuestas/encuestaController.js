angular.module(appTeclo).controller('encuestaController', function($controller, $timeout, $rootScope, $interval, $scope, $localStorage, $location, $window,
    $timeout, growl, ModalService, showAlert, encuestaService, idEncuesta, idUsuarioEncuesta, idUsuEncuIntento, WizardHandler) {

    $scope.controllerActual = 'encuestaController';
    $scope.paramConfigPage = {
        bigCurrentPage: 1,
        bigTotalItems: 0,
        itemsPerPage: 0,
        maxSize: 0,
        tiempoEncuesta: 0,
        tiempoReanuda: false,
        tiempoCorriendo: false,
        flagTimer: true,
        segundo: 0,
        minuto: 0,
        hora: 0,
        flagFinalizaEncueta: false
    };
    $scope.formato = '0';
    var iniciarConteo = undefined;


    $scope.idEncuesta = idEncuesta;
    $scope.encuestaDetalle = new Object({
        idUsuarioEncuesta: idUsuarioEncuesta,
        usuario: undefined,
        nuIntentos: 0,
        stAplicaEncuesta: false,
        encuesta: new Object({
            idEncuesta: undefined,
            cdEncuesta: undefined,
            nbEncuesta: undefined,
            txEncuesta: undefined,
            txInstrucciones: undefined,
            nuPreguntas: 0,
            tipoEncuesta: new Object({}),
            secciones: new Array()
        })
    });

    $scope.detalleFinalEncuesta = {
        idUsuEncuIntento: idUsuEncuIntento,
        usuario: null,
        fhInicio: null,
        fhFin: null,
        StEncuestaVO: null,
        nuCalificacion: null,
        stCalificacion: null,
        stMostrar: null,
        nuPreguntas: null,
        nuPreguntasCorrectas: null,
        nuPreguntasIncorr: null,
        nuPreguntasNoRespondidas: null
    };

    $scope.encuestaDetalleFinal = new Object({
        idUsuarioEncuesta: undefined,
        usuario: undefined,
        nuIntentos: 0,
        stAplicaEncuesta: false,
        encuesta: new Object({
            idEncuesta: undefined,
            cdEncuesta: undefined,
            nbEncuesta: undefined,
            txEncuesta: undefined,
            txInstrucciones: undefined,
            nuPreguntas: 0,
            tipoEncuesta: new Object({}),
            secciones: new Array()
        })
    });

    $scope.posicionActual = -1;
    $scope.seccionSeleccion = "";
    $scope.seccionVO = undefined;
    //$scope.seccionContestada=new Array();
    $scope.redireccionar = false;
    $scope.preguntasContestadasEncuesta = 0;

    $scope.currentLocation = $location.url();
    var targetPath = "";
    var targetSearch = "";
    var targetHash = "";


    var startWatchingTimer = $timeout(startWatchingForLocationChanges, 0, false);

    $scope.$on("$locationChangeSuccess", function handleLocationChangeSuccessEvent(event) {
        $scope.currentLocation = $location.url();
    });

    var stopWatchingLocation = null;

    $scope.showConfirmacionSaliryLiberar = function(messageTo, action) {
        ModalService.showModal({
            templateUrl: 'views/templatemodal/templateModalConfirmacion.html',
            controller: 'mensajeModalController',
            inputs: { message: messageTo }
        }).then(function(modal) {
            modal.element.modal();
            modal.close.then(function handleResolve(result) {
                if (result) {
                    $location.path(targetPath)
                        .search(targetSearch)
                        .hash(targetHash);

                    stopWatchingLocation();

                    $scope.$applyAsync(startWatchingForLocationChanges);
                    action();
                }
            });
        });
    };


    function handleLocationChangeStartEvent(event) {

        targetPath = $location.path();
        targetSearch = $location.search();
        targetHash = $location.hash();
        let valor = targetPath.substring(1, 14);
        if(targetPath == '/login'){
            $interval.cancel(iniciarConteo);
        };
        if (((!$scope.paramConfigPage.flagFinalizaEncueta && valor != targetPath && !$scope.redireccionar && $scope.paramConfigPage.tiempoReanuda) ||
                (!$scope.paramConfigPage.flagFinalizaEncueta && valor != targetPath && !$scope.redireccionar && $scope.paramConfigPage.tiempoCorriendo)) &&
            (targetPath != undefined && targetPath != '/login')
        ) {
            event.preventDefault();
            $scope.showConfirmacionSaliryLiberar('Al salir de la evaluación, el tiempo  seguirá avanzando  ¿Desea continuar?', function() {
                if ($scope.posicionActual >= 0) {
                    $scope.controllerActual = 'NA';
                    $scope.guardaAvancePorPagina($scope.paramConfigPage.bigCurrentPage);
                   
                }
                $interval.cancel(iniciarConteo);
            });
        }
    };

    function startWatchingForLocationChanges() {
        stopWatchingLocation = $scope.$on("$locationChangeStart", handleLocationChangeStartEvent);
    }

    $scope.estatusFinalizaEncuesta = function() {
        $timeout(() => {
            let wizardExampled = WizardHandler.wizard('wizardExample');
            if ($scope.paramConfigPage.flagFinalizaEncueta) {
                if (wizardExampled)
                    wizardExampled.goTo(2); // se envia al paso incompleto
            } else {
                if (wizardExampled)
                    wizardExampled.goTo(0);
            }
        }, 300);
    };

    $scope.saveTiempo = function(tiempo) {
        encuestaService.saveTiempo(idUsuEncuIntento, tiempo + 1).success(function(data) {}).error(function(data) {
            growl.warning(data.message);
        });
    };

    $scope.reanudar = function() {
        encuestaService.validaTiempoIntento(idUsuEncuIntento).success(function(data) {
            if (data.stContinua == 1) {
                $scope.paramConfigPage.tiempoEncuesta = data.tpRestante;
                let wizardExampled = WizardHandler.wizard('wizardExample');
                if (wizardExampled)
                    wizardExampled.goTo(1); // 
                var pocision = $scope.posicionActual == -1 ? 0 : $scope.posicionActual;
                $scope.cambiarPregunta(null, $scope.encuestaDetalle.encuesta.secciones[pocision], 1);
                $scope.iniciarConteo();

            } else {

                $scope.paramConfigPage.segundo = 4;
                $scope.paramConfigPage.minuto = 0;
                $scope.paramConfigPage.hora = 0;
                $scope.paramConfigPage.flagFinalizaEncueta = true;
                $scope.redireccionar = true;
                showAlert.aviso("Se ha terminado el tiempo de la evaluación", $scope.regresarEncuestas2);

            }
        }).error(function(data) {
            growl.warning(data.message);
        });
    };

    $scope.getEncuestaUsuario = function(idEncuesta, idUsuarioEncuesta) {

        encuestaService.getEncuestaUsuario(idEncuesta, idUsuarioEncuesta).success(function(data) {
            if (data != null) {
                var detalle = data;
                $scope.encuestaDetalle;
                for (let i in detalle.encuesta.secciones) {
                    for (let j in detalle.encuesta.secciones[i].preguntas) {
                        for (const k in detalle.encuesta.secciones[i].preguntas[j].opciones) {
                            if (detalle.encuesta.secciones[i].preguntas[j].opciones[k].stMarcado == 1) {
                                $scope.preguntasContestadasEncuesta++;
                                detalle.encuesta.secciones[i].nuPreguntasContestadas++;
                                detalle.encuesta.secciones[i].preguntas[j].stMarcado = 1;
                            }
                        }
                    }
                }
                $scope.encuestaDetalle = detalle;
                $scope.paramConfigPage.tiempoEncuesta = detalle.encuesta.nuTiempo;
                var estatusEncuesta = $scope.encuestaDetalle.intentoDetalleVO.stEncuesta.cdStEncuesta;
                if (estatusEncuesta == 'EC' || estatusEncuesta == 'FIN') {
                    $scope.paramConfigPage.tiempoCorriendo = true;
                };

            } else {
                growl.warning("Sin evaluaciones por asignar", { ttl: 5000 });
            }
        }).error(function(data) {
            growl.warning(data.message);
        });
    };

    $scope.regresarInstruccion = function(nuPagina) {
        $scope.encuestaDetalle.intentoDetalleVO.stEncuesta.cdStEncuesta = "EC";
        $scope.guardaAvancePorPagina(nuPagina);
        //$scope.saveEncuesta(seccionVO);
        //$scope.getEncuestaUsuario(idEncuesta, idUsuarioEncuesta);

    };
    $scope.pausarEncuesta = function(nuPagina, tiempo) {
        //$scope.saveTiempo(tiempo)
        $scope.redireccionar = true;
        showAlert.confirmacion("¿Desea guardar la evaluación?", $scope.guardaAvancePorPagina, nuPagina, $scope.testCancelConfirmacion2);

    };



    $scope.$watch("paramConfigPage.bigCurrentPage", function(newValue, oldValue) {
        if (newValue === oldValue) {
            return;
        }
        $scope.guardaAvancePorPagina(oldValue);
    });

    $scope.guardaAvancePorPagina = function(numPagina) {
        if($scope.redireccionar){
            $interval.cancel(iniciarConteo);
        };
        if (document.getElementById("myTable"))
            document.getElementById("myTable").scrollTop = 0;
        $('html, body').animate({ scrollTop: 0 }, 'slow');
        var guardaSeccionPorPaginaVO = new Object();
        var preguntasPorPagina = new Array();
        var seccionVO = new Object();
        var seccionVO = angular.copy($scope.encuestaDetalle.encuesta.secciones[$scope.posicionActual]);
        var preguntasPorPagina = seccionVO.preguntas.slice(((numPagina - 1) * $scope.paramConfigPage.itemsPerPage), ((numPagina) * $scope.paramConfigPage.itemsPerPage));
        seccionVO.preguntas = preguntasPorPagina;
        $scope.saveEncuesta(seccionVO);
    };


    $scope.saveEncuesta = function(seccionVO) {
        var guardarSeccion = false;
        var seccionContestada = new Array();
        var listPreguntaSeccion = angular.copy(seccionVO.preguntas);
        for (let i in listPreguntaSeccion) {
            let guardar = false;
            let objectEncuesta = new Object({
                idEncuesta: angular.copy($scope.encuestaDetalle.encuesta.idEncuesta),
                idSeccion: angular.copy(seccionVO.idSeccion),
                idPregunta: undefined,
                idOpcion: undefined,
                idIntento: idUsuEncuIntento
            });

            objectEncuesta.idPregunta = listPreguntaSeccion[i].idPregunta;
            for (let j in listPreguntaSeccion[i].opciones) {
                if (listPreguntaSeccion[i].opciones[j].stMarcado === 1) {
                    guardar = true
                    objectEncuesta.idOpcion = listPreguntaSeccion[i].opciones[j].idOpcion != undefined ? listPreguntaSeccion[i].opciones[j].idOpcion : 0;
                }
            }
            if (guardar) {
                seccionContestada.push(objectEncuesta);
            }
        }
        encuestaService.saveRespuestaEncuesta(seccionContestada).success(function(data) {
            if (data == true) {
                if ($scope.redireccionar) {
                    $timeout(() => {
                        $scope.regresarEncuestas()
                    }, 1000);
                }
                guardarSeccion = true;
                //  growl.success("Se guardaron respuestas ",{ ttl: 5000 });
            } else
                growl.success("No se pudieron guardar respuestas", { ttl: 5000 });
            guardarSeccion = false;

        }).error(function(data) {
            if (data != null && data.status != null && data.status == 400) {
                $scope.paramConfigPage.tiempoReanuda = true;
                $scope.paramConfigPage.flagFinalizaEncueta = true;
                showAlert.aviso(data.message, $scope.regresarEncuestas2);
                // growl.error(data.message, { ttl: 3000 });
                /// $scope.consultaEvaEvaluados();
            } else {
                growl.error('Ha ocurrido un error al tratar de activar la evaluación, favor de validar.', { ttl: 4000 });
            }

            guardarSeccion = false;
        });
        return guardarSeccion;
    };


    $scope.cambiarPregunta = function(nuPagina, seccionVO, instruccion) {
        var guardarSeccion = false;
        $scope.paramConfigPage.flagTimer = true;
        if (nuPagina != null) {
            guardarSeccion = $scope.guardaAvancePorPagina(nuPagina);
        }
        if (seccionVO != undefined || seccionVO != null) {
            $scope.posicionActual = $scope.encuestaDetalle.encuesta.secciones.indexOf(seccionVO);
            var evaluaContestadas = $scope.encuestaDetalle.encuesta.secciones[$scope.posicionActual].nuPreguntasContestadas != undefined ?
                $scope.encuestaDetalle.encuesta.secciones[$scope.posicionActual].nuPreguntasContestadas : 0;
            $scope.seccionSeleccion = seccionVO.cdSeccion;
            $scope.seccionVO = seccionVO;
            $scope.encuestaDetalle.encuesta.secciones[$scope.posicionActual].nuPreguntasContestadas = evaluaContestadas;
            $scope.paramConfigPage.bigTotalItems = $scope.seccionVO.preguntas.length;
            if (instruccion != 1) {
                $scope.paramConfigPage.bigCurrentPage = 1;
                document.getElementById("myTable").scrollTop = 0;
                $('html, body').animate({ scrollTop: 0 }, 'slow');
            }
        } else {
            growl.warning("No se pudo cargar seccion", { ttl: 5000 });
        }
    };


    $scope.cargarEncuesta = function(idEncuesta, nuIntentos) {
        encuestaService.cargarEncuesta(idEncuesta, nuIntentos).success(function(data) {
            if (data == true) {
                guardarSeccion = true;
                growl.success("Se Guardo Sección", { ttl: 5000 });
            } else {
                growl.success("No Se Pudo Guardar Sección", { ttl: 5000 });
            }
        }).error(function(data) {
            growl.warning(data.message);
        });
    };

    //Funcion obtiene las respuestas por parametros de busqueda
    $scope.finalizaEncuesta = function(tiempo) {
        var lisSeccionesValid = new Array();
        var secciones = $scope.encuestaDetalle.encuesta.secciones;
        var preguntasSinContestar = false;
        for (let i in secciones) {
            if (secciones[i].nuPreguntasContestadas < secciones[i].preguntas.length) {
                preguntasSinContestar = true;
                let comprobacion = new Object({
                    nbSeccion: secciones[i].nbSeccion,
                    preguntasSinContestar: secciones[i].preguntas.length - secciones[i].nuPreguntasContestadas,
                    preguntas: new Array()
                });
                for (let j in secciones[i].preguntas) {
                    var contador = 0;
                    for (let k in secciones[i].preguntas[j].opciones) {
                        if (contador == 0 && secciones[i].preguntas[j].opciones[k].stMarcado != 1) {
                            comprobacion.preguntas.push(secciones[i].preguntas[j]);
                            contador++
                        }
                    }

                }
                lisSeccionesValid.push(comprobacion);

            }
        }

        if (preguntasSinContestar) {
            growl.warning("Hay preguntas sin contestar en las secciones amarillas o rojas");
        } else {
            showAlert.confirmacion("¿Finalizar Evaluación?", $scope.testConfirmacion, $scope.object, $scope.testCancelConfirmacion);
        }
    };


    $scope.testCancelConfirmacion = function() {
       
    }

    $scope.testCancelConfirmacion2 = function() {
        // growl.info('',{title: ''});
        $scope.redireccionar = false;
       
    }

    $scope.testConfirmacion = function(object) {
        encuestaService.finalizaEncuesta($scope.detalleFinalEncuesta).success(function(data) {
            if (data != null) {
                $scope.paramConfigPage.segundo = 0;
                $scope.paramConfigPage.minuto = 0;
                $scope.paramConfigPage.hora = 0;
                $scope.paramConfigPage.flagTimer = false;
                ///$scope.saveStepTwo(); 
                $scope.asignarValores(data);
            } else {
                growl.success("No Se Pudo Finalizar la encuesta", { ttl: 5000 });
                guardarSeccion = false;
            }
        }).error(function(data) {
            growl.error(data.message);
        });
    };



    $scope.regresarEncuestas = function() {
        $scope.controllerActual = 'NA';
        $location.path("/encuestas");
        //$scope.redireccionar=false;

    };

    $scope.regresarEncuestas2 = function() {
        $timeout(() => {
            $scope.controllerActual = 'NA';
            $location.path("/encuestas");
        }, 1000);

    };

    //    Asignar Valores
    $scope.asignarValores = function(datos) {
        $scope.paramConfigPage.flagFinalizaEncueta = datos != null;
        $interval.cancel(iniciarConteo);
        $scope.paramConfigPage.tiempoReanuda = false;
        $scope.estatusFinalizaEncuesta();
        $scope.detalleFinalEncuesta = angular.copy(datos);
        $scope.encuestaDetalleFinal = angular.copy($scope.encuestaDetalle);
        growl.success("La evaluación se finalizó", { ttl: 5000 });
    };


    $scope.observaTiempo = function() {
        if ($scope.paramConfigPage.flagTimer) {
            if ($scope.paramConfigPage.tiempoEncuesta > 0) {
                $scope.paramConfigPage.tiempoEncuesta--;
                $scope.cambiaTiempo($scope.paramConfigPage.tiempoEncuesta);
            } else {
                $scope.paramConfigPage.segundo = 0;
                $scope.paramConfigPage.minuto = 0;
                $scope.paramConfigPage.hora = 0;
                $scope.paramConfigPage.flagTimer = false;
                $interval.cancel(iniciarConteo);
                $scope.guardaAvancePorPagina($scope.paramConfigPage.bigCurrentPage);
                $("#confirmacion").modal('hide'); //ocultamos el modal
                $('body').removeClass('modal-open'); //eliminamos la clase del body para poder hacer scroll
                $('.modal-backdrop').remove(); //eliminamos el backdrop del modal
                showAlert.aviso("Se ha terminado el tiempo de la evaluación", $scope.testConfirmacion);
            }

        }
    };

    $scope.cambiaTiempo = function(segundoRestante) {
        var segundoDB = segundoRestante;
        var segundos = segundoDB;
        var segundos_s = segundos % 60;
        var minutos = Math.floor(segundos / 60);
        var horas = Math.floor(minutos / 60);
        var minutos_s = minutos % 60;
        var dias = Math.floor(horas / 24);
        var horas_s = horas % 24;

        $scope.paramConfigPage.segundo = segundos_s;
        $scope.paramConfigPage.minuto = minutos_s;
        $scope.paramConfigPage.hora = horas_s;
    };




    //    Angular wizard
    $scope.saveWizard = function() {

        var wizardExample = WizardHandler.wizard('wizardExample');
        wizardExample.setEditMode(true);
        growl.info("Fin del angular wizard");

    };

    $scope.cancelWizard = function() {
        alert('Cancel');
    };
    $scope.iniciarConteo = function() {
        $scope.paramConfigPage.tiempoReanuda = true;
        iniciarConteo = $interval($scope.observaTiempo, 1000);

    };
    $scope.getEncuestaUsuario(idEncuesta, idUsuarioEncuesta);

    $scope.estatusFinalizaEncuesta();


});