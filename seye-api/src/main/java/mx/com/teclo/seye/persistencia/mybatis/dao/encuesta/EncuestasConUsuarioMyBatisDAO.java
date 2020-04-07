package mx.com.teclo.seye.persistencia.mybatis.dao.encuesta;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import mx.com.teclo.seye.persistencia.vo.encuesta.EncuestaTipoRestanteVO;
import mx.com.teclo.seye.persistencia.vo.encuesta.EncuestasConUsuarioTotalesVO;

@Mapper
public interface EncuestasConUsuarioMyBatisDAO {
	
	String CONSULTA_ENCUESTASCONUSUARIO = "select ID_ENCUESTA as idEncuesta, ID_TIPO_ENCUESTA as id_TipoEncuesta, CD_ENCUESTA as cdEncuesta, NB_ENCUESTA as nbEncuesta, "
										+ "TX_ENCUESTA as txEncuesta, NU_ORDEN as nuOrden, NB_ENCUESTA_ORIGEN as nbEncuestaOrigen, " 
										+ "TX_INSTRUCCIONES as txInstrucciones, NU_PREGUNTAS as nuPreguntas, to_char(FH_VIG_INI, 'dd/mm/yyyy') as fhVigIni, "
										+ "to_char(FH_VIG_FIN, 'dd/mm/yyyy') as fhVigFin, " 
										+ "NU_CALIFICACION_APRO as nuCalificacionApro, NU_SECCIONES as nuSecciones, " 
										+ "CD_TIPO_ENCUESTA as cdTipoEncuesta, NB_TIPO_ENCUESTA as nbTipoEncuesta, " 
										+ "numEnComp || ' de ' || totalUsuarios nuEncuestados, " 
										+ "nvl(promCalificacion,0) as promedio, " 
										+ "ROUND ((100*numEnComp)/(case when totalUsuarios is null or totalUsuarios =0 then 1 else totalUsuarios end),0) as aplicacionProm, " 
										+ "numAprobados || ' de ' || totalUsuarios as efectividad " 
										+ "from ( " 
										+ "select " 
										+ "tee001.ID_ENCUESTA, tee001.ID_TIPO_ENCUESTA, tee001.CD_ENCUESTA, tee001.NB_ENCUESTA, tee001.TX_ENCUESTA, tee001.TX_INSTRUCCIONES, "
										+ "tee001.NU_PREGUNTAS, tee001.FH_VIG_INI, tee001.NU_ORDEN, tee001.NB_ENCUESTA_ORIGEN, " 
										+ "tee001.FH_VIG_FIN, tee001.NU_CALIFICACION_APRO, tee001.NU_SECCIONES, " 
										+ "tee014.CD_TIPO_ENCUESTA, tee014.NB_TIPO_ENCUESTA, " 
										+ "(select count(*) from tee002d_ee_usu_encuesta tee002 where id_encuesta = tee001.ID_ENCUESTA and tee002.st_activo = 1) as totalUsuarios, " 
										+ "(select round(avg(NU_CALIFICACION),0) from tee006d_ee_usu_encu_inten tee006 " 
										+ "inner join tee002d_ee_usu_encuesta tee002 on tee002.ID_USUARIO_ENCUESTA = tee006.ID_USUARIO_ENCUESTA " 
										+ "where tee002.id_encuesta = tee001.ID_ENCUESTA and tee006.st_mostrar = 1) as promCalificacion, " 
										+ "(select count(*) from tee006d_ee_usu_encu_inten tee006 inner join tee002d_ee_usu_encuesta tee002 on tee002.ID_USUARIO_ENCUESTA = tee006.ID_USUARIO_ENCUESTA " 
										+ "where tee002.ID_ENCUESTA = tee001.ID_ENCUESTA and tee006.st_mostrar = 1 and tee006.NU_CALIFICACION>=tee001.NU_CALIFICACION_APRO) as numAprobados, " 
										+ "(select count(*) from tee006d_ee_usu_encu_inten tee006 " 
										+ "inner join tee002d_ee_usu_encuesta tee002 on tee002.ID_USUARIO_ENCUESTA = tee006.ID_USUARIO_ENCUESTA " 
										+ "where tee006.st_mostrar = 1 and tee002.NU_INTENTOS>0 and tee001.ID_ENCUESTA = tee002.id_encuesta) as numEnComp " 
										+ "from TEE001D_EE_ENCUESTAS tee001 inner join tee014c_ee_tipo_encuestas tee014 on tee014.id_tipo_encuesta = tee001.id_tipo_encuesta " 
										+ "where (case when #{tipoBusqueda} =0 then 1 " 
										+ "            when #{tipoBusqueda} = 1 and #{valorBusqueda} = tee001.cd_encuesta then 1 " 
										+ "            when #{tipoBusqueda} = 2 and #{valorBusqueda} = tee001.nb_encuesta then 1 " 
										+ "        end) = 1 " 
										+ "        and " 
										+ "        (case " 
										+ "            when trim(#{fhInicio}) is null  then 1 " 
										+ "            when trim(#{fhInicio}) is not null " 
										+ "                 and ((to_date(#{fhInicio}, 'dd/mm/yyyy') BETWEEN trunc(tee001.FH_VIG_INI) and trunc(tee001.FH_VIG_FIN)) or " + 
										"                    (to_date(#{fhFin}, 'dd/mm/yyyy') BETWEEN trunc(tee001.FH_VIG_INI) and trunc(tee001.FH_VIG_FIN))) then 1 " 
										+ "        end) = 1 order by tee001.NU_ORDEN asc ) t1 " 
										+ "where " 
										+ "    (case " 
										+ "        when #{stEncuesta} = 0 then 1 " 
										+ "        when #{stEncuesta} = 1 and numEnComp = totalUsuarios then 1 " 
										+ "        when #{stEncuesta} = 2 and numEnComp > 0 then 1 " 
										+ "        when #{stEncuesta} = 3 and numEnComp = 0 then 1 " 
										+ "    end) = 1";
	
