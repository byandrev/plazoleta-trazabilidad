package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.EstadoPedidoRequestDto;
import com.pragma.powerup.application.dto.response.EstadoPedidoResponseDto;
import com.pragma.powerup.application.handler.IEstadoPedidoHandler;
import com.pragma.powerup.application.mapper.IEstadoPedidoRequestDtoMapper;
import com.pragma.powerup.application.mapper.IEstadoPedidoResponseDtoMapper;
import com.pragma.powerup.domain.api.IEstadoPedidoServicePort;
import com.pragma.powerup.domain.model.EstadoPedidoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstadoPedidoHandler implements IEstadoPedidoHandler {

    private final IEstadoPedidoServicePort estadoPedidoService;

    private final IEstadoPedidoRequestDtoMapper RequestMapper;

    private final IEstadoPedidoResponseDtoMapper responseMapper;

    @Override
    public EstadoPedidoResponseDto save(EstadoPedidoRequestDto estadoPedidoRequestDto) {
        EstadoPedidoModel estadoCreated = estadoPedidoService.save(RequestMapper.toModel(estadoPedidoRequestDto));
        return responseMapper.toResponse(estadoCreated);
    }

}
