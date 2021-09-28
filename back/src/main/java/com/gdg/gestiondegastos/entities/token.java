
package com.gdg.gestiondegastos.entities;

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
@Table(name = "token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class token {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    private String confirmacion;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaCreacion;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_usuario" )
    private Usuario usuario;
}
