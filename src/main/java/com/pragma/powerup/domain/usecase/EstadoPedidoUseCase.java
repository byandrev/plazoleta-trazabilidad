package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IEstadoPedidoServicePort;
import com.pragma.powerup.domain.model.EstadoPedidoModel;
import com.pragma.powerup.domain.model.PaginationInfo;
import com.pragma.powerup.domain.model.PaginationResult;
import com.pragma.powerup.domain.model.PedidoTimeModel;
import com.pragma.powerup.domain.spi.IEstadoPedidoPersistencePort;
import com.pragma.powerup.domain.utils.ConvertDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoPedidoUseCase implements IEstadoPedidoServicePort {

    private final IEstadoPedidoPersistencePort estadoPedidoPersistencePort;

    @Override
    public EstadoPedidoModel save(EstadoPedidoModel estadoPedido) {
        estadoPedido.setFecha(ConvertDate.getCurrentDateTimeUTC());
        return estadoPedidoPersistencePort.save(estadoPedido);
    }

    @Override
    public List<EstadoPedidoModel> getAll(Long userId, Long pedidoId) {
        List<EstadoPedidoModel> list = estadoPedidoPersistencePort.getAll(userId, pedidoId);
        return list;
    }

    @Override
    public PaginationResult<PedidoTimeModel> getTimePedidos(Long restauranteId, PaginationInfo pagination) {
        return estadoPedidoPersistencePort.getTimePedidos(restauranteId, pagination);
    }

}
