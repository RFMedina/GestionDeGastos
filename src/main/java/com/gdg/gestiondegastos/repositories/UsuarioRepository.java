package com.gdg.gestiondegastos.repositories;

import com.gdg.gestiondegastos.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
    public Usuario findByCorreo( @Param("correo") String correo);
    
     
     
    @Modifying
    @Query("update Usuario u set u.contrasenya = :contrasenya where u.id = :id_usuario")
    public void updateContrasenya(Integer id_usuario, String contrasenya);
    
    /*SI EL DE ARRIBA NO TE FUNCIONA PRUEBA ESTE
    @Modifying
    @Query("update Usuario u set u.contrasenya = :contrasenya where u.id = :id_usuario")
    public void updateContrasenya(@Param("id_usuario") Integer id_usuario, @Param("contrasenya") String contrasenya);*/
    
    
    //BORRA DE LA BBDD, NO DEL USUARIO_GRUPO, aunque como esta en cascade, deberia borrar tambien del usuario_grupo
    @Modifying
    @Query("delete from Usuario u where u.id = :id_usuario")
    public void borrarUsuario(Integer id_usuario);

    
} 