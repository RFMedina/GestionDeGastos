package com.gdg.gestiondegastos.controllers;

import com.gdg.gestiondegastos.dto.GestionarResponseDto;
import com.gdg.gestiondegastos.dto.GrupoDto;
import com.gdg.gestiondegastos.dto.GrupoDto2;
import com.gdg.gestiondegastos.dto.MovimientoDto;
import com.gdg.gestiondegastos.dto.NuevoGrupoDto;
import com.gdg.gestiondegastos.dto.NuevoMovDto;
import java.sql.SQLException;
import java.util.stream.Collectors;

import com.gdg.gestiondegastos.dto.MovimientoGrupoDto;
import com.gdg.gestiondegastos.dto.TablaBSDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdg.gestiondegastos.dto.UsuarioDto;
import com.gdg.gestiondegastos.dto.UsuarioDto3;
import com.gdg.gestiondegastos.entities.Movimiento;
import com.gdg.gestiondegastos.entities.Usuario;
import com.gdg.gestiondegastos.entities.UsuarioGrupo;
import com.gdg.gestiondegastos.feign.BackFeign;
import com.gdg.gestiondegastos.repositories.GrupoRepository;
import com.gdg.gestiondegastos.repositories.MovimientosRepository;
import com.gdg.gestiondegastos.repositories.PresupuestoRepository;
import com.gdg.gestiondegastos.repositories.TokenRepository;
import com.gdg.gestiondegastos.repositories.UsuarioGrupoRepository;
import com.gdg.gestiondegastos.repositories.UsuarioRepository;
import com.gdg.gestiondegastos.services.CorreoService;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private TokenRepository repoToken;
    @Autowired
    private BackFeign feign;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PasswordEncoder clave;
    @Autowired
    private CorreoService service;

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
    public String crear(Model m, UsuarioDto usuario, RedirectAttributes redirectAttrs) throws ClassNotFoundException, SQLException {
        Boolean b=feign.crear( usuario.getNombre(), usuario.getCorreo(),clave.encode(usuario.getContrasenya()),
                usuario.getTelefono(), Boolean.FALSE, false);
        if(b){
            redirectAttrs.addFlashAttribute("msg", "Usuario registrado con exito. Revise su bandeja de entrada y verifique su cuenta");
            return "redirect:/gestion/login";
        }else{
            redirectAttrs.addFlashAttribute("msg", "Usuario ya registrado, pruebe con otro");
            return "redirect:/gestion/agregar";
        }
    }
    
    @GetMapping("/confirmar")
    public String confirmarCuenta(Model m, String token, RedirectAttributes redirectAttrs){
        Boolean t=feign.confirmarCuenta(token);
        if(t){
            redirectAttrs.addFlashAttribute("msg", "Usuario verificado con exito");
            return "redirect:/gestion/login";
        }else{
            redirectAttrs.addFlashAttribute("msg", "El usuario no se ha verificado");
            return "redirect:/gestion/error";
        }
        
    }

    @Autowired
    private AuthenticationManager am;

    @PostMapping("/ingresar") // hacer login
    public String ingresar(Model m, String correo[], String[] contrasenya) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(correo[0], contrasenya[0]);
        Authentication auth = am.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        Boolean v = feign.ingresar(correo[0], contrasenya[0]);
        if (v) {
            return "redirect:/gestion/inicio";
        } else {
            return "redirect:/gestion/login";
        }
    }

    // Antes del Security
    @GetMapping("/inicio")
    public String inicio(Model m) {
        UsuarioDto usuario = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        UsuarioDto use = feign.inicio(usuario.getId());
        m.addAttribute("usuario", use);
        m.addAttribute("grupo", use.getGrupo());
        return "principal";
    }

    @GetMapping("/perfil")
    public String perfil(Model m) {
        UsuarioDto usuValidado = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        // Usuario user=repoUsuario.findById(usuValidado.getId()).get();
        m.addAttribute("usuario", feign.perfil(usuValidado.getId()));
        return "perfil";
    }

    @PostMapping("/guardarPerfil")
    public String guardarPerfil(UsuarioDto usuario) {
        feign.guardarPerfil(usuario.getId(), usuario.getContrasenya(), usuario.getNombre(), usuario.getCorreo(),
                usuario.getTelefono(), Boolean.FALSE, Boolean.TRUE);
        return "redirect:/gestion/perfil";
    }

    @GetMapping("/contrasenya")
    public String contrasenya(Model m) {
        UsuarioDto usuValidado = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        m.addAttribute("usuario", feign.contrasenya(usuValidado.getId()));
        return "cambiarContraseya";
    }

    @PostMapping("/guardarcontrasenya")
    public String guardarContrasenya(String contrasenya) {
        UsuarioDto usuValidado = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        feign.guardarContrasenya(clave.encode(contrasenya), usuValidado.getId());

        return "redirect:/gestion/perfil";
    }

    @GetMapping("/grupo/{idGrupo}")
    public String verGrupos(Model m, @PathVariable Integer idGrupo) {
        GrupoDto2 g = feign.verGrupos(idGrupo);

        m.addAttribute("grupo", g.getGrupo());
        m.addAttribute("movimientos", g.getMovimientos());
        m.addAttribute("presupuesto", g.getPresupuesto());
        return "grupos";
    }

    @GetMapping("{idGrupo}/borrar")
    public String borrarGrupos(@PathVariable Integer idGrupo) {
        feign.borrarGrupos(idGrupo);
        return "redirect:/gestion/misGrupos";
    }

    @GetMapping("/grupo/{idGrupo}/gestionar")
    public String gestionarGrupos(Model m, @PathVariable Integer idGrupo) {
        GestionarResponseDto res = feign.gestionarGrupos(idGrupo);
        m.addAttribute("grupo", res.getGrupo());
        m.addAttribute("usuarioGrupo", res.getUsuarioGrupo());
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
        Boolean b = feign.borrarUsuario(idUsuarioGrupo, idGrupo);
        if (b)
            return "redirect:/gestion/inicio";
        else
            return "redirect:/gestion/grupo/{idGrupo}";
    }

    @GetMapping("/grupo/nuevoUsuarioGrupo")
    public String anadirUsuario(Model m, String correo, @PathVariable Integer idGrupo) {
        feign.anadirUsuario(correo, idGrupo);
        return "redirect:/gestion/grupo/" + idGrupo;
    }

    @GetMapping("grupo/cambiarNombre")
    public String cambiarNombreGrupo(String nombre, @PathVariable Integer idGrupo) {
        feign.cambiarNombreGrupo(nombre, idGrupo);
        return "redirect:/gestion/grupo/" + idGrupo;
    }

    @GetMapping("/inicio/nuevoGrupo")
    public String nuevoGrupo(Model m) {
        UsuarioDto usuValidado = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        NuevoGrupoDto res = feign.nuevoGrupo(usuValidado.getId());
        m.addAttribute("grupo", res.getGrupo());
        return "nuevoGrupo";
    }

    @PostMapping("/inicio/guardarGrupo")
    public String guardarGrupo(GrupoDto grupo, Double presupuesto) {
        UsuarioDto usuValidado = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        feign.guardarGrupo(grupo.getId(), grupo.getNombre(), presupuesto, usuValidado.getId());
        return "redirect:/gestion/inicio";
    }

    @GetMapping("/grupo/{idGrupo}/nuevoMovimiento")
    public String nuevoMovimientos(Model m, @PathVariable Integer idGrupo) {
        UsuarioDto usuValidado = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        NuevoMovDto mov = feign.nuevoMovimientos(idGrupo, usuValidado.getId());
        m.addAttribute("movimiento", mov.getMovimiento());
        m.addAttribute("idUsuarioGrupo", mov.getIdUsuarioGrupo());
        m.addAttribute("idGrupo", mov.getIdGrupo());
        return "nuevoMov";
    }

    //
    @PostMapping("/grupo/guardarMovimiento")
    public String guardarMovimiento(Model m, MovimientoDto mov, Integer idUsuarioGrupo, Integer idGrupo) {
        feign.guardarMovimiento(mov.getId(), mov.getCategoria(), mov.getCantidad(), mov.getConcepto(), mov.getFecha(),
                idUsuarioGrupo, idGrupo);
        return "redirect:/gestion/grupo/" + idGrupo;
    }

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
        m.addAttribute("grupos", feign.misGrupos(user.getId()));
        return "verGrupos";
    }

    @GetMapping("/tablaMovimientos")
    @ResponseBody
    public TablaBSDto obtenerMovimientos2(String search, String sort, String order, Integer offset, Integer limit) {
        UsuarioDto usuario = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return feign.obtenerMovimientos2(usuario.getId(), search, sort, order, offset, limit);
    }

    @GetMapping("/tablaGrupos")
    @ResponseBody
    public TablaBSDto obtenerMovimientos(Integer idGrupo, String search, String sort, String order, Integer offset,
            Integer limit) {
        return feign.obtenerMovimientos(idGrupo, search, sort, order, offset, limit);
    }

    @GetMapping("/misMovimientos")
    public String misMov(Model m) {
        UsuarioDto usuario = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        UsuarioDto use = feign.misMov(usuario.getId());
        m.addAttribute("movimientos", use.getMovimientos());
        m.addAttribute("usuarioGrupo", use.getUsuarioGrupo());
        m.addAttribute("presupuestoPersonal", use.getPresupuestoPersonal());

        return "verMovimientos";
    }

    @GetMapping("/contactos")
    public String contactos(Model m) {
        return "contactos";
    }

}
