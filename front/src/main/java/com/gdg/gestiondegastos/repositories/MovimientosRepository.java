package com.gdg.gestiondegastos.repositories;

import java.util.List;

import com.gdg.gestiondegastos.entities.Movimiento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovimientosRepository extends JpaRepository<Movimiento, Integer> {

        @Query("select m from Movimiento m " + " where m.usuarioGrupo.grupo.id = :id_grupo" + " and ( "
                        + " (:search is null or m.concepto like concat('%', :search, '%') ) "
                        + " or (:search is null or m.categoria like concat('%', :search, '%')) "
                        + " or (:search is null or m.usuarioGrupo.usuario.nombre like concat('%', :search, '%')) "
                        + ")")
        public Page<Movimiento> leerPorGrupo(Integer id_grupo, String search, Pageable paginable);

        @Query("select m from Movimiento m where m.usuarioGrupo.usuario.id = :id_usuario")
        public List<Movimiento> leerPorUsuario(Integer id_usuario);

}