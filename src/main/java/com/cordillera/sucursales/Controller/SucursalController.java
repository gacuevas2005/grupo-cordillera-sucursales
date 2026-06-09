package com.cordillera.sucursales.Controller;

import com.cordillera.sucursales.Dto.SucursalRequestDto;
import com.cordillera.sucursales.Dto.SucursalResponseDto;
import com.cordillera.sucursales.Service.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@Tag(name = "Gestión de Sucursales", description = "Endpoints para administrar, consultar y desactivar (Soft Delete) las sucursales de Grupo Cordillera")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    // 1. Obtener sucursal por ID
    @Operation(summary = "Obtener sucursal por ID", description = "Retorna los detalles exactos de una sucursal específica. Este endpoint es consumido internamente por el microservicio de Ventas para cruzar datos.")
    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponseDto> obtenerPorId(
            @Parameter(description = "ID único de la sucursal", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }

    // 2. Listar todas las sucursales
    @Operation(summary = "Listar todas las sucursales", description = "Obtiene una lista completa de todas las sucursales registradas en el sistema, ideal para llenar selectores (combobox) en el frontend.")
    @GetMapping
    public ResponseEntity<List<SucursalResponseDto>> listarTodas() {
        return ResponseEntity.ok(sucursalService.listarTodas());
    }

    // 3. Crear una nueva sucursal
    @Operation(summary = "Registrar nueva sucursal", description = "Crea y guarda una nueva sucursal en la base de datos a partir de los datos enviados en el cuerpo de la petición.")
    @PostMapping
    public ResponseEntity<SucursalResponseDto> crear(
            @Valid @RequestBody SucursalRequestDto dto) {
        SucursalResponseDto nuevaSucursal = sucursalService.crear(dto);
        return new ResponseEntity<>(nuevaSucursal, HttpStatus.CREATED);
    }

    // 4. Desactivar sucursal (Soft Delete)
    @Operation(summary = "Desactivar sucursal (Borrado Lógico)", description = "Cambia el estado de una sucursal a inactiva en lugar de borrarla físicamente de la base de datos, manteniendo la integridad del historial de ventas.")
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(
            @Parameter(description = "ID de la sucursal que se desea dar de baja", example = "1") @PathVariable Long id) {
        sucursalService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}