package com.gdg.gestiondegastos.controllers;

import com.gdg.gestiondegastos.dto.UsuarioDto;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
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
    // @Autowired
    // private ModelMapper obj;
    @Autowired
    private PasswordEncoder clave;

    // Este es un get para ver la principal y asÃ­ ver los cambios
    @GetMapping("")
    public String principal() {
        return "paginaInicial";
    }

    @GetMapping("/agregar")
    public String agregarUsuario(Model m, Usuario usuario) {
        m.addAttribute("usuario", new Usuario());
        // repoUsuario.save(usuario);
        return "crearUsuario";
    }

    @GetMapping("/login") // Pagina de inicio principal
    public String principal2(Model m) {
        // m.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping("/crear")
    public String crear(Model m, Usuario usuario) throws ClassNotFoundException, SQLException {
        Usuario usu = repoUsuario.findByCorreo(usuario.getCorreo());
        if (usu != null) {
            m.addAttribute("msg", "Correo ya registrado");
            return "crearUsuario";
        } else {
            usuario.setContrasenya(clave.encode(usuario.getContrasenya()));

            Grupo grupo = new Grupo();
            grupo.setNombre("Mi presupuesto personal");
            grupo.setFechaCreacion(java.sql.Date.from(Instant.now(Clock.systemDefaultZone())));
            Grupo grupoCreado = repoGrupo.save(grupo);
            // ArrayList<Presupuesto> p = new ArrayList<>();
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
            // repoUsuario.save(usuario);
            return "login";
        }
    }

    @GetMapping("/inicio/nuevoGrupo")
    public String nuevoGrupo(Model m) {
        UsuarioDto usuValidado = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Grupo g = new Grupo();

        g.setUsuarioGrupo(repoUsuarioGrupo.leerPorUsuario(usuValidado.getId()));

        m.addAttribute("grupo", g);
        return "nuevoGrupo";
    }

    @PostMapping("/inicio/guardarGrupo")
    public String guardarGrupo(Grupo grupo, Double presupuesto) {
        UsuarioDto usuValidado = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        grupo.setFechaCreacion(java.sql.Date.from(Instant.now(Clock.systemDefaultZone())));
        Grupo grupoCreado = repoGrupo.save(grupo);
        Presupuesto pre = new Presupuesto();
        pre.setCantidadInicio(presupuesto);
        pre.setCantidadFinal(presupuesto);
        pre.setFechaInicio(java.sql.Date.from(Instant.now(Clock.systemDefaultZone())));
        pre.setGrupo(grupoCreado);
        repoPresupuesto.save(pre);

        ArrayList<UsuarioGrupo> ug = new ArrayList<>();
        ug.add(new UsuarioGrupo(0, Boolean.TRUE, repoUsuario.findById(usuValidado.getId()).get(), grupoCreado,
                new ArrayList<>()));
        repoUsuarioGrupo.save(ug.get(0));
        return "redirect:/gestion/inicio";
    }

    @Autowired
    private AuthenticationManager am;

    @PostMapping("/ingresar") // hacer login
    public String ingresar(Model m, String correo[], String[] contrasenya) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(correo[0], contrasenya[0]);
        Authentication auth = am.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Usuario usuario = new Usuario();
        System.out.println(" USUARIO  1    " + correo);
        try {
            usuario = repoUsuario.findByCorreo(correo[0]);
            System.out.println(" USUARIO   2   " + usuario.getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (usuario.getNombre() != null) {
            return "redirect:inicio";
        } else {
            return "login";
        }
    }

    // Antes del Security
    @GetMapping("/inicio")
    public String inicio(Model m) {
        UsuarioDto usuValidado = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Usuario user = repoUsuario.findById(usuValidado.getId()).get();
        // user = repoUsuario.getById(idUsuario);

        // Suma todas las cantidades iniciales indicadas en el presupuesto del usuario

        Double presupuestoPersonal = 0d;
        if (user.getUsuarioGrupo().stream().map(x -> x.getGrupo().getPresupuesto()).findFirst().isPresent()) {

            presupuestoPersonal = user.getUsuarioGrupo().stream().map(x -> x.getGrupo().getPresupuesto()).findFirst()
                    .get().stream().collect(Collectors.summingDouble(p -> p.getCantidadFinal()));
        }

        m.addAttribute("presupuestoPersonal", presupuestoPersonal);

        m.addAttribute("movimientos",
                repoMovimientos.leerPorUsuario(usuValidado.getId()).stream().limit(4).collect(Collectors.toList()));

        m.addAttribute("usuarioGrupo", repoUsuarioGrupo.leerPorUsuario(usuValidado.getId()));

        return "principal";
    }

    @GetMapping("/perfil")
    public String perfil(Model m) {
        UsuarioDto usuValidado = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        m.addAttribute("usuario", repoUsuario.findById(usuValidado.getId()).get());

        return "perfil";
    }

    @PostMapping("/guardarPerfil")
    public String guardarPerfil(Usuario usuario) {

        // Usuario user = repoUsuario.findById(usuario.getId()).get();
        repoUsuario.save(usuario);

        return "redirect:/gestion/perfil";
    }

    @GetMapping("/contrasenya")
    public String contrasenya(Model m) {
        UsuarioDto usuValidado = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        m.addAttribute("usuario", repoUsuario.findById(usuValidado.getId()).get());

        return "cambiarContraseya";
    }

    @PostMapping("/guardarcontrasenya")
    public String guardarContrasenya(Usuario usuario, String contrasenya) {
        UsuarioDto usuValidado = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Usuario user = repoUsuario.findById(usuValidado.getId()).get();

        user.setContrasenya(clave.encode(contrasenya));
        repoUsuario.save(user);

        return "redirect:/gestion/perfil";
    }

    /*
     * //Despues del Security
     * 
     * @GetMapping("/inicio") public String inicio(Model m,@RequestParam Integer
     * idUsuario) { Usuario user = repoUsuario.findById(idUsuario).get(); // user =
     * repoUsuario.getById(idUsuario);
     * 
     * // Suma todas las cantidades iniciales indicadas en el presupuesto del
     * usuario m.addAttribute("presupuestoPersonal",
     * user.getUsuarioGrupo().stream().map(x ->
     * x.getGrupo().getPresupuesto()).collect(Collectors .summingDouble(p ->
     * p.stream().collect(Collectors.summingDouble(z -> z.getCantidadInicio())))));
     * 
     * m.addAttribute("movimientos", repoMovimientos.leerPorUsuario(idUsuario));
     * 
     * return "principal"; }
     */
    @GetMapping("/grupo/{idGrupo}")
    public String verGrupos(Model m, @PathVariable Integer idGrupo) {

        m.addAttribute("grupo", repoGrupo.findById(idGrupo).get());
        m.addAttribute("movimientos", repoMovimientos.leerPorGrupo(idGrupo));
        m.addAttribute("presupuesto", repoPresupuesto.findByIdGrupo(idGrupo));
        return "grupos";
    }

    @GetMapping("{idGrupo}/borrar")
    public String verGrupos(@PathVariable Integer idGrupo) {

        repoGrupo.deleteById(idGrupo);

        return "redirect:/gestion/misGrupos";
    }

    @GetMapping("/grupo/{idGrupo}/gestionar")
    public String gestionarGrupos(Model m, @PathVariable Integer idGrupo) {

        m.addAttribute("usuarioGrupo", repoUsuarioGrupo.leerPorGrupo(idGrupo));
        m.addAttribute("grupo", repoGrupo.findById(idGrupo).get());

        return "gestionGrupos";
    }

    /*
     * //Sin ajax
     * 
     * @GetMapping("/grupo/{idGrupo}/borrarUsuario") public String
     * borrarUsuario(Integer idUsuarioGrupo, Integer idGrupo) {
     * repoUsuarioGrupo.deleteById(idUsuarioGrupo); return
     * "redirect:/gestion/grupo/{idGrupo}/gestionar"; }
     */
    // Con ajax
    @GetMapping("/grupo/{idGrupo}/borrarUsuario")
    public String borrarUsuario(Integer idUsuarioGrupo, Integer idGrupo) {
        repoUsuarioGrupo.deleteById(idUsuarioGrupo);

        if (repoUsuarioGrupo.leerPorGrupo(idGrupo).isEmpty()) {
            repoGrupo.deleteById(idGrupo);
            return "redirect:/gestion/inicio";
        }

        return "redirect:/gestion/grupo/{idGrupo}";
    }

    @GetMapping("/grupo/nuevoUsuarioGrupo")
    public String anadirUsuario(Model m, String correo, @RequestParam Integer idGrupo) {
        Usuario nuevoUsuario = repoUsuario.findByCorreo(correo);
        UsuarioGrupo usuariosGrupo = repoUsuarioGrupo.leerPorUsuarioYGrupo(nuevoUsuario.getId(), idGrupo);
        if (usuariosGrupo == null) {
            if (nuevoUsuario != null) {
                repoUsuarioGrupo.anadirUsuario(nuevoUsuario.getId(), idGrupo, 0);
            } else {
                m.addAttribute("msg", "Usuario no encontrado");
            }
        } else {
            m.addAttribute("msg", "El usuario que intenta agregar ya se encuentra en el grupo");
        }
        return "redirect:/gestion/grupo/" + idGrupo;
    }

    @GetMapping("grupo/cambiarNombre")
    public String cambiarNombreGrupo(String nombre, @RequestParam Integer idGrupo) {

        repoGrupo.cambiarNombre(idGrupo, nombre);
        return "redirect:/gestion/grupo/" + idGrupo;
    }

    // Ejemplo ded url: http://localhost:8080/gestion/grupo/6
    @GetMapping("/grupo/{idGrupo}/nuevoMovimiento")
    public String nuevoMovimientos(Model m, @PathVariable Integer idGrupo) {

        UsuarioDto usuValidado = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Movimiento mov = new Movimiento();
        // UsuarioGrupo ug =
        // repoGrupo.findById(idGrupo).get().getUsuarioGrupo().stream().filter(x->x.getUsuario().getId().equals(usuValidado.getId())).findFirst().get();
        // mov.setUsuarioGrupo(repoUsuarioGrupo.findById(ug.getId()).get());
        UsuarioGrupo ug = repoUsuarioGrupo.leerPorUsuarioYGrupo(usuValidado.getId(), idGrupo);
        mov.setUsuarioGrupo(ug);
        m.addAttribute("movimiento", mov);
        m.addAttribute("idUsuarioGrupo", ug.getId());
        m.addAttribute("idGrupo", idGrupo);

        return "nuevoMov";
    }

    //
    @PostMapping("/grupo/guardarMovimiento")
    public String guardarMovimiento(Model m, Movimiento mov, Integer idUsuarioGrupo, Integer idGrupo) {
        mov.setUsuarioGrupo(repoUsuarioGrupo.findById(idUsuarioGrupo).get());
        Movimiento movNuevo = repoMovimientos.save(mov);
        Presupuesto p = repoPresupuesto.findByIdGrupo(idGrupo);
        /*
         * if(p.getCantidadFinal().equals(p.getCantidadInicio())){
         * p.setCantidadFinal(p.getCantidadFinal() + movNuevo.getCantidad()); }else{
         * p.setCantidadFinal(p.getCantidadFinal() + mov.getCantidad()); }
         */
        p.setCantidadFinal(p.getCantidadFinal() + movNuevo.getCantidad());
        repoPresupuesto.save(p);
        return "redirect:/gestion/grupo/" + idGrupo;
    }
    /*
     * @GetMapping("/perfil") public String perfil(Model m) { UsuarioDto usu =
     * (UsuarioDto)
     * (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
     * Usuario user = repoUsuario.findById(usu.getId()).get();
     * m.addAttribute("user", user); return "perfil"; }
     */

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/gestion/paginaInicial";
    }

    @GetMapping("/misGrupos")
    public String misGrupos(Model m) {
        UsuarioDto user = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        m.addAttribute("grupos", repoUsuarioGrupo.leerPorUsuario(user.getId()));
        return "verGrupos";
    }

    @GetMapping("/misMovimientos")
    public String misMov(Model m) {
        UsuarioDto user = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Usuario use = repoUsuario.findById(user.getId()).get();
        m.addAttribute("movimientos",
                repoMovimientos.leerPorUsuario(user.getId()).stream().collect(Collectors.toList()));
        m.addAttribute("usuarioGrupo", repoUsuarioGrupo.leerPorUsuario(user.getId()));
        Double presupuestoPersonal = 0d;
        if (use.getUsuarioGrupo().stream().map(x -> x.getGrupo().getPresupuesto()).findFirst().isPresent()) {

            presupuestoPersonal = use.getUsuarioGrupo().stream().map(x -> x.getGrupo().getPresupuesto()).findFirst()
                    .get().stream().collect(Collectors.summingDouble(p -> p.getCantidadFinal()));
        }
        m.addAttribute("presupuestoPersonal", presupuestoPersonal);
        return "verMovimientos";
    }

}
