package com.dreamsoft.ingresosegresos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder build) throws  Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    auth.inMemoryAuthentication()
            .withUser("admin")
            .password("{noop}123")
            .roles("ADMIN")
            .and()
            .withUser("user")
            .password("{noop}123")
            .roles("USER");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/usuarios/editarUsuario/**", "/usuarios/crearUsuario/**", "/usuarios/desactivarUsuario/**"
                , "/empresas/crearEmpresa/**", "empresas/editarEmpresa/**"
                )
                .hasRole("ADMIN")
                .antMatchers("/usuarios/verUsuarios/**","/","/empresas/verEmpresas", "movimientos/**")
                .hasAnyRole("ADMIN","USER")
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/errores/accessDenied");
    }
}


