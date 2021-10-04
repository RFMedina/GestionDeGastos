/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.services;

import com.gdg.gestiondegastos.entities.Grupo;
import com.gdg.gestiondegastos.entities.Movimiento;
import com.gdg.gestiondegastos.entities.Presupuesto;
import com.gdg.gestiondegastos.entities.Usuario;
import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import com.gdg.gestiondegastos.interfaces.IGeneracionDatos;
import com.gdg.gestiondegastos.repositories.GrupoRepository;
import com.gdg.gestiondegastos.repositories.MovimientosRepository;
import com.gdg.gestiondegastos.repositories.PresupuestoRepository;
import com.gdg.gestiondegastos.repositories.UsuarioGrupoRepository;
import com.gdg.gestiondegastos.repositories.UsuarioRepository;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Joche
 */
@Service
public class GeneracionDatos implements IGeneracionDatos {

    @Autowired
    private UsuarioRepository usuarios;


    @Autowired
    private GrupoRepository grupos;

    @Autowired
    private UsuarioGrupoRepository usuarioGrupos;

    @Autowired
    private PresupuestoRepository presupuestos;

    @Autowired
    private MovimientosRepository movimientos;

    @Autowired
    private PasswordEncoder encoder;

    private Date fecha = new Date();

    @Override
    public void generarDatos() {
        if (usuarios.findAll().iterator().hasNext() == false) {
            var user = usuarios.save(new Usuario(1, "Raul", encoder.encode("1111"), "1234", "mail@mail.com", true, null));
            var grupo = grupos.save(new Grupo(1, "Grupo personal de Raul", Date.from(Instant.now()), null, null));
            var usuarioGrupo = usuarioGrupos.save(new UsuarioGrupo(1, true, user, grupo, null));
            presupuestos.save(new Presupuesto(1, 1000d, 1200d, Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(100000)), grupo));
            movimientos.save(new Movimiento(1, "Hogar", Date.from(Instant.now()), "Aspiradora Turbo3000", -50d, usuarioGrupo));
            movimientos.save(new Movimiento(2, "Entretenimiento", Date.from(Instant.now()), "Nintendo Switch", -30.50, usuarioGrupo));
            movimientos.save(new Movimiento(3, "Entretenimiento", Date.from(Instant.now()), "Cancelacion 'Nintendo Switch'", +30.50, usuarioGrupo));
            movimientos.save(new Movimiento(4, "Regalo", Date.from(Instant.now()), "Reintegro cupon ONCE", +5d, usuarioGrupo));

            
            
            user = usuarios.save( new Usuario(2, "Diego", encoder.encode("1111"), "1234", "mail2@mail.com", true, null));
            grupo = grupos.save(new Grupo(2, "Grupo personal de Diego", Date.from(Instant.now()), null, null));
            usuarioGrupo = usuarioGrupos.save(new UsuarioGrupo(2, true, user, grupo, null));
            presupuestos.save(new Presupuesto(2, 220d, 1130.5, Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(100000)), grupo));
            movimientos.save(new Movimiento(5, "Entretenimiento", Date.from(Instant.now()), "2 Entradas Cruella", -9d, usuarioGrupo));
            movimientos.save(new Movimiento(6, "Entretenimiento", Date.from(Instant.now()), "Dark Souls REMASTERED", -20.50, usuarioGrupo));
            movimientos.save(new Movimiento(7, "Hogar", Date.from(Instant.now()), "Beca grado", +1950d, usuarioGrupo));
            movimientos.save(new Movimiento(8, "Transporte", Date.from(Instant.now()), "Bono tranvia TUZSA", -605d, usuarioGrupo));

