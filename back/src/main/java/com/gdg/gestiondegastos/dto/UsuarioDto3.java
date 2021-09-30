/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.dto;

import com.gdg.gestiondegastos.entities.Contactos;
import com.gdg.gestiondegastos.entities.Movimiento;
import com.gdg.gestiondegastos.entities.TokenEntity;
import com.gdg.gestiondegastos.entities.Usuario;
import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//Me encomiendo a los santos tocando el DTO, seguramente esto cambie por graves fallos

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto3{

    private Integer id;
    
    private String correo;
    private String contrasenya;
    private List<UsuarioGrupo> usuarioGrupo;
    private Double presupuestoPersonal;
    private List<Movimiento> movimientos;
    private List<Contactos> contactos;
    private TokenEntity token;
  
    //añadiddod por JoseMari
   private GrupoDto grupo;
   private String nombre;
   private String telefono;
   private Boolean modoOscuro;
   private Boolean verificado;
   
    
    
}