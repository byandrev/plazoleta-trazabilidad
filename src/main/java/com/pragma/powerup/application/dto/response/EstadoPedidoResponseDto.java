package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.domain.model.EstadoType;

public class EstadoPedidoResponseDto {

    private String id;

    private Long pedidoId;

    private Long clienteId;

    private String correoCliente;

    private EstadoType estadoAnterior;

    private EstadoType estadoNuevo;

    private Long empleadoId;

    private String correoEmpleado;

}
