package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.EstadoPedidoRequestDto;
import com.pragma.powerup.application.dto.response.EstadoPedidoResponseDto;

import java.util.List;

public interface IEstadoPedidoHandler {

    EstadoPedidoResponseDto save(EstadoPedidoRequestDto estadoPedidoRequestDto);

    List<EstadoPedidoResponseDto> getAll(Long userId, Long pedidoId);

}
