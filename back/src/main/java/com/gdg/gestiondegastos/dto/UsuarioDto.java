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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//Me encomiendo a los santos tocando el DTO, seguramente esto cambie por graves fallos

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioDto {

   private Integer id;
    
    private String nombre;
    private String contrasenya;
    private String telefono;
    private String correo;
    private Boolean modoOscuro;
    private Boolean verificado;
    
    
    public UsuarioDto(Usuario usu){
        id=usu.getId();
        nombre=usu.getNombre();
        telefono=usu.getTelefono();
        correo=usu.getCorreo();
        contrasenya=usu.getContrasenya();
        modoOscuro=usu.getModoOscuro();
        verificado=usu.getVerificado();
    }
        
}
