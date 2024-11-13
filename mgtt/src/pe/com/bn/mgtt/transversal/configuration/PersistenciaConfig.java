package pe.com.bn.mgtt.transversal.configuration;


import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

public class PersistenciaConfig {


    private static final Map<String, String> dataSourceNames = new HashMap<>();

    // Registro de data sources para pasar sus nombres y rutas
    static {
        dataSourceNames.put("SATE", "jdbc/db_sate");
        dataSourceNames.put("TABLAS", "jdbc/db_sate_tablas");
      
    }

    /**
     * Configura el DataSource basado en el nombre especificado.
     * 
     * @param dataSourceKey Clave del data source en el mapa dataSourceNames.
     * @return DataSource configurado.
     * @throws NamingException en caso de error en JNDI.
     */
    public DataSource configureDataSource(String dataSourceKey) throws NamingException {
        JndiObjectFactoryBean dataSource = new JndiObjectFactoryBean();
        dataSource.setExpectedType(DataSource.class);
        dataSource.setJndiName(dataSourceNames.get(dataSourceKey));
        dataSource.setLookupOnStartup(true);
        dataSource.setProxyInterface(DataSource.class);

        try {
            dataSource.afterPropertiesSet();
        } catch (NamingException e) {
            throw new RuntimeException("Error configurando el DataSource para: " + dataSourceKey, e);
        }
        return (DataSource) dataSource.getObject();
    }

    /**
     * Configura la SqlSessionFactory de MyBatis.
     * 
     * @param dataSource DataSource configurado.
     * @param mapperLocation Ruta de los mappers XML.
     * @param typeAliasesPackage Paquete de alias para las clases DTO.
     * @return SqlSessionFactory configurada.
     * @throws Exception en caso de error.
     */
    public SqlSessionFactory configureSqlSessionFactory(DataSource dataSource, Resource[] mapperLocation,
            String typeAliasesPackage) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(mapperLocation);
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
        sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
        return sqlSessionFactory;
    }

    /**
     * Configura el MapperScanner para MyBatis.
     * 
     * @param basePackage Paquete donde se encuentran los mappers.
     * @param sqlSessionFactoryName Nombre de la SqlSessionFactory.
     * @return MapperScannerConfigurer configurado.
     */
    public MapperScannerConfigurer configureMapperScanner(String basePackage, String sqlSessionFactoryName) {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage(basePackage);
        configurer.setSqlSessionFactoryBeanName(sqlSessionFactoryName);
        return configurer;
    }

    /**
     * Configura el TransactionManager para un DataSource.
     * 
     * @param dataSource DataSource configurado.
     * @return TransactionManager configurado.
     */
    public PlatformTransactionManager configureTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}

