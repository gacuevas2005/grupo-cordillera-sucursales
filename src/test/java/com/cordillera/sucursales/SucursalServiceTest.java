package com.cordillera.sucursales;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.cordillera.sucursales.Dto.SucursalResponseDto;
import com.cordillera.sucursales.Model.Sucursal;
import com.cordillera.sucursales.Repository.SucursalRepository;
import com.cordillera.sucursales.Service.SucursalService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    private Sucursal sucursalMock;

    @BeforeEach
    void setUp() {
        // Configuramos el mock con los campos reales de tu Entidad
        sucursalMock = new Sucursal();
        sucursalMock.setId(1L);
        sucursalMock.setCodigo("SUC-MEL-001");
        sucursalMock.setNombre("Sucursal Plaza");
        sucursalMock.setDireccion("Ortúzar 123");
        sucursalMock.setComuna("Melipilla");
        sucursalMock.setRegion("Metropolitana");
        sucursalMock.setActiva(true);
    }

    @Test
    void cuandoObtenerPorId_entoncesRetornaSucursal() {
        // GIVEN
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursalMock));

        // WHEN
        SucursalResponseDto resultado = sucursalService.obtenerPorId(1L);

        // THEN
        assertNotNull(resultado);
        assertEquals("Sucursal Plaza", resultado.getNombre());
        assertEquals("SUC-MEL-001", resultado.getCodigo());
        verify(sucursalRepository, times(1)).findById(1L);
    }

    @Test
    void cuandoObtenerTodas_entoncesRetornaLista() {
        // GIVEN
        when(sucursalRepository.findAll()).thenReturn(List.of(sucursalMock));

        // WHEN
        List<SucursalResponseDto> lista = sucursalService.listarTodas();

        // THEN
        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
        assertEquals("Melipilla", lista.get(0).getComuna());
        verify(sucursalRepository, times(1)).findAll();
    }

    @Test
    void cuandoSucursalNoExiste_entoncesLanzaExcepcion() {
        // GIVEN
        when(sucursalRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sucursalService.obtenerPorId(99L);
        });

        assertNotNull(exception);
        verify(sucursalRepository, times(1)).findById(99L);
    }
}