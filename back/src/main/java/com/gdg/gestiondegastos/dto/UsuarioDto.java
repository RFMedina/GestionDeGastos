/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.dto;

import com.gdg.gestiondegastos.entities.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Data;

//Me encomiendo a los santos tocando el DTO, seguramente esto cambie por graves fallos

@Data
public class UsuarioDto {

    private Integer id;
    
    private String correo;
    private String contrasenya;
    
    private List<String> roles;
    
    public UsuarioDto(Usuario usu){
        id=usu.getId();
        correo=usu.getCorreo();
        contrasenya=usu.getContrasenya();
        roles.add("ROLE_Usuario");
    }
        
}
