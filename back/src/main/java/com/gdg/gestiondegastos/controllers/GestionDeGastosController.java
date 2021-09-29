package com.gdg.gestiondegastos.controllers;

import com.gdg.gestiondegastos.dto.GrupoDto;
import com.gdg.gestiondegastos.dto.MovimientoDto;
import com.gdg.gestiondegastos.dto.UsuarioDto;
import com.gdg.gestiondegastos.dto.UsuarioDto2;
import com.gdg.gestiondegastos.dto.UsuarioGrupoDto;
import com.gdg.gestiondegastos.dto.PresupuestoDto;
import com.gdg.gestiondegastos.entities.Grupo;
import com.gdg.gestiondegastos.entities.Movimiento;
import com.gdg.gestiondegastos.entities.Presupuesto;
import com.gdg.gestiondegastos.entities.Usuario;
import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import com.gdg.gestiondegastos.repositories.GrupoRepository;
import com.gdg.gestiondegastos.repositories.MovimientosRepository;
import com.gdg.gestiondegastos.repositories.PresupuestoRepository;
import com.gdg.gestiondegastos.repositories.UsuarioGrupoRepository;
import com.gdg.gestiondegastos.repositories.UsuarioRepository;
import java.sql.SQLException;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gestion")
public class GestionDeGastosController {

    @Autowired
    private UsuarioRepository repoUsuario;
    @Autowired
    private GrupoRepository repoGrupo;
    @Autowired
    private UsuarioGrupoRepository repoUsuarioGrupo;
    @Autowired
    private PresupuestoRepository repoPresupuesto;
    @Autowired
    private MovimientosRepository repoMovimientos;
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/agregar")
    public Map<String, Object> agregarUsuario(Usuario usuario) {
        Map<String, Object> m = new HashMap<>();
        m.put("usuario", new Usuario());
        return m;
    }

    @PostMapping("/crear")
    public void crear(Usuario usuario) throws ClassNotFoundException, SQLException {
        UsuarioDto usu = mapper.map(repoUsuario.findByCorreo(usuario.getCorreo()), UsuarioDto.class);

        if (usu != null) {
            Map<String, Object> m = new HashMap<>();
            m.put("msg", "Correo ya registrado");
        } else {

            Grupo grupo = new Grupo();
            grupo.setNombre("Mi presupuesto personal");
            grupo.setFechaCreacion(java.sql.Date.from(Instant.now(Clock.systemDefaultZone())));
            Grupo grupoCreado = repoGrupo.save(grupo);
            Presupuesto pre = new Presupuesto();
            pre.setCantidadInicio(0.0);
            pre.setCantidadFinal(0.0);
            pre.setFechaInicio(java.sql.Date.from(Instant.now(Clock.systemDefaultZone())));
            pre.setGrupo(grupoCreado);
            repoPresupuesto.save(pre);
            ArrayList<UsuarioGrupo> ug = new ArrayList<>();
            ug.add(new UsuarioGrupo(0, Boolean.TRUE, usuario, grupoCreado, new ArrayList<>()));
            repoUsuarioGrupo.save(ug.get(0));
            usuario.setUsuarioGrupo(ug);
        }
    }

    @GetMapping("/inicio/nuevoGrupo")
    public Map<String, Object> nuevoGrupo(Integer idUsuario) {

        Map<String, Object> m = new HashMap<>();

        Grupo g = new Grupo();

        g.setUsuarioGrupo(repoUsuarioGrupo.leerPorUsuario(idUsuario));

        m.put("grupo", mapper.map(repoUsuario.findById(idUsuario).get(), UsuarioDto.class));
        return m;
    }

    @PostMapping("/inicio/guardarGrupo")
    public void guardarGrupo(Grupo grupo, Double presupuesto, Integer idUsuario) {
        grupo.setFechaCreacion(java.sql.Date.from(Instant.now(Clock.systemDefaultZone())));
        Grupo grupoCreado = repoGrupo.save(grupo);
        Presupuesto pre = new Presupuesto();
        pre.setCantidadInicio(presupuesto);
        pre.setCantidadFinal(presupuesto);
        pre.setFechaInicio(java.sql.Date.from(Instant.now(Clock.systemDefaultZone())));
        pre.setGrupo(grupoCreado);
        repoPresupuesto.save(pre);

        ArrayList<UsuarioGrupo> ug = new ArrayList<>();
        ug.add(new UsuarioGrupo(0, Boolean.TRUE, repoUsuario.findById(idUsuario).get(), grupoCreado,
                new ArrayList<>()));
        repoUsuarioGrupo.save(ug.get(0));
    }

