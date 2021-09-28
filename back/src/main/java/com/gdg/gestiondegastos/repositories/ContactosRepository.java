
package com.gdg.gestiondegastos.repositories;

import com.gdg.gestiondegastos.entities.Contactos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface ContactosRepository extends JpaRepository<Contactos, Integer>{
    
    public List<Contactos> findByUsuarioHost(@Param("usuario_host") Integer usuarioHost);
}
