package com.gdg.gestiondegastos.dto;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoGrupoDto {
    private String concepto;
    private String categoria;
    private String nombreUsuario;
    private Date fecha;
    private Double cantidad;
}
