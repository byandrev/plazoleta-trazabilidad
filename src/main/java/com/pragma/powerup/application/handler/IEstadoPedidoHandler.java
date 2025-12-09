package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.EstadoPedidoRequestDto;
import com.pragma.powerup.application.dto.response.EstadoPedidoResponseDto;

public interface IEstadoPedidoHandler {

    EstadoPedidoResponseDto save(EstadoPedidoRequestDto estadoPedidoRequestDto);

}
