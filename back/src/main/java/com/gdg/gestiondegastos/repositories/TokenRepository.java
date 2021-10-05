/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.repositories;

import com.gdg.gestiondegastos.entities.TokenEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Usuario
 */
public interface TokenRepository extends JpaRepository<TokenEntity, String>{
    TokenEntity findByConfirmacion(String confirmacion);
    
    @Modifying
    @Query("delete from TokenEntity t where t.id = :id")
    public void borrarToken(Integer id); 
}
