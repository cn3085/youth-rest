package org.youth.api.config.web;

import java.util.Collection;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		Collection<TomcatConnectorCustomizer> tomcatConnectorCustomizer = factory.getTomcatConnectorCustomizers();
		tomcatConnectorCustomizer.add(connector -> connector.setParseBodyMethods("POST, PUT, DELETE"));
		factory.setTomcatConnectorCustomizers(tomcatConnectorCustomizer);
	}
	
	

}
