package com.gdg.gestiondegastos.dto;

import com.gdg.gestiondegastos.entities.Movimiento;
import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovimientoDto {
    private Integer id;
    private String categoria;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;
    private String concepto;
    private Double cantidad;
    private UsuarioGrupoDto usuarioGrupo;
    
}
