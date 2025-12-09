package com.pragma.powerup.infrastructure.out.mongo.repository;

import com.pragma.powerup.infrastructure.out.mongo.entity.EstadoPedidoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IEstadoPedidoRepository extends MongoRepository<EstadoPedidoEntity, String> {
}
