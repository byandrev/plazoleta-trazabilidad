package com.pragma.powerup.infrastructure.out.mongo.adapter;

import com.pragma.powerup.domain.model.EstadoPedidoModel;
import com.pragma.powerup.domain.spi.IEstadoPedidoPersistencePort;
import com.pragma.powerup.infrastructure.out.mongo.entity.EstadoPedidoEntity;
import com.pragma.powerup.infrastructure.out.mongo.mapper.IEstadoPedidoEntityMapper;
import com.pragma.powerup.infrastructure.out.mongo.repository.IEstadoPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EstadoPedidoAdapter implements IEstadoPedidoPersistencePort {

    private final IEstadoPedidoRepository repository;

    private final IEstadoPedidoEntityMapper mapper;

    @Override
    public EstadoPedidoModel save(EstadoPedidoModel estadoPedido) {
        EstadoPedidoEntity entity = mapper.toEntity(estadoPedido);
        return mapper.toModel(repository.save(entity));
    }

}
