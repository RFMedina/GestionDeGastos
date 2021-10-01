/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.security;

import com.gdg.gestiondegastos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Usuario
 */
@Component
public class AuthProvider implements AuthenticationProvider{
    
    @Autowired
    private SecurityUserDetailsService uDS;
    @Autowired
    private PasswordEncoder pE;
    @Autowired
    private UsuarioRepository uR;

    @Override
    public Authentication authenticate(Authentication arg0) throws AuthenticationException {
        String username=arg0.getName();
        return null;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }
    
}
