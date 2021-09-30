/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service("correoService")
public class CorreoService {
    private JavaMailSender javaMS;
    
    @Autowired
    public CorreoService(JavaMailSender jms){
        javaMS=jms;
    }
    
    @Async
    public void enviarCorreo(SimpleMailMessage correo){
        javaMS.send(correo);
    }
}
