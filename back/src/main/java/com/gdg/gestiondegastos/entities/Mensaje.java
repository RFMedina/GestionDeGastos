/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.entities;

import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Joche
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mensaje {   
    
    private String usuario;
    private String mensaje;
    //private Date fecha;
    

}
