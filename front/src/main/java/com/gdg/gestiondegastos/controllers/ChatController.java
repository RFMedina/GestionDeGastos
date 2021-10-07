/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.controllers;

import com.gdg.gestiondegastos.entities.Mensaje;
import com.gdg.gestiondegastos.feign.BackFeign;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Joche
 */
@RestController
@RequestMapping("/gestion")
public class ChatController {
    @Autowired
    private BackFeign feign;    
    
    @PostMapping("/mensajes")
    public void guardarMensaje(@RequestBody Map<String,List<String>>  mensaje,@RequestParam String grupo) throws ExecutionException, InterruptedException{
        feign.guardarMensaje(mensaje,grupo);  
    }
    
    @PostMapping("/mensajes/{grupo}") 
    public Map<String,List<Mensaje>> obtenerMensaje(@PathVariable String grupo) throws ExecutionException, InterruptedException{
        return feign.obtenerMensaje(grupo);
    }
    
    
}
