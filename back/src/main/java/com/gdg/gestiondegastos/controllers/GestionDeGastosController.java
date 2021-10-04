package com.gdg.gestiondegastos.controllers;

import java.sql.SQLException;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.gdg.gestiondegastos.dto.GrupoDto;
import com.gdg.gestiondegastos.dto.GrupoDto2;
import com.gdg.gestiondegastos.dto.MovimientoCarteraDto;
import com.gdg.gestiondegastos.dto.MovimientoDto;
import com.gdg.gestiondegastos.dto.MovimientoGrupoDto;
import com.gdg.gestiondegastos.dto.PresupuestoDto;
import com.gdg.gestiondegastos.dto.TablaBSDto;
import com.gdg.gestiondegastos.dto.UsuarioDto;
import com.gdg.gestiondegastos.dto.UsuarioDto2;
import com.gdg.gestiondegastos.dto.UsuarioGrupoDto;
import com.gdg.gestiondegastos.entities.Grupo;
import com.gdg.gestiondegastos.entities.Movimiento;
import com.gdg.gestiondegastos.entities.Presupuesto;
import com.gdg.gestiondegastos.entities.TokenEntity;
import com.gdg.gestiondegastos.entities.Usuario;
import com.gdg.gestiondegastos.entities.UsuarioGrupo;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gestionback")
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
    private ModelMapper mapper;
    @Autowired
    private CorreoService service;

    @GetMapping("/agregar") // Terminado
    public Map<String, Object> agregarUsuario(Usuario usuario) {
        Map<String, Object> m = new HashMap<>();
        m.put("usuario", new Usuario());
        return m;
    }

    @PostMapping("/crear")
    public void crear(Usuario usuario) throws ClassNotFoundException, SQLException {
        repoUsuario.save(usuario);
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
        usuario.setVerificado(false);
        usuario.setModoOscuro(false);
        TokenEntity confirm = new TokenEntity(usuario);
        repoToken.save(confirm);
        SimpleMailMessage correo = new SimpleMailMessage();
        correo.setTo(usuario.getCorreo());
        correo.setSubject("Complete su registro");
        correo.setFrom("gestiondegastoshiberus@gmail.com");
        correo.setText(
                "Confirme su cuenta haciendo click en el siguiente enlace de validación: \n http://localhost:8080/confirmar?token="
                        + confirm.getConfirmacion());
        service.enviarCorreo(correo);
        //return "Se le ha enviado un correo de confirmación al correo " + usuario.getCorreo();

    }

    @RequestMapping(value = "/confirmar", method = { RequestMethod.GET, RequestMethod.POST })
    public String confirmarCuenta(@RequestParam("token") String token) {
        TokenEntity t = repoToken.findByConfirmacion(token);
        if (t != null) {
            Usuario usuario = repoUsuario.findByCorreo(t.getUsuario().getCorreo());
            usuario.setVerificado(true);
            repoUsuario.save(usuario);
            return "Usuario validado con exito";
        } else {
            return "El link ha caducado.";
        }
    }

    @GetMapping("/inicio/nuevoGrupo") // Terminado
    public Map<String, Object> nuevoGrupo(Integer idUsuario) {

        Map<String, Object> m = new HashMap<>();

        Grupo g = new Grupo();

        g.setUsuarioGrupo(repoUsuarioGrupo.leerPorUsuario(idUsuario));

        m.put("grupo", mapper.map(g, GrupoDto2.class));
        return m;
    }

    // Nota de Jorge: El problema estaba en que dentro de grupo hay un campo
    // presupuesto y se estaba intentando rellenar. He cambiado el parámetro de
    // presupuesto a pPresupuesto.
    @PostMapping("/inicio/guardarGrupo")
    public void guardarGrupo(Grupo grupo, Double pPresupuesto, Integer pIdUsuario) {
        grupo.setFechaCreacion(java.sql.Date.from(Instant.now(Clock.systemDefaultZone())));
        Grupo grupoCreado = repoGrupo.save(grupo);
        Presupuesto pre = new Presupuesto();
        pre.setCantidadInicio(pPresupuesto);
        pre.setCantidadFinal(pPresupuesto);
        pre.setFechaInicio(java.sql.Date.from(Instant.now(Clock.systemDefaultZone())));
        pre.setGrupo(grupoCreado);
        repoPresupuesto.save(pre);

        ArrayList<UsuarioGrupo> ug = new ArrayList<>();
        ug.add(new UsuarioGrupo(0, Boolean.TRUE, repoUsuario.findById(pIdUsuario).get(), grupoCreado,
                new ArrayList<>()));
        repoUsuarioGrupo.save(ug.get(0));
    }

    @PostMapping("/ingresar")
    public Boolean ingresar(String correo[], String[] contrasenya) {
        Usuario usuario = repoUsuario.findByCorreo(correo[0]);
        if (!usuario.getVerificado())
            return false;
        return (usuario.getNombre() != null);
    }

    @GetMapping("/inicio") // Terminado
    public UsuarioDto inicio(Integer idUsuario) {
        UsuarioDto user = mapper.map(repoUsuario.findById(idUsuario).get(), UsuarioDto.class);
        user.setPresupuestoPersonal(user.getUsuarioGrupo().stream().map(x -> x.getGrupo().getPresupuesto()).findFirst()
                .get().stream().collect(Collectors.summingDouble(p -> p.getCantidadFinal())));

        user.setMovimientos(repoMovimientos.leerPorUsuarioGrupo(idUsuario).stream()
                .map(x -> mapper.map(x, MovimientoDto.class)).sorted((x, y) -> -1).collect(Collectors.toList()));

        user.setGrupo(repoUsuarioGrupo.leerPorUsuario(idUsuario).stream()
                .map(x -> mapper.map(x.getGrupo(), GrupoDto.class)).findFirst().get());
        return user;
    }

    @GetMapping("/perfil") // Terminado
    public UsuarioDto2 perfil(Integer idUsuario) {
        UsuarioDto2 user = mapper.map(repoUsuario.findById(idUsuario).get(), UsuarioDto2.class);

        return user;
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
    /*
     * //comentado por el xema
     * 
     * @PostMapping("/guardarcontrasenya") public void guardarContrasenya(Usuario
     * usuario, String contrasenya, Integer idUsuario) { Usuario user =
     * repoUsuario.findById(idUsuario).get();
     * 
     * repoUsuario.save(user);
     * 
     * }
     */

    @PostMapping("/guardarcontrasenya")
    public void guardarContrasenya(String contrasenya, Integer idUsuario) {
        Usuario user = repoUsuario.findById(idUsuario).get();
        user.setContrasenya(contrasenya);
        repoUsuario.save(user);

    }

    @GetMapping("/grupo/{idGrupo}") // Terminado
    public Map<String, Object> verGrupos(@PathVariable Integer idGrupo) {

        Map<String, Object> m = new HashMap<>();
        Pageable pa = PageRequest.of(0, 10000);

        m.put("grupo", mapper.map(repoGrupo.findById(idGrupo).get(), GrupoDto.class));

        m.put("movimientos", repoMovimientos.leerPorGrupo(idGrupo, "", pa).stream()
                .map(x -> mapper.map(x, MovimientoDto.class)).collect(Collectors.toList()));

        m.put("presupuesto", mapper.map(repoPresupuesto.findById(idGrupo).get(), PresupuestoDto.class));

        return m;
    }

    @GetMapping("{idGrupo}/borrar")
    public void borrarGrupos(@PathVariable Integer idGrupo) {

        repoGrupo.deleteById(idGrupo);

    }

    @GetMapping("/grupo/{idGrupo}/gestionar") // Terminado
    public Map<String, Object> gestionarGrupos(@PathVariable Integer idGrupo) {

        Map<String, Object> m = new HashMap<>();

        m.put("grupo", mapper.map(repoGrupo.findById(idGrupo).get(), GrupoDto.class));

        m.put("usuarioGrupo", repoUsuarioGrupo.leerPorGrupo(idGrupo).stream()
                .map(x -> mapper.map(x, UsuarioGrupoDto.class)).collect(Collectors.toList()));

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

    @GetMapping("/grupo/{idGrupo}/nuevoMovimiento") // Terminado
    public Map<String, Object> nuevoMovimientos(@PathVariable Integer idGrupo, Integer idUsuario) {
        Map<String, Object> m = new HashMap<>();
        Movimiento mov = new Movimiento();
        UsuarioGrupo ug = repoUsuarioGrupo.leerPorUsuarioYGrupo(idUsuario, idGrupo);
        mov.setUsuarioGrupo(ug);

        mov.setFecha(java.sql.Date.from(Instant.now(Clock.systemDefaultZone())));
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
        List<String> listaCorreo = repoUsuarioGrupo.leerPorGrupo(idGrupo).stream().map(x -> x.getUsuario().getCorreo())
                .collect(Collectors.toList());
        for (String c : listaCorreo) {
            if (!c.equals(repoUsuarioGrupo.findById(idUsuarioGrupo).get().getUsuario().getCorreo())) {
                SimpleMailMessage correo = new SimpleMailMessage();
                correo.setTo(c);
                correo.setSubject("Nuevo movimiento en su grupo " + repoGrupo.findById(idGrupo).get().getNombre());
                correo.setFrom("gestiondegastoshiberus@gmail.com");
                correo.setText("Se ha añadido un nuevo moviemiento a su grupo "
                        + repoGrupo.findById(idGrupo).get().getNombre() + "\n " + "     Concepto: " + mov.getConcepto()
                        + "\n Importe: " + mov.getCantidad() + "\n Añadido por: "
                        + repoUsuarioGrupo.findById(idUsuarioGrupo).get().getUsuario().getNombre()
                        + "\n Acceda a su grupo para ver todos los movimientos con el siguiente link "
                        + "\n http://localhost:8080/gestion");
                service.enviarCorreo(correo);
            }
        }
    }

    @GetMapping("/misGrupos") // Terminaddo
    public Map<String, Object> misGrupos(Integer idUsuario) {
        Map<String, Object> m = new HashMap<>();
        m.put("grupos", repoUsuarioGrupo.leerPorUsuario(idUsuario).stream()
                .map(x -> mapper.map(x.getGrupo(), GrupoDto.class)).collect(Collectors.toList()));
        return m;
    }

    @GetMapping("/misMovimientos") // Terminado
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

    @GetMapping("/tablaMovimientos")
    public TablaBSDto obtenerMovimientos2(Integer idUsuario, String search, String sort, String order, Integer offset,
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

        Page<Movimiento> p = repoMovimientos.leerPorGrupo(idUsuario, search, pa);

        return new TablaBSDto(p.getTotalElements(),
                p.get().map(
                        x -> new MovimientoCarteraDto(x.getConcepto(), x.getCategoria(), x.getFecha(), x.getCantidad()))
                        .collect(Collectors.toList()));
    }

    @GetMapping("/tablaGrupos")
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

}
