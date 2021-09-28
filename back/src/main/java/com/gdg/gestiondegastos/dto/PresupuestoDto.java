package com.gdg.gestiondegastos.dto;

import com.gdg.gestiondegastos.entities.Presupuesto;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PresupuestoDto {
    private Integer id;
    private Double cantidadInicio;
    private Double cantidadFinal;
    private Date fechaInicio;
    private Date fechaFinal;

}
