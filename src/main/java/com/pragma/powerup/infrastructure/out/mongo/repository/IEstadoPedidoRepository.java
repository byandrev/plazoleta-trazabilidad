package com.pragma.powerup.infrastructure.out.mongo.repository;

import com.pragma.powerup.domain.model.PedidoTimeModel;
import com.pragma.powerup.infrastructure.out.mongo.entity.EstadoPedidoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IEstadoPedidoRepository extends MongoRepository<EstadoPedidoEntity, String> {

    List<EstadoPedidoEntity> findAllByClienteIdAndPedidoId(Long clienteId, Long pedidoId);

    @Aggregation(pipeline = {
            "{ '$match': { 'id_pedido': { '$ne': null }, 'estado_nuevo': { '$in': ['PENDIENTE', 'ENTREGADO'] } } }",
            "{ '$group': { '_id': '$id_pedido' } }",
            "{ '$count': 'total' }"
    })
    long countTotalTimeByPedido();

    @Aggregation(pipeline = {
            "{ '$match': { 'estado_nuevo': { '$in': ['PENDIENTE', 'ENTREGADO'] } } }",

            "{ '$group': { " +
                    "'_id': '$id_pedido', " +
                    "'fechaPreparacion': { '$min': { '$cond': [ { '$eq': ['$estado_nuevo', 'PENDIENTE'] }, '$fecha', null ] } }, " +
                    "'fechaEntregado': { '$max': { '$cond': [ { '$eq': ['$estado_nuevo', 'ENTREGADO'] }, '$fecha', null ] } }, " +
                    "'pedido': { '$first': '$id_pedido' } " +
                    "} }",

            "{ '$project': { " +
                    "'_id': 0," +
                    "'fechaInicio': '$fechaPreparacion'," +
                    "'fechaFin': '$fechaEntregado'," +
                    "'tiempo': { '$divide': [ { '$subtract': ['$fechaEntregado', '$fechaPreparacion'] }, 1000 ] }," +
                    "'pedido': '$pedido'," +
                    "} }",

            "{ '$skip': ?#{#pageable.offset} }",
            "{ '$limit': ?#{#pageable.pageSize} }"
    })
    List<PedidoTimeModel> getTimeByPedido(Pageable pageable);

}
