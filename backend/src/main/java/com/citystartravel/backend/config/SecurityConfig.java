package com.citystartravel.backend.config;

import com.citystartravel.backend.security.CustomUserDetailsService;
import com.citystartravel.backend.security.JwtAuthenticationEntryPoint;
import com.citystartravel.backend.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity //This is the primary spring security annotation that is used to enable web security in a project.

/*
@EnableGlobalMethodSecurity: This is used to enable method level security based on annotations. You can use following three types of annotations for securing your methods -
securedEnabled: It enables the @Secured annotation using which you can protect your controller/service methods like so - @Secured("ROLE_ADMIN")public User getAllUsers() {}
jsr250Enabled: It enables the @RolesAllowed annotation - @RolesAllowed("ROLE_ADMIN") public Poll createPoll() {}
prePostEnabled: It enables more complex expression based access control syntax with @PreAuthorize and @PostAuthorize annotations -
        @PreAuthorize("isAnonymous()") public boolean isUsernameAvailable() {}
        @PreAuthorize("hasRole('USER')") public Poll createPoll() {}
*/
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)


public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    /*This class is used to return a 401 unauthorized error to clients that try to access a protected resource without proper authentication. It implements Spring Security’s AuthenticationEntryPoint interface.*/
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;


    /*We’ll use JWTAuthenticationFilter to implement a filter that -
        reads JWT authentication token from the Authorization header of all the requests
        validates the token
        loads the user details associated with that token.
        Sets the user details in Spring Security’s SecurityContext. Spring Security uses the user details to perform authorization checks. We can also access the user details stored in the SecurityContext in our controllers to perform our business logic.*/
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /*AuthenticationManagerBuilder is used to create an AuthenticationManager instance which is the main Spring Security interface for authenticating a user.
    You can use AuthenticationManagerBuilder to build in-memory authentication, LDAP authentication, JDBC authentication, or add your custom authentication provider.*/
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.
                userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico","/**/*.png","/**/*.gif","/**/*.svg","/**/*.jpg",
                        "/**/*.html","/**/*.css","/**/*.js")
                .permitAll()

                .antMatchers("/api/auth/**")
                .permitAll()
                .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
                .permitAll()
                .antMatchers(HttpMethod.GET,  "/api/users/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}
