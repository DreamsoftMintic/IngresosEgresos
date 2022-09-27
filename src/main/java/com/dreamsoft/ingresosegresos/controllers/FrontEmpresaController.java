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
public class FrontEmpresaController {

    @Autowired
    EmpresaService empresaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    MovimientoService movimientoService;

    private final Logger LOG = Logger.getLogger(""+FrontEmpresaController.class);

    @GetMapping("/empresas/verEmpresas")
    public String viewEmpresas(Model model){
        LOG.log(Level.INFO,"viewEmpresas");
        List<Empresa> empresas = empresaService.getEmpresas();
        model.addAttribute("empresas", empresas);
        return "empresas/verEmpresas";
    }

    @GetMapping("/empresas/crearEmpresa")
    public String createEmpresa(Model model){
        LOG.log(Level.INFO,"createEmpresa");
        Empresa empresa = new Empresa();
        model.addAttribute("empresa",empresa);
        return "empresas/crearEmpresa";
    }

    //controlador accion boton guardar en pagina crear empresa
    @PostMapping("/guardarEmpresa")
    public String saveEmpresa(Empresa empresa){
        LOG.log(Level.INFO,"saveEmpresa");
        System.out.println(empresa.toString());
        empresa.setFechaCr(LocalDate.now());
        empresa.setFechaUpd(LocalDate.now());
        empresa = empresaService.createEmpresa(empresa);
        return "redirect:/empresas/verEmpresas";
    }

    @GetMapping("/empresas/editarEmpresa/{id}")
    public String editEmpresa(@PathVariable("id") Long id, Model model){
        LOG.log(Level.INFO,"editEmpresa");
        Empresa empresa = empresaService.getEmpresa(id);
        model.addAttribute("empresa",empresa);
        return "empresas/editarEmpresa";
    }

    //controlador accion boton guardar en pagina editar empresa
    @PostMapping("/actualizarEmpresa/{id}")
    public String updateEmpresa(@PathVariable("id") Long id, Empresa empresa){
        LOG.log(Level.INFO,"updateEmpresa");
        System.out.println(empresa.toString());
        empresa.setFechaUpd(LocalDate.now());
        empresa = empresaService.updateEmpresa(id, empresa);
        return "redirect:/empresas/verEmpresas";
    }

    @GetMapping("/empresas/eliminarEmpresa/{id}")
    public String deleteEmpresa(@PathVariable("id") Long id, Model model){
        LOG.log(Level.INFO,"deleteEmpresa");
        empresaService.deleteEmpresa(id);
        return "redirect:/empresas/verEmpresas";
    }

    //Filtro de movimientos por empresa
    @GetMapping("/empresa/{id}/movimientos")
    public String movimientosPorEmpresa(@PathVariable("id")Long id, Model model){
        LOG.log(Level.INFO,"movimientosPorEmpresa");
        List<MovimientoDinero> movimientos = movimientoService.obtenerPorEmpresa(id);
        model.addAttribute("movimientos",movimientos);
        Long sumaMonto = movimientoService.MontosPorEmpresa(id);
        model.addAttribute("SumaMontos",sumaMonto);
        return "movimientos/verMovimientos"; //Llamamos al HTML
    }

    //Filtro de usuarios por empresa
    @GetMapping("/empresa/{id}/usuarios")
    public String usuariosPorEmpresa(@PathVariable("id")Long id, Model model){
        LOG.log(Level.INFO,"movimientosPorEmpresa");
        List<Empleado> usuarios = usuarioService.obtenerPorEmpresa(id);
        model.addAttribute("usuarios", usuarios);
        return "usuarios/verUsuarios"; //Llamamos al HTML
    }


}
