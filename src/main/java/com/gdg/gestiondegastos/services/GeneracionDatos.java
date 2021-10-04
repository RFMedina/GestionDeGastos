/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.services;

import com.gdg.gestiondegastos.entities.Usuario;
import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import com.gdg.gestiondegastos.interfaces.IGeneracionDatos;
import com.gdg.gestiondegastos.repositories.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Joche
 */
@Service
public class GeneracionDatos implements IGeneracionDatos{
    @Autowired
    private UsuarioRepository usuarios;

    @Autowired
    private PasswordEncoder encoder;
    
    @Override
    public void generarDatos() {
        if(usuarios.findAll().iterator().hasNext() == false){
            usuarios.save(
                    new Usuario(1, "Pepe", encoder.encode("1111"), "1234", "mail@mail.com", true, null));
        
            usuarios.save(
                    new Usuario(2, "Marta", encoder.encode("1111"), "565656", "mail2@mail.com", true, null));
        
            usuarios.save(
                    new Usuario(3, "Sara", encoder.encode("1111"), "444444", "mail3@mail.com", true, null));
        }
        
    }
    
    
}
