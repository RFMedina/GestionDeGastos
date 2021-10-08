/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.controllers;

import com.gdg.gestiondegastos.dto.UsuarioDto;
import com.gdg.gestiondegastos.entities.Mensaje;
import com.gdg.gestiondegastos.feign.BackFeign;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@Controller
@RequestMapping("/gestion")
public class ChatController {
    @Autowired
    private BackFeign feign;    
    
    @PostMapping("/mensajes")
    public void guardarMensaje(@RequestBody Map<String,List<String>>  mensaje,@RequestParam String grupo) throws ExecutionException, InterruptedException{
        feign.guardarMensaje(mensaje,grupo);  
    }
    
    @PostMapping("/mensajes/{grupo}") 
    public String obtenerMensaje(@PathVariable String grupo, Model m) throws ExecutionException, InterruptedException{
        UsuarioDto usuario = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        
        Map<String, List<Mensaje>> a = feign.obtenerMensaje(grupo);
        m.addAttribute("fechas", a.keySet().stream().sorted((x, y) -> -1).collect(Collectors.toList()));
        m.addAttribute("mensajes",a.values().stream().sorted((x, y) -> -1).collect(Collectors.toList()));
        return "chat";
    }
    
    @PostMapping("/crearChat")
    public void crearChat(@RequestParam String grupo) throws ExecutionException, InterruptedException{
        feign.crearChat(grupo);  
    }
    
    
}
