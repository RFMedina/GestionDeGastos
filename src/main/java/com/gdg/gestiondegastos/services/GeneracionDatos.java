/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.services;

import com.gdg.gestiondegastos.entities.Grupo;
import com.gdg.gestiondegastos.entities.Usuario;
import com.gdg.gestiondegastos.interfaces.IGeneracionDatos;
import com.gdg.gestiondegastos.repositories.GrupoRepository;
import com.gdg.gestiondegastos.repositories.UsuarioRepository;
import java.util.Date;
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
    private GrupoRepository grupos;
    
    @Autowired
    private PasswordEncoder encoder;
    
    private Date fecha = new Date();
    @Override
    public void generarDatos() {
        if(usuarios.findAll().iterator().hasNext() == false){
            //USERS
            usuarios.save(
                    new Usuario(1, "Raul", encoder.encode("Raul"), "1234", "Raul@gmail.com", true, null));
            usuarios.save(
                    new Usuario(2, "Admin", encoder.encode("Admin"), "565656", "Admin@gmail.com", true, null));
            usuarios.save(
                    new Usuario(3, "Sofia", encoder.encode("Sofia"), "444444", "Sofia@gmail.com", true, null));
            usuarios.save(
                    new Usuario(3, "Martin", encoder.encode("Martin"), "444444", "Martin@gmail.com", true, null));
            usuarios.save(
                    new Usuario(3, "Jose María", encoder.encode("Josema"), "444444", "JoseMa@gmail.com", true, null));
            
            //GRUPOS
            grupos.save(
                    new Grupo(1,"Mi Grupo",null,null));
        }
        
    }
    
    
}
