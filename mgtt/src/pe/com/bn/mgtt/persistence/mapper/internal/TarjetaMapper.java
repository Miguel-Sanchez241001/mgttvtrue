package pe.com.bn.mgtt.persistence.mapper.internal;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pe.com.bn.mgtt.persistence.dto.internal.EstadoTarjeta;
import pe.com.bn.mgtt.persistence.dto.internal.SolicitudTarjeta;
import pe.com.bn.mgtt.persistence.dto.internal.Tarjeta;
import pe.com.bn.mgtt.persistence.dto.internal.TarjetaOpcion;
import pe.com.bn.mgtt.persistence.dto.internal.TarjetaResumen;

 

public interface TarjetaMapper {

    @Insert("INSERT INTO BN_SATE.BNSATE05_TARJETA(" +
            "B00_ID_EMP," +
            "B02_REP," +
            "B06_ID_CLI," +
            "B05_NOM_AGENCIA," +
            "B05_USO_DISP_EFECT," +
            "B05_PORCENTAJE_DISP_EFECT," +
            "B05_USO_EXTRANJERO," +
            "B05_USO_COMPRAS_WEB," +
            "B05_TIPO_TARJETA," +
            "B05_OBSERVACIONES," +
            "B05_TIPO_MONEDA," +
            "B05_ENTREGA_UBICACION," +
            "B05_ENTREGA_AGENCIA_BN," +
            "B05_ENTREGA_UBIGEO," +
            "B05_ENTREGA_DIRECCION," +
            "B05_ENTREGA_REFERENCIA," +
            "B05_FEC_CREACION," +
            "B05_EMAIL," +
            "B05_OPERADOR_CELULAR," +
            "B05_NUM_CELULAR," +
            "B05_FLAG_ACT_CONTACTO," +
            "B05_DISENO) " +
            "VALUES (#{idEmpresa}, #{idUsu}, #{idCli},#{entregaAgenciaBNombre},  #{usoDispocionEfectivo}, #{porcentajeDisposicionEfectivo}, #{usoExtranjero}, #{usoComprasWeb}, #{tipoTarjeta}, #{observaciones}, #{tipoMoneda}, #{entregaUbicacion}, #{entregaAgenciaBN}, #{entregaUbigeo}, #{entregaDireccion}, #{entregaReferencia}, #{fechaCreacion}, #{email}, #{operadorCelular}, #{numeroCelular}, #{flagActualizarContacto}, #{diseno})")
    public void registrarTarjeta(Tarjeta tarjeta);

    @Insert("INSERT INTO BN_SATE.BNSATE07_EST_TARJETA(" +
            "B05_ID_TAR," +
            "B07_ESTADO," +
            "B07_MOTIVO," +
            "B07_FEC_REGISTRO," +
            "B07_USUARIO_CREA) " +
            "VALUES (" +
            "#{idTarjeta}," +
            "#{estado}," +
            "#{motivo}," +
            "#{fechaRegistro}," +
            "#{usuarioRegistro})")
    public void registrarEstadoTarjeta(EstadoTarjeta estadoTarjeta);

    @Select("SELECT BN05.B05_ID_TAR," +
            "BN06.B06_TIPO_DOCUMENTO, BN06.B06_NUM_DOCUMENTO," +
            "BN06.B06_NOMBRES, CONCAT(BN06.B06_APPATERNO, CONCAT(' ', BN06.B06_APMATERNO)) AS APELLIDOS," +
            "BN05.B05_USO_DISP_EFECT, BN05.B05_USO_EXTRANJERO, BN05.B05_TIPO_TARJETA, BN05.B05_DISENO " +
            "FROM BN_SATE.BNSATE05_TARJETA BN05 " +
            "INNER JOIN BN_SATE.BNSATE06_CLIENTE BN06 ON BN05.B06_ID_CLI = BN06.B06_ID_CLI " +
            "INNER JOIN BN_SATE.BNSATE00_EMPRESA BN00 ON BN00.B00_ID_EMP = BN05.B00_ID_EMP " +
            "WHERE BN05.B05_NUM_TARJETA IS NULL " +
            "AND (SELECT B07_ESTADO FROM BN_SATE.BNSATE07_EST_TARJETA WHERE B07_ETA = (SELECT MAX(B07.B07_ETA) FROM BN_SATE.BNSATE07_EST_TARJETA B07 WHERE B07.B05_ID_TAR = BN05.B05_ID_TAR)) = #{codEstadoTarjeta} " +
            "AND BN00.B00_NUM_RUC = #{numRuc}")
    @ResultMap("mapSolicitudTarjeta")
    public List<SolicitudTarjeta> buscarTodosSolicitudesTarjetaPendientes(
            @Param("codEstadoTarjeta") String codEstadoTarjeta,
            @Param("numRuc") String numRuc);

