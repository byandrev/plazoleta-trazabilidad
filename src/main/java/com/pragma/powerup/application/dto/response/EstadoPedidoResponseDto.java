package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.domain.model.EstadoType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EstadoPedidoResponseDto {

    private String id;

    private Long pedidoId;

    private Long restaurantId;

    private Long clienteId;

    private LocalDateTime fecha;

    private String correoCliente;

    private EstadoType estadoAnterior;

    private EstadoType estadoNuevo;

}
