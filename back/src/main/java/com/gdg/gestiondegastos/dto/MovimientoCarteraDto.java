package com.gdg.gestiondegastos.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoCarteraDto {
    private String concepto;
    private String categoria;
    private Date fecha;
    private Double cantidad;
}
