package com.ticketflow.romeuzxg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenFilter tokenFilter;

    public SecurityConfig(TokenFilter tokenFilter) {
        this.tokenFilter = tokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.POST, "/users/register", "/users/login", "/users/refresh").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/view").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/users/update/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/users/deactivate").authenticated()

                        .requestMatchers(HttpMethod.POST, "/organizer/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/organizer/view").hasAnyRole("ORGANIZER", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/organizer/updaterecord/{id}").hasRole("ORGANIZER")
                        .requestMatchers(HttpMethod.PATCH, "/organizer/update/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/organizer/deactivate/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/event/create").hasAnyRole("ORGANIZER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/event/view").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/event/updaterecords/{id}").hasRole("ORGANIZER")
                        .requestMatchers(HttpMethod.PATCH, "/event/update/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/event/deactivate/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/event/deleterecords/{id}").hasRole("ORGANIZER")

                        .requestMatchers(HttpMethod.POST, "/session/createsession").hasRole("ORGANIZER")
                        .requestMatchers(HttpMethod.POST, "/session/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/session/list").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/session/update/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/session/updateownsession/{id}").hasRole("ORGANIZER")
                        .requestMatchers(HttpMethod.DELETE, "/session/deletesession/{id}").hasRole("ORGANIZER")
                        .requestMatchers(HttpMethod.DELETE, "/session/deletebyid/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/location/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/location/view").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/location/update/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/location/delete/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/sector/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/sector/list").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/sector/update/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/sector/delete/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/seat/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/seat/view").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/seat/update/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/seat/deactivate/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/order/createorder").hasAnyRole("CUSTOMER", "ORGANIZER")
                        .requestMatchers(HttpMethod.GET, "/order/list").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/order/listown").hasAnyRole("CUSTOMER", "ORGANIZER")
                        .requestMatchers(HttpMethod.PATCH, "/order/update/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/order/deleterecords/{id}").hasAnyRole("CUSTOMER", "ORGANIZER")
                        .requestMatchers(HttpMethod.DELETE, "/order/delete/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/ticket/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/ticket/view").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/ticket/viewrecords").hasAnyRole("CUSTOMER", "ORGANIZER")
                        .requestMatchers(HttpMethod.GET, "/ticket/{id}/qrcode").hasAnyRole("CUSTOMER", "ORGANIZER", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/ticket/update/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/ticket/delete/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/payment/createcustompayment").hasAnyRole("CUSTOMER", "ORGANIZER")
                        .requestMatchers(HttpMethod.POST, "/payment/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/payment/viewrecords").hasAnyRole("CUSTOMER", "ORGANIZER")
                        .requestMatchers(HttpMethod.GET, "/payment/listall").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/payment/update/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/payment/delete/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/checkin/createrecords").hasRole("ORGANIZER")
                        .requestMatchers(HttpMethod.POST, "/checkin/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/checkin/viewrecords").hasRole("ORGANIZER")
                        .requestMatchers(HttpMethod.GET, "/checkin/view").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/checkin/update/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/checkin/delete/{id}").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

}
