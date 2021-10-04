/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.dto;

import com.gdg.gestiondegastos.entities.Grupo;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Usuario
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoDto2 {
    private Integer id;
    private String nombre;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
    private PresupuestoDto presupuesto;
    private Grupo grupo;
    private List<MovimientoDto> movimientos;
    //private List<MovimientoDto2> movimientos;
}
