package com.cordillera.sucursales.Controller;

import com.cordillera.sucursales.Dto.SucursalRequestDto;
import com.cordillera.sucursales.Dto.SucursalResponseDto;
import com.cordillera.sucursales.Service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    // 1. Obtener sucursal por ID (El que usa el microservicio de Ventas)
    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponseDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sucursalService.obtenerPorId(id));
    }

    // 2. Listar todas las sucursales
    @GetMapping
    public ResponseEntity<List<SucursalResponseDto>> listarTodas() {
        return ResponseEntity.ok(sucursalService.listarTodas());
    }

    // 3. Crear una nueva sucursal
    @PostMapping
    public ResponseEntity<SucursalResponseDto> crear(@Valid @RequestBody SucursalRequestDto dto) {
        SucursalResponseDto nuevaSucursal = sucursalService.crear(dto);
        return new ResponseEntity<>(nuevaSucursal, HttpStatus.CREATED);
    }

    // 4. Desactivar sucursal (Soft Delete)
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        sucursalService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}