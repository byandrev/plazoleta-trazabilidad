package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.EstadoPedidoModel;

import java.util.List;

public interface IEstadoPedidoPersistencePort {

    EstadoPedidoModel save(EstadoPedidoModel estadoPedido);

    List<EstadoPedidoModel> getAll(Long userId, Long pedidoId);

}
