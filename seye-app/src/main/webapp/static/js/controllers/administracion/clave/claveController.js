angular.module(appTeclo).controller('claveController',
    function($scope, userService, $timeout, showAlert, growl, claveService, userService) {

        $scope.lists = {
            parametros: [],
            claves: [],
            clavesBackup: [],
            perfiles: [],
            estatus: []
        };

        /* $scope.order = 'estatus';
        $scope.reverse = false; */

        $scope.generales = {
            actual: {},
            actualBackup: {},
            tipo: 'password',
            filter: '',
            sugerido: '',
            clave: {
                txValor: ''
            }
        };

        //	Datetimepicker
        $scope.dateTimePickerOptions = {
            format: 'DD/MM/YYYY hh:mm:ss',
            minDate: moment(),
            maxDate: moment().add(2, 'day')
        };

        $scope.banderas = {
            eye: true,
            modal: false,
            selectPerfil: false
        };

        function filtroParametro(st, list) {
            if (list != undefined && list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    if (list[i].stActual == st) {
                        angular.copy(list[i], $scope.generales.actual);
                        angular.copy(list[i], $scope.generales.actualBackup);
                        break;
                    }
                }
            }
        };

        function claves() {
            claveService.claves().success(function(data) {
                if (data.length > 0)
                    data.forEach(e => {
                        e.eye = true;
                        e.tipo = 'password';
                        e.cdcolor = e.estatus.cdColor;
                    });
                angular.copy(data, $scope.lists.clavesBackup);
                angular.copy(data, $scope.lists.claves);
                filtroParametro(1, $scope.lists.claves);
            }).error(function(data) {
                growl.error(data.message);
            });
        };

        $scope.verClave = function(data) {

            if (data.txValor == undefined) {
                growl.error(`No existe valor a visualizar`);
                return;
            }


            // RESTABLECEMOS LOS VALORES DE LAS LISTAS
            restablecerClave();
            if ($scope.banderas.eye) {
                let obj = new Object({ password: data.txValor, action: 'decrypt' });
                userService.toggleEncryption(obj).success(function(result) {
                    data.txValor = result;
                    $scope.banderas.eye = false;
                    $scope.generales.tipo = 'text';
                }).error(function(e) {
                    if (e !== null) { growl.error(`Ocurrió un error en la petición ${e.message}`); } else { growl.error('Falló la petición'); }
                });
            } else {
                data.txValor = $scope.generales.actualBackup.txValor;
                $scope.banderas.eye = true;
                $scope.generales.tipo = 'password';
            }
        };

        $scope.claveTabla = function(data) {
            // DEBEMOS RESTABLECER LOS DATOS DEL PASSWORD
            if (!$scope.banderas.eye) {
                $scope.generales.actual.txValor = $scope.generales.actualBackup.txValor;
                $scope.banderas.eye = true;
                $scope.generales.tipo = 'password';
            }
            if (data.eye) {
                restablecerClave();
                let obj = new Object({ password: data.txValor, action: 'decrypt' });
                userService.toggleEncryption(obj).success(function(result) {
                    data.txValor = result;
                    data.eye = false;
                    data.tipo = 'text';
                }).error(function(e) {
                    if (e !== null) { growl.error(`Ocurrió un error en la petición ${e.message}`); } else { growl.error('Falló la petición'); }
                });
            } else {
                restablecerClave();
            }
        };

        function restablecerClave() {
            for (var i = 0; i < $scope.lists.claves.length; i++) {
                for (var j = 0; j < $scope.lists.clavesBackup.length; j++) {
                    if ($scope.lists.claves[i].idPassword == $scope.lists.clavesBackup[j].idPassword) {
                        $scope.lists.claves[i].eye = true;
                        $scope.lists.claves[i].tipo = 'password';
                        $scope.lists.claves[i].txValor = $scope.lists.clavesBackup[j].txValor;
                        break;
                    }
                }
            }
        };

        $scope.nuevaClave = function() {
            claveSugerido();
        };

        function claveSugerido() {
            claveService.clavesSugerido().success(function(data) {
                showAlert.confirmacion(`Se ha generado la siguiente clave sugerida ${data}. ¿Desea utilizarla?`,
                    //Aceptar
                    function() {
                        $scope.banderas.modal = true;
                        $scope.generales.clave.txValor = data;
                        $timeout(function() {
                            $scope.banderas.selectPerfil = true;
                        }, 900);
                    },
                    //Cancelar
                    function() {
                        $scope.banderas.modal = true;
                        $timeout(function() {
                            $scope.banderas.selectPerfil = true;
                        }, 900);
                    }
                );
            }).error(function(data) {
                growl.error(data.message);
            });
        };

        $scope.descartar = function() {
            $scope.banderas.modal = false;
            $scope.generales.clave = {};
        };

        $scope.guardar = function(data, form) {
            if (form.$invalid) {
                showAlert.requiredFields(form);
                growl.error('Formulario incompleto', { ttl: 4000 });
                return;
            } else {

                $scope.lists.clavesBackup = [];
                $scope.lists.claves = [];

                var perfiles = [];
                perfiles.push(data.perfiles);
                data.perfiles = perfiles;
                claveService.nueva(data).success(function(data) {
                    if (data) {
                        claves();
                        $scope.descartar();
                    } else {
                        growl.error("Ocurrió un error inesperado al momento de guardar la nueva clave");
                        $scope.descartar();
                    }
                }).error(function(error) {
                    growl.error(data.message);
                    $scope.descartar();
                });

            }
        };

        function st() {
            claveService.st().success(function(data) {
                for (let i = 0; i < data.length; i++) {
                    if (data[i].cdStPassword == 'TEE024C_HIST') {
                        data.splice(i, 1);
                        break;
                    }
                }
                $scope.lists.estatus = data;
                $scope.generales.clave.estatus = $scope.lists.estatus[0];
            }).error(function(error) {
                growl.error(error.message);
            });
        };

        function onControllerInit() {
            claves();
            st();
        };

        $scope.activarClave = function(vo) {
            showAlert.confirmacion('Se reemplazará la contraseña actual ¿Desea continuar?', ejecutaActivar, vo, cancelaActivar);
        };

        function ejecutaActivar(vo) {
            claveService.activar(vo).success(function(data) {
                if (data) {
                    claves();
                } else {
                    growl.error("Ocurrió un error inesperado al momento de guardar la nueva clave");
                }
            }).error(function(error) {
                growl.error(error.message);
            });
        };

        function cancelaActivar() {
            return;
        };

        onControllerInit();

    });