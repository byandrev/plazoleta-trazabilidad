package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.EstadoPedidoRequestDto;
import com.pragma.powerup.application.dto.request.PaginationRequestDto;
import com.pragma.powerup.application.dto.response.EmpleadoTiempoResponseDto;
import com.pragma.powerup.application.dto.response.EstadoPedidoResponseDto;
import com.pragma.powerup.application.dto.response.PaginationResponseDto;
import com.pragma.powerup.application.dto.response.PedidoTimeResponseDto;
import com.pragma.powerup.application.mapper.*;
import com.pragma.powerup.domain.api.IEstadoPedidoServicePort;
import com.pragma.powerup.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadoPedidoHandlerTest {

    private static final Long PEDIDO_ID = 10L;
    private static final Long CLIENTE_ID = 10L;
    private static final Long EMPLEADO_ID = 5L;
    private static final Long RESTAURANTE_ID = 5L;

    @Mock
    private IEstadoPedidoServicePort estadoPedidoService;
    @Mock
    private IEstadoPedidoRequestDtoMapper requestMapper;
    @Mock
    private IEstadoPedidoResponseDtoMapper responseMapper;
    @Mock
    private IPaginationRequestMapper paginationRequestMapper;
    @Mock
    private IPaginationResponseMapper paginationResponseMapper;
    @Mock
    private IPedidoTimeResponseDtoMapper timeResponseMapper;
    @Mock
    private IEmpleadoTiempoResponseMapper empleadoTiempoResponseMapper;

    @InjectMocks
    private EstadoPedidoHandler estadoPedidoHandler;

    private EstadoPedidoRequestDto estadoPedidoRequestDto;
    private EstadoPedidoModel estadoPedidoModel;
    private EstadoPedidoResponseDto estadoPedidoResponseDto;

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
    @DisplayName("save() debe mapear DTO a Model y llamar al servicio para guardar")
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
    @DisplayName("save() debe propagar excepciones del servicio")
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

    @Test
    @DisplayName("getAll() debe mapear DTO a Model y llamar al servicio para listar")
    void getAll_SuccessfulFlow_CallsMapperAndService() {
        EstadoPedidoModel pedidoModel = EstadoPedidoModel.builder().build();
        List<EstadoPedidoModel> mockList = List.of(EstadoPedidoModel.builder().build());
        when(estadoPedidoService.getAll(CLIENTE_ID, PEDIDO_ID)).thenReturn(mockList);
        when(responseMapper.toResponse(pedidoModel)).thenReturn(estadoPedidoResponseDto);

        List<EstadoPedidoResponseDto> result = estadoPedidoHandler.getAll(CLIENTE_ID, PEDIDO_ID);

        verify(estadoPedidoService).getAll(CLIENTE_ID, PEDIDO_ID);
        verify(responseMapper).toResponse(pedidoModel);
        verifyNoMoreInteractions(estadoPedidoService, requestMapper, responseMapper);

        assertNotNull(result);
    }

    @Test
    @DisplayName("getTimePedidos() debe mapear paginación y llamar al servicio")
    void getTimePedidos_SuccessfulFlow_CallsMapperAndService() {
        PaginationRequestDto paginationRequestDto = PaginationRequestDto.builder().page(0).size(10).build();
        PaginationInfo paginationModel = PaginationInfo.builder().page(0).size(10).build();
        PedidoTimeModel pedidoTimeModel = PedidoTimeModel.builder().pedido(PEDIDO_ID).tiempo(30F).build();
        PedidoTimeResponseDto pedidoTimeResponseDto = PedidoTimeResponseDto.builder().pedido(PEDIDO_ID).tiempo(30F).build();
        PaginationResult<PedidoTimeModel> paginationResult = new PaginationResult<>(List.of(pedidoTimeModel), 1, 0, 10);

        when(paginationRequestMapper.toModel(paginationRequestDto)).thenReturn(paginationModel);
        when(estadoPedidoService.getTimePedidos(RESTAURANTE_ID, paginationModel)).thenReturn(paginationResult);
        when(timeResponseMapper.toResponse(pedidoTimeModel)).thenReturn(pedidoTimeResponseDto);
        when(paginationResponseMapper.toResponse(any(PaginationResult.class))).thenReturn(
                new PaginationResponseDto<>(List.of(pedidoTimeResponseDto), 1, 0, 10)
        );

        PaginationResponseDto<PedidoTimeResponseDto> result = estadoPedidoHandler.getTimePedidos(RESTAURANTE_ID, paginationRequestDto);

        verify(paginationRequestMapper).toModel(paginationRequestDto);
        verify(estadoPedidoService).getTimePedidos(RESTAURANTE_ID, paginationModel);
        verify(timeResponseMapper).toResponse(pedidoTimeModel);
        verify(paginationResponseMapper).toResponse(any(PaginationResult.class));

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
    }

    @Test
    @DisplayName("getRankingEmpleados() debe mapear paginación y llamar al servicio")
    void getRankingEmpleados_SuccessfulFlow_CallsMapperAndService() {
        PaginationRequestDto paginationRequestDto = PaginationRequestDto.builder().page(0).size(10).build();
        PaginationInfo paginationModel = PaginationInfo.builder().page(0).size(10).build();
        EmpleadoTiempoModel empleadoTiempoModel = EmpleadoTiempoModel.builder().empleado(EMPLEADO_ID).tiempoMedioSegundos(30D).build();
        EmpleadoTiempoResponseDto empleadoTiempoResponseDto = EmpleadoTiempoResponseDto.builder().empleado(EMPLEADO_ID).tiempoMedioSegundos(30D).build();
        PaginationResult<EmpleadoTiempoModel> paginationResult = new PaginationResult<>(List.of(empleadoTiempoModel), 1, 0, 10);

        when(paginationRequestMapper.toModel(paginationRequestDto)).thenReturn(paginationModel);
        when(estadoPedidoService.getRankingEmpleados(RESTAURANTE_ID, paginationModel)).thenReturn(paginationResult);
        when(empleadoTiempoResponseMapper.toResponse(empleadoTiempoModel)).thenReturn(empleadoTiempoResponseDto);
        when(paginationResponseMapper.toResponse(any(PaginationResult.class))).thenReturn(
                new PaginationResponseDto<>(List.of(empleadoTiempoResponseDto), 1, 0, 10)
        );

        PaginationResponseDto<EmpleadoTiempoResponseDto> result = estadoPedidoHandler.getRankingEmpleados(RESTAURANTE_ID, paginationRequestDto);

        verify(paginationRequestMapper).toModel(paginationRequestDto);
        verify(estadoPedidoService).getRankingEmpleados(RESTAURANTE_ID, paginationModel);
        verify(empleadoTiempoResponseMapper).toResponse(empleadoTiempoModel);
        verify(paginationResponseMapper).toResponse(any(PaginationResult.class));

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
    }

}

