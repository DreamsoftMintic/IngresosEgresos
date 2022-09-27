package com.dreamsoft.ingresosegresos.controllers;



import com.dreamsoft.ingresosegresos.entities.Empleado;
import com.dreamsoft.ingresosegresos.entities.Empresa;
import com.dreamsoft.ingresosegresos.entities.MovimientoDinero;
import com.dreamsoft.ingresosegresos.services.EmpresaService;
import com.dreamsoft.ingresosegresos.services.MovimientoService;
import com.dreamsoft.ingresosegresos.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FrontMovimientosController {

    @Autowired
    MovimientoService movimientoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EmpresaService empresaService;



    private final Logger LOG = Logger.getLogger(""+FrontMovimientosController.class);

    @GetMapping("/movimientos/verMovimientos")
    public String viewMovimientos(Model model){
        LOG.log(Level.INFO,"viewMovimientos");
        List<MovimientoDinero> movimientos = movimientoService.getMovimientos();
        model.addAttribute("movimientos", movimientos);
        Long sumaMonto=movimientoService.obtenerSumaMontos();
        model.addAttribute("SumaMontos",sumaMonto);//Mandamos la suma de todos los montos a la plantilla
        return "movimientos/verMovimientos";
    }


    @GetMapping("/movimientos/crearMovimiento")
    public String createMovimiento(Model model){
        LOG.log(Level.INFO,"createMovimiento");
        //movimiento
        MovimientoDinero movimiento = new MovimientoDinero();
        model.addAttribute("movimiento",movimiento);
        //usuario
        List<Empleado> usuarios = usuarioService.getUsuarios();
        model.addAttribute("usuarios",usuarios);
        //empresa
        List<Empresa> empresas = empresaService.getEmpresas();
        model.addAttribute("empresas",empresas);
        return "movimientos/crearMovimiento";
    }
    //controlador accion boton guardar en pagina crear movimiento
    @PostMapping("/guardarMovimiento")
    public String saveMovimiento(MovimientoDinero movimiento){
        LOG.log(Level.INFO,"saveMovimiento");
        System.out.println(movimiento.toString());
        Empleado empleado = usuarioService.getUsuario(movimiento.getEmpleado().getId());
        movimiento.setEmpresaId(empleado.getEmpresa().getId());
        movimiento.setFechaCr(LocalDate.now());
        movimiento.setFechaUpd(LocalDate.now());
        System.out.println(movimiento.toString());
        movimiento = movimientoService.createMovimiento(movimiento);
        return "redirect:/movimientos/verMovimientos";
    }

    @GetMapping("/movimientos/editarMovimiento/{id}")
    public String editMovimiento(@PathVariable("id") Long id, Model model){
        LOG.log(Level.INFO,"editMovimiento");
        //movimiento
        MovimientoDinero movimiento = movimientoService.getMovimiento(id);
        model.addAttribute("movimiento",movimiento);
        //usuario
        List<Empleado> usuarios = usuarioService.getUsuarios();
        model.addAttribute("usuarios",usuarios);
        //empresa
        List<Empresa> empresas = empresaService.getEmpresas();
        model.addAttribute("empresas",empresas);
        return "movimientos/editarMovimiento";
    }
    //controlador accion boton guardar en pagina editar movimiento
    @PostMapping("/actualizarMovimiento/{id}")
    public String updateMovimiento(@PathVariable("id") Long id, MovimientoDinero movimiento){
        LOG.log(Level.INFO,"updateMovimiento");
        System.out.println(movimiento.toString());
        movimiento.setFechaUpd(LocalDate.now());
        movimiento = movimientoService.updateMovimiento(id, movimiento);
        return "redirect:/movimientos/verMovimientos";
    }

    @GetMapping("/movimientos/eliminarMovimiento/{id}")
    public String deleteMovimiento(@PathVariable("id") Long id, Model model){
        LOG.log(Level.INFO,"deleteMovimiento");
        movimientoService.deleteMovimiento(id);
        return "redirect:/movimientos/verMovimientos";
    }
}
