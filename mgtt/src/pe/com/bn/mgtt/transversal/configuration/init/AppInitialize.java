package pe.com.bn.mgtt.transversal.configuration.init;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
 import org.springframework.context.annotation.Bean;
 
@Configuration
@ComponentScan(basePackages = {"pe.com.bn.mgtt.transversal.configuration", "pe.com.bn.mgtt.infraestructura.service.internal.impl"})
@PropertySource("classpath:mgtt.properties")
public class AppInitialize {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
