package com.dreamsoft.ingresosegresos.services;

import com.dreamsoft.ingresosegresos.entities.MovimientoDinero;
import com.dreamsoft.ingresosegresos.repositories.MovimientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MovimientoService {

    private MovimientoRepository repository;

    public MovimientoService(MovimientoRepository repository) { this.repository = repository; }

    public List<MovimientoDinero> getMovimientos() { return this.repository.findAll(); }

    public MovimientoDinero getMovimiento(Long id){
        Optional<MovimientoDinero> movimientoDinero = this.repository.findById(id);
        return movimientoDinero.orElse(null);

    }

    public MovimientoDinero createMovimiento(MovimientoDinero newMovimientoDinero) { return this.repository.save(newMovimientoDinero); }

    public boolean deleteMovimiento(Long id) {
        try {
            this.repository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public MovimientoDinero updateMovimiento(Long id, MovimientoDinero movimientoDinero) {
        Optional<MovimientoDinero> dbData = this.repository.findById(id);

        if (dbData.isPresent()) {
            MovimientoDinero e = dbData.get();
            e.setMonto(movimientoDinero.getMonto());
            e.setConcepto(movimientoDinero.getConcepto());
            e.setFechaMovimiento(movimientoDinero.getFechaMovimiento());
            e.setFechaCr(movimientoDinero.getFechaCr());
            e.setFechaUpd(movimientoDinero.getFechaUpd());
            e.setEmpleado(movimientoDinero.getEmpleado());
            this.repository.save(e);
            return  e;

        }
        return null;


    }

    public MovimientoDinero partialUpdateMovimiento(Long id, Map<Object, Object> fields){
        Optional<MovimientoDinero> dbData = this.repository.findById(id);

        if(dbData.isPresent()){
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(MovimientoDinero.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, dbData.get(), value);

            });

            MovimientoDinero e = dbData.get();
            this.repository.save(e);
            return  e;
        }

        return null;
    }

    public ArrayList<MovimientoDinero> obtenerPorEmpleado(Long id) { //Obterner teniendo en cuenta el id del empleado
        return this.repository.findByEmpleado(id);
    }

    public ArrayList<MovimientoDinero> obtenerPorEmpresa(Long id) { //Obtener movimientos teniendo en cuenta el id de la empresa a la que pertencen los empleados que la registraron
        return this.repository.findByEmpresa(id);
    }

    //Servicio para ver la suma de todos los montos
    public Long obtenerSumaMontos(){
        return this.repository.SumarMonto();
    }

    //Servicio para ver la suma de los montos por empleado
    public Long MontosPorEmpleado(Long id){
        return this.repository.MontosPorEmpleado(id);
    }

    //Servicio para ver la suma de los montos por empresa
    public Long MontosPorEmpresa(Long id){
        return this.repository.MontosPorEmpresa(id);
    }




}