/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.feign;

import com.gdg.gestiondegastos.entities.Movimiento;
import lombok.Data;

/**
 *
 * @author Usuario
 */
@Data
class NuevoMovDto {
    Integer idGrupo;
    Integer idUsuario;
    Movimiento mov; 
}
