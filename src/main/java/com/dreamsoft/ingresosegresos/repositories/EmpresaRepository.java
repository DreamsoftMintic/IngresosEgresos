package com.dreamsoft.ingresosegresos.repositories;

import com.dreamsoft.ingresosegresos.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
