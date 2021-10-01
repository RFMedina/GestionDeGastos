/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.entities;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "movimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String categoria;

    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;

    private String concepto;
    private Double cantidad;

    /*
     * @ManyToOne
     * 
     * @JoinColumn(name = "id_usuario_grupo") private UsuarioGrupo idUsuarioGrupo;
     */
    // (Recibe el id de Grupo)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_usuario_grupo")
    private UsuarioGrupo usuarioGrupo;
}
