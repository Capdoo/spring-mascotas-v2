package com.mascotas.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:qa.application.properties")
@Profile("qa")
public class PropertiesSourceQa {
}
