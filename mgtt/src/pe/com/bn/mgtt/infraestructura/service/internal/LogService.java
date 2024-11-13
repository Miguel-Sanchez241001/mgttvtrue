package pe.com.bn.mgtt.infraestructura.service.internal;

import java.util.Date;
import java.util.List;

import pe.com.bn.mgtt.persistence.dto.internal.LogAuditoria;

public interface LogService {
    public void logConsultaTarjeta(String datos);
    public void logConsultaCliente(String datos);
    public void logOperacionBloquear(String datos);
    public void logOperacionSolicitarTarjeta(String datos);
    public void logOperacionModificarDireccion(String datos);
    public void logReporteTarjetaBloqueos(String datos);
    public void logReporteTarjetaBloqueosExporta(String datos);
    public void logConsultaLog(String datos);
    public void logConsultaLogExporta(String datos);
    public List<LogAuditoria> getLogsByDateRange(Date fechaInicio, Date fechaFin);
   public List<LogAuditoria> getLogsByUsername(String username);
public List<LogAuditoria> getLogsByAccion(String descripcion);
public List<LogAuditoria> getLogsByAgencia(String agenciaName);
}
