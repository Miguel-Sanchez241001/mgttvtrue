package pe.com.bn.mgtt.transversal.configuration;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class MyBatisConfig {

    private static final String DATA_SOURCE_SATE = "SATE";
    private static final String DATA_SOURCE_TABLAS = "TABLAS";

    private static final String SQL_SESSION_FACTORY_SATE = "sqlSessionFactorySate";
    private static final String SQL_SESSION_FACTORY_TABLAS = "sqlSessionFactoryTablas";

    private static final String MAPPER_PACKAGE_SATE = "pe.com.bn.mgtt.persistence.mapper.internal";
    private static final String MAPPER_PACKAGE_TABLAS = "pe.com.bn.mgtt.persistence.mapper.external";

    private static final String TYPE_ALIASES_PACKAGE_SATE = "pe.com.bn.mgtt.persistence.dto.internal";
    private static final String TYPE_ALIASES_PACKAGE_TABLAS = "pe.com.bn.mgtt.persistence.dto.external";

    @Value("classpath:pe/com/bn/mgtt/persistence/mapper/internal/*.xml")
    private Resource[] mapperLocationSate;

    @Value("classpath:pe/com/bn/mgtt/persistence/mapper/external/*.xml")
    private Resource[] mapperLocationTablas;

    private final PersistenciaConfig persistenciaConfig = new PersistenciaConfig();

    @Bean
    public DataSource dataSourceSate() throws NamingException {
        return persistenciaConfig.configureDataSource(DATA_SOURCE_SATE);
    }

    @Bean
    public DataSource dataSourceTablas() throws NamingException {
        return persistenciaConfig.configureDataSource(DATA_SOURCE_TABLAS);
    }

    @Bean(name = SQL_SESSION_FACTORY_SATE)
    public SqlSessionFactory sqlSessionFactorySate() throws Exception {
        return persistenciaConfig.configureSqlSessionFactory(
                dataSourceSate(), mapperLocationSate, TYPE_ALIASES_PACKAGE_SATE);
    }

    @Bean(name = SQL_SESSION_FACTORY_TABLAS)
    public SqlSessionFactory sqlSessionFactoryTablas() throws Exception {
        return persistenciaConfig.configureSqlSessionFactory(
                dataSourceTablas(), mapperLocationTablas, TYPE_ALIASES_PACKAGE_TABLAS);
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurerSate() {
        return persistenciaConfig.configureMapperScanner(
                MAPPER_PACKAGE_SATE, SQL_SESSION_FACTORY_SATE);
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurerTablas() {
        return persistenciaConfig.configureMapperScanner(
                MAPPER_PACKAGE_TABLAS, SQL_SESSION_FACTORY_TABLAS);
    }

    
    @Bean
    public PlatformTransactionManager transactionManagerSate() throws NamingException {
        return persistenciaConfig.configureTransactionManager(dataSourceSate());
    }
}
