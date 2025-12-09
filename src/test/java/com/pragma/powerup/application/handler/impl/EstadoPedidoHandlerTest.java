package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.EstadoPedidoRequestDto;
import com.pragma.powerup.application.dto.response.EstadoPedidoResponseDto;
import com.pragma.powerup.application.mapper.IEstadoPedidoRequestDtoMapper;
import com.pragma.powerup.application.mapper.IEstadoPedidoResponseDtoMapper;
import com.pragma.powerup.domain.api.IEstadoPedidoServicePort;
import com.pragma.powerup.domain.model.EstadoPedidoModel;
import com.pragma.powerup.domain.model.EstadoType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadoPedidoHandlerTest {

    @Mock
    private IEstadoPedidoServicePort estadoPedidoService;

    @Mock
    private IEstadoPedidoRequestDtoMapper requestMapper;

    @Mock
    private IEstadoPedidoResponseDtoMapper responseMapper;

    @InjectMocks
    private EstadoPedidoHandler estadoPedidoHandler;

    private EstadoPedidoRequestDto estadoPedidoRequestDto;
    private EstadoPedidoModel estadoPedidoModel;
    private EstadoPedidoResponseDto estadoPedidoResponseDto;

    private final Long PEDIDO_ID = 1L;
    private final Long CLIENTE_ID = 10L;
    private final Long EMPLEADO_ID = 5L;

    @BeforeEach
    void setUp() {
        estadoPedidoRequestDto = EstadoPedidoRequestDto.builder()
                .pedidoId(PEDIDO_ID)
                .clienteId(CLIENTE_ID)
                .correoCliente("test@gmail.com")
                .estadoAnterior(EstadoType.PENDIENTE)
                .estadoNuevo(EstadoType.EN_PREPARACION)
                .empleadoId(EMPLEADO_ID)
                .correoEmpleado("empleado@gmail.com")
                .build();

        estadoPedidoModel = EstadoPedidoModel.builder()
                .id("1")
                .pedidoId(PEDIDO_ID)
                .clienteId(CLIENTE_ID)
                .correoCliente("test@gmail.com")
                .fecha(LocalDateTime.now())
                .estadoAnterior(EstadoType.PENDIENTE)
                .estadoNuevo(EstadoType.EN_PREPARACION)
                .empleadoId(EMPLEADO_ID)
                .correoEmpleado("empleado@gmail.com")
                .build();

        estadoPedidoResponseDto = mock(EstadoPedidoResponseDto.class);
    }

    @Test
    @DisplayName("save debe mapear DTO a Model y llamar al servicio para guardar")
    void save_SuccessfulFlow_CallsMapperAndService() {
        when(requestMapper.toModel(estadoPedidoRequestDto)).thenReturn(estadoPedidoModel);
        when(estadoPedidoService.save(estadoPedidoModel)).thenReturn(estadoPedidoModel);
        when(responseMapper.toResponse(estadoPedidoModel)).thenReturn(estadoPedidoResponseDto);

        EstadoPedidoResponseDto result = estadoPedidoHandler.save(estadoPedidoRequestDto);

        verify(requestMapper).toModel(estadoPedidoRequestDto);
        verify(estadoPedidoService).save(estadoPedidoModel);
        verify(responseMapper).toResponse(estadoPedidoModel);
        verifyNoMoreInteractions(estadoPedidoService, requestMapper, responseMapper);

        assertNotNull(result);
        assertEquals(estadoPedidoResponseDto, result);
    }

    @Test
    @DisplayName("save debe propagar excepciones del servicio")
    void save_ServiceThrowsException_PropagatesException() {
        when(requestMapper.toModel(estadoPedidoRequestDto)).thenReturn(estadoPedidoModel);
        when(estadoPedidoService.save(estadoPedidoModel)).thenThrow(new RuntimeException("Error al guardar"));

        assertThrows(RuntimeException.class, () ->
                estadoPedidoHandler.save(estadoPedidoRequestDto)
        );

        verify(requestMapper).toModel(estadoPedidoRequestDto);
        verify(estadoPedidoService).save(estadoPedidoModel);
        verify(responseMapper, never()).toResponse(any());
    }

}

