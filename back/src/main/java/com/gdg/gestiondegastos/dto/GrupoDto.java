/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.dto;

import com.gdg.gestiondegastos.entities.Grupo;
import com.gdg.gestiondegastos.entities.Presupuesto;
import java.util.Date;
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
public class GrupoDto {
    private Integer id;
    private String nombre;
    private Date fechaCreacion;
    private List<PresupuestoDto> presupuesto;
    private Boolean rol;
    

}
