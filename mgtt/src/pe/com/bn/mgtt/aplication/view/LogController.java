package pe.com.bn.mgtt.aplication.view;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import lombok.Getter;
import lombok.Setter;
import pe.com.bn.mgtt.aplication.model.LogModel;
import pe.com.bn.mgtt.infraestructura.service.internal.LogService;
import pe.com.bn.mgtt.transversal.util.ConstantesGenerales;
import pe.com.bn.mgtt.transversal.util.UsefulWebApplication;
import pe.com.bn.mgtt.transversal.util.enums.Funcionalidad;

@Controller("logController")
@Scope("view")
@Getter
@Setter
public class LogController implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LogManager.getLogger(LogController.class);

    private LogModel logModel;

    @Autowired
    private LogService logService;

    @PostConstruct
    public void init() {
        logModel = new LogModel();
        logModel.inicializarFormulario();
    }

    public void buscarLog() {
        logger.info("Inicio Busqueda...");
        logModel.setBusquedaRealizada(false); // Restablecer al inicio
        logModel.setBloqueosRealizados(null); // Limpiar la lista
        logger.info("Tipo de búsqueda: {}",logModel.getTiposBusquedaSeleccione());
        try {
            if (logModel.isBusquedaPorUsername()) {
            	logger.info("Username: {}",logModel.getUsername());
                 logModel.setBloqueosRealizados(logService.getLogsByUsername(logModel.getUsername()));
            } else if (logModel.isBusquedaPorFecha()) {
                Date fechaInicio = logModel.getFechaInicio();
                Date fechaFin = logModel.getFechaFin();
                logger.info("Fecha Inicio : {}",fechaInicio);
                logger.info("Fecha Fin : {}",fechaFin);
                if (fechaInicio.after(fechaFin)) {
                    UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR, "RESULTADO BÚSQUEDA",
                            "La fecha de inicio no puede ser posterior a la fecha de fin");
                    logModel.setBloqueosRealizados(null);
                    return;
                }
                logModel.setBloqueosRealizados(logService.getLogsByDateRange(fechaInicio, fechaFin));
            } else if (logModel.isBusquedaPorAgencia()) {
                logModel.setBloqueosRealizados(logService.getLogsByAgencia(logModel.getAgenciaName()));
                logger.info("Agencia : {}",logModel.getAgenciaName());
             } else if (logModel.isBusquedaPorAccion()) {
                Funcionalidad accion = logModel.getAccionSeleccionada();
                logModel.setBloqueosRealizados(logService.getLogsByAccion(accion.getDescripcion()));
                logger.info("Funcionalidad : {}",accion.getDescripcion());
            } else {
                UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR, "RESULTADO BÚSQUEDA",
                        "Debe seleccionar un tipo de búsqueda");
                logModel.setBloqueosRealizados(null);
                return;
            }

            if (logModel.getBloqueosRealizados() == null || logModel.getBloqueosRealizados().isEmpty()) {
                UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_INFO, "RESULTADO BÚSQUEDA",
                        "No se encontraron resultados");
                logModel.setBloqueosRealizados(null);
            }else {
            	 logModel.setBusquedaRealizada(true);
            	
            }
            UsefulWebApplication
			.actualizarComponente("formReporteLog");
        } catch (Exception e) {
            logger.error("Error en buscarLog", e);
            UsefulWebApplication.mostrarMensajeJSF(ConstantesGenerales.SEVERITY_ERROR, "RESULTADO BÚSQUEDA",
                    "Ocurrió un error al buscar los logs");
            logModel.setBloqueosRealizados(null);
        }
    }
}
