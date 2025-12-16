package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.EstadoPedidoRequestDto;
import com.pragma.powerup.application.dto.request.PaginationRequestDto;
import com.pragma.powerup.application.dto.response.EstadoPedidoResponseDto;
import com.pragma.powerup.application.dto.response.PaginationResponseDto;
import com.pragma.powerup.application.dto.response.PedidoTimeResponseDto;
import com.pragma.powerup.domain.model.PaginationInfo;

import java.util.List;

public interface IEstadoPedidoHandler {

    EstadoPedidoResponseDto save(EstadoPedidoRequestDto estadoPedidoRequestDto);

    List<EstadoPedidoResponseDto> getAll(Long userId, Long pedidoId);

    PaginationResponseDto<PedidoTimeResponseDto> getTimePedidos(PaginationRequestDto pagination);

}
