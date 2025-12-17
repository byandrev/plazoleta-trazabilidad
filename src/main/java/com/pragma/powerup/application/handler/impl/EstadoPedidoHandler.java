package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.EstadoPedidoRequestDto;
import com.pragma.powerup.application.dto.request.PaginationRequestDto;
import com.pragma.powerup.application.dto.response.EmpleadoTiempoResponseDto;
import com.pragma.powerup.application.dto.response.EstadoPedidoResponseDto;
import com.pragma.powerup.application.dto.response.PaginationResponseDto;
import com.pragma.powerup.application.dto.response.PedidoTimeResponseDto;
import com.pragma.powerup.application.handler.IEstadoPedidoHandler;
import com.pragma.powerup.application.mapper.*;
import com.pragma.powerup.domain.api.IEstadoPedidoServicePort;
import com.pragma.powerup.domain.model.EmpleadoTiempoModel;
import com.pragma.powerup.domain.model.EstadoPedidoModel;
import com.pragma.powerup.domain.model.PaginationResult;
import com.pragma.powerup.domain.model.PedidoTimeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadoPedidoHandler implements IEstadoPedidoHandler {

    private final IEstadoPedidoServicePort estadoPedidoService;
    private final IEstadoPedidoRequestDtoMapper requestMapper;
    private final IEstadoPedidoResponseDtoMapper responseMapper;
    private final IPedidoTimeResponseDtoMapper timeResponseDtoMapper;
    private final IEmpleadoTiempoResponseMapper empleadoTiempoResponseMapper;
    private final IPaginationResponseMapper paginationResponseMapper;
    private final IPaginationRequestMapper paginationRequestMapper;

    @Override
    public EstadoPedidoResponseDto save(EstadoPedidoRequestDto estadoPedidoRequestDto) {
        EstadoPedidoModel estadoCreated = estadoPedidoService.save(requestMapper.toModel(estadoPedidoRequestDto));
        return responseMapper.toResponse(estadoCreated);
    }

    @Override
    public List<EstadoPedidoResponseDto> getAll(Long userId, Long pedidoId) {
        List<EstadoPedidoModel> list = estadoPedidoService.getAll(userId, pedidoId);
        return list.stream().map(responseMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public PaginationResponseDto<PedidoTimeResponseDto> getTimePedidos(Long restauranteId, PaginationRequestDto pagination) {
        PaginationResult<PedidoTimeModel> times = estadoPedidoService.getTimePedidos(restauranteId, paginationRequestMapper.toModel(pagination));
        PaginationResult<PedidoTimeResponseDto> result = times.map(timeResponseDtoMapper::toResponse);
        return paginationResponseMapper.toResponse(result);
    }

    @Override
    public PaginationResponseDto<EmpleadoTiempoResponseDto> getRankingEmpleados(Long restauranteId, PaginationRequestDto pagination) {
        PaginationResult<EmpleadoTiempoModel> times = estadoPedidoService.getRankingEmpleados(restauranteId, paginationRequestMapper.toModel(pagination));
        PaginationResult<EmpleadoTiempoResponseDto> result = times.map(empleadoTiempoResponseMapper::toResponse);
        return paginationResponseMapper.toResponse(result);
    }

}
