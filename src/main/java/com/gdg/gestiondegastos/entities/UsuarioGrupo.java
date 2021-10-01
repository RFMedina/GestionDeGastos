
package com.gdg.gestiondegastos.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario_grupo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioGrupo {
    
    //Campos propios
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    //el rol es booleano; true=admin,false="normal"
    private Boolean rol;
    
    
    //Uniones de tablas
    
    //(Recibe el id de Usuario)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_usuario" )
    private Usuario usuario;
    
    //(Recibe el id de Grupo)    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_grupo" )
    private Grupo grupo;
    
    //(Envia el id a Movimiento)
    
    @OneToMany(mappedBy = "usuarioGrupo",cascade = CascadeType.ALL)
    private List<Movimiento> movimiento;
}
