package com.apirestful.apirestful.config;

import com.apirestful.apirestful.Security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration // Indica que esta classe é uma configuração do Spring
@EnableWebSecurity // Habilita a segurança web no aplicativo
@EnableMethodSecurity(prePostEnabled = true) // Habilita a segurança de método com pré-autorização
public class SecurityConfig {

    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;

    // Definição de URLs públicas que não exigem autenticação
    private static final String[] PUBLIC_MATCHERS ={
            "/"
    };
    private static final String[] PUBLIC_MATCHERS_POST ={
            "/user",
            "/login"
    };

    // Configuração do filtro de segurança
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Desabilita CSRF e CORS
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);

        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                        .passwordEncoder(bCryptPasswordEncoder());

        authenticationManager = authenticationManagerBuilder.build();

        // Configura as autorizações de acesso
        http.authorizeRequests()
                .requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                .requestMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated();

        // Configura o gerenciamento de sessão para STATELESS (sem estado)
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build(); // Retorna a cadeia de filtros de segurança configurada
    }

    // Configuração do source de CORS
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        // Cria uma configuração CORS padrão permitindo todos os métodos
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a configuração a todas as URLs

        return source; // Retorna o source de configuração CORS
    }

    // Configuração de um codificador de senha BCrypt
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder () {
        return new BCryptPasswordEncoder(); // Retorna um codificador de senha BCrypt
    }

}