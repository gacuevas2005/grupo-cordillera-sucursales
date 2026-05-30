package com.cordillera.sucursales;

import com.cordillera.sucursales.Controller.SucursalController;
import com.cordillera.sucursales.Dto.SucursalRequestDto;
import com.cordillera.sucursales.Dto.SucursalResponseDto;
import com.cordillera.sucursales.Service.SucursalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SucursalController.class)
@AutoConfigureMockMvc(addFilters = false) // Apagamos la seguridad
public class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SucursalService sucursalService;

    @Autowired
    private ObjectMapper objectMapper;

    private SucursalResponseDto responseDto;
    private SucursalRequestDto requestDto;

    @BeforeEach
    void setUp() {
        // 1. Configuramos lo que devuelve el backend
        responseDto = new SucursalResponseDto();
        responseDto.setId(1L);
        responseDto.setCodigo("SUC-MEL-001");
        responseDto.setNombre("Sucursal Plaza");
        responseDto.setComuna("Melipilla");
        responseDto.setRegion("Metropolitana");
        responseDto.setActiva(true);

        // 2. Configuramos lo que enviaría el frontend en un POST
        requestDto = new SucursalRequestDto();
        requestDto.setCodigo("SUC-MEL-001");
        requestDto.setNombre("Sucursal Plaza");
        requestDto.setComuna("Melipilla");
        requestDto.setRegion("Metropolitana");
    }

    @Test
    void cuandoObtenerPorId_entoncesRetorna200YObjeto() throws Exception {
        // GIVEN: Usamos el nombre exacto de tu servicio (obtenerPorId)
        when(sucursalService.obtenerPorId(1L)).thenReturn(responseDto);

        // WHEN & THEN
        mockMvc.perform(get("/api/sucursales/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.comuna").value("Melipilla"));
    }

    @Test
    void cuandoListarTodas_entoncesRetorna200YLista() throws Exception {
        // GIVEN: Usamos el nombre exacto (listarTodas)
        when(sucursalService.listarTodas()).thenReturn(List.of(responseDto));

        // WHEN & THEN
        mockMvc.perform(get("/api/sucursales")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigo").value("SUC-MEL-001"))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void cuandoCrear_entoncesRetorna201() throws Exception {
        // GIVEN: Simulamos la creación
        when(sucursalService.crear(any(SucursalRequestDto.class))).thenReturn(responseDto);

        // WHEN & THEN: Simulamos un POST y esperamos un 201 Created
        mockMvc.perform(post("/api/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))) // Convertimos a JSON
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Sucursal Plaza"));
    }

    @Test
    void cuandoDesactivar_entoncesRetorna204() throws Exception {
        // GIVEN: Como el método del servicio es void, usamos doNothing
        doNothing().when(sucursalService).desactivar(1L);

        // WHEN & THEN: Simulamos un PATCH y esperamos un 204 No Content
        mockMvc.perform(patch("/api/sucursales/1/desactivar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}