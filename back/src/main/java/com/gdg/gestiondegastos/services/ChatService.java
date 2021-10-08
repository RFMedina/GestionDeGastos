/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.services;
import com.gdg.gestiondegastos.entities.Mensaje;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author Joche
 */
@Service
public class ChatService {
    
    private static final String COLLECCION_FIRESTORE = "chat";
    
    
    public void guardarMensajes(Map<String,List<String>> datos, String grupo) throws InterruptedException, ExecutionException{        
        Firestore dbFirestore = FirestoreClient.getFirestore();        
        dbFirestore.collection(COLLECCION_FIRESTORE).document(grupo).set(datos);        
    }
    
    public void crearChat(String grupo) throws InterruptedException, ExecutionException{        
        Firestore dbFirestore = FirestoreClient.getFirestore();        
        Map<String,List<String>> comienzo=new HashMap<>();
        List<String> lst = List.of("", "¡Hola! Bienvenido al chat");
        comienzo.put(java.sql.Date.from(Instant.now(Clock.systemDefaultZone())).toString(), lst);
        dbFirestore.collection(COLLECCION_FIRESTORE).document(grupo).set(comienzo);       
        
    }
        
    public Map<String,List<Mensaje>> getMensajes(String grupo) throws InterruptedException, ExecutionException{
        Firestore dbFirestore = FirestoreClient.getFirestore();
        
       DocumentReference consulta = dbFirestore.collection(COLLECCION_FIRESTORE).document(grupo);
       ApiFuture<DocumentSnapshot> future = consulta.get();
       
       DocumentSnapshot datos = future.get();
       
        Map<String,List<Mensaje>> mensaje = new HashMap<>();
        List<Mensaje> msjs = new ArrayList<>();
        
       
       if(datos.exists()){    
            mensaje=datos.getData().entrySet().stream()
                .collect(Collectors.toMap(x->x.getKey(), x-> List.of(new Mensaje(((List<String>)x.getValue()).get(0),((List<String>)x.getValue()).get(1)))));
                
           return mensaje;
       }else{
           crearChat(grupo);
           
           return getMensajes(grupo);
       }    
    }
    
    public void actualizarMensajes(Map<String,List<String>> datos, String grupo) throws InterruptedException, ExecutionException{        
        Firestore dbFirestore = FirestoreClient.getFirestore();        
        dbFirestore.collection(COLLECCION_FIRESTORE).document(grupo).set(datos);        
    }
    
    public void eliminarMensajes(String grupo) throws InterruptedException, ExecutionException{        
        Firestore dbFirestore = FirestoreClient.getFirestore();        
        dbFirestore.collection(COLLECCION_FIRESTORE).document(grupo).delete();        
    }
    
}
