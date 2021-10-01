package com.gdg.gestiondegastos.repositories;

import com.gdg.gestiondegastos.entities.Grupo;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GrupoRepository extends JpaRepository<Grupo, Integer> {

    @Transactional
    @Modifying
    @Query("update Grupo g set g.nombre = :nombre where g.id = :id_grupo")
    public void cambiarNombre(@Param("id_grupo") Integer id_grupo, @Param("nombre") String nombre);

}