            user = usuarios.save(new Usuario(3, "Jose Maria", encoder.encode("1111"), "1234", "mail3@mail.com", true, null));
            grupo = grupos.save(new Grupo(3, "Grupo personal de Josema", Date.from(Instant.now()), null, null));
            usuarioGrupo = usuarioGrupos.save(new UsuarioGrupo(3, true, user, grupo, null));
            presupuestos.save(new Presupuesto(3, 200d, 1633.65, Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(100000)), grupo));
            movimientos.save(new Movimiento(9, "Hogar", Date.from(Instant.now()), "Nomina HIBERUS", 1100.75, usuarioGrupo));
            movimientos.save(new Movimiento(10, "Medicina", Date.from(Instant.now()), "Pastillas, vitaminas etc", -15.70, usuarioGrupo));
            movimientos.save(new Movimiento(11, "Transporte", Date.from(Instant.now()), "Beca grado", -1.50, usuarioGrupo));
            movimientos.save(new Movimiento(12, "Medicina", Date.from(Instant.now()), "Bono tranvia TUZSA", +250.1, usuarioGrupo));
            
            // CREAR GRUPO RAUL-DIEGO-JOSE
            grupo = grupos.save(new Grupo(4, "Grupo Raul-Diego-Jose", Date.from(Instant.now()), null, null));
            

            usuarioGrupos.save(new UsuarioGrupo(4,true,usuarios.findById(1).get(),grupos.findById(4).get(),null)); //4 = Raul    6=Raul
            usuarioGrupos.save(new UsuarioGrupo(5,false,usuarios.findById(2).get(),grupos.findById(4).get(),null)); //5= Diego   7=Diego
            usuarioGrupos.save(new UsuarioGrupo(6,false,usuarios.findById(3).get(),grupos.findById(4).get(),null)); //6= Jose    8=Jose
            presupuestos.save(new Presupuesto(4, 1000d, 1433.13, Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(100000)), grupo));
            //6 7 y 8
            movimientos.save(new Movimiento(9, "Alimentacion", Date.from(Instant.now()), "Compra de 5 yatekomo", 1100.75, usuarioGrupos.getById(5)));
            movimientos.save(new Movimiento(9, "Regalo", Date.from(Instant.now()), "Propina de la abuela", 1100.75, usuarioGrupos.getById(4)));
            movimientos.save(new Movimiento(9, "Transporte", Date.from(Instant.now()), "Monedas en el suelo", 1100.75, usuarioGrupos.getById(5)));
            movimientos.save(new Movimiento(9, "Regalo", Date.from(Instant.now()), "Libro de Stephen King para Paloma", 1100.75, usuarioGrupos.getById(6)));
            movimientos.save(new Movimiento(9, "Entretenimiento", Date.from(Instant.now()), "Cascos New Skill Kimera V2", 1100.75, usuarioGrupos.getById(4)));
            movimientos.save(new Movimiento(9, "Comunicacion", Date.from(Instant.now()), "Resolución juicio del bar", 1100.75, usuarioGrupos.getById(4)));
            movimientos.save(new Movimiento(9, "Higiene", Date.from(Instant.now()), "Cremas, pasta de dientes etc", 1100.75, usuarioGrupos.getById(6)));
            movimientos.save(new Movimiento(9, "Entretenimiento", Date.from(Instant.now()), "Humble Bundle", 1100.75, usuarioGrupos.getById(5)));
            movimientos.save(new Movimiento(9, "Regalo", Date.from(Instant.now()), "Tarjeta reagalo hipercor", 1100.75, usuarioGrupos.getById(4)));
            movimientos.save(new Movimiento(9, "Alimentacion", Date.from(Instant.now()), "Carro de la compra", 1100.75, usuarioGrupos.getById(5)));
            movimientos.save(new Movimiento(9, "Transporte", Date.from(Instant.now()), "Mas viajes de tranvia", 1100.75, usuarioGrupos.getById(6)));
            movimientos.save(new Movimiento(9, "Alimentacion", Date.from(Instant.now()), "Cena UDON", 1100.75, usuarioGrupos.getById(4)));


            
            
        }

    }

}
