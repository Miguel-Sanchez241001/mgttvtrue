package pe.com.bn.mgtt.persistence.mapper.internal;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import pe.com.bn.mgtt.persistence.dto.internal.LogAuditoria;

public interface LogMapper {
	 @Insert("INSERT INTO BN_SATE.BNSATE20_LOG_BLOQUEO (B20_FECHA, B20_HORA, B20_USUARIO, B20_ACCION, B20_DATOS, B20_COD_AGENCIA, B20_NAME_AGENCIA) " +
	            "VALUES (#{fecha}, #{hora}, #{usuario}, #{accion}, #{datos}, #{codAgencia}, #{nameAgencia})")
	 public void insertLog(LogAuditoria log);
	 
	   @Select("SELECT * FROM BN_SATE.BNSATE20_LOG_BLOQUEO")
	   @ResultMap("mapLogAuditoria")
	   public  List<LogAuditoria> getAllLogs();
	   
	   @Select("SELECT * FROM BN_SATE.BNSATE20_LOG_BLOQUEO WHERE TRUNC(B20_FECHA) BETWEEN TRUNC(#{fechaInicio}) AND TRUNC(#{fechaFin})")
	   @ResultMap("mapLogAuditoria")
	   List<LogAuditoria> getLogsByDateRange(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);


 	    @Select("SELECT * FROM BN_SATE.BNSATE20_LOG_BLOQUEO WHERE B20_USUARIO = #{usuario}")
	    @ResultMap("mapLogAuditoria")
	    public  List<LogAuditoria> getLogsByUsername(
	    		@Param("usuario") String usuario);
	    
	    @Select("SELECT * FROM BN_SATE.BNSATE20_LOG_BLOQUEO WHERE B20_ACCION = #{accion}")
	    @ResultMap("mapLogAuditoria")
	    public List<LogAuditoria> getLogsByAccion(@Param("accion") String accion);

 	    @Select("SELECT * FROM BN_SATE.BNSATE20_LOG_BLOQUEO WHERE B20_NAME_AGENCIA = #{agenciaName}")
	    @ResultMap("mapLogAuditoria")
	    public List<LogAuditoria> getLogsByAgencia(@Param("agenciaName") String agenciaName);

}
