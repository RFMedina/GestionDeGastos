/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Joche
 */
@Service
public class FirebaseInitialization {
    
    @PostConstruct
    public void initialization() {
       
        
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream(new ClassPathResource("serviceAccountKey.json").getFile());

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://gestion-gastos-chat-default-rtdb.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (Exception ex) {
            Logger.getLogger(FirebaseInitialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
