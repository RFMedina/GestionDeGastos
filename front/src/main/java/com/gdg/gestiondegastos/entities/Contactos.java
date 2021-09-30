
package com.gdg.gestiondegastos.entities;

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

@Entity
@Table(name = "contactos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contactos {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "usuarioHost" )
    private Usuario usuarioHost;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "usuarioInv" )
    private Usuario usuarioInv;
}
