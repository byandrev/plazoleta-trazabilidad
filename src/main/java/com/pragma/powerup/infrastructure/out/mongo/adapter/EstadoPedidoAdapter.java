package com.pragma.powerup.infrastructure.out.mongo.adapter;

import com.pragma.powerup.domain.model.EstadoPedidoModel;
import com.pragma.powerup.domain.spi.IEstadoPedidoPersistencePort;
import com.pragma.powerup.infrastructure.exception.ResourceNotFound;
import com.pragma.powerup.infrastructure.out.mongo.entity.EstadoPedidoEntity;
import com.pragma.powerup.infrastructure.out.mongo.mapper.IEstadoPedidoEntityMapper;
import com.pragma.powerup.infrastructure.out.mongo.repository.IEstadoPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<EstadoPedidoModel> getAll(Long userId, Long pedidoId) {
        List<EstadoPedidoEntity> listEntity = repository.findAllByClienteIdAndPedidoId(userId, pedidoId);

        if (listEntity.isEmpty()) {
            throw new ResourceNotFound("El pedido " + pedidoId + " no existe");
        }

        return listEntity.stream().map(mapper::toModel).collect(Collectors.toList());
    }

}
