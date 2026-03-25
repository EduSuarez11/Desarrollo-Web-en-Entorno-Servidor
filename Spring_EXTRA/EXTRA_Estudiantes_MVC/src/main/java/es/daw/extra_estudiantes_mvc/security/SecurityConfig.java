package es.daw.extra_estudiantes_mvc.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity // Permite la anotación @PreAuthorized
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {

        return http
                // MVC con sesión: IF_REQUIRED
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                // H2 console (solo si lo usas). Si no, puedes quitar este bloque.
                //.csrf(csrf -> csrf.ignoringRequestMatchers("/h2/**"))

                // PENDIENTE CONFIGURAR CORRECTAMENTE CSRF!!!!
                .csrf(csrf -> csrf.disable())
                //.headers(headers -> headers.frameOptions(f -> f.disable()))// frames de h2-console

                .headers(h -> h.frameOptions(frame -> frame.sameOrigin()))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/","/login","/home","/h2-console/**").permitAll()
                        .requestMatchers("/invitado/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        //.requestMatchers("/error", "/error/**").permitAll()
                        //.requestMatchers(HttpMethod.GET, "/events","/events/**").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/principal", true) // true indica que siempre se usa esa url cuando el login es correcto
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .authenticationProvider(authenticationProvider)
                .build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                         PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
