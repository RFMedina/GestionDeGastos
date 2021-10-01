package com.gdg.gestiondegastos.repositories;

import com.gdg.gestiondegastos.entities.Presupuesto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PresupuestoRepository extends JpaRepository<Presupuesto,Integer>{

    @Query(value="SELECT * FROM presupuesto p WHERE p.id_grupo = :id_grupo",nativeQuery = true)
    public Presupuesto findByIdGrupo( @Param("id_grupo") int id_grupo); 
     
     
}