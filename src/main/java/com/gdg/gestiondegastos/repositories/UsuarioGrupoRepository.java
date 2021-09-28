package com.gdg.gestiondegastos.repositories;

import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupo,Integer>{
    
    @Query("select ug from UsuarioGrupo ug where ug.usuario.id = :id_usuario")
    public List<UsuarioGrupo> leerPorUsuario(Integer id_usuario);
    
    @Query("select ug from UsuarioGrupo ug where ug.grupo.id = :id_grupo")
    public List<UsuarioGrupo> leerPorGrupo(Integer id_grupo);
    
    @Query("select ug from UsuarioGrupo ug where ug.usuario.id = :id_usuario and ug.grupo.id = :id_grupo")
    public UsuarioGrupo leerPorUsuarioYGrupo(Integer id_usuario, Integer id_grupo);
    
    @Modifying
    @Query(value="INSERT INTO usuario_grupo(id_usuario, id_grupo, rol) values (:id_usuario,:id_grupo, :rol)", nativeQuery=true)
    @Transactional
    public void anadirUsuario(@Param("id_usuario")Integer id_usuario, @Param("id_grupo")Integer id_grupo, @Param("rol")Integer rol);
}