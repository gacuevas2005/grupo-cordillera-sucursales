package com.cordillera.sucursales.Repository;

import com.cordillera.sucursales.Model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
}
