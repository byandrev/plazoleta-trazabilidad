package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.EstadoPedidoModel;

import java.util.List;

public interface IEstadoPedidoServicePort {

    EstadoPedidoModel save(EstadoPedidoModel estadoPedido);

    List<EstadoPedidoModel> getAll(Long userId, Long pedidoId);

}
