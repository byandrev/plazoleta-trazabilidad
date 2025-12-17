package com.pragma.powerup.infrastructure.out.mongo.adapter;

import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.domain.spi.IEstadoPedidoPersistencePort;
import com.pragma.powerup.infrastructure.exception.ResourceNotFound;
import com.pragma.powerup.infrastructure.out.mongo.entity.EstadoPedidoEntity;
import com.pragma.powerup.infrastructure.out.mongo.mapper.IEstadoPedidoEntityMapper;
import com.pragma.powerup.infrastructure.out.mongo.mapper.IPaginationMapper;
import com.pragma.powerup.infrastructure.out.mongo.repository.IEstadoPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EstadoPedidoAdapter implements IEstadoPedidoPersistencePort {

    private final IEstadoPedidoRepository repository;
    private final IEstadoPedidoEntityMapper mapper;
    private final IPaginationMapper paginationMapper;

    @Override
    public EstadoPedidoModel save(EstadoPedidoModel estadoPedido) {
        EstadoPedidoEntity entity = mapper.toEntity(estadoPedido);
        return mapper.toModel(repository.save(entity));
    }

    @Override
    public List<EstadoPedidoModel> getAll(Long userId, Long pedidoId) {
        List<EstadoPedidoEntity> listEntity = repository.findAllByClienteIdAndPedidoId(userId, pedidoId);

        if (listEntity.isEmpty()) {
            throw new ResourceNotFound("El pedido " + pedidoId + " no existe");
        }

        return listEntity.stream().map(mapper::toModel).collect(Collectors.toList());
    }

    @Override
    public PaginationResult<PedidoTimeModel> getTimePedidos(Long restauranteId, PaginationInfo pagination) {
        Pageable pageable = paginationMapper.toPageable(pagination);
        List<PedidoTimeModel> content = repository.getTimeByPedidos(restauranteId, pageable);
        Long totalCount = repository.countTotalTimeByPedido(restauranteId);
        long total = (totalCount != null) ? totalCount : 0L;
        Page<PedidoTimeModel> page = new PageImpl<>(content, pageable, total);
        return paginationMapper.toModel(page);
    }

    @Override
    public PaginationResult<EmpleadoTiempoModel> getRankingEmpleados(Long restauranteId, PaginationInfo pagination) {
        Pageable pageable = paginationMapper.toPageable(pagination);
        List<EmpleadoTiempoModel> content = repository.getRankingTiempoByEmpleado(restauranteId, pageable);
        Long totalCount = repository.countTotalEmpleadosRanking(restauranteId);
        long total = (totalCount != null) ? totalCount : 0L;
        Page<EmpleadoTiempoModel> page = new PageImpl<>(content, pageable, total);
        return paginationMapper.toModel(page);
    }

}

