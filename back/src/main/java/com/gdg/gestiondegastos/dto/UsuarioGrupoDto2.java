/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.dto;

import com.gdg.gestiondegastos.entities.Grupo;
import com.gdg.gestiondegastos.entities.Movimiento;
import com.gdg.gestiondegastos.entities.Usuario;
import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioGrupoDto2 {
    private Integer id;
    private Boolean rol;
    private GrupoDto grupo;
    private List<MovimientoDto> movimientos;
}
