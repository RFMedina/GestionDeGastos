package com.gdg.gestiondegastos.dto;

import com.gdg.gestiondegastos.entities.Movimiento;
import java.util.Date;
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

    public MovimientoDto(Movimiento mov) {
        id = mov.getId();
        categoria = mov.getCategoria();
        fecha = mov.getFecha();
        concepto = mov.getConcepto();
        cantidad = mov.getCantidad();
    }
}
