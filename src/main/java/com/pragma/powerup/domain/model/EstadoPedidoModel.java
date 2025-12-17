package com.pragma.powerup.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EstadoPedidoModel {

    private String id;

    private Long pedidoId;

    private Long restaurantId;

    private Long clienteId;

    private String correoCliente;

    private LocalDateTime fecha;

    private EstadoType estadoAnterior;

    private EstadoType estadoNuevo;

    private Long empleadoId;

    private String correoEmpleado;

}
