/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.configuracion;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
    
    
    
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    
    //Aquí se configura el acceso
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        
        http.csrf().disable().authorizeRequests().antMatchers("/gestion/agregar").anonymous().antMatchers("/gestion/crear").anonymous().antMatchers("/gestion").permitAll().antMatchers("/gestion/confirmar").permitAll().antMatchers("/gestion/ingresar").anonymous()
        .antMatchers("/gestion/**").authenticated().antMatchers("/ingresar").authenticated().antMatchers("/inicio").authenticated()
            .and().formLogin().loginPage("/gestion/login").permitAll().successForwardUrl("/gestion/paginaPrincipal").failureForwardUrl("/gestion/login");
        //http.formLogin().loginPage("/gestion/login").successForwardUrl("/gestion/paginaPrincipal").failureForwardUrl("/gestion/login");
        http.logout().logoutSuccessUrl("/gestion").invalidateHttpSession(true).deleteCookies("JSESSIONID").clearAuthentication(true);
        /*http.anonymous().disable().csrf().disable().authorizeRequests().antMatchers("/registro**")
                .permitAll().anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").usernameParameter("correo").passwordParameter("contrasenya")
                .successForwardUrl("/paginaPrincipal/{id}").failureForwardUrl("/paginaPrincipal")
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/paginaPrincipal").invalidateHttpSession(true)
                .clearAuthentication(true).permitAll();*/
    }
    
    //Aquí se configura Usuario/Password
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
                 .withUser("jorge").password("{noop}1111").roles("Usuario")
                 .and()
                 .withUser("juan").password("{noop}1111").roles("Administrador");*/
        auth.userDetailsService(validacion).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); //To change body of generated methods, choose Tools | Templates.
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }    

    
}
