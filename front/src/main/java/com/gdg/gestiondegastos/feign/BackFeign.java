/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdg.gestiondegastos.feign;

import com.gdg.gestiondegastos.dto.GestionarResponseDto;
import com.gdg.gestiondegastos.dto.GrupoDto;
import com.gdg.gestiondegastos.dto.GrupoDto2;
import com.gdg.gestiondegastos.dto.GrupoDto4;
import com.gdg.gestiondegastos.dto.GrupoDto5;
import com.gdg.gestiondegastos.dto.NuevoGrupoDto;
import com.gdg.gestiondegastos.dto.NuevoMovDto;
import com.gdg.gestiondegastos.dto.TablaBSDto;
import com.gdg.gestiondegastos.dto.UsuarioDto;
import com.gdg.gestiondegastos.dto.UsuarioDto2;
import com.gdg.gestiondegastos.dto.UsuarioDto3;
import com.gdg.gestiondegastos.dto.UsuarioGrupoDto;
import com.gdg.gestiondegastos.dto.UsuarioGrupoDto2;
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
@FeignClient(name = "GestionBack", url = "http://localhost:8080")
@RequestMapping("/gestionback")
public interface BackFeign {

    @GetMapping("/tablaMovimientos")
    public TablaBSDto obtenerMovimientos2(@RequestParam Integer idUsuario, @RequestParam String search,
                    @RequestParam String sort, @RequestParam String order, @RequestParam Integer offset,
                    @RequestParam Integer limit);

    @GetMapping("/tablaGrupos")
    public TablaBSDto obtenerMovimientos(@RequestParam Integer idGrupo, @RequestParam String search,
                    @RequestParam String sort, @RequestParam String order, @RequestParam Integer offset,
                    @RequestParam Integer limit);

    @GetMapping("/inicio/nuevoGrupo")
    public NuevoGrupoDto nuevoGrupo(@RequestParam Integer idUsuario);

    @PostMapping("/inicio/guardarGrupo")
    public void guardarGrupo(@RequestParam Integer id, @RequestParam String nombre,
                    @RequestParam Double pPresupuesto, @RequestParam Integer pIdUsuario);

    @GetMapping("/grupo/{idGrupo}/nuevoMovimiento")
    public NuevoMovDto nuevoMovimientos(@RequestParam Integer idGrupo, @RequestParam Integer idUsuario);

    @PostMapping("/grupo/guardarMovimiento")
    public void guardarMovimiento(@RequestParam Integer id, @RequestParam String categoria,
                    @RequestParam Double cantidad, @RequestParam String concepto,
                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fecha,
                    @RequestParam Integer idUsuarioGrupo, @RequestParam Integer idGrupo);

    @GetMapping("/agregar")
    public UsuarioDto agregarUsuario(@SpringQueryMap Usuario usuario);

   
    @PostMapping("/crear") 
    public Boolean crear(@RequestParam String nombre, @RequestParam String correo, @RequestParam String contrasenya,
            @RequestParam String telefono, @RequestParam Boolean modoOscuro, @RequestParam Boolean verificado);
     

    @PostMapping("/ingresar")
    public Boolean ingresar(@RequestParam String correo, @RequestParam String contrasenya);

    @GetMapping("/inicio")
    public UsuarioDto inicio(@RequestParam Integer idUsuario);

    @GetMapping("/perfil")
    public UsuarioDto3 perfil(@RequestParam Integer idUsuario);

    @PostMapping("/guardarPerfil")
    public void guardarPerfil(@RequestParam Integer id, @RequestParam String contrasenya,
                    @RequestParam String nombre, @RequestParam String correo, @RequestParam String telefono,
                    @RequestParam Boolean modoOscuro, @RequestParam Boolean verificado);

    @GetMapping("/contrasenya")
    public UsuarioDto contrasenya(@RequestParam Integer idUsuario);

    @PostMapping("/guardarcontrasenya")
    public void guardarContrasenya(@RequestParam String contrasenya, @RequestParam Integer idUsuario);

    @GetMapping("/misMovimientos")
    public UsuarioDto misMov(@RequestParam Integer idUsuario);

    @GetMapping("/grupo/{idGrupo}")
    public GrupoDto2 verGrupos(@RequestParam Integer idGrupo);
    
    @GetMapping("/grupo/{idGrupo}/gestionar")
    public GestionarResponseDto gestionarGrupos(@RequestParam Integer idUsuario, @RequestParam Integer idGrupo);
    
    @GetMapping("/grupo/{idGrupo}/borrarUsuario")
    public Boolean borrarUsuario(@RequestParam Integer idUsuarioGrupo, @PathVariable Integer idGrupo);
    
    @GetMapping("/grupo/nuevoUsuarioGrupo")
    public void anadirUsuario(@RequestParam String correo, @RequestParam Integer idGrupo);
    
    @GetMapping("{idGrupo}/borrar")
    public void borrarGrupos(@RequestParam Integer idGrupo);
    
    @GetMapping("grupo/cambiarNombre")
    public void cambiarNombreGrupo(@RequestParam String nombre, @RequestParam Integer idGrupo);
    
    @GetMapping("/misGrupos")
    public GrupoDto5 misGrupos(@RequestParam Integer idUsuario);
    
    @GetMapping("/confirmar")
    public Boolean confirmarCuenta(@RequestParam String token);
}
