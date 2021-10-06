
package com.gdg.gestiondegastos.repositories;

import com.gdg.gestiondegastos.entities.Contactos;
import com.gdg.gestiondegastos.entities.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface ContactosRepository extends JpaRepository<Contactos, Integer>{
    
    @Query("select c from Contactos c where c.usuarioHost.id = :id_usuario")
    public List<Contactos> findByUsuarioHost(Integer id_usuario);
    
    
    @Query("select c from Contactos c where c.usuarioHost.id = :id_usuario")
    public Usuario leerPorUsuarioHost(Integer id_usuario);
    
    @Modifying
    @Query(value="INSERT INTO contactos(usuario_host, usuario_inv) values (:usuario_host,:usuario_inv)", nativeQuery=true)
    @Transactional
    public void anadirContacto(@Param("usuario_host")Integer id_usuario, @Param("usuario_inv")Integer id_grupo);
            
}
