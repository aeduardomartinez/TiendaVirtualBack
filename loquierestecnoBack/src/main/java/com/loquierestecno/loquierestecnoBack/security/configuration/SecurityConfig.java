package com.loquierestecno.loquierestecnoBack.security.configuration;

import com.loquierestecno.loquierestecnoBack.constants.ConstantesGenerales;
import com.loquierestecno.loquierestecnoBack.model.Usuario;
import com.loquierestecno.loquierestecnoBack.repository.UsuarioRepository;
import com.loquierestecno.loquierestecnoBack.service.MyUserDetailsService;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UsuarioRepository usuarioRepository;
    private final MyUserDetailsService userDetailsService;

    @Bean
    public UserDetailsService userDetailsService() {
        return correo -> {
            Usuario usuario = usuarioRepository.findByCorreo(correo)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correo));
            return new org.springframework.security.core.userdetails.User(
                    usuario.getCorreo(),
                    usuario.getContrasena(),
                    usuario.getAuthorities()
            );
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        PasswordEncoder pE = new BCryptPasswordEncoder(14); // El valor '14' da cerca de 1 Seg. 13 da 600ms
        return pE;
    }

    @Bean
    public AuthenticationManager authenticationManager (HttpSecurity http) //(AuthenticationConfiguration config)
            throws Exception {
        //return config.getAuthenticationManager();
        AuthenticationManagerBuilder authenticationManagerBuilder =  http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        return authenticationManagerBuilder.build();
    }

    // =============================================================================
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/usuarios/registro").permitAll()
                        .requestMatchers("/api/producto/**").hasAuthority("SCOPE_ROLE_ADMINISTRADOR")
                        .requestMatchers("/api/usuarios/login").permitAll()
                        .requestMatchers("/api/usuarios/authenticate").permitAll()
                        .requestMatchers("/api/carrito/**").permitAll()
                        .requestMatchers("/api/admin/**").hasAuthority("SCOPE_ROLE_ADMINISTRADOR")
                        .requestMatchers("/api/cliente/**").hasRole("CLIENTE")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(configure -> configure.jwt(Customizer.withDefaults()))
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] signingKey = Decoders.BASE64.decode(ConstantesGenerales.LLAVE_CIFRADO);
        return NimbusJwtDecoder.withSecretKey(Keys.hmacShaKeyFor(signingKey)).macAlgorithm(MacAlgorithm.HS512).build();
    }

}