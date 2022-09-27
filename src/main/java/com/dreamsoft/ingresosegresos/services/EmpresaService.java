package com.dreamsoft.ingresosegresos.services;

import com.dreamsoft.ingresosegresos.entities.Empresa;
import com.dreamsoft.ingresosegresos.repositories.EmpresaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmpresaService {
    private EmpresaRepository repository;

    public EmpresaService(EmpresaRepository repository){
        this.repository = repository;
    }


    //Servicio para el controlador Get, retorna todos los campos(filas) de la tabla
    public List<Empresa> getEmpresas(){
        return this.repository.findAll();
    }

    //Servicio para el controlador Get con id especifico, retorna solo un campo(fila) de la tabla
    public Empresa getEmpresa(Long id){
        Optional<Empresa> empresa = this.repository.findById(id);
        return empresa.orElse(null);
    }
    //Servicio para el controlador Post, crea un campo(fila) en la tabla
    public Empresa createEmpresa(Empresa newEmpresa){
        return this.repository.save(newEmpresa);
    }

    //servicio para el controlador Delete, elimina un campo(fila) en la tabla
    public Boolean deleteEmpresa(Long id) {
        try{
            this.repository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //servicio para el controlador Put, actualiza un campo(fila) en la tabla. Actualiza todos los valores.
    public Empresa updateEmpresa(Long id, Empresa empresa){
        Optional<Empresa> dbData = this.repository.findById(id);

        if(dbData.isPresent()){
            Empresa e = dbData.get();
            e.setNombreEmpresa(empresa.getNombreEmpresa());
            e.setDireccionEmpresa(empresa.getDireccionEmpresa());
            e.setTelefonoEmpresa(empresa.getTelefonoEmpresa());
            e.setFechaCr(empresa.getFechaCr());
            e.setFechaUpd(empresa.getFechaUpd());
            e.setEmpleados(empresa.getEmpleados());
            //e.setMovimientos(empresa.getMovimientos());
            e.setNIT(empresa.getNIT());
            this.repository.save(e);
            return e;
        }
            return null;

    }

    //servicio para controlador Patch
    public Empresa partialUpdateEmpresa(Long id, Map<Object, Object> fields){
        Optional<Empresa> dbData = this.repository.findById(id);

        if(dbData.isPresent()){
            fields.forEach((key,value) ->{
                Field field = ReflectionUtils.findField(Empresa.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, dbData.get(), value);
            });
            Empresa e = dbData.get();
            this.repository.save(e);
            return e;

        }
        return null;

    }
}
