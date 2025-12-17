package com.pragma.powerup.infrastructure.out.mongo.repository;

import com.pragma.powerup.domain.model.EmpleadoTiempoModel;
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
            "{ '$match': { 'id_restaurante': ?0, 'estado_nuevo': { '$in': ['PENDIENTE', 'ENTREGADO'] } } }",

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
    List<PedidoTimeModel> getTimeByPedidos(Long restauranteId, Pageable pageable);

    @Aggregation(pipeline = {
            "{ '$match': { 'id_restaurante': ?0, 'id_pedido': { '$ne': null }, 'estado_nuevo': { '$in': ['PENDIENTE', 'ENTREGADO'] } } }",
            "{ '$group': { '_id': '$id_pedido' } }",
            "{ '$project': { '_id': 0, 'idEmpleado': '$id_empleado' } }",
            "{ '$group': { '_id': '$idEmpleado' } }",
            "{ '$count': 'total' }"
    })
    long countTotalEmpleadosRanking(Long restauranteId);

    @Aggregation(pipeline = {
            "{ '$match': { 'id_restaurante': ?0, 'id_pedido': { '$ne': null }, 'estado_nuevo': { '$in': ['PENDIENTE', 'ENTREGADO'] } } }",

            "{ '$group': { " +
                    "'_id': '$id_pedido', " +
                    "'empleado': { '$last': '$id_empleado' }, " +
                    "'fechaInicio': { '$min': { '$cond': [ { '$eq': ['$estado_nuevo', 'PENDIENTE'] }, '$fecha', null ] } }, " +
                    "'fechaFin': { '$max': { '$cond': [ { '$eq': ['$estado_nuevo', 'ENTREGADO'] }, '$fecha', null ] } }, " +
                    "} }",

            "{ '$group': { " +
                    "'_id': '$empleado', " +
                    "'tiempoMedioSegundos': { '$avg': { '$divide': [{ '$subtract': ['$fechaFin', '$fechaInicio'] }, 1000] } } " +
                    "} }",

            "{ '$project': { " +
                    "'_id': 0," +
                    "'empleado': '$_id'," +
                    "'tiempoMedioSegundos': '$tiempoMedioSegundos' " +
                    "} }",

            "{ '$sort': { 'tiempoMedioSegundos': 1 } }",
            "{ '$skip': ?#{#pageable.offset} }",
            "{ '$limit': ?#{#pageable.pageSize} }"
    })
    List<EmpleadoTiempoModel> getRankingTiempoByEmpleado(Long restauranteId, Pageable pageable);

}
