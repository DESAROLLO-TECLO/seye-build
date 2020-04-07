angular.module(appTeclo).controller('encuestasController', function(encuestasService, ModalService, $rootScope, $scope, $timeout, $filter, $document, jwtService, storageService, showAlert, growl, catalogoService) {
	
	const SEARCH_ALL = 1;
	const SEARCH_EMAIL = 2;
	const SEARCH_NOM = 3;
	const SEARCH_APE = 4;
	const SEARCH_CDI = 5;
	const SEARCH_NUCEL = 6;
	const REGEXPEMAIL = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,6}$/;
	
	let gridOptionsBack = new Array();
	let dataOriginalBack = new Array();
	$scope.busqueda = {
		encuestas: "todos"
	};
	$scope.tabla = new Object({
        order: 'folio',
        reverse: false
    });
	$scope.view = new Object({
        filterSearchVal: '',
        rowsPerPageVal: 0
    })
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
        detalleIntentoResp: false
    });
	
	var options=[{descripcion:'TODOS',codigo:1,codigoString:'T'},
                 {descripcion:'EMAIL',codigo:2,codigoString:'EMAIL'},
                 {descripcion:'NOMBRE(S)',codigo:3,codigoString:'NB'},
                 {descripcion:'APELLIDO(S)',codigo:4,codigoString:'APS'},
                 {descripcion:'CÓDIGO DE IDENTIFICACIÓN',codigo:5,codigoString:'CDI'},
                 {descripcion:'NÚMERO DE CELULAR',codigo:6,codigoString:'NUC'}];
	
	var optionsAll=[{descripcion:'ACTIVOS',codigo:'AC',valor:1},
	                {descripcion:'INACTIVOS',codigo:'INAC',valor:0},
	                {descripcion:'ACTIVOS/INACTIVOS',codigo:'ACINAC',valor:null}];
	
	$scope.searchVO=new Object({options: options,
								typeSearch:options[0],
								searchAll:optionsAll[0],
								optionsForAll: optionsAll,
								optionsTpIdentifi:[],
								tipoIdentifVO : new Object({}),
						        showListView : false});

    $timeout(function() {
        $scope.flags.isModalUsers = true;
    }, 300);
	
    $scope.gridOptions = new Object({
        data: new Array(),
        urlSync: false,
        pagination: {
            itemsPerPage: '6'
        }
    });
    
    $scope.userVO=new Object({});    
    
    $scope.initController = function(){
    	encuestasService.getEncuestas().success(function(data) {
            if(data.length != 0){
            	extraerDatos(data.usuarioEncuestaListVO);
            }else{
            	growl.warning("Sin evaluaciónes por asignar", { ttl: 5000 });
            }
        }).error(function(e){
            if(e.status != undefined && e.status != 400) {
            	growl.error(e.status.descripcion, { ttl: 5000 });
            } else {
           	 	growl.error(e.message, { ttl: 5000 });
            }
        });
    };
    $scope.busquedaEncuestas = function(consulta){
    	switch(consulta){
    	case "todos":
    		angular.copy(gridOptionsBack, $scope.gridOptions.data);
            break;
    	case "pendientes":
    		angular.copy(gridOptionsBack, $scope.gridOptions.data);
    		for(var i = 0; i < gridOptionsBack.length; i++){
    			if($scope.gridOptions.data[i].estatusBusq == "Finalizado"){
    				$scope.gridOptions.data.splice(i,1);
    				i--;
    			}
    		}
    		break;
    	case "finalizadas":
    		angular.copy(gridOptionsBack, $scope.gridOptions.data);
    		for(var i = 0; i < gridOptionsBack.length; i++){
    			if($scope.gridOptions.data[i].estatusBusq != "Finalizado"){
    				$scope.gridOptions.data.splice(i,1);
    				i--;
    			}
    		}
    		break;
    	}
    }
    extraerDatos = function(data){
    	angular.copy(data,dataOriginalBack);
    	let encuestas = new Array();
    	for(var i = 0; i < data.length; i++){
    		encuestas.push({idEncuesta: null});
    		encuestas[i].idUsuarioEncuesta = data[i].idUsuarioEncuesta;
    		encuestas[i].idEncuesta = data[i].encuesta.idEncuesta;
    		encuestas[i].cdEncuesta = data[i].encuesta.cdEncuesta;
    		encuestas[i].nbEncuesta = data[i].encuesta.nbEncuesta;
    		encuestas[i].txEncuesta = data[i].encuesta.txEncuesta;
    		encuestas[i].stAplicaEncuesta = data[i].stAplicaEncuesta;
    		encuestas[i].nuCalificacion = 0;
    		encuestas[i].seccion = "0 de " + data[i].encuesta.seccionListVO.length;
	    	encuestas[i].preguntas = "0  de " + totalPreguntas(data[i].encuesta.seccionListVO);
	    	encuestas[i].intentos = data[i].nuIntegerentos;
	    	encuestas[i].nuTiempo = data[i].encuesta.nuTiempo;
        	if(data[i].usuarioEncuestaIntentos.length != 0){
        		for(let j = 0; j < data[i].usuarioEncuestaIntentos.length; j++){
		        	if(data[i].usuarioEncuestaIntentos[j].stMostrar){
		        		encuestas[i].idUsuEncuIntento = data[i].usuarioEncuestaIntentos[j].idUsuEncuIntento;
		        		encuestas[i].fhInicio = data[i].usuarioEncuestaIntentos[j].fhInicio;
		    	        encuestas[i].fhFin = data[i].usuarioEncuestaIntentos[j].fhFin;
                        encuestas[i].nuCalificacion = data[i].usuarioEncuestaIntentos[j].nuCalificacion;
                        encuestas[i].aprobado = data[i].usuarioEncuestaIntentos[j].stCalificacion.cdStCalificacion;
		    	        if(data[i].encuesta.seccionListVO != null){
			    	        encuestas[i].seccion = data[i].usuarioEncuestaIntentos[j].nuSeccionesRsp + " de " + data[i].encuesta.seccionListVO.length;
			    	    	encuestas[i].preguntas = totalContestadas(data[i].usuarioEncuestaIntentos[j]) + " de " + totalPreguntas(data[i].encuesta.seccionListVO);
		    	        }else{
		    	        	encuestas[i].seccion = "No tiene sección asociado";
		    	        	encuestas[i].preguntas = "No tiene preguntas asociadas";
		    	        }   
		    	        encuestas[i].estatus = data[i].usuarioEncuestaIntentos[j].stEncuesta.nbStEncuesta;
                        encuestas[i].cdColor = data[i].usuarioEncuestaIntentos[j].stEncuesta.cdColor;
                        if(encuestas[i].aprobado == "A" && data[i].usuarioEncuestaIntentos[j].nuPreguntasVacias == 0){
                            encuestas[i].descargarPDF = true;
                        }else if(encuestas[i].aprobado == "F"){
                            encuestas[i].descargarPDF = false;
                        }
		    	        if(encuestas[i].fhFin){
		    	        	encuestas[i].accion = "Reintentar";
		    	        	encuestas[i].estatusBusq = data[i].usuarioEncuestaIntentos[j].stEncuesta.nbStEncuesta;
		    	        }else if(encuestas[i].fhInicio){
		    	        	encuestas[i].accion = "En Curso";
		    	        	encuestas[i].descargarPDF = false;
		    	        	encuestas[i].accionBtn = "CONTINUAR";
		    	        }else{
		    	        	encuestas[i].accion = "Iniciar Evaluación";
		    	        	encuestas[i].descargarPDF = false;
		    	        	encuestas[i].accionBtn = "INICIAR";
		    	        }
        			}
        		}
    		}
    	}
        $scope.gridOptions.data = encuestas;
    	angular.copy(encuestas, gridOptionsBack);
    	$("#select2-itemsOnPageSelect2-container").text(encuestas.length);																																										
    };
    totalContestadas =(intento)=>{
    	return intento.nuPreguntasCorrectas + intento.nuPreguntasIncorr;
    }
    totalPreguntas = function(secciones){
    	let totalPreguntas = 0; 
    	for(var j = 0; j < secciones.length; j++){
    		totalPreguntas = totalPreguntas + secciones[j].preguntas.length;
    	}
    	return totalPreguntas;
    }
    $scope.verIntentos = (index) => {
    	$scope.titleIntentos = "Intentos Realizados";
    	$scope.cdEncuesta = dataOriginalBack[index].encuesta.cdEncuesta;
		$scope.nbEncuesta = dataOriginalBack[index].encuesta.txEncuesta;
		$scope.nuSecciones = dataOriginalBack[index].encuesta.seccionListVO.length;
        $scope.nuPreguntas = totalPreguntas(dataOriginalBack[index].encuesta.seccionListVO);
        $scope.idUsuarioEncuesta = dataOriginalBack[index].idUsuarioEncuesta;
        $scope.listIntentos = new Array();
    	for(var j = 0; j < dataOriginalBack[index].usuarioEncuestaIntentos.length; j++){
    		if(dataOriginalBack[index].usuarioEncuestaIntentos[j].fhInicio != null && dataOriginalBack[index].usuarioEncuestaIntentos[j].stMostrar 
    			|| dataOriginalBack[index].usuarioEncuestaIntentos[j].fhFin != null){
	    		$scope.listIntentos.push(dataOriginalBack[index].usuarioEncuestaIntentos[j]);
	    		$scope.listIntentos.idUsuarioEncuesta = dataOriginalBack[index].idUsuarioEncuesta;
    		}
    	}
//    	$scope.listIntentos = dataOriginalBack[index].usuarioEncuestaIntentos;
    	$scope.marcarTodos();
    	$scope.flags.verIntentos = true;
    	$scope.flags.detalleIntento = false;
    }
    $scope.detalleIntento = (vista,idUsuarioEncuesta,idIntento) => {
    	$scope.preguntaRespuesta = new Array();
    	if(false){
    		$scope.titleIntentos = "Detalle del intento";
        	if(vista != 1)
        		$scope.btnRegresar = true;
        	$scope.flags.verIntentos = true;
        	$scope.flags.detalleIntento = true;
        	$scope.flags.detalleIntentoResp = false;
    	}else{
	    	encuestasService.detalleEncuesta(idIntento).success(function(encuestaIntento) {
	//    		angular.copy(encuestaIntento,$scope.preguntaRespuesta); 
	    		let listIntentos = new Array();
	    		for(var i = 0; i < dataOriginalBack.length; i++){
	        		if(dataOriginalBack[i].idUsuarioEncuesta == idUsuarioEncuesta){
	        			for(var j = 0; j < dataOriginalBack[i].usuarioEncuestaIntentos.length; j++){
	        	    		if(dataOriginalBack[i].usuarioEncuestaIntentos[j].fhInicio != null && dataOriginalBack[i].usuarioEncuestaIntentos[j].stMostrar 
	        	    			|| dataOriginalBack[i].usuarioEncuestaIntentos[j].fhFin != null){
	        		    		listIntentos.push(dataOriginalBack[i].usuarioEncuestaIntentos[j]);
	        	    		}
	        	    	}
	        			$scope.nuIntento = obtenerIntento(listIntentos,idIntento);
	        			$scope.preguntaRespuesta = dataOriginalBack[i].encuesta.seccionListVO;
	    	    		for(var j = 0; j < dataOriginalBack[i].usuarioEncuestaIntentos.length; j++){
	    		    		if(dataOriginalBack[i].usuarioEncuestaIntentos[j].idUsuEncuIntento == idIntento){
	    		    			$scope.cdEncuesta = dataOriginalBack[i].encuesta.cdEncuesta;
	    		    			$scope.nbEncuesta = dataOriginalBack[i].encuesta.txEncuesta;
	    		    			$scope.nuSecciones = dataOriginalBack[i].encuesta.seccionListVO.length;
	    		    	        $scope.nuPreguntas = totalPreguntas(dataOriginalBack[i].encuesta.seccionListVO);
	    		    	        $scope.nuCalificacion = dataOriginalBack[i].usuarioEncuestaIntentos[j].nuCalificacion;
	//    		    	        $scope.nuIntento = dataOriginalBack[i].usuarioEncuestaIntentos[j].nuCalificacion + 1;
	    		    	        $scope.fhInicio = dataOriginalBack[i].usuarioEncuestaIntentos[j].fhInicio;
	    		    	        $scope.fhFin = dataOriginalBack[i].usuarioEncuestaIntentos[j].fhFin;
	    		    	        $scope.nuCorrectas = dataOriginalBack[i].usuarioEncuestaIntentos[j].nuPreguntasCorrectas == null ? 0 : dataOriginalBack[i].usuarioEncuestaIntentos[j].nuPreguntasCorrectas;
	    		    	        $scope.nuIncorrectas = dataOriginalBack[i].usuarioEncuestaIntentos[j].nuPreguntasIncorr == null ? 0 : dataOriginalBack[i].usuarioEncuestaIntentos[j].nuPreguntasIncorr;
	    		    	        $scope.nuSinContestar = encuestaIntento.nuPreguntasVacias;
	    		    	        $scope.nbStCalificacion = encuestaIntento.stCalificacion.nbStCalificacion;
	    		    		}
	    	    		}
	        		}
	        	}
	//    		$scope.preguntaRespuesta =  $scope.preguntaRespuesta.filter( onlyUnique );
//	    		for(var i = 0; i < $scope.preguntaRespuesta.length; i++){
//	    			$scope.preguntaRespuesta[i].resumenRespuesta = new Array();
//		    		for(var j = 0; j < encuestaIntento.resumenRespuesta.length; j++){
//		    			if(encuestaIntento.resumenRespuesta[j].id.idSeccion == $scope.preguntaRespuesta[i].idSeccion){
//		    				$scope.preguntaRespuesta[i].resumenRespuesta.push(encuestaIntento.resumenRespuesta[j]);
//		    				$scope.preguntaRespuesta.stCalificacion = encuestaIntento.stCalificacion;
//		    			}
//		    		}
//	    		}
	    	
	        	$scope.titleIntentos = "Detalle del intento";
	        	if(vista != 1)
	        		$scope.btnRegresar = true;
	        	$scope.flags.verIntentos = true;
	        	$scope.flags.detalleIntento = true;
	        	$scope.flags.detalleIntentoResp = false;
	        }).error(function(e) {
	            if(e.descripcion != undefined){
	            	growl.error(e.descripcion,{ ttl: 5000 });
	            }else if(e.message != undefined) {
	            	growl.error(e.message,{ ttl: 5000 });
	            }else {showAlert.error('Falló la petición');}
	        });
    	}
    }
    function onlyUnique(value, index, self) { 
        return self.indexOf(value) === index;
    }
    obtenerIntento = (list,idIntento) =>{
    	list.sort(function (a, b){
    	    return (b.idUsuEncuIntento - a.idUsuEncuIntento)
    	})
    	let count = 0;
    	for(var i = 0; i < list.length; i++){
    		count++;
    		if(list[i].idUsuEncuIntento == idIntento){
    			break;
    		}
    	}
    	return count;
    }
    obtenerSecciones = (list) =>{
    	let secciones = new Array();
    	for(var i = 0; i < list.length; i++){
    		secciones.push(list)
    	}
    	return secciones;
    }
    $scope.regresarIntentos = () =>{
    	$scope.btnRegresar = false;
    	$scope.flags.detalleIntento = false;
    }
    $scope.cerrarVerIntentos = () =>{
    	$scope.btnRegresar = false;
    	$scope.flags.verIntentos = false;
    }
    verificarMarcarTodos = function() {
    	let count = 0;
    	for (let i = 0; i < $scope.listIntentos.length; i++) {
    		if($scope.listIntentos[i].checked)
    			count++;
    	}
    	if(count==$scope.listIntentos.length){
    		$scope.flags.todos = true;
    	} else {
    		$scope.flags.todos = false;
        }
    	$scope.generales.txMarcaTodos = $scope.flags.todos ? 'Desmarcar todos' : 'Marcar todos';
    }
    $scope.checkManual = function(intento){
    	intento.txAgregar = intento.checked ? 'Deseleccionar':'Seleccionar';
    	verificarMarcarTodos();
     }
    $scope.marcarTodos = function() {
    	$scope.generales.txMarcaTodos = $scope.flags.todos ? 'Desmarcar todos' : 'Marcar todos';
        for (let i = 0; i < $scope.listIntentos.length; i++) {
        	if($scope.listIntentos[i] != null && $scope.listIntentos[i].stCalificacion != null && $scope.listIntentos[i].stCalificacion.cdStCalificacion == 'A'){
        		$scope.listIntentos[i].checked = $scope.flags.todos ? true : false;
        		$scope.listIntentos[i].txAgregar = $scope.flags.todos ? 'Deseleccionar' : 'Seleccionar';
        	}
        }
    };
    $scope.mostrarPDF = (idUsuarioEncuesta,idUsuEncuIntento) => {
    	if(idUsuarioEncuesta == null)
    		idUsuarioEncuesta = $scope.listIntentos.idUsuarioEncuesta;
    	let listIdUsuEncuIntento = new Array();
    	let count = 0;
    	if(idUsuEncuIntento == null){
    		for (let i = 0; i < $scope.listIntentos.length; i++) {
    			if($scope.listIntentos[i].checked){
    				listIdUsuEncuIntento.push($scope.listIntentos[i].idUsuEncuIntento);
    				obtenerPDF(idUsuarioEncuesta,$scope.listIntentos[i].idUsuEncuIntento);
    			}else
    				count++;    				
            }
    		if(count == $scope.listIntentos.length)
    			growl.warning("No hay intentos seleccionados",{ ttl: 5000 });
    	}else{
	//    		listIdUsuEncuIntento.push(idUsuEncuIntento);
    		encuestasService.getDetallePDF(idUsuarioEncuesta,idUsuEncuIntento).success(function(archivo) {
//        		archivo = '././static/dist/ejemploPrueba/Ejemplo de encuesta.pdf';
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
    }
    obtenerPDF = (idUsuarioEncuesta,idUsuEncuIntento) =>{
    	encuestasService.getDetallePDF(idUsuarioEncuesta,idUsuEncuIntento).success(function(archivo) {
//    		archivo = '././static/dist/ejemploPrueba/Ejemplo de encuesta.pdf';
    		let filename = "Descarga Comprobante";
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
    $scope.showPdf = function (messageTo) {
        ModalService.showModal({
            templateUrl: 'views/templatemodal/templateModalPdf.html',
            controller: 'mensajeModalController',
            inputs: {message: messageTo}
        }).then(function (modal) {
            modal.element.modal();
        });
    };
    
    $scope.descargarExcel = function() {
        let peticionReporteVO = new Object();
        peticionReporteVO.header = getCabeceras();
        peticionReporteVO.values = getContenido($scope.gridOptions.data);
        peticionReporteVO.titulo = "Reporte Mis Evaluaciones"
        encuestasService.descargarReporteExcel(peticionReporteVO).success(function(data, status, headers) {
            let filename = headers('filename');
            let file = new Blob([data], { type: 'application/vnd.ms-excel;base64,' });
            encuestasService.downloadfile(file, filename);
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
        headers.push("CALIFICACIÓN");
        headers.push("SECCIÓN");
        headers.push("PREGUNTAS");
        headers.push("INTENTOS");
        return headers;
    };
    getContenido = (list) => {
        let array = new Array();
        for (let i = 0; i < list.length; i++) {
            let elemento = new Array();
            elemento.push(list[i].cdEncuesta);
            elemento.push(list[i].nbEncuesta);
            elemento.push(list[i].fhInicio);
            elemento.push(list[i].fhFin);
            elemento.push(list[i].nuCalificacion);
            elemento.push(list[i].seccion);
            elemento.push(list[i].preguntas);
            elemento.push(list[i].intentos);
            array.push(elemento);
        }
        return array;
    };
    /**
     * @desciption : 
     * * ARREGLO DE OBJETOS PARA IMÁGENES DE AVATARS
     */
    $scope.arrayAvatars = [
        {idAvatar:1,srcAvatar:'static/dist/img/male-1.png',isSelected:false},
        {idAvatar:2,srcAvatar:'static/dist/img/male-2.png',isSelected:false},
        {idAvatar:3,srcAvatar:'static/dist/img/female-3.png',isSelected:false},
        {idAvatar:4,srcAvatar:'static/dist/img/female-4.png',isSelected:false}
    ];
    
    /**
     * @title           :*CAMBIAR EL TIPO DE BÚSQUEDA DE USUARIOS*
     * @author          :DanielUnitis
     * @modified        :---
     * @createdate      :20/Ene/2020
     * @modifieddate    :--/--/--
     * @version         :1.0
     * @params          :Object model
     * @description     :
     * * Se muestra un input si es por Nombre, Apellido o Usuario
     * * Se muestra un combo si es por tipo de identificación y se setea por default la primera opción
     * * Se valida por código de tipo de búsqueda para habilitar el select de activos inactivos de TODOS
     */
    $scope.changeTypeSearch = function(model) {
    	
    	switch(model.codigo){
	    	case SEARCH_ALL:
	    		
	    		$scope.flags.isTypeSearchCdIdentifi = false;
	            $scope.flags.isTypeSearchInput = false;
	            $scope.flags.isTypeSearchAll = true;
	    		
	            $scope.searchVO.searchAll=optionsAll[0];
	            
	            break;
	    	case SEARCH_CDI:
	    		
	            $scope.flags.isTypeSearchInput = false;
	    		$scope.flags.isTypeSearchAll = false;
	            $scope.flags.isTypeSearchCdIdentifi = true;
	            
	    		break;
	    	default:
	    		
	    		$scope.flags.isTypeSearchInput = true;
    			$scope.flags.isTypeSearchAll = false;
    			$scope.flags.isTypeSearchCdIdentifi = false;
	    		
		        $scope.searchVO.searchInput = "";
		        
		        if($scope.formEncuestas.searchValue !== undefined) {
		            $scope.formEncuestas.searchValue.$setPristine();
		        }
	    		break;
    	}
    };
    
    /**
     * @title           :*MOSTRAR/OCULTAR PESTAÑA DE BÚSQUEDA O NUEVO REGISTRO DE USUARIO*
     * @author          :DanielUnitis
     * @modified        :----------
     * @createdate      :20/Ene/2020
     * @modifieddate    :--/--/----
     * @version         :1.0
     * @params          :String tab
     */
    $scope.toggleTabUser = function(tab) {
        switch(tab) {
            case 'search':
                $scope.flags.isSearchTab = true;
                $scope.flags.isNewTab = false;
                $scope.flags.isPassword = false;
                $scope.toggleDataNew('personal');
                break;
            case 'new':
            	$scope.flags.showMenuDataUser=false;
                $scope.flags.isNewTab = true;
                $scope.flags.isSearchTab = false;
                break;
        }
    };
    
    /**
     * @title           :*MOSTRAR/OCULTAR LAS PESTAÑAS DEL REGISTRO DE USUARIOS*
     * @author          :DanielUnitis
     * @modified        :----------
     * @createdate      :20/Ene/2020
     * @modifieddate    :--/--/----
     * @version         :1.0
     * @params          :String tab
     * @description     :
     * * Datos personales: true | false
     * * Datos del usuario: true | false
     * * Datos de contacto: true | false
     */
    $scope.toggleDataNew = function(tab) {
        switch(tab) {
            case 'personal':
                $scope.flags.isDataPersonal = true;
                $scope.flags.isDataUser = false;
                $scope.flags.isDataContact = false;
                break;
            case 'user':
                $scope.flags.isDataPersonal = false;
                $scope.flags.isDataUser = true;
                $scope.flags.isDataContact = false;
                break;
            case 'contact':
                $scope.flags.isDataPersonal = false;
                $scope.flags.isDataUser = false;
                $scope.flags.isDataContact = true;
                break;
        }
    };
    
    /**
     * @title           :*REALIZAR LA BÚSQUEDA DE USUARIOS*
     * @author          :CésarGómez
     * @modified        :CésarGómez
     * @createdate      :11/Ene/2019
     * @modifieddate    :18/Ene/2019
     * @version         :1.4
     * @description     :
     * * Se valida el formulario
     * * Cuando es por Nombre, Apellido o Usuario se realiza la petición getUsersByType
     * * Cuando es por Perfil se realiza la petición getUsersByProfile
     * * Se validan los resultados
     * * Cuando no se encuentran registros se manda mensaje y se regresan las banderas al estado original de búsqueda
     * * Cuando hay registros se llenan los arreglos de usuarios y se cambia el estado de las banderas para mostrar los resultados
     */
    $scope.searchUsers = function() {

        const VALIDATERESPONSE = function(array) {
            if(array.length === 0) {

                $scope.gridOptions.data = new Array();
                $scope.searchVO.arrayUsersOriginal = new Array();
                $scope.searchVO.arrayUsers = new Array();
                $scope.searchVO.arrayUsersTmp =  new Array();
                growl.error('No se encontraron registros');
                
             //   showAlert.aviso("No se encontraron registros",
                    //Aceptar
                  //  function() {
                        $scope.flags.isPromise = false;
                        $scope.flags.isFirstModal = true,
                        $scope.flags.isModalUsers = true;
                  //  }
              //  );
              return;

            } else {

                $scope.gridOptions.data = array;
                $scope.searchVO.arrayUsersOriginal = angular.copy(array);
                $scope.searchVO.arrayUsers = array;
                $scope.searchVO.arrayUsersTmp =  array;
                $scope.flags.isPromise = false;
                $scope.flags.isModalUsers = false;
            }
        };

        if($scope.formEncuestas.$invalid) {
            showAlert.requiredFields($scope.formEncuestas);
            growl.error('Formulario incompleto');
			
           // showAlert.requiredFields($scope.formEncuestas, 'modal');
        } else {

            let params = new Object({
            	tpBusqueda:'T',
            	valor:null,
            	estatusCuenta:null,
      		  	tipoIdentifVO: null,
      		  	cdIdentificacion:null
            });

            $scope.flags.isPromise = true;
            $scope.flags.isModalUsers = false;
            
            if($scope.flags.isTypeSearchAll){
            	
            	params.estatusCuenta=$scope.searchVO.searchAll.valor;
            	
            }else if($scope.flags.isTypeSearchCdIdentifi){
            	params.tpBusqueda='CD_IDENTIFICACION';
            	params.tipoIdentifVO=$scope.searchVO.tpIdentifi;
            	params.cdIdentificacion=$scope.searchVO.cdIdentificacion;
            }else if($scope.flags.isTypeSearchInput){
            	
            	params.tpBusqueda='default';
            	params.nbEmail=null;
            	params.nuCelular=null;
            	params.nbUser=null;
            	params.nbApaterno=null;
            	params.nbAmaterno=null;
            	params.cdOrigenRegistro=null;
            	
            	if($scope.searchVO.typeSearch.codigo == SEARCH_EMAIL){
            		
            		params.nbEmail=$scope.searchVO.searchInput;
            	
            	}else if($scope.searchVO.typeSearch.codigo == SEARCH_NOM){
            		
            		params.nbUser=$scope.searchVO.searchInput;	
            	
            	}else if($scope.searchVO.typeSearch.codigo == SEARCH_APE){
            		
            		params.nbApaterno=$scope.searchVO.searchInput;	
            		params.nbAmaterno=$scope.searchVO.searchInput;
            	
            	}else if($scope.searchVO.typeSearch.codigo == SEARCH_NUCEL){
            		
            		params.nuCelular=$scope.searchVO.searchInput;	
            	
            	}
            }
                
            let jsonRequest=JSON.stringify(params);
            userCiudadanosService.getUsersCiudadanos(jsonRequest).success(function(users) {

                VALIDATERESPONSE(users);

            }).error(function(e) {
            	$scope.flags.isPromise = false;
                $scope.flags.isModalUsers = true;
                $scope.flags.isSearchTab=true;
                $scope.flags.isFirstModal = true;
                
                if(e.descripcion != undefined){
                	growl.error(e.descripcion,{ ttl: 4000 });
                }else if(e.message != undefined) {
                	growl.error(e.message,{ ttl: 4000 });
                }else {showAlert.error('Falló la petición');}
            });
//            $scope.flags.isFirstModal = false;
        }
    };
    
    
    /**
     * @title           :*GUARDAR USUARIO*
     * @author          :DanielUnitis
     * @modified        :DanielUnitis
     * @createdate      :16/Ene/2019
     * @modifieddate    :21/Ene/2019
     * @version         :1.6
     * @params          :Object userVO, String action
     * @description     :
     * * Se valida el formulario
     * * Se ejecuta la petición para guardar el usuario nuevo
     */
    $scope.saveOrUpdateUser = function(userVO,action){
    	 
    	if($scope.formEncuestas.$invalid) {
    		 showAlert.requiredFields($scope.formEncuestas, 'modal');
    		 
    		 if($scope.formEncuestas.nbUsuario.$invalid
    	                || $scope.formEncuestas.nbApaterno.$invalid
    	                || $scope.formEncuestas.nbAmaterno.$invalid
    	                || $scope.formEncuestas.catTpIdentifi.$invalid
    	                || $scope.formEncuestas.cdIdentificacion.$invalid){
    			 $scope.toggleDataNew('personal');
    			 growl.error('Formulario Incorrecto');
    			 return;
    		 }
    	                
    		 
    		 if($scope.formEncuestas.cdUsuario.$invalid && action == 'edit'
                     || $scope.formEncuestas.nbContrasenia.$invalid){
    			 $scope.toggleDataNew('user');
    			 growl.error('Formulario Incorrecto');
    			 return;
    		 }
                     
    		 
    		 if($scope.formEncuestas.nbEmail.$invalid 
                     || $scope.formEncuestas.nuCelular.$invalid){
    			 $scope.toggleDataNew('contact');
    			 growl.error('Formulario Incorrecto');
    			 return;
    		 }
    		 
    		 growl.error('Formulario Incorrecto');
    		 return;
    	 }
    	
    	switch(action) {
	        case "new":
	        	userCiudadanosService.saveUsuarioExterno(userVO).success(function(user) {
	                
	                growl.success('Usario guardado correctamente', { ttl: 4000 });
	                $scope.toggleCloseSaveUser('new');
	                
	                $scope.gridOptions.data.push(user);// se actualiza la lista con el nuevo registro

	            }).error(function(e){
	                if(e.status != undefined && e.status != 400) {
	               	 growl.error(e.status.descripcion,{ ttl: 4000 });
	                } else {
	               	 growl.error(e.message,{ ttl: 4000 });
	                }
	            });
	            break;
	        case "edit":
	        	userCiudadanosService.updateUsuarioExterno(userVO).success(function(user) {
	                
	        		growl.success('Usuario actualizado correctamente', { ttl: 4000 });
    	            $scope.flags.isModalUsers = false;
    	            $scope.flags.isNewTab = false;
    	            $scope.flags.isModalUsersEdit = false;
	        			                
	            }).error(function(e){
	                if(e.status != undefined && e.status != 400) {
	               	 growl.error(e.status.descripcion,{ ttl: 4000 });
	                } else {
	               	 growl.error(e.message,{ ttl: 4000 });
	                }
	            });
	        	
	            break;
    	}
    	
    	
    }
    
    /**
     * @title           :*EDITAR USUARIO*
     * @author          :CésarGómez
     * @modified        :CésarGómez
     * @createdate      :17/Ene/2019
     * @modifieddate    :18/Ene/2019
     * @version         :1.1
     * @params          :Object userVO
     * @description     :
     * * Se agrega la propiedad de foto al objeto recibido
     * * Se valida si el usuario es el logueado actualmente para desencriptar su contraseña
     * * Se realiza una copia del objeto recibido
     * * Se switchean los flags para mostrar el modal de edición de usuario (Se reutiliza el formulario de nuevo usuario)
     */
    $scope.editUser = function(userVO) {
       
        $scope.userTmp = new Object();
        $scope.userVO = userVO;
        $scope.userVO.patternEmail = REGEXPEMAIL;
        
        angular.copy($scope.userVO, $scope.userTmp);

        $scope.flags.isModalUsers = true;
        $scope.flags.isNewTab = true;
        $scope.flags.isSearchTab = false;
        $scope.flags.isModalUsersEdit = true;
        $scope.flags.showMenuDataUser=true;
        
        $timeout(function() {
        	for(let i=0; i<userVO.listTipoIdentifiAndValue.length; i++){
        		let item=userVO.listTipoIdentifiAndValue[i];
        		let size=$scope.searchVO.optionsTpIdentifi.length;
    			let j;
    			for(j=0; j<size; j++){
    				if(item.tipoIdentificacion.idTipoIndenti == $scope.searchVO.optionsTpIdentifi[j].idTipoIndenti){
    					userVO.listTipoIdentifiAndValue[i].tipoIdentificacion=$scope.searchVO.optionsTpIdentifi[j];
    					$("#select2-catTpIdentifi"+item.idAuxView+"-container").text($scope.searchVO.optionsTpIdentifi[j].nbTipoDocumen);
    					break;
    				}
    			}
        	}
        }, 300);
    };
    
    /**
     * @title           :*MOSTRAR EL MODO DE VISTA (MOSAICO O LISTA) DE LOS USUARIOS ENCONTRADOS EN LA BÚSQUEDA*
     * @author          :CésarGómez
     * @modified        :----------
     * @createdate      :14/Ene/2019
     * @modifieddate    :--/--/----
     * @version         :1.0
     * @params          :String mode
     */
    $scope.toggleModeView = function(mode) {
        switch(mode) {
            case 'grid':
                $scope.flags.isGridMode = true;
                $scope.flags.isListMode = false;
                break;
            case 'list':
                $scope.flags.isListMode = true;
                $scope.flags.isGridMode = false;
                break;
            default:
                $scope.flags.isGridMode = true;
                $scope.flags.isListMode = false;
        }

        $scope.flags.isButtonMode = false;

        $timeout(function() {
            $scope.flags.isButtonMode = true;
        }, 500);
    };
    
    /**
     * @title           :*ABRIR | CERRAR MODAL DE EDICIÓN O NUEVO USUARIO*
     * @author          :CésarGómez
     * @modified        :----------
     * @createdate      :18/Ene/2019
     * @modifieddate    :--/--/----
     * @version         :1.0
     * @params          :String action
     * @description     :
     * * Se comprueba si se ejecuta el modal de nuevo usuario o edición de usuario
     * * Cuando es nuevo usuario se limpia el formulario y se deja listo para registrar un nuevo usuario
     * * Cuando es edición se comprueba que no se hayan hecho cambios
     * * Cuando es edición se confirma el cierra del modal si se realizaron cambios al usuario
     */
    $scope.toggleCloseSaveUser = function(action) {

        let clearUser = function() {

            $scope.userVO = new Object({
                patternEmail: REGEXPEMAIL,
                tipoIdentificacion : $scope.searchVO.tipoIdentifVO
            });
            
            if($scope.searchVO.tipoIdentifVO != undefined)
            	$("#select2-catTpIdentifi-container").text($scope.searchVO.tipoIdentifVO.nbTipoDocumen);
            else
            	$("#select2-catTpIdentifi-container").text('Seleccione una opción');
            
            $scope.formEncuestas.$setPristine();
            $scope.toggleDataNew('personal');

            $scope.flags.isPassword = false;

            if(!$scope.flags.isFirstModal) {
                $timeout(function() {
                    $scope.flags.isModalUsers = false;
                }, 200);
                $('.modaluser__form').removeClass('modalAnimateIn').addClass('modalAnimateOut');
            }
        };

        switch(action) {
            case 'new':
                clearUser();
                break;
            case 'edit':
                
                if(angular.equals($scope.userTmp, $scope.userVO)) {
                    $scope.flags.isNewTab = false;
                    $scope.flags.isModalUsersEdit = false;
                    $scope.flags.isPassword = false;
                    clearUser();
                } else {
                    showAlert.confirmacion("Esta acción descartará todos los cambios realizados. ¿Desea continuar?",
                        //Aceptar
                        function() {

                            $scope.flags.isNewTab = false;
                            $scope.flags.isModalUsersEdit = false;
                            $scope.flags.isPassword = false;

                            $scope.gridOptions.data = $scope.gridOptions.data.map( user => {
                                
                                if(user.idUsuarioExterno === $scope.userTmp.idUsuarioExterno) {
                                    Object.assign(user, $scope.userTmp);
                                }

                                return user;
                            });

                            clearUser();
                        },
                        //Cancelar
                        function() {
                            $scope.flags.isModalUsers = true;
                            $scope.flags.isNewTab = true;
                            $scope.flags.isModalUsersEdit = true;
                        }
                    );
                }
                break;
        }
    };
    
    
    /**
     * @title           :*VISTA DE TARJETA DE USUARIO EN MODO MOSAICO (GRID)*
     * @author          :CésarGómez
     * @modified        :CésarGómez
     * @createdate      :11/Ene/2019
     * @modifieddate    :14/Ene/2019
     * @version         :1.1
     * @params          :Event event, String option
     * @description     :
     * * Se cambia de vista frontal a vista anversa, y viceversa de acuerdo a la opción mandada
     * * Se lanza la ejecución de la función flipcard para realizar el cambio de vista (front | back)
     */
    $scope.toggleUserCard = function(event, option) {

        if($scope.eventCardSelected !== undefined) {
            flipCard($scope.eventCardSelected, 'close');
        }

        flipCard(event, option);
    };

    /**
     * @title           :*REALIZAR LA VOLTERETA DE LOS BOX DE CADA USUARIO (MODO MOSAICO)*
     * @author          :CésarGómez
     * @modified        :CésarGómez
     * @createdate      :11/Ene/2019
     * @modifieddate    :14/Ene/2019
     * @version         :1.2
     * @params          :Event event, String option
     * @description     :
     * * Se recibe el evento del box de usuario
     * * Se obtiene el target y el parent del box
     * * Se valida que el parent contenga la clase flip-container
     * * De acuerdo a la opción recibida se voltea el box de anverso a reverso o viceversa
     */
    flipCard = function(event, option) {

        let parent = event.target.parentNode;

        while (parent) {

            parent = parent.parentNode;

            if(parent.classList.contains('flip-container')) {

                switch(option) {
                    case 'open':
                        parent.classList.add('flipped');
                        parent.parentNode.parentNode.classList.add('z-index-1');
                        break;
                    case 'close':
                        parent.classList.remove('flipped');
                        parent.parentNode.parentNode.classList.remove('z-index-1');
                        break;
                    default:
                        break;
                }

                break;
            }
        }

        $scope.eventCardSelected = event;
    };
    
    /**
     * @title           :*FILTRO PARA LOS USUARIOS*
     * @author          :CésarGómez
     * @modified        :CésarGómez
     * @createdate      :11/Ene/2019
     * @modifieddate    :18/Ene/2019
     * @version         :1.3
     * @params          :String model
     * @description     :
     * * Se valida que el model no venga vacío para proceder con el filtrado
     * * Si viene vacío el model se persiste el arreglo de usuarios
     * * Se recorre el arreglo de usuarios temporal para crear copias de sus propiedades
     * * Se ponen en minúscula los valores de las propiedades copiadas
     * * Se valida que model coincida con el valor de alguna de las propiedades copiadas
     * * Si hay coincidencias se sobreescribe el arreglo de usuarios
     */
    $scope.filterRefresh = function(model) {
        // $scope.gridOptions.data = $filter('filter')($scope.searchVO.arrayUsersTmp, model);
        // $scope.gridOptions.data = $filter('filter')($scope.searchVO.arrayUsersTmp, {nbUsuario: model});
        // $scope.searchVO.arrayUsers = $scope.gridOptions.data;

        if(model !== "") {

            let restoreValues = function(user, obj) {
                user.nbUsuario = obj.nbUsuario;
                user.nbApaterno = obj.nbApaterno;
                user.nbAmaterno = obj.nbAmaterno;
                user.nbLogin = obj.nbLogin;
            }

            $scope.searchVO.arrayUsers = new Array();

            for(let user of $scope.searchVO.arrayUsersTmp) {

                let nbUserTmp = angular.copy(user.nbUsuario);
                let nbApaternTmp = angular.copy(user.nbApaterno);
                let nbAmaternTmp = angular.copy(user.nbAmaterno);
                let nbLoginTmp = angular.copy(user.nbLogin);
                
                model = model.toLowerCase();
                user.nbUsuario = user.nbUsuario.toLowerCase();
                user.nbApaterno = user.nbApaterno.toLowerCase();
                user.nbAmaterno = user.nbAmaterno.toLowerCase();
                user.nbLogin = user.nbLogin.toLowerCase();

                let obj = new Object({
                    nbUsuario: nbUserTmp,
                    nbApaterno: nbApaternTmp,
                    nbAmaterno: nbAmaternTmp,
                    nbLogin: nbLoginTmp
                });

                if(user.nbUsuario.indexOf(model) !== -1 
                    || user.nbApaterno.indexOf(model) !== -1 
                    || user.nbAmaterno.indexOf(model) !== -1
                    || user.nbLogin.indexOf(model) !== -1) {

                    restoreValues(user, obj);

                    $scope.searchVO.arrayUsers.push(user);

                } else {
                    restoreValues(user, obj);
                }

            }

            $scope.gridOptions.data = $scope.searchVO.arrayUsers;

        } else {
            $scope.gridOptions.data = $scope.searchVO.arrayUsersTmp;
            $scope.searchVO.arrayUsers = $scope.gridOptions.data;
        }
    };
    
    /**
     * @title           :*MOSTRAR/OCULTAR MODAL DE BÚSQUEDA O NUEVO SEGÚN LA ACCIÓN MANDADA*
     * @author          :CésarGómez
     * @modified        :----------
     * @createdate      :14/Ene/2019
     * @modifieddate    :--/--/----
     * @version         :1.0
     * @params          :String action
     */
    $scope.toggleModal = function(action) {
        switch(action) {
            case 'search':
                $scope.flags.isModalUsers = true;
                $scope.flags.isSearchTab = true;
                $scope.flags.isNewTab = false;
                $scope.flags.showMenuDataUser=true;
                break;
            case 'new':
                $scope.flags.isModalUsers = true;
                $scope.flags.isNewTab = true;
                $scope.flags.isSearchTab = false;
                $scope.flags.showMenuDataUser=false;
                break;
            default:
                $scope.flags.isModalUsers = false;
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
		
    mostrarMsjTablaResulados("TEE019P_MSJ_TRESUL");

    $scope.initController();
});