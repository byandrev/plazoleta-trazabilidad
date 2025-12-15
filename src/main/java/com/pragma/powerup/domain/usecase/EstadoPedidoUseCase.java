package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IEstadoPedidoServicePort;
import com.pragma.powerup.domain.model.EstadoPedidoModel;
import com.pragma.powerup.domain.spi.IEstadoPedidoPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoPedidoUseCase implements IEstadoPedidoServicePort {

    private final IEstadoPedidoPersistencePort estadoPedidoPersistencePort;

    @Override
    public EstadoPedidoModel save(EstadoPedidoModel estadoPedido) {
        estadoPedido.setFecha(LocalDateTime.now());
        return estadoPedidoPersistencePort.save(estadoPedido);
    }

    @Override
    public List<EstadoPedidoModel> getAll(Long userId, Long pedidoId) {
        List<EstadoPedidoModel> list = estadoPedidoPersistencePort.getAll(userId, pedidoId);
        return list;
    }

}
