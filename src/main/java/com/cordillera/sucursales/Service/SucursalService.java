package com.cordillera.sucursales.Service;

import com.cordillera.sucursales.Dto.SucursalRequestDto;
import com.cordillera.sucursales.Dto.SucursalResponseDto;
import com.cordillera.sucursales.Model.Sucursal;
import com.cordillera.sucursales.Repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public SucursalResponseDto crear(SucursalRequestDto dto) {
        Sucursal entity = new Sucursal();
        entity.setCodigo(dto.getCodigo());
        entity.setNombre(dto.getNombre());
        entity.setDireccion(dto.getDireccion());
        entity.setComuna(dto.getComuna());
        entity.setRegion(dto.getRegion());
        entity.setActiva(true);

        Sucursal guardada = sucursalRepository.save(entity);
        return entityToDto(guardada);
    }

    public List<SucursalResponseDto> listarTodas() {
        return sucursalRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public SucursalResponseDto obtenerPorId(Long id) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        return entityToDto(sucursal);
    }

    public void desactivar(Long id) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        sucursal.setActiva(false);
        sucursalRepository.save(sucursal);
    }

    private SucursalResponseDto entityToDto(Sucursal entity) {
        SucursalResponseDto dto = new SucursalResponseDto();
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setNombre(entity.getNombre());
        dto.setDireccion(entity.getDireccion());
        dto.setComuna(entity.getComuna());
        dto.setRegion(entity.getRegion());
        dto.setActiva(entity.getActiva());
        return dto;
    }
}