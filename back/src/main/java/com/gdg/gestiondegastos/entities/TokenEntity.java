
package com.gdg.gestiondegastos.entities;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="token_id")
    private Integer tokenid;
    
    @Column(name="confirmacion")
    private String confirmacion;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaCreacion;
    
    @OneToOne(targetEntity=Usuario.class, fetch=FetchType.EAGER)
    @JoinColumn(nullable=false, name="id_usuario")
    private Usuario usuario;
}
