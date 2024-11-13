package pe.com.bn.mgtt.persistence.mapper.internal;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import pe.com.bn.mgtt.persistence.dto.internal.Permiso;
import pe.com.bn.mgtt.persistence.dto.internal.bloqueo.MotivosBloqueo;

 
public interface PermisosMapper {

	
	@Select("SELECT * FROM BN_SATE.BNSATE10_PER_PERMISOS WHERE B11_ID_ROL = #{idRol}")
	@ResultMap("mapPermiso")
	public List<Permiso> buscarPermisosPorRol(@Param("idRol")Long idRol);
	
	@Select("select "
			+ "permiso.B10_COD_FUNCIONALIDAD as codBloq, "
			+ "SUBSTR(permiso.B10_DESCRIPCION, 1, INSTR(permiso.B10_DESCRIPCION, '-') - 1) as tipoBloq, "
			+ " SUBSTR(permiso.B10_DESCRIPCION, INSTR(permiso.B10_DESCRIPCION, '-') + 1) as descripcion "
			+ "from BN_SATE.BNSATE10_PER_PERMISOS permiso "
			+ "WHERE permiso.B11_ID_ROL = #{idRol}")
	@ResultMap("mapBloqueos")
	public List<MotivosBloqueo> buscarMotivosBloqueo(@Param("idRol")Long idRol);
}
