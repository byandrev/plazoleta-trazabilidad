package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.EstadoPedidoModel;
import com.pragma.powerup.domain.model.PaginationInfo;
import com.pragma.powerup.domain.model.PaginationResult;
import com.pragma.powerup.domain.model.PedidoTimeModel;

import java.util.List;

public interface IEstadoPedidoPersistencePort {

    EstadoPedidoModel save(EstadoPedidoModel estadoPedido);

    List<EstadoPedidoModel> getAll(Long userId, Long pedidoId);

    PaginationResult<PedidoTimeModel> getTimePedidos(Long restauranteId, PaginationInfo pagination);

}
