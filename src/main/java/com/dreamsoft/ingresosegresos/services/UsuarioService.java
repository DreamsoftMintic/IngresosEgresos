package com.dreamsoft.ingresosegresos.services;


import com.dreamsoft.ingresosegresos.entities.Empleado;
import com.dreamsoft.ingresosegresos.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService {
    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository){
        this.repository = repository;
    }


    //Servicio para el controlador Get, retorna todos los campos(filas) de la tabla
    public List<Empleado> getUsuarios(){
        return this.repository.findAll();
    }

    //Servicio para el controlador Get con id especifico, retorna solo un campo(fila) de la tabla
    public Empleado getUsuario(Long id){
        Optional<Empleado> empleado = this.repository.findById(id);
        return empleado.orElse(null);
    }
    //Servicio para el controlador Post, crea un campo(fila) en la tabla
    public Empleado createUsuario(Empleado newEmpleado){
        return this.repository.save(newEmpleado);
    }

    //servicio para el controlador Delete, elimina un campo(fila) en la tabla
    public Boolean deleteUsuario(Long id) {
        try{
            this.repository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //servicio para el controlador Put, actualiza un campo(fila) en la tabla. Actualiza todos los valores.
    public Empleado updateUsuario(Long id, Empleado empleado){
        Optional<Empleado> dbData = this.repository.findById(id);

        if(dbData.isPresent()){
            Empleado e = dbData.get();
            e.setNombreEmpleado(empleado.getNombreEmpleado());
            e.setDocumento(empleado.getDocumento());
            e.setRol(empleado.getRol());
            e.setFechaCr(empleado.getFechaCr());
            e.setFechaUpd(empleado.getFechaUpd());
            e.setCorreoEmpleado(empleado.getCorreoEmpleado());
            e.setEstado(empleado.isEstado());
            e.setMovimientos(empleado.getMovimientos());
            e.setEmpresa(empleado.getEmpresa());
            e.setTelefono(empleado.getTelefono());
            e.setUsername(empleado.getUsername());
            e.setPass(empleado.getPass());
            this.repository.save(e);
            return e;
        }
        return null;

    }

    //servicio para controlador Patch
    public Empleado partialUpdateUsuario(Long id, Map<Object, Object> fields){
        Optional<Empleado> dbData = this.repository.findById(id);

        if(dbData.isPresent()){

            fields.forEach((key,value) ->{
                Field field = ReflectionUtils.findField(Empleado.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, dbData.get(), value);
            });
            Empleado e = dbData.get();
            this.repository.save(e);
            return e;

        }
        return null;

    }

    //Metodo para buscar empleados por empresa
    public ArrayList<Empleado> obtenerPorEmpresa(Long id){
        return this.repository.findByEmpresa(id);
    }
}
