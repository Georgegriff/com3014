package com.com3014.group1.projectmatching.config;

import com.com3014.group1.projectmatching.auth.AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configure the system security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Provides authentication for Users
     */
    @Autowired
    @Qualifier("authProvider")
    private AuthenticationProvider authProvider;

    /**
     * Deals with successful authentication attempts
     */
    @Autowired
    private AuthenticationSuccessHandler authSuccessHandler;

    /**
     * Configure the security
     *
     * @param httpSecurity HTTP Security to set the configuration on
     * @throws Exception Exception thrown by HttpSecurity
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // Allow /login, /register, CSS and Javascript giles through the secuirty
        httpSecurity
                .authorizeRequests()
                .antMatchers("/login", "/register", "/css/**", "/js/login.js", "/js/plugins/form/plugin.js", "/js/plugins/form/template/template.htm", "/js/vendor/**.min.js", "/js/vendor/**.js").permitAll().anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(authSuccessHandler)
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(new ApplicationLoginEntryPoint("/login"));
    }

    /**
     * Configure the security with an AuthenticationManagerBuilder
     *
     * @param authManagerBuilder The AuthenticationManagerBuilder
     */
    @Override
    public void configure(AuthenticationManagerBuilder authManagerBuilder) {
        authManagerBuilder.authenticationProvider(authProvider);
    }

    /**
     * Provide a password encoder
     *
     * @return The password encoder
     */
    @Bean
    public PasswordEncoder passwordEncode() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
