package com.gdg.gestiondegastos.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
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

    
    @GetMapping("/tablaGrupos")
    @ResponseBody
    public TablaBSDto obtenerMovimientos(Integer idGrupo, String search, String sort, String order, Integer offset,
            Integer limit) {

        Sort sT = Sort.by("cantidad").descending();
        if (sort != null) {
            if ("nombreUsuario".equals(sort)) {
                sT = Sort.by("usuarioGrupo.usuario.nombre");
            } else {
                sT = Sort.by(sort).ascending();
            }
            if ("desc".equals(order)) {
                sT = sT.descending();
            }
        }

        Pageable pa = PageRequest.of(offset / limit, limit, sT);

        Page<Movimiento> p = repoMovimientos.leerPorGrupo(idGrupo, search, pa);

        return new TablaBSDto(p.getTotalElements(),
                p.get().map(x -> new MovimientoGrupoDto(x.getConcepto(), x.getCategoria(),
                        x.getUsuarioGrupo().getUsuario().getNombre(), x.getFecha(), x.getCantidad()))
                        .collect(Collectors.toList()));
    }
    


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
        /*UsuarioDto usu = mapper.map(repoUsuario.findByCorreo(usuario.getCorreo()), UsuarioDto.class);
        if (usu != null) {
            return "Correo ya registrado";
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
            TokenEntity confirm=new TokenEntity(usuario);
            repoToken.save(confirm);
            SimpleMailMessage correo=new SimpleMailMessage();
            correo.setTo(usuario.getCorreo());
            correo.setSubject("Complete su registro");
            correo.setFrom("gestiondegastoshiberus@gmail.com");
            correo.setText("Confirme su cuenta haciendo click en el siguiente enlace de validación: \n http://localhost:8080/confirmar?token="+confirm.getConfirmacion());
            service.enviarCorreo(correo);
            
        }*/
        m.addAttribute("usuario", feign.agregarUsuario(usuario));
        //return "Se le ha enviado un correo de confirmación al correo "+usuario.getCorreo();
        return "redirect:/gestion/login";
    }

    @Autowired
    private AuthenticationManager am;

    @PostMapping("/ingresar") // hacer login
    public String ingresar(Model m, String correo[], String[] contrasenya) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(correo[0], contrasenya[0]);
        Authentication auth = am.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        //Usuario usuario = repoUsuario.findByCorreo(correo[0]);
        Boolean v=feign.ingresar(correo[0], contrasenya[0]);
        System.out.println("Bo:" + v);
        if (v) {
            return "redirect:inicio";
        } else {
            return "login";
        }        
    }

    // Antes del Security
    @GetMapping("/inicio")//Falla movimientos, no los coge
    public String inicio(Model m) {
        UsuarioDto usuario = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        //Usuario use=repoUsuario.findById(usuario.getId()).get();
        UsuarioDto use=feign.inicio(usuario.getId());
        m.addAttribute("usuario", use);
        m.addAttribute("grupo", use.getGrupo());
        return "principal";
    }

    @GetMapping("/perfil")
    public String perfil(Model m) {
        UsuarioDto usuValidado = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        //Usuario user=repoUsuario.findById(usuValidado.getId()).get();
        m.addAttribute("usuario", feign.perfil(usuValidado.getId()));
        return "perfil";
    }

    @PostMapping("/guardarPerfil")
    public String guardarPerfil(UsuarioDto usuario) {
        feign.guardarPerfil(usuario.getId(), usuario.getContrasenya(), usuario.getNombre(), usuario.getCorreo(), usuario.getTelefono(), Boolean.FALSE,Boolean.TRUE);
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
        GrupoDto2 g=feign.verGrupos(idGrupo);
        
        m.addAttribute("grupo", g.getGrupo());
        m.addAttribute("movimientos", g.getMovimientos());
        m.addAttribute("presupuesto", g.getPresupuesto());
        return "grupos";
    }

    @GetMapping("{idGrupo}/borrar")
    public String verGrupos(@PathVariable Integer idGrupo) {
        feign.borrarGrupos(idGrupo);
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
        /*
         * Movimiento mov = new Movimiento(); // UsuarioGrupo ug = //
         * repoGrupo.findById(idGrupo).get().getUsuarioGrupo().stream().filter(x->x.
         * getUsuario().getId().equals(usuValidado.getId())).findFirst().get(); //
         * mov.setUsuarioGrupo(repoUsuarioGrupo.findById(ug.getId()).get());
         * UsuarioGrupo ug = repoUsuarioGrupo.leerPorUsuarioYGrupo(usuValidado.getId(),
         * idGrupo); mov.setUsuarioGrupo(ug); m.addAttribute("movimiento", mov);
         * m.addAttribute("idUsuarioGrupo", ug.getId()); m.addAttribute("idGrupo",
         * idGrupo);
         */
        

          NuevoMovDto mov = feign.nuevoMovimientos(idGrupo, usuValidado.getId()) ;
        
        m.addAttribute("movimiento",mov.getMovimiento());
        m.addAttribute("idUsuarioGrupo",mov.getIdUsuarioGrupo() );
        m.addAttribute("idGrupo",mov.getIdGrupo());
        return "nuevoMov";
    }

    //
    @PostMapping("/grupo/guardarMovimiento")
    public String guardarMovimiento(Model m, MovimientoDto mov, Integer idUsuarioGrupo, Integer idGrupo) {
        /*
         * mov.setUsuarioGrupo(repoUsuarioGrupo.findById(idUsuarioGrupo).get());
         * Movimiento movNuevo = repoMovimientos.save(mov); Presupuesto p =
         * repoPresupuesto.findByIdGrupo(idGrupo); /*
         * if(p.getCantidadFinal().equals(p.getCantidadInicio())){
         * p.setCantidadFinal(p.getCantidadFinal() + movNuevo.getCantidad()); }else{
         * p.setCantidadFinal(p.getCantidadFinal() + mov.getCantidad()); }
         * 
         * p.setCantidadFinal(p.getCantidadFinal() + movNuevo.getCantidad());
         * repoPresupuesto.save(p);
         */
        feign.guardarMovimiento(mov.getId(), mov.getCategoria(), mov.getCantidad(),mov.getConcepto(),mov.getFecha(), idUsuarioGrupo, idGrupo);
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
        m.addAttribute("grupos", feign.misGrupos(user.getId()));
        return "verGrupos";
    }

    @GetMapping("/misMovimientos")
    public String misMov(Model m) {
        UsuarioDto usuario = (UsuarioDto) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        UsuarioDto use=feign.misMov(usuario.getId());
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
