package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.application.handler.IEstadoPedidoHandler;
import com.pragma.powerup.application.handler.impl.EstadoPedidoHandler;
import com.pragma.powerup.application.mapper.*;
import com.pragma.powerup.domain.api.IEstadoPedidoServicePort;
import com.pragma.powerup.domain.spi.IEstadoPedidoPersistencePort;
import com.pragma.powerup.domain.usecase.EstadoPedidoUseCase;
import com.pragma.powerup.infrastructure.out.mongo.adapter.EstadoPedidoAdapter;
import com.pragma.powerup.infrastructure.out.mongo.mapper.IEstadoPedidoEntityMapper;
import com.pragma.powerup.infrastructure.out.mongo.mapper.IPaginationMapper;
import com.pragma.powerup.infrastructure.out.mongo.repository.IEstadoPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IEstadoPedidoRepository estadoPedidoRepository;
    private final IEstadoPedidoEntityMapper estadoPedidoEntityMapper;
    private final IPaginationMapper paginationMapper;
    private final IEstadoPedidoRequestDtoMapper requestMapper;
    private final IEstadoPedidoResponseDtoMapper responseMapper;
    private final IPedidoTimeResponseDtoMapper timeResponseDtoMapper;
    private final IEmpleadoTiempoResponseMapper empleadoTiempoResponseMapper;
    private final IPaginationResponseMapper paginationResponseMapper;
    private final IPaginationRequestMapper paginationRequestMapper;

    @Bean
    public IEstadoPedidoPersistencePort estadoPedidoPersistencePort() {
        return new EstadoPedidoAdapter(estadoPedidoRepository, estadoPedidoEntityMapper, paginationMapper);
    }

    @Bean
    public IEstadoPedidoServicePort estadoPedidoServicePort() {
        return new EstadoPedidoUseCase(estadoPedidoPersistencePort());
    }

    @Bean
    public IEstadoPedidoHandler estadoPedidoHandler() {
        return new EstadoPedidoHandler(
                estadoPedidoServicePort(),
                requestMapper,
                responseMapper,
                timeResponseDtoMapper,
                empleadoTiempoResponseMapper,
                paginationResponseMapper,
                paginationRequestMapper
        );
    }
}
