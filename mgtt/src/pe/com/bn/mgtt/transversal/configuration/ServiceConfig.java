package pe.com.bn.mgtt.transversal.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"pe.com.bn.mgtt.infraestructura.service.internal.impl",
		"pe.com.bn.mgtt.infraestructura.service.external.impl","pe.com.bn.mgtt.infraestructura.facade"})
public class ServiceConfig {

}
