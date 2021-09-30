/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.feign;

import com.gdg.gestiondegastos.dto.GrupoDto;
import com.gdg.gestiondegastos.dto.NuevoGrupoDto;
import com.gdg.gestiondegastos.dto.NuevoMovDto;
import com.gdg.gestiondegastos.dto.UsuarioDto;
import com.gdg.gestiondegastos.dto.UsuarioDto2;
import com.gdg.gestiondegastos.dto.UsuarioDto3;
import com.gdg.gestiondegastos.entities.Grupo;
import com.gdg.gestiondegastos.entities.Movimiento;
import com.gdg.gestiondegastos.entities.Usuario;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import javax.ws.rs.Consumes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author Usuario
 */
@FeignClient(name="GestionBack", url="http://localhost:8080") 
@RequestMapping("/gestionback")
public interface BackFeign {

    @GetMapping("/inicio/nuevoGrupo")
    public NuevoGrupoDto nuevoGrupo(@RequestParam Integer idUsuario); 

    @PostMapping("/inicio/guardarGrupo")
    public void guardarGrupo(@RequestParam Integer id, @RequestParam String nombre, @RequestParam Double pPresupuesto, @RequestParam Integer pIdUsuario);
    
   @GetMapping("/grupo/{idGrupo}/nuevoMovimiento")
    public NuevoMovDto nuevoMovimientos(@RequestParam Integer idGrupo, @RequestParam Integer idUsuario);
    
    @PostMapping("/grupo/guardarMovimiento")
    public void guardarMovimiento(@RequestParam Integer id, @RequestParam String categoria,@RequestParam Double cantidad, @RequestParam String concepto,
                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fecha,
                                  @RequestParam Integer idUsuarioGrupo, @RequestParam Integer idGrupo);
    
     @GetMapping("/agregar")
    public UsuarioDto agregarUsuario(@SpringQueryMap Usuario usuario);
    
    @PostMapping("/crear")
    public void crear(@SpringQueryMap Usuario usuario);
    
    @PostMapping("/ingresar")
    public Boolean ingresar(@RequestParam String correo, @RequestParam String contrasenya);
    
    @GetMapping("/inicio")
    public UsuarioDto inicio(@RequestParam Integer idUsuario);
    
    @GetMapping("/perfil")
    public UsuarioDto3 perfil(@RequestParam Integer idUsuario); 
    
    @PostMapping("/guardarPerfil")
    public void guardarPerfil(@RequestParam Integer id, @RequestParam String contrasenya, 
                              @RequestParam String nombre, @RequestParam String correo, @RequestParam String telefono, @RequestParam Boolean modoOscuro, @RequestParam Boolean verificado);
    
    @GetMapping("/contrasenya")
    public UsuarioDto contrasenya(@RequestParam Integer idUsuario);
    
     @PostMapping("/guardarcontrasenya")
    public void guardarContrasenya(@RequestParam String contrasenya, @RequestParam Integer idUsuario);
    
    /*
    
    
    @PostMapping("/crear")
    public void crear(@SpringQueryMap Usuario usuario);
    
    
    
    @GetMapping("/grupo/{idGrupo}")
    public Map<String, Object> verGrupos(@PathVariable Integer idGrupo);
    
    @GetMapping("{idGrupo}/borrar")
    public void borrarGrupos(@PathVariable Integer idGrupo);
    
    @GetMapping("/grupo/{idGrupo}/gestionar")
    public Map<String, Object> gestionarGrupos(@PathVariable Integer idGrupo);
    
    @GetMapping("/grupo/{idGrupo}/borrarUsuario")
    public Boolean borrarUsuario(Integer idUsuarioGrupo, @PathVariable Integer idGrupo);
    
    @GetMapping("/grupo/nuevoUsuarioGrupo")
    public void anadirUsuario(String correo, @RequestParam Integer idGrupo);
    
    @GetMapping("grupo/cambiarNombre")
    public void cambiarNombreGrupo(String nombre, @RequestParam Integer idGrupo);
    
    
    
    @GetMapping("/misGrupos")
    public Map<String, Object> misGrupos(Integer idUsuario);
    
    @GetMapping("/misMovimientos")
    public Map<String, Object> misMov(Integer idUsuario);
*/
}
