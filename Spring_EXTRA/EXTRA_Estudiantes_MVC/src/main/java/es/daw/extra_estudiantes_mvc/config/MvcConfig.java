package es.daw.extra_estudiantes_mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Para endpoints que solo necesitan mostrar una vista
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/invitado").setViewName("info");
        registry.addViewController("/principal").setViewName("principal");
        registry.addViewController("/filtrar-estudiantes").setViewName("estudiantes/filtro");
        registry.addViewController("/ciclos-informatica").setViewName("ciclos-info");
    }
}
