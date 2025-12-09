package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.EstadoPedidoModel;

public interface IEstadoPedidoPersistencePort {

    EstadoPedidoModel save(EstadoPedidoModel estadoPedido);

}
