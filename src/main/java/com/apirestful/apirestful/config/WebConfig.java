package com.apirestful.apirestful.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Indica que esta classe é uma configuração do Spring
@EnableWebMvc // Habilita o suporte do Spring MVC
public class WebConfig implements WebMvcConfigurer { // Implementa a interface WebMvcConfigurer

    // Método para adicionar configurações de CORS (Cross-Origin Resource Sharing)
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**"); // Define que todas as URLs terão configurações CORS aplicadas
    }

}
