/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.controllers;

import com.gdg.gestiondegastos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Joche
 */
@Controller
@RequestMapping("/informacion")
public class InformacionController {
    @Autowired
    private UsuarioRepository repoUsu;
    
    @GetMapping
    public String verInfro(Model m){
        m.addAttribute("usuarios", repoUsu.findAll());
        
        return "informacion";
    }
    
}
