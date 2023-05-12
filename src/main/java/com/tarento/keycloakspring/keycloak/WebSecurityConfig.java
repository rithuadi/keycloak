package com.tarento.keycloakspring.keycloak;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import javax.servlet.Filter;

//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

public class WebSecurityConfig {
    public static final String ADMIN = "admin";
    public static final String USER = "user";

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("websecurityconfig api");
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/test/anonymous", "/test/anonymous/**").permitAll()
//                .requestMatchers(HttpMethod.GET, "/test/admin", "/test/admin/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/test/admin", "/test/admin/**").hasRole("admin")
                .requestMatchers(HttpMethod.GET, "/test/user").hasRole("user")
                .requestMatchers(HttpMethod.GET, "/test/jwtRoles").permitAll()
                .anyRequest().authenticated();
        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthConverter);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        System.out.println("inside code -" + http.headers().toString());
        return http.build();

//        @Bean
//        public JwtDecoder jwtDecoder() {
//            return NimbusJwtDecoder.withPublicKey(yourPublicKey).build();
//        }


    }

//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/user").authenticated()
//                .and()
////                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
////                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
//                .addFilterBefore(jwtAuthenticationFilter, Filter.class);
//
//
//    }

}
