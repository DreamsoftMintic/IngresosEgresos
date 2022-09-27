package com.dreamsoft.ingresosegresos.repositories;

import com.dreamsoft.ingresosegresos.entities.MovimientoDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface MovimientoRepository extends JpaRepository<MovimientoDinero, Long> {
    //Metodo para filtrar movimientos por empleado
    @Query(value ="select * from movimiento_dinero where empleado_id= ?1", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpleado(Long id);

    //Metodo para filtrar movimientos por empresa
    @Query(value="select * from movimiento_dinero where empresa_id= ?1", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpresa(Long id);

    //Metodo para ver la suma de TODOS LOS MOVIMIENTOS
    @Query(value="SELECT SUM(monto) from movimiento_dinero", nativeQuery = true)
    public abstract Long SumarMonto();

    //Metodo para ver la suma de los montos por empleado
    @Query(value="SELECT SUM(monto) from movimiento_dinero where empleado_id=?1", nativeQuery = true)
    public abstract Long MontosPorEmpleado(Long id); //id del empleado

    //Metodo para ver la suma de los movimientos por empresa
    @Query(value="select sum(monto) from movimiento_dinero where empresa_id= ?1", nativeQuery = true)
    public abstract Long MontosPorEmpresa(Long id); //Id de la empresa
}
