/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.services;

import com.gdg.gestiondegastos.entities.Grupo;
import com.gdg.gestiondegastos.entities.Presupuesto;
import com.gdg.gestiondegastos.entities.Usuario;
import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import com.gdg.gestiondegastos.interfaces.IGeneracionDatos;
import com.gdg.gestiondegastos.repositories.GrupoRepository;
import com.gdg.gestiondegastos.repositories.PresupuestoRepository;
import com.gdg.gestiondegastos.repositories.UsuarioGrupoRepository;
import com.gdg.gestiondegastos.repositories.UsuarioRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
    private GrupoRepository grupos;
    
    @Autowired
    private UsuarioGrupoRepository usuariogrupos;
    
    @Autowired
    private PresupuestoRepository presupuestos;

    @Autowired
    private PasswordEncoder encoder;
    
    @Override
    public void generarDatos() {
        if(usuarios.findAll().iterator().hasNext() == false){
            var pepe = new Usuario(1, "Pepe", encoder.encode("1111"), "1234", "mail@mail.com", true, null);
            var grupoPepe=new Grupo(1, "Grupo personal de Pepe", Date.from(Instant.now()), null, null);
              
            usuariogrupos.save(new UsuarioGrupo(1, true, pepe, grupoPepe, null));
            presupuestos.save(new Presupuesto(1, 1000d, 1000d, Date.from(Instant.now()),Date.from(Instant.now().plusSeconds(100000)), grupoPepe));
        
            
            var marta = new Usuario(2, "Marta", encoder.encode("1111"), "565656", "mail2@mail.com", true, null);
            var grupoMarta=new Grupo(2, "Grupo personal de Marta", Date.from(Instant.now()), null, null);
            
              
            usuariogrupos.save(new UsuarioGrupo(2, true, marta, grupoMarta, null));
            presupuestos.save(new Presupuesto(2, 100d, 100d, Date.from(Instant.now()),Date.from(Instant.now().plusSeconds(100000)), grupoMarta));
        
            var sara = new Usuario(3, "Sara", encoder.encode("1111"), "444444", "mail3@mail.com", true, null);
            var grupoSara = new Grupo(3, "Grupo personal de Sara", Date.from(Instant.now()), null, null);
          
            usuariogrupos.save(new UsuarioGrupo(3, true, sara, grupoSara, null));
            presupuestos.save(new Presupuesto(3, 100d, 100d, Date.from(Instant.now()),Date.from(Instant.now().plusSeconds(100000)), grupoSara));
        
        }
        
    }
    
    
}
