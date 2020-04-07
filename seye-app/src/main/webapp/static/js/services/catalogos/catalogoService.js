angular.module(appTeclo).service('catalogoService', function($http, config, growl) {

	this.buscarTipoMultasTodas = function() {
        var listTipoMultas = [
            { "id": 1, "nombre": "Tipo 1" },
            { "id": 2, "nombre": "Tipo 2" },
            { "id": 3, "nombre": "Tipo 3" },
            { "id": 4, "nombre": "Tipo 4" }
        ];
        return listTipoMultas;
    };
    
    this.busquedaMedidasCorrectivasTodas = function() {
        return $http.get(config.baseUrl + "/medidasCorrectivasActivas");
    };

    this.buscarMedidaCorrectivaPorArt = function(nuArticulo, nuNumeral, cdLiteral) {
        return $http.get(config.baseUrl + "/medidasCorrectPorArt", {
            params: {
                "nuArticulo": nuArticulo,
                "nuNumeral": nuNumeral,
                "cdLiteral": cdLiteral
            }
        });
    };

    // Servicios de Catalogos
    this.buscarDatosCrud = function(nombreServicio) {
        return $http.get(config.baseUrl + nombreServicio);
    };
    this.buscarCatalogosWebOpcionesPorCatalogo = function(catalogoId) { //****************************************************
        return $http.get(config.baseUrl + "/catalogosWebOpciones/" + catalogoId);
    };

    /**
     * @Descripción: Servicio para mostrar notificación en el error del AJAX
     */
    this.showAviso = (data) => {
        if (data != undefined && data.status === 404) {
            growl.error(data.message, { ttl: 4000 });
        } else if (data != undefined && data.status === 400) {
            growl.error(data.message, { ttl: 4000 });
        } else if (data != undefined && data.status === 403) {
            growl.error(data.message, { ttl: 4000 });
        } else {
            growl.error('Ocurrió un error en la operación', { ttl: 4000 });
        }
    };
    this.valideUniqueName = (listObjects, valueToFilter, propertyToFilter) => {
        let isValidName =  true;
        if(listObjects != undefined && listObjects.length > 0){
            for (let i = 0; i < listObjects.length; i++) {
                if(listObjects[i][propertyToFilter] === valueToFilter){
                    isValidName = false;
                    break;
                }
            }
        }
        return isValidName;
    };

    this.filterSchedule = (array, data, firstProperty) => {
        if(array.length > 0){
            let arrayReturn= new Array();
            let fhStart = moment(data.fhStar);
            for(let i=0; i < array.length; i++){
                let fhMomentStart = moment(array[i][firstProperty]);
                if(fhStart.isSameOrBefore(fhMomentStart))
                    arrayReturn.push(array[i]);
            }
            return arrayReturn;
        }
        return null;
    };
    
    this.buscarFiltroTiposBusquedaEvaData = function(){
    	var filtroTiposBusquedaEvaData = [];
		
		var objetoF = {
			"codigo"		: "1",
			"descripcion"	: "Folio Encuesta"
		};
		var objetoN = {
			"codigo"		: "2",
			"descripcion"	: "Nombre Encuesta"
		};
			
		filtroTiposBusquedaEvaData.push(objetoF);
		filtroTiposBusquedaEvaData.push(objetoN);
		return filtroTiposBusquedaEvaData;
    }
    
    this.buscarFiltroCatalogosEstatusEncuesta = function() {
    	var filtroEstatusEvaData = [];

    	var objeto1 = {
    		"idStEncuesta"	: "1",
    		"nbStEncuesta"	: "Terminada"
    	};
    	var objeto2 = {
    		"idStEncuesta"	: "2",
    		"nbStEncuesta"	: "En Proceso"
    	};

    	var objeto3 = {
    		"idStEncuesta"	: "3",
    		"nbStEncuesta"	: "No Iniciada"
    	};
    	
    	filtroEstatusEvaData.push(objeto1);
    	filtroEstatusEvaData.push(objeto2);
    	filtroEstatusEvaData.push(objeto3);
    	
    	return filtroEstatusEvaData;
    };
    
    this.buscarParametroPorCd = function(cdParametro) {
        return $http.get(config.baseUrl + "/catalogo/parametroCd", {
            params: {
                "cdParametro": cdParametro
            }
        });
    };
});