    @Select("SELECT BN05.*," +
            "(SELECT BN00.B00_RAZON_SOCIAL FROM BN_SATE.BNSATE00_EMPRESA BN00 WHERE BN00.B00_ID_EMP = BN05.B00_ID_EMP) AS BN00_EMPRESA," +
            "SUBSTR(BN05.B05_ENTREGA_UBIGEO, 1, 2) AS B05_ENTREGA_DEPARTAMENTO," +
            "SUBSTR(BN05.B05_ENTREGA_UBIGEO, 3, 2) AS B05_ENTREGA_PROVINCIA," +
            "SUBSTR(BN05.B05_ENTREGA_UBIGEO, 5, 2) AS B05_ENTREGA_DISTRITO," +
            "(CASE WHEN BN05.B05_ENTREGA_UBICACION = '4' THEN (SELECT B00_RAZON_SOCIAL FROM BN_SATE.BNSATE00_EMPRESA BN00 WHERE BN00.B00_ID_EMP = BN05.B05_ENTREGA_AGENCIA_BN) ELSE '' END) AS ENTREGA_UBICACION_UE," +
            "BN07.B07_ESTADO," +
            "BN07.B07_MOTIVO, BN07.B07_FEC_REGISTRO, BN07.B07_USUARIO_CREA " +
            "FROM BN_SATE.BNSATE05_TARJETA BN05 " +
            "INNER JOIN BN_SATE.BNSATE07_EST_TARJETA BN07 ON BN07.B05_ID_TAR = BN05.B05_ID_TAR " +
            "INNER JOIN BN_SATE.BNSATE00_EMPRESA BN00 ON BN00.B00_ID_EMP = BN05.B00_ID_EMP " +
            "WHERE BN05.B05_NUM_TARJETA = #{numTarjeta} " +
            "AND BN07.B07_ETA = (SELECT MAX(B07.B07_ETA) FROM BN_SATE.BNSATE07_EST_TARJETA B07 WHERE B07.B05_ID_TAR = BN05.B05_ID_TAR) " )
    @ResultMap("mapTarjeta")
    public Tarjeta buscarTarjetaPorNumeroTarjeta(
            @Param("numTarjeta") String numTarjeta );

    @Select("SELECT BN05.*," +
            "(SELECT BN00.B00_RAZON_SOCIAL FROM BN_SATE.BNSATE00_EMPRESA BN00 WHERE BN00.B00_ID_EMP = BN05.B00_ID_EMP) AS BN00_EMPRESA," +
            "SUBSTR(BN05.B05_ENTREGA_UBIGEO, 1, 2) AS B05_ENTREGA_DEPARTAMENTO," +
            "SUBSTR(BN05.B05_ENTREGA_UBIGEO, 3, 2) AS B05_ENTREGA_PROVINCIA," +
            "SUBSTR(BN05.B05_ENTREGA_UBIGEO, 5, 2) AS B05_ENTREGA_DISTRITO," +
            "(CASE WHEN BN05.B05_ENTREGA_UBICACION = '3' THEN (SELECT B00_RAZON_SOCIAL FROM BN_SATE.BNSATE00_EMPRESA BN00 WHERE BN00.B00_ID_EMP = BN05.B00_ID_EMP) ELSE '' END) AS ENTREGA_UBICACION_UE," +
            "(SELECT B07_ESTADO FROM BN_SATE.BNSATE07_EST_TARJETA WHERE B07_ETA = (SELECT MAX(B07_ETA) FROM BN_SATE.BNSATE07_EST_TARJETA WHERE B05_ID_TAR = BN05.B05_ID_TAR)) AS B07_ESTADO " +
            "FROM BN_SATE.BNSATE05_TARJETA BN05 " +
            "INNER JOIN BN_SATE.BNSATE06_CLIENTE BN06 ON BN05.B06_ID_CLI = BN06.B06_ID_CLI " +
            "INNER JOIN BN_SATE.BNSATE00_EMPRESA BN00 ON BN00.B00_ID_EMP = BN05.B00_ID_EMP " +
            "WHERE BN06.B06_TIPO_DOCUMENTO = #{tipoDocumento} AND BN06.B06_NUM_DOCUMENTO = #{numDocumento} " )
    @ResultMap("mapTarjeta")
    public List<Tarjeta> buscarTarjetaPorTipoDocumento(
            @Param("tipoDocumento") String tipoDocumento,
            @Param("numDocumento") String numDocumento );

    @Select("SELECT BN05.*," +
            "(SELECT BN00.B00_RAZON_SOCIAL FROM BN_SATE.BNSATE00_EMPRESA BN00 WHERE BN00.B00_ID_EMP = BN05.B00_ID_EMP) AS BN00_EMPRESA," +
            "SUBSTR(BN05.B05_ENTREGA_UBIGEO, 1, 2) AS B05_ENTREGA_DEPARTAMENTO," +
            "SUBSTR(BN05.B05_ENTREGA_UBIGEO, 3, 2) AS B05_ENTREGA_PROVINCIA," +
            "SUBSTR(BN05.B05_ENTREGA_UBIGEO, 5, 2) AS B05_ENTREGA_DISTRITO," +
            "(CASE WHEN BN05.B05_ENTREGA_UBICACION = '4' THEN (SELECT B00_RAZON_SOCIAL FROM BN_SATE.BNSATE00_EMPRESA BN00 WHERE BN00.B00_ID_EMP = BN05.B05_ENTREGA_AGENCIA_BN) ELSE '' END) AS ENTREGA_UBICACION_UE," +
            "BN07.B07_ESTADO," +
            "BN07.B07_MOTIVO, BN07.B07_FEC_REGISTRO, BN07.B07_USUARIO_CREA " +
            "FROM BN_SATE.BNSATE05_TARJETA BN05 " +
            "INNER JOIN BN_SATE.BNSATE06_CLIENTE BN06 ON BN05.B06_ID_CLI = BN06.B06_ID_CLI " +
            "INNER JOIN BN_SATE.BNSATE07_EST_TARJETA BN07 ON BN07.B05_ID_TAR = BN05.B05_ID_TAR " +
            "INNER JOIN BN_SATE.BNSATE00_EMPRESA BN00 ON BN00.B00_ID_EMP = BN05.B00_ID_EMP " +
            "WHERE BN06.B06_TIPO_DOCUMENTO = #{tipoDocumento} AND BN06.B06_NUM_DOCUMENTO = #{numDocumento} " +
            "AND BN07.B07_ETA = (SELECT MAX(B07.B07_ETA) FROM BN_SATE.BNSATE07_EST_TARJETA B07 WHERE B07.B05_ID_TAR = BN05.B05_ID_TAR) " +
            "AND (BN07.B07_ESTADO = '5' OR (BN07.B07_ESTADO = '6' AND BN07.B07_MOTIVO = 'R')) ")
    @ResultMap("mapTarjeta")
    public List<Tarjeta> buscarTarjetaPorTipoDocumentoValidosParaBloqueo(
            @Param("tipoDocumento") String tipoDocumento,
            @Param("numDocumento") String numDocumento );

    @Select("SELECT emp.b00_razon_social, tar.b05_usuario_creacion, tar.b05_fec_creacion, eta.b07_estado, eta.b07_fec_registro, tar.b05_tipo_tarjeta, tar.b05_diseno, tar.b05_num_tarjeta, cli.b06_tipo_documento, cli.b06_num_documento, cli.b06_nombres, cli.b06_appaterno || ' ' || cli.b06_apmaterno AS b06_apellidos " +
            "FROM BN_SATE.BNSATE05_TARJETA tar " +
            "JOIN BN_SATE.BNSATE00_EMPRESA emp ON tar.b00_id_emp = emp.b00_id_emp " +
            "JOIN BN_SATE.BNSATE06_CLIENTE cli ON tar.b06_id_cli = cli.b06_id_cli " +
            "JOIN BN_SATE.BNSATE07_EST_TARJETA eta ON eta.b05_id_tar = tar.b05_id_tar " +
            "JOIN (SELECT tar1.b05_id_tar, MAX(eta1.b07_fec_registro) b07_fec_registro FROM BN_SATE.BNSATE05_TARJETA tar1 " +
            "JOIN BN_SATE.BNSATE07_EST_TARJETA eta1 ON eta1.b05_id_tar = tar1.b05_id_tar " +
            "GROUP BY tar1.b05_id_tar) qry ON tar.b05_id_tar = qry.b05_id_tar AND eta.b07_fec_registro = qry.b07_fec_registro " +
            "WHERE emp.b00_num_cuenta_corriente = #{cuentaCorriente} AND tar.b05_num_tarjeta IS NOT NULL AND TRUNC(eta.b07_fec_registro) BETWEEN TRUNC(TO_DATE(#{fechaInicio}, 'dd/mm/yy')) AND TRUNC(TO_DATE(#{fechaFin}, 'dd/mm/yy'))")
    @ResultMap("mapTarjetaResumen")
    public List<TarjetaResumen> obtenerListaTarjetas(
            @Param("cuentaCorriente") String cuentaCorriente,
            @Param("fechaInicio") String fechaInicio,
            @Param("fechaFin") String fechaFin);

    @Select("SELECT COUNT(*) FROM BN_SATE.BNSATE05_TARJETA tar " +
            "JOIN BN_SATE.BNSATE00_EMPRESA emp ON tar.b00_id_emp = emp.b00_id_emp " +
            "JOIN BN_SATE.BNSATE06_CLIENTE cli ON tar.b06_id_cli = cli.b06_id_cli " +
            "JOIN BN_SATE.BNSATE07_EST_TARJETA eta ON eta.b05_id_tar = tar.b05_id_tar " +
            "JOIN (SELECT tar1.b05_id_tar, MAX(eta1.b07_fec_registro) b07_fec_registro FROM BN_SATE.BNSATE05_TARJETA tar1 " +
            "JOIN BN_SATE.BNSATE07_EST_TARJETA eta1 ON eta1.b05_id_tar = tar1.b05_id_tar " +
            "GROUP BY tar1.b05_id_tar) qry ON tar.b05_id_tar = qry.b05_id_tar AND eta.b07_fec_registro = qry.b07_fec_registro " +
            "WHERE cli.B06_TIPO_DOCUMENTO = #{tipoDocumento} AND cli.B06_NUM_DOCUMENTO = #{numDocumento} " +
            "AND emp.B00_NUM_RUC = #{numRuc} AND eta.b07_estado IN ('1', '2', '3', '5', '6')")
    public long cantidadTarjetasDisponiblesPorDocumento(
            @Param("tipoDocumento") String tipoDocumento,
            @Param("numDocumento") String numDocumento,
            @Param("numRuc") String numRuc);

    @Select("SELECT BN05.* FROM BN_SATE.BNSATE05_TARJETA BN05 WHERE BN05.B05_ID_TAR = #{idTarjeta}")
    @ResultMap("mapTarjeta")
    public Tarjeta buscarTarjetaPorId(@Param("idTarjeta") Long idTarjeta);

    // Soporte de múltiples tarjetas
    @Select("SELECT * FROM (" +
            "SELECT BN05.* " +
            "FROM BN_SATE.BNSATE05_TARJETA BN05 " +
            "WHERE BN05.B00_ID_EMP = #{idEmpresa} " +
            "AND BN05.B02_REP = #{idRep} " +
            "AND BN05.B06_ID_CLI = #{idCli} " +
            "ORDER BY BN05.B05_FEC_CREACION DESC" +
            ") WHERE ROWNUM = 1")
    @ResultMap("mapTarjeta")
    public Tarjeta buscarTarjeta(
            @Param("idEmpresa") Long idEmpresa,
            @Param("idRep") Long idRep,
            @Param("idCli") Long idCli);

    @Update("UPDATE BN_SATE.BNSATE05_TARJETA SET " +
            "B05_EMAIL = #{email}, " +
            "B05_OPERADOR_CELULAR = #{operadorCelular}, " +
            "B05_NUM_CELULAR = #{numeroCelular}, " +
            "B05_FLAG_ACT_CONTACTO = #{flagActualizarContacto} " +
            "WHERE B05_ID_TAR = #{id}")
    public void actualizarContacto(Tarjeta tarjeta);

    @Update("UPDATE BN_SATE.BNSATE05_TARJETA SET " +
            "B05_FEC_INICIO_LINEA = #{fechaInicioLinea}, " +
            "B05_FEC_TERMINO_LINEA = #{fechaTerminoLinea}, " +
            "B05_MONTO_LINEA_ASIGNADO = #{montoLineaAsignado}, " +
            "B05_MONTO_LINEA_ACTUAL = #{montoLineaActual}, " +
            "B05_MONTO_COMPRA_USADO = #{montoCompraUsado}, " +
            "B05_MONTO_POR_PROCESAR = #{montoPorProcesar}, " +
            "B05_DISP_USADO = #{dispEfectivoUsado} " +
            "WHERE B05_NUM_TARJETA = #{numTarjeta}")
    public void actualizarSaldos(Tarjeta tarjeta);

    @Update("UPDATE BN_SATE.BNSATE05_TARJETA SET " +
            "B05_EST_CUENTA = #{estadoCuenta}, " +
            "B05_FLAG_ACT_EST_CUENTA = #{flagActualizarEstadoCuenta} " +
            "WHERE B05_ID_TAR = #{id}")
    public void actualizarestadoCuenta(Tarjeta tarjeta);
    
    @Select("SELECT BN05.* FROM BN_SATE.BNSATE05_TARJETA BN05 " +
            "JOIN BN_SATE.BNSATE06_CLIENTE BN06 ON BN05.B06_ID_CLI = BN06.B06_ID_CLI " +
            "WHERE BN06.B06_TIPO_DOCUMENTO = #{tipoDocumento} " +
            "AND BN06.B06_NUM_DOCUMENTO = #{numeroDocumento} " +
            "ORDER BY BN05.B05_FEC_CREACION ASC " +
            "FETCH FIRST 1 ROWS ONLY")
    @ResultMap("mapTarjeta")
    public Tarjeta buscarPrimeraTarjetaCliente(
        @Param("tipoDocumento") String tipoDocumento,
        @Param("numeroDocumento") String numeroDocumento
    );
    
    
    
    @Select(" SELECT" + 
            "    BN05.B05_ID_TAR," + 
            "    BN00.B00_ID_EMP," + 
            "    BN06.B06_ID_CLI," + 
            "    '' || SUBSTR(BN05.B05_NUM_TARJETA, 4) AS NUMEROTAR," + 
            "    BN06.B06_NOMBRES || ' ' || BN06.B06_APPATERNO || ' ' || BN06.B06_APMATERNO AS NOMBRECOMPLETO," + 
            "    CASE" + 
            "        WHEN BN05.B05_TIPO_TARJETA = '531013' THEN 'MC CORP BLK'" + 
            "        WHEN BN05.B05_TIPO_TARJETA = '530927' THEN 'MC CORP'" + 
            "        ELSE ''" + 
            "    END AS B05_TIPO_TARJETA," + 
            "    CASE" + 
            "        WHEN BN05.B05_DISENO = '1' THEN 'VIATICO'" + 
            "        WHEN BN05.B05_DISENO = '2' THEN 'CAJA CHICA'" + 
            "        WHEN BN05.B05_DISENO = '3' THEN 'ENCARGO'" + 
            "        ELSE ''" + 
            "    END AS B05_DISENO," + 
            "    CASE" + 
            "        WHEN BN07.B07_ESTADO = '5' THEN 'ACTIVADA'" + 
            "        WHEN BN07.B07_ESTADO = '6' THEN 'BLOQUEADA'" + 
            "        WHEN BN07.B07_ESTADO = '7' THEN 'CANCELADA'" + 
            "        ELSE ''" + 
            "    END AS B07_ESTADO " +  
            "FROM " +  
            "    BN_SATE.BNSATE05_TARJETA BN05 " + 
            "    INNER JOIN BN_SATE.BNSATE06_CLIENTE BN06 ON BN05.B06_ID_CLI = BN06.B06_ID_CLI " + 
            "    INNER JOIN BN_SATE.BNSATE07_EST_TARJETA BN07 ON BN07.B05_ID_TAR = BN05.B05_ID_TAR " + 
            "    INNER JOIN BN_SATE.BNSATE00_EMPRESA BN00 ON BN00.B00_ID_EMP = BN05.B00_ID_EMP " + 
            "WHERE " +   
            "    BN06.B06_TIPO_DOCUMENTO = #{tipoDocumento} " + 
            "    AND BN06.B06_NUM_DOCUMENTO = #{numDocumento} " + 
            "    AND BN05.B05_NUM_TARJETA IS NOT NULL " + 
            "    AND BN07.B07_ETA = (SELECT MAX(B07.B07_ETA) " + 
            "                        FROM BN_SATE.BNSATE07_EST_TARJETA B07 " + 
            "                        WHERE B07.B05_ID_TAR = BN05.B05_ID_TAR)")
    @ResultMap("mapTarjetaOpcion")
    public List<TarjetaOpcion> cantidadTarjetasCliente(
            @Param("tipoDocumento") String tipoDocumento,
            @Param("numDocumento") String numDocumento );

    
    @Select("SELECT " + 
            "    BN05.B05_ID_TAR, " + 
            "    BN00.B00_ID_EMP, " + 
            "    BN06.B06_ID_CLI, " + 
            "    '' || SUBSTR(BN05.B05_NUM_TARJETA, 4) AS NUMEROTAR, " + 
            "    BN06.B06_NOMBRES || ' ' || BN06.B06_APPATERNO || ' ' || BN06.B06_APMATERNO AS NOMBRECOMPLETO, " + 
            "    CASE " + 
            "        WHEN BN05.B05_TIPO_TARJETA = '531013' THEN 'MC CORP BLK' " + 
            "        WHEN BN05.B05_TIPO_TARJETA = '530927' THEN 'MC CORP' " + 
            "        ELSE '' " + 
            "    END AS B05_TIPO_TARJETA, " + 
            "    CASE " + 
            "        WHEN BN05.B05_DISENO = '1' THEN 'VIATICO' " + 
            "        WHEN BN05.B05_DISENO = '2' THEN 'CAJA CHICA' " + 
            "        WHEN BN05.B05_DISENO = '3' THEN 'ENCARGO' " + 
            "        ELSE '' " + 
            "    END AS B05_DISENO, " + 
            "    CASE " + 
            "        WHEN BN07.B07_ESTADO = '5' THEN 'ACTIVADA' " + 
            "        WHEN BN07.B07_ESTADO = '6' THEN 'BLOQUEADA' " + 
            "        WHEN BN07.B07_ESTADO = '7' THEN 'CANCELADA' " + 
            "        ELSE '' " + 
            "    END AS B07_ESTADO " + 
            "FROM " + 
            "    BN_SATE.BNSATE05_TARJETA BN05 " + 
            "    INNER JOIN BN_SATE.BNSATE06_CLIENTE BN06 ON BN05.B06_ID_CLI = BN06.B06_ID_CLI " + 
            "    INNER JOIN BN_SATE.BNSATE07_EST_TARJETA BN07 ON BN07.B05_ID_TAR = BN05.B05_ID_TAR " + 
            "    INNER JOIN BN_SATE.BNSATE00_EMPRESA BN00 ON BN00.B00_ID_EMP = BN05.B00_ID_EMP " + 
            "WHERE " + 
            "    BN05.B05_NUM_TARJETA = #{numTar} " + 
            "    AND BN07.B07_ETA = (SELECT MAX(B07.B07_ETA) " + 
            "                        FROM BN_SATE.BNSATE07_EST_TARJETA B07 " + 
            "                        WHERE B07.B05_ID_TAR = BN05.B05_ID_TAR)")
    @ResultMap("mapTarjetaOpcion")
    public List<TarjetaOpcion> cantidadTarjetasClientePorTar(@Param("numTar") String numTar);

    
    
    
  
    @Select("SELECT BN05.*," +
            "(SELECT BN00.B00_RAZON_SOCIAL FROM BN_SATE.BNSATE00_EMPRESA BN00 WHERE BN00.B00_ID_EMP = BN05.B00_ID_EMP) AS BN00_EMPRESA," +
            "SUBSTR(BN05.B05_ENTREGA_UBIGEO, 1, 2) AS B05_ENTREGA_DEPARTAMENTO," +
            "SUBSTR(BN05.B05_ENTREGA_UBIGEO, 3, 2) AS B05_ENTREGA_PROVINCIA," +
            "SUBSTR(BN05.B05_ENTREGA_UBIGEO, 5, 2) AS B05_ENTREGA_DISTRITO," +
            "(CASE WHEN BN05.B05_ENTREGA_UBICACION = '4' THEN (SELECT B00_RAZON_SOCIAL FROM BN_SATE.BNSATE00_EMPRESA BN00 WHERE BN00.B00_ID_EMP = BN05.B05_ENTREGA_AGENCIA_BN) ELSE '' END) AS ENTREGA_UBICACION_UE," +
            "BN07.B07_ESTADO," +
            "BN07.B07_MOTIVO, BN07.B07_FEC_REGISTRO, BN07.B07_USUARIO_CREA " +
            "FROM BN_SATE.BNSATE05_TARJETA BN05 " +
            "INNER JOIN BN_SATE.BNSATE06_CLIENTE BN06 ON BN05.B06_ID_CLI = BN06.B06_ID_CLI " +
            "INNER JOIN BN_SATE.BNSATE07_EST_TARJETA BN07 ON BN07.B05_ID_TAR = BN05.B05_ID_TAR " +
            "INNER JOIN BN_SATE.BNSATE00_EMPRESA BN00 ON BN00.B00_ID_EMP = BN05.B00_ID_EMP " +
            "WHERE " +
            " BN07.B07_ETA = (SELECT MAX(B07.B07_ETA) FROM BN_SATE.BNSATE07_EST_TARJETA B07 WHERE B07.B05_ID_TAR = BN05.B05_ID_TAR) " +
            "AND (BN07.B07_ESTADO = '5' OR BN07.B07_ESTADO = '6'  OR BN07.B07_ESTADO = '7')  " +
            "AND BN05.B05_ID_TAR = #{idTar}")
    @ResultMap("mapTarjeta")
    public Tarjeta buscarTarjetaPorIdValidosParaBloqueo(
            @Param("idTar") Long idTar );

    
    

}
