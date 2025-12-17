package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.*;

import java.util.List;

public interface IEstadoPedidoPersistencePort {

    EstadoPedidoModel save(EstadoPedidoModel estadoPedido);

    List<EstadoPedidoModel> getAll(Long userId, Long pedidoId);

    PaginationResult<PedidoTimeModel> getTimePedidos(Long restauranteId, PaginationInfo pagination);

    PaginationResult<EmpleadoTiempoModel> getRankingEmpleados(Long restauranteId, PaginationInfo pagination);

}
