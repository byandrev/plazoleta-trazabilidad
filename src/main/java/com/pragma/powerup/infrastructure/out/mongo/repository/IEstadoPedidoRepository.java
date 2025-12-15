package com.pragma.powerup.infrastructure.out.mongo.repository;

import com.pragma.powerup.infrastructure.out.mongo.entity.EstadoPedidoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IEstadoPedidoRepository extends MongoRepository<EstadoPedidoEntity, String> {

    List<EstadoPedidoEntity> findAllByClienteIdAndPedidoId(Long clienteId, Long pedidoId);

}
