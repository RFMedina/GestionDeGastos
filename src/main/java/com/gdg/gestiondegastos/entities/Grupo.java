package com.gdg.gestiondegastos.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Grupo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grupo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    private String nombre;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaCreacion;
    
    //Uniones de tablas
    
    //-----------COMPROBAR 1---------- 
    //(Envia su id a Presupuesto)
    @OneToMany(mappedBy = "grupo")
    private List<Presupuesto> presupuesto;
    
    //(Envia su id a UsuarioGrupo)
    @OneToMany(mappedBy = "grupo")
    private List<UsuarioGrupo> usuarioGrupo;
}