	@Select(value = CONSULTA_ENCUESTASCONUSUARIO)
	@Options(statementType = StatementType.CALLABLE)
	public List<EncuestasConUsuarioTotalesVO> consultaEncuestasConUsuario(
			@Param ("tipoBusqueda") Integer tipoBusqueda,
			@Param ("valorBusqueda") String valorBusqueda, 
			@Param ("fhInicio") String fhInicio, 
			@Param ("fhFin") String fhFin, 
			@Param ("stEncuesta") Integer stEncuesta);
	
	String CONSULTA_INTENTOS_FINALIZAR_TIEMPO = "select tee006.ID_USU_ENCU_INTENTO as idUsuarioEncuesta " 
											+ "from tee006d_ee_usu_encu_inten tee006 " 
											+ "inner join tee002d_ee_usu_encuesta tee002 on tee002.ID_USUARIO_ENCUESTA = tee006.ID_USUARIO_ENCUESTA " 
											+ "inner join tee001d_ee_encuestas tee001 on tee001.id_encuesta = tee002.id_encuesta " 
											+ "where tee006.st_mostrar = 1 and tee006.FH_INICIO is not null " 
											+ "and tee006.id_st_encuesta = 1 " 
											+ "and tee002.id_encuesta = #{idEncuesta} " 
											+ "and (tee001.NU_TIEMPO - ((to_date(#{fhActual}, 'DD/MM/YYYY hh24:mi:ss') - tee006.FH_INICIO) * 86400)) < 0"; 
	
	@Select(value = CONSULTA_INTENTOS_FINALIZAR_TIEMPO)
	List<EncuestaTipoRestanteVO> intentosFinalizarPorTiempo(
			@Param ("idEncuesta") Long idEncuesta,
			@Param ("fhActual") String  fhActual);
	
	String VALIDA_TIEMPO_RESTANTE_INTENTO = "select tee006.ID_USU_ENCU_INTENTO as idUsuarioEncuesta, " 
										+ "tee001.NU_TIEMPO - trunc(((to_date(#{fhActual}, 'DD/MM/YYYY hh24:mi:ss') - tee006.FH_INICIO) * 86400)) as tpRestante, " 
										+ "(case when (tee001.NU_TIEMPO - trunc(((to_date(#{fhActual}, 'DD/MM/YYYY hh24:mi:ss') - tee006.FH_INICIO) * 86400))) < 0 then 0 " 
										+ "    ELSE 1 end) as stContinua " 
										+ "from tee006d_ee_usu_encu_inten tee006 " 
										+ "inner join tee002d_ee_usu_encuesta tee002 on tee002.ID_USUARIO_ENCUESTA = tee006.ID_USUARIO_ENCUESTA " 
										+ "inner join tee001d_ee_encuestas tee001 on tee001.id_encuesta = tee002.id_encuesta " 
										+ "where tee006.st_mostrar = 1 " 
										+ "and tee006.ID_USU_ENCU_INTENTO = #{idUsuarioEncuesta}";
	
	@Select(value = VALIDA_TIEMPO_RESTANTE_INTENTO)
	EncuestaTipoRestanteVO validarPorTiempoRestante(
			@Param ("idUsuarioEncuesta") Long idUsuarioEncuesta,  
			@Param ("fhActual") String  fhActual);
}
