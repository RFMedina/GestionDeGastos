/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.dto;

import com.gdg.gestiondegastos.entities.Movimiento;
import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Usuario
 */
@Data        
@AllArgsConstructor
@NoArgsConstructor
public class NuevoMovDto {
   private Movimiento movimiento; 
   private Integer idUsuarioGrupo;
   private Integer idGrupo;
}
