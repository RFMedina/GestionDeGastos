/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.services;

import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service("correoService")
public class CorreoService {
    public JavaMailSender javaMS;
    
    @Autowired
    public CorreoService(JavaMailSender jms){
        javaMS=jms;
    }
    
    @Async
    public void enviarCorreo(MimeMessage correo){
        javaMS.send(correo);
    }
}
