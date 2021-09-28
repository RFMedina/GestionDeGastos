/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.repositories;

import com.gdg.gestiondegastos.entities.TokenEntity;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Usuario
 */
public interface TokenRepository extends CrudRepository<TokenEntity, String>{
    TokenEntity findByConfirmacion(String confirmacion);
}
