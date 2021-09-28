/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.configuracion;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ApplicationConfig extends WebSecurityConfigurerAdapter {
  @Bean
  public PasswordEncoder passEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.anonymous().disable().csrf().disable().authorizeRequests()
        .antMatchers("/gestion", "/gestion/login", "/gestion/agregar").permitAll().anyRequest().authenticated().and()
        .formLogin().loginPage("/login").usernameParameter("correo").passwordParameter("contrasenya")
        .successForwardUrl("/paginaPrincipal/{id}").failureForwardUrl("/paginaPrincipal").permitAll().and().logout()
        .logoutSuccessUrl("/paginaPrincipal").invalidateHttpSession(true).clearAuthentication(true).permitAll();
  }

}
