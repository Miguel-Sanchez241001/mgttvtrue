package pe.com.bn.mgtt.infraestructura.service.internal.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bn.mgtt.infraestructura.service.internal.LogService;
import pe.com.bn.mgtt.persistence.dto.internal.LogAuditoria;
import pe.com.bn.mgtt.persistence.mapper.internal.LogMapper;
import pe.com.bn.mgtt.transversal.configuration.security.UsuarioSeguridad;
import pe.com.bn.mgtt.transversal.util.UsefulWebApplication;
import pe.com.bn.mgtt.transversal.util.enums.Funcionalidad;

@Service
public class LogServiceImpl implements LogService {

	
    @Autowired
    private LogMapper logMapper;
    
	  private void logAction(String datos, Funcionalidad accion) {
	        UsuarioSeguridad usuario = UsefulWebApplication.obtenerUsuario();
	        if (usuario != null) {
	            LogAuditoria log = new LogAuditoria();
	            log.setFecha(new java.sql.Date(new Date().getTime()));
	            log.setHora(new java.text.SimpleDateFormat("HH:mm:ss").format(new Date()));
	            log.setUsuario(usuario.getUsername()); 
	            log.setAccion(accion.getDescripcion());
	            log.setDatos(datos);
	            log.setCodAgencia(usuario.getCodigoArea());
	            log.setNameAgencia(usuario.getNombreArea());

	            logMapper.insertLog(log);
	        }
	    }
	  @Override
	    public void logConsultaTarjeta(String datos) {
	        logAction(datos, Funcionalidad.CONSULTAS_TARJETA);
	    }

	    @Override
	    public void logConsultaCliente(String datos) {
	        logAction(datos, Funcionalidad.CONSULTAS_CLIENTE);
	    }

	    @Override
	    public void logOperacionBloquear(String datos) {
	        logAction(datos, Funcionalidad.OPERACIONES_BLOQUEAR);
	    }

	    @Override
	    public void logOperacionSolicitarTarjeta(String datos) {
	        logAction(datos, Funcionalidad.OPERACIONES_SOLICITAR_TARJETA);
	    }

	    @Override
	    public void logOperacionModificarDireccion(String datos) {
	        logAction(datos, Funcionalidad.OPERACIONES_MODIFICAR_DIRECCION);
	    }

	    @Override
	    public void logReporteTarjetaBloqueos(String datos) {
	        logAction(datos, Funcionalidad.REPORTE_TARJETA_BLOQUEOS);
	    }

	    @Override
	    public void logReporteTarjetaBloqueosExporta(String datos) {
	        logAction(datos, Funcionalidad.REPORTE_TARJETA_BLOQUEOS_EXPORTA);
	    }

	    @Override
	    public void logConsultaLog(String datos) {
	        logAction(datos, Funcionalidad.CONSULTAS_LOG);
	    }

	    @Override
	    public void logConsultaLogExporta(String datos) {
	        logAction(datos, Funcionalidad.CONSULTAS_LOG_EXPORTA);
	    }
	    
	    @Override
	    public List<LogAuditoria> getLogsByDateRange(Date  fechaInicio, Date  fechaFin) {
	        return  logMapper.getLogsByDateRange(fechaInicio, fechaFin);
	    }

	    @Override
	    public List<LogAuditoria> getLogsByUsername(String username) {
	        return logMapper.getLogsByUsername(username);
	    }
	    @Override
	    public List<LogAuditoria> getLogsByAccion(String descripcion) {
	        try {
	            return logMapper.getLogsByAccion(descripcion);
	        } catch (Exception e) {
	            throw new RuntimeException("Error al obtener logs por acción", e);
	        }
	    }

	    @Override
	    public List<LogAuditoria> getLogsByAgencia(String agenciaName) {
	        try {
	            return logMapper.getLogsByAgencia(agenciaName);
	        } catch (Exception e) {
	            throw new RuntimeException("Error al obtener logs por agencia", e);
	        }
	    }

}
