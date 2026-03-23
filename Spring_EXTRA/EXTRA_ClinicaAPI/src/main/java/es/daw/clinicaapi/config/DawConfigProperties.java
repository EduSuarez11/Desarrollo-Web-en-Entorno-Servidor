package es.daw.clinicaapi.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource( value = "classpath:daw.properties")
public class DawConfigProperties {
}
