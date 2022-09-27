package com.dreamsoft.ingresosegresos.repositories;

import com.dreamsoft.ingresosegresos.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface UsuarioRepository extends JpaRepository<Empleado, Long> {
    @Query(value="SELECT * FROM empleado where empresa_id= ?1", nativeQuery=true)
    public abstract ArrayList<Empleado> findByEmpresa(Long id);
    Empleado findByUsername(String username);
}
