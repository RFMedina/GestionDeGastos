
package com.gdg.gestiondegastos.entities;

import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    private String nombre;
    private String contrasenya;
    private String telefono;
    private String correo;
    private Boolean modoOscuro;
    private Boolean verificado;
    
    //Uniones de Tablas
    //(Envia el id a UsuarioGrupo)
    @OneToMany(mappedBy = "usuario",fetch = FetchType.EAGER)
    private List<UsuarioGrupo> usuarioGrupo;
    
}
