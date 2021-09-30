package com.gdg.gestiondegastos.dto;

import com.gdg.gestiondegastos.entities.Movimiento;
import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovimientoDto {
    private Integer id;
    private String categoria;
    private Date fecha;
    private String concepto;
    private Double cantidad;
    
    
}
