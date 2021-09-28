/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.dto;

import com.gdg.gestiondegastos.entities.Grupo;
import com.gdg.gestiondegastos.entities.Movimiento;
import com.gdg.gestiondegastos.entities.Usuario;
import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Joche
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioGrupoDto {
    private Integer id;
    private Boolean rol;
    private Usuario usuario;
    private Grupo grupo;
    private List<Movimiento> movimiento;
    
    public UsuarioGrupoDto(UsuarioGrupo ug){
        id=ug.getId();
        rol = ug.getRol();
        usuario=ug.getUsuario();
        grupo=ug.getGrupo();
        movimiento=ug.getMovimiento();
        
    }
}
