/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.dto;

import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GestionarResponseDto {
    private GrupoDto4 grupo;
    private List<ContactoDto> contactos;
    private List<UsuarioGrupoDto> usuarioGrupo;
    private Boolean usuYGrupo;
}
