/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.dto;

import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDto { 
    private Integer id;
    private String categoria;
    
    private LocalDateTime fecha;
    
    private String concepto;
    private Double cantidad;
    private UsuarioGrupoDto usuarioGrupo;
}
