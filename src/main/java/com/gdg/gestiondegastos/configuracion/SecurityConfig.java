/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsService validacion;    
    
    //Aquí se configura el acceso
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
             .antMatchers("/").permitAll()
             .antMatchers("/info").permitAll()
             .antMatchers("/gestion").permitAll()
             .antMatchers("/grupos").permitAll()
             .antMatchers("/url1").permitAll()
             .antMatchers("/login").permitAll()
             .antMatchers("/altaUsuario").permitAll()
             .antMatchers("/url2").hasAnyRole("Administrador", "Usuario")
             .antMatchers("/vuelos/**").hasAnyRole("Administrador")
             .antMatchers("/traducir/**").hasAnyRole("Administrador", "Usuario")
             .antMatchers("/url3").hasRole("Administrador");
        http.formLogin();   //  /login. Spring
        http.csrf().disable();
        //http.formLogin();   //  /login. Spring
    }

    
    
    
    //Aquí se configura Usuario/Password
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
                 .withUser("jorge").password("{noop}1111").roles("Usuario")
                 .and()
                 .withUser("juan").password("{noop}1111").roles("Administrador");*/
        int a=3;
        auth.userDetailsService(validacion).passwordEncoder(passwordEncoder());
    }
    
    
    @Bean(name="passwordEncoder")
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    
    
}
