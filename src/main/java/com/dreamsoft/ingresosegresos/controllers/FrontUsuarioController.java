package com.dreamsoft.ingresosegresos.controllers;

import com.dreamsoft.ingresosegresos.entities.Empleado;
import com.dreamsoft.ingresosegresos.entities.Empresa;
import com.dreamsoft.ingresosegresos.entities.MovimientoDinero;
import com.dreamsoft.ingresosegresos.services.EmpresaService;
import com.dreamsoft.ingresosegresos.services.MovimientoService;
import com.dreamsoft.ingresosegresos.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



@Controller
public class FrontUsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EmpresaService empresaService;

    @Autowired
    MovimientoService movimientoService;

    private final Logger LOG = Logger.getLogger("" + FrontUsuarioController.class);

    @GetMapping("/usuarios/verUsuarios")
    public String viewUsuarios(Model model) {
        LOG.log(Level.INFO, "viewUsuarios");
        List<Empleado> usuarios = usuarioService.getUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/verUsuarios";
    }

    @GetMapping("/usuarios/crearUsuario")
    public String createUsuario(Model model) {
        LOG.log(Level.INFO, "createUsuario");
        //usuario
        Empleado usuario = new Empleado();
        model.addAttribute("usuario", usuario);
        //empresa
        List<Empresa> empresas = empresaService.getEmpresas();
        model.addAttribute("empresas", empresas);
        return "usuarios/crearUsuario";
    }

    //controlador accion boton guardar en pagina crear usuario
    @PostMapping("/guardarUsuario")
    public String saveUsuario(Empleado usuario) {
        LOG.log(Level.INFO, "saveUsuario");
        System.out.println(usuario.toString());

        //metodo natalie
        //llamar metodo encriptar password
        String passEncriptada = new BCryptPasswordEncoder().encode(usuario.getPass());
        usuario.setPass(passEncriptada);

        usuario.setEstado(true);
        usuario.setFechaCr(LocalDate.now());
        usuario.setFechaUpd(LocalDate.now());
        usuario = usuarioService.createUsuario(usuario);
        return "redirect:/usuarios/verUsuarios";
    }

    @GetMapping("/usuarios/editarUsuario/{id}")
    public String editUsuario(@PathVariable("id") Long id, Model model) {
        LOG.log(Level.INFO, "editUsuario");
        //usuario
        Empleado usuario = usuarioService.getUsuario(id);
        model.addAttribute("usuario", usuario);
        //empresa
        List<Empresa> empresas = empresaService.getEmpresas();
        model.addAttribute("empresas", empresas);
        return "usuarios/editarUsuario";
    }

    //controlador accion boton guardar en pagina editar usuario
    @PostMapping("/actualizarUsuario/{id}")
    public String updateUsuario(@PathVariable("id") Long id, Empleado usuario) {
        LOG.log(Level.INFO, "updateUsuario");
        System.out.println(usuario.toString());

        String Oldpass = usuarioService.getUsuario(id).getPass();
        if (usuario.getPass().equals(Oldpass)) {
            // llamar metodo encriptar password
            String passEncriptada = new BCryptPasswordEncoder().encode(usuario.getPass());
            usuario.setPass(passEncriptada);
        }

        usuario.setFechaUpd(LocalDate.now());
        usuario = usuarioService.updateUsuario(id, usuario);
        return "redirect:/usuarios/verUsuarios";
    }

    @GetMapping("/usuarios/desactivarUsuario/{id}")
    public String deactivateUsuario(@PathVariable("id") Long id, Model model) {
        LOG.log(Level.INFO, "deactivateUsuario");
        Empleado usuario = usuarioService.getUsuario(id);
        model.addAttribute("usuario", usuario);
        usuario.setEstado(false);
        usuario.setFechaUpd(LocalDate.now());
        usuario = usuarioService.updateUsuario(id, usuario);
        return "redirect:/usuarios/verUsuarios";
    }

    @GetMapping("/usuarios/verUsuariosInactivos")
    public String viewUsuariosInactivos(Model model) {
        LOG.log(Level.INFO, "viewUsuariosInactivos");
        List<Empleado> usuarios = usuarioService.getUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/verUsuariosInactivos";
    }

    @GetMapping("/usuarios/activarUsuario/{id}")
    public String activateUsuario(@PathVariable("id") Long id, Model model) {
        LOG.log(Level.INFO, "activateUsuario");
        Empleado usuario = usuarioService.getUsuario(id);
        model.addAttribute("usuario", usuario);
        usuario.setEstado(true);
        usuario.setFechaUpd(LocalDate.now());
        usuario = usuarioService.updateUsuario(id, usuario);
        return "redirect:/usuarios/verUsuarios";
    }

    @GetMapping("/usuario/{id}/movimientos") //Filtro de movimientos por empleado
    public String movimientosPorEmpleado(@PathVariable("id") Long id, Model model) {
        LOG.log(Level.INFO, "movimientosPorEmpleado");
        List<MovimientoDinero> movimientos = movimientoService.obtenerPorEmpleado(id);
        model.addAttribute("movimientos", movimientos);
        Long sumaMonto = movimientoService.MontosPorEmpleado(id);
        model.addAttribute("SumaMontos", sumaMonto);
        return "movimientos/verMovimientos"; //Llamamos al HTML
    }

    //Metodo para encriptar contraseñas

}
