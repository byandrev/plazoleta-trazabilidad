package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.*;

import java.util.List;

public interface IEstadoPedidoServicePort {

    EstadoPedidoModel save(EstadoPedidoModel estadoPedido);

    List<EstadoPedidoModel> getAll(Long userId, Long pedidoId);

    PaginationResult<PedidoTimeModel> getTimePedidos(Long restauranteId, PaginationInfo pagination);

    PaginationResult<EmpleadoTiempoModel> getRankingEmpleados(Long restauranteId, PaginationInfo pagination);

}
