/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.dto;

import java.util.Date;
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
public class PresupuestoDto {
    private Integer id;
    private Double cantidadInicio;
    private Double cantidadFinal;
    private Date fechaInicio;
    private Date fechaFinal;
}
