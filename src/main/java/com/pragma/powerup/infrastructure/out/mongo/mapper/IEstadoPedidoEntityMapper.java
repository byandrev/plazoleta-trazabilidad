package com.pragma.powerup.infrastructure.out.mongo.mapper;

import com.pragma.powerup.domain.model.EstadoPedidoModel;
import com.pragma.powerup.infrastructure.out.mongo.entity.EstadoPedidoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IEstadoPedidoEntityMapper {

    EstadoPedidoEntity toEntity(EstadoPedidoModel estadoPedidoModel);

    EstadoPedidoModel toModel(EstadoPedidoEntity estadoPedidoEntity);

}
