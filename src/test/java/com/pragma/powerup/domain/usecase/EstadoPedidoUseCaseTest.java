package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.model.EstadoPedidoModel;
import com.pragma.powerup.domain.model.EstadoType;
import com.pragma.powerup.domain.spi.IEstadoPedidoPersistencePort;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadoPedidoUseCaseTest {

    private final static Long USER_ID = 1L;
    private final static Long PEDIDO_ID = 10L;

    @Mock
    private IEstadoPedidoPersistencePort estadoPedidoPersistencePort;

    @InjectMocks
    private EstadoPedidoUseCase estadoPedidoUseCase;

    private EstadoPedidoModel estadoPedidoRequest;
    private EstadoPedidoModel estadoPedidoSaved;

    @BeforeEach
    void setUp() {
        estadoPedidoRequest = EstadoPedidoModel.builder()
                .pedidoId(1L)
                .clienteId(10L)
                .correoCliente("test@gmail.com")
                .estadoAnterior(EstadoType.PENDIENTE)
                .estadoNuevo(EstadoType.EN_PREPARACION)
                .empleadoId(5L)
                .correoEmpleado("empleado@gmail.com")
                .build();

        estadoPedidoSaved = EstadoPedidoModel.builder()
                .id("1")
                .pedidoId(1L)
                .clienteId(10L)
                .correoCliente("test@gmail.com")
                .fecha(LocalDateTime.now())
                .estadoAnterior(EstadoType.PENDIENTE)
                .estadoNuevo(EstadoType.EN_PREPARACION)
                .empleadoId(5L)
                .correoEmpleado("empleado@gmail.com")
                .build();
    }

    @Test
    @DisplayName("save() debe crear un estado de pedido exitosamente y establecer la fecha")
    void save_shouldCreateEstadoPedidoSuccessfully_andSetFecha() {
        when(estadoPedidoPersistencePort.save(any(EstadoPedidoModel.class))).thenReturn(estadoPedidoSaved);

        EstadoPedidoModel result = estadoPedidoUseCase.save(estadoPedidoRequest);

        verify(estadoPedidoPersistencePort).save(any(EstadoPedidoModel.class));
        
        assertNotNull(result);
        assertNotNull(result.getFecha());
        assertEquals("1", result.getId());
        assertEquals(1L, result.getPedidoId());
        assertEquals(10L, result.getClienteId());
        assertEquals(EstadoType.PENDIENTE, result.getEstadoAnterior());
        assertEquals(EstadoType.EN_PREPARACION, result.getEstadoNuevo());
    }

    @Test
    @DisplayName("save() debe establecer la fecha antes de guardar el estado de pedido")
    void save_shouldSetFechaBeforeSaving() {
        when(estadoPedidoPersistencePort.save(any(EstadoPedidoModel.class))).thenAnswer(invocation -> {
            EstadoPedidoModel model = invocation.getArgument(0);
            assertNotNull(model.getFecha(), "La fecha debe estar establecida antes de guardar");
            return estadoPedidoSaved;
        });

        estadoPedidoUseCase.save(estadoPedidoRequest);

        verify(estadoPedidoPersistencePort).save(any(EstadoPedidoModel.class));
    }

    @Test
    @DisplayName("save() debe crear un estado de pedido con todos los campos correctamente")
    void save_shouldCreateEstadoPedidoWithAllFields() {
        EstadoPedidoModel requestWithAllFields = EstadoPedidoModel.builder()
                .pedidoId(2L)
                .clienteId(20L)
                .correoCliente("test2@gmail.com")
                .estadoAnterior(EstadoType.EN_PREPARACION)
                .estadoNuevo(EstadoType.LISTO)
                .empleadoId(6L)
                .correoEmpleado("empleado2@gmail.com")
                .build();

        EstadoPedidoModel savedWithAllFields = EstadoPedidoModel.builder()
                .id("456")
                .pedidoId(2L)
                .clienteId(20L)
                .correoCliente("test2@gmail.com")
                .fecha(LocalDateTime.now())
                .estadoAnterior(EstadoType.EN_PREPARACION)
                .estadoNuevo(EstadoType.LISTO)
                .empleadoId(6L)
                .correoEmpleado("empleado2@gmail.com")
                .build();

        when(estadoPedidoPersistencePort.save(any(EstadoPedidoModel.class))).thenReturn(savedWithAllFields);

        EstadoPedidoModel result = estadoPedidoUseCase.save(requestWithAllFields);

        verify(estadoPedidoPersistencePort).save(any(EstadoPedidoModel.class));
        
        assertNotNull(result);
        assertEquals("456", result.getId());
        assertEquals(2L, result.getPedidoId());
        assertEquals(20L, result.getClienteId());
        assertEquals("test2@gmail.com", result.getCorreoCliente());
        assertEquals(EstadoType.EN_PREPARACION, result.getEstadoAnterior());
        assertEquals(EstadoType.LISTO, result.getEstadoNuevo());
        assertEquals(6L, result.getEmpleadoId());
        assertEquals("empleado2@gmail.com", result.getCorreoEmpleado());
        assertNotNull(result.getFecha());
    }

    @Test
    @DisplayName("getAll() debe retornar una lista vacía si no hay resultados")
    void getAllEstadoPedido_shouldReturnEmptyList() {
        List<EstadoPedidoModel> emptyMockPage = List.of();
        when(estadoPedidoPersistencePort.getAll(USER_ID, PEDIDO_ID)).thenReturn(emptyMockPage);

        List<EstadoPedidoModel> result = estadoPedidoUseCase.getAll(USER_ID, PEDIDO_ID);

        assertNotNull(result, "La lista no debe ser null");
        assertEquals(0, result.size(), "El contenido debe estar vacío");

        verify(estadoPedidoPersistencePort).getAll(USER_ID, PEDIDO_ID);
    }

    @Test
    @DisplayName("getAll() debe retornar una lista con los resultados")
    void getAllEstadoPedido() {
        List<EstadoPedidoModel> emptyMockPage = List.of(EstadoPedidoModel.builder().build());
        when(estadoPedidoPersistencePort.getAll(USER_ID, PEDIDO_ID)).thenReturn(emptyMockPage);

        List<EstadoPedidoModel> result = estadoPedidoUseCase.getAll(USER_ID, PEDIDO_ID);

        assertNotNull(result, "La lista no debe ser null");
        assertEquals(1, result.size(), "El contenido debe estar vacío");

        verify(estadoPedidoPersistencePort).getAll(USER_ID, PEDIDO_ID);
    }

}