    @PostMapping("/ingresar")
    public Boolean ingresar(String correo[], String[] contrasenya) {

        Usuario usuario = new Usuario();
        System.out.println(" USUARIO  1    " + correo);
        try {
            usuario = repoUsuario.findByCorreo(correo[0]);
            System.out.println(" USUARIO   2   " + usuario.getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (usuario.getNombre() != null);
    }

    @GetMapping("/inicio") // Terminado
    public Map<String, Object> inicio(Integer idUsuario) {
        UsuarioDto user = mapper.map(repoUsuario.findById(idUsuario).get(), UsuarioDto.class);
        Double presupuestoPersonal = 0d;
        if (user.getUsuarioGrupo().stream().map(x -> x.getGrupo().getPresupuesto()).findFirst().isPresent()) {

            presupuestoPersonal = user.getUsuarioGrupo().stream().map(x -> x.getGrupo().getPresupuesto()).findFirst()
                    .get().stream().collect(Collectors.summingDouble(p -> p.getCantidadFinal()));
        }
        Map<String, Object> m = new HashMap<>();
        m.put("presupuestoPersonal", presupuestoPersonal);

        m.put("movimientos", repoMovimientos.leerPorUsuario(idUsuario).stream().limit(4)
                .map(x -> mapper.map(x, MovimientoDto.class)).collect(Collectors.toList()));

        m.put("usuarioGrupo", repoUsuarioGrupo.leerPorUsuario(idUsuario).stream()
                .map(x -> mapper.map(x.getGrupo(), GrupoDto.class)).collect(Collectors.toList()));

        return m; 
    }

    @GetMapping("/perfil") // Terminado
    public Map<String, Object> perfil(Integer idUsuario) {
        Map<String, Object> m = new HashMap<>();
        m.put("usuario", mapper.map(repoUsuario.findById(idUsuario).get(), UsuarioDto2.class));

        return m;
    }

    @PostMapping("/guardarPerfil")
    public void guardarPerfil(Usuario usuario) {
        repoUsuario.save(usuario);

    }

    @GetMapping("/contrasenya") // Terminado
    public Map<String, Object> contrasenya(Integer idUsuario) {
        Map<String, Object> m = new HashMap<>();
        m.put("usuario", mapper.map(repoUsuario.findById(idUsuario).get(), UsuarioDto2.class));
        return m;
    }

    @PostMapping("/guardarcontrasenya")
    public void guardarContrasenya(Usuario usuario, String contrasenya, Integer idUsuario) {
        Usuario user = repoUsuario.findById(idUsuario).get();

        repoUsuario.save(user);

    }

    @GetMapping("/grupo/{idGrupo}") // Terminado
    public Map<String, Object> verGrupos(@PathVariable Integer idGrupo) {

        Map<String, Object> m = new HashMap<>();

        m.put("grupo", mapper.map(repoGrupo.findById(idGrupo).get(), GrupoDto.class));

        m.put("movimientos", repoMovimientos.leerPorUsuario(idGrupo).stream()
                .map(x -> mapper.map(x, MovimientoDto.class)).collect(Collectors.toList()));

        m.put("presupuesto", mapper.map(repoPresupuesto.findById(idGrupo).get(), PresupuestoDto.class));

        return m;
    }

    @GetMapping("{idGrupo}/borrar")
    public void borrarGrupos(@PathVariable Integer idGrupo) {

        repoGrupo.deleteById(idGrupo);

    }

    @GetMapping("/grupo/{idGrupo}/gestionar")
    public Map<String, Object> gestionarGrupos(@PathVariable Integer idGrupo) {

        Map<String, Object> m = new HashMap<>();

        m.put("usuarioGrupo", repoUsuarioGrupo.leerPorGrupo(idGrupo));
        m.put("grupo", repoGrupo.findById(idGrupo).get());

        return m;
    }

    @GetMapping("/grupo/{idGrupo}/borrarUsuario")
    public Boolean borrarUsuario(Integer idUsuarioGrupo, Integer idGrupo) {
        repoUsuarioGrupo.deleteById(idUsuarioGrupo);

        if (repoUsuarioGrupo.leerPorGrupo(idGrupo).isEmpty()) {
            repoGrupo.deleteById(idGrupo);
            return true;
        }

        return false;
    }

    @GetMapping("/grupo/nuevoUsuarioGrupo")
    public String anadirUsuario(String correo, @RequestParam Integer idGrupo) {
        Usuario nuevoUsuario = repoUsuario.findByCorreo(correo);
        UsuarioGrupo usuariosGrupo = repoUsuarioGrupo.leerPorUsuarioYGrupo(nuevoUsuario.getId(), idGrupo);
        Map<String, Object> m = new HashMap<>();
        if (usuariosGrupo == null) {
            if (nuevoUsuario != null) {
                repoUsuarioGrupo.anadirUsuario(nuevoUsuario.getId(), idGrupo, 0);
            } else {
                return "Usuario no encontrado";
            }
        } else {
            return "El usuario que intenta agregar ya se encuentra en el grupo";
        }
        return "Usuario agregado";
    }

    @GetMapping("grupo/cambiarNombre")
    public void cambiarNombreGrupo(String nombre, @RequestParam Integer idGrupo) {

        repoGrupo.cambiarNombre(idGrupo, nombre);
    }

    @GetMapping("/grupo/{idGrupo}/nuevoMovimiento")//Terminado
    public Map<String, Object> nuevoMovimientos(@PathVariable Integer idGrupo, Integer idUsuario) {
        Map<String, Object> m = new HashMap<>();
        Movimiento mov = new Movimiento();
        UsuarioGrupo ug = repoUsuarioGrupo.leerPorUsuarioYGrupo(idUsuario, idGrupo);
        mov.setUsuarioGrupo(ug);
        m.put("movimiento", mapper.map(mov, MovimientoDto.class));
        m.put("idUsuarioGrupo", ug.getId());
        m.put("idGrupo", idGrupo);

        return m;
    }

    @PostMapping("/grupo/guardarMovimiento")
    public void guardarMovimiento(Movimiento mov, Integer idUsuarioGrupo, Integer idGrupo) {
        mov.setUsuarioGrupo(repoUsuarioGrupo.findById(idUsuarioGrupo).get());
        Movimiento movNuevo = repoMovimientos.save(mov);
        Presupuesto p = repoPresupuesto.findByIdGrupo(idGrupo);
        p.setCantidadFinal(p.getCantidadFinal() + movNuevo.getCantidad());
        repoPresupuesto.save(p);
    }

    @GetMapping("/misGrupos")//Terminaddo
    public Map<String, Object> misGrupos(Integer idUsuario) {
        Map<String, Object> m = new HashMap<>();
        m.put("grupos", repoUsuarioGrupo.leerPorUsuario(idUsuario).stream()
                .map(x -> mapper.map(x.getGrupo(), GrupoDto.class)).collect(Collectors.toList()));
        return m;
    }

    @GetMapping("/misMovimientos")//Terminado
    public Map<String, Object> misMov(Integer idUsuario) {
        
        Map<String, Object> m = new HashMap<>();
         UsuarioDto use = mapper.map(repoUsuario.findById(idUsuario).get(), UsuarioDto.class);
         
        m.put("movimientos", repoMovimientos.leerPorUsuario(idUsuario).stream()
                .map(x -> mapper.map(x, MovimientoDto.class)).collect(Collectors.toList()));

        m.put("usuarioGrupo", repoUsuarioGrupo.leerPorUsuario(idUsuario).stream()
                .map(x -> mapper.map(x.getGrupo(), GrupoDto.class)).collect(Collectors.toList()));
        
        Double presupuestoPersonal = 0d;
        if (use.getUsuarioGrupo().stream().map(x -> x.getGrupo().getPresupuesto()).findFirst().isPresent()) {

            presupuestoPersonal = use.getUsuarioGrupo().stream().map(x -> x.getGrupo().getPresupuesto()).findFirst()
                    .get().stream().collect(Collectors.summingDouble(p -> p.getCantidadFinal()));
        }
        m.put("presupuestoPersonal", presupuestoPersonal);
        
        return m;
    }

}
