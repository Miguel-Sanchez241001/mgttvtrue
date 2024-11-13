package pe.com.bn.mgtt.aplication.view;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import lombok.Getter;
import lombok.Setter;

@Controller("homeController")
@Scope("view")
@Getter
@Setter
public class HomeController implements Serializable {

    private static final long serialVersionUID = 1L;

    // Logger
    private static final Logger logger = LogManager.getLogger(HomeController.class);

    // Propiedades para los mensajes y estados de conexión
    private String mensaje;
    private String estadoDataSourceSate;
    private String estadoDataSourceTablas;

    @Autowired
    private DataSource dataSourceSate;

    @Autowired
    private DataSource dataSourceTablas;

    // Constructor
    public HomeController() {
        logger.info("HomeController ha sido inicializado.");
        this.mensaje = "Bienvenido al módulo de gestión de tarjetas de Tesoro.";
    }

    // Método para verificar las conexiones a los data sources
    @PostConstruct
    public void verificarConexiones() {
        this.estadoDataSourceSate = verificarConexionOracle(dataSourceSate) ? "Conectado a Oracle" : "Desconectado de Oracle";
        this.estadoDataSourceTablas = verificarConexionSqlServer(dataSourceTablas) ? "Conectado a SQL Server" : "Desconectado de SQL Server";
    }

    // Verificación para Oracle
    private boolean verificarConexionOracle(DataSource dataSource) {
        int intentos = 3;
        while (intentos > 0) {
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM DUAL")) {
                stmt.executeQuery();
                return true;
            } catch (SQLException e) {
                logger.error("Error al conectar al data source Oracle: " + e.getMessage());
                intentos--;
                if (intentos == 0) {
                    return false;
                }
            }
        }
        return false;
    }

    // Verificación para SQL Server
    private boolean verificarConexionSqlServer(DataSource dataSource) {
        int intentos = 3;
        while (intentos > 0) {
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT 1")) {
                stmt.executeQuery();
                return true;
            } catch (SQLException e) {
                logger.error("Error al conectar al data source SQL Server: " + e.getMessage());
                intentos--;
                if (intentos == 0) {
                    return false;
                }
            }
        }
        return false;
    }

 
    // Método para actualizar el mensaje y mostrarlo en pantalla
    public void mostrarMensaje() {
        this.mensaje = "Acción realizada exitosamente";
        logger.info("Mostrar mensaje en pantalla: " + this.mensaje);
    }
}
