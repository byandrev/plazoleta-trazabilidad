package com.pragma.powerup.infrastructure.out.mongo.entity;

import com.pragma.powerup.domain.model.EstadoType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "trazabilidad")
public class EstadoPedidoEntity {

    @Id
    private String id;

    @Field("id_pedido")
    private Long pedidoId;

    @Field("id_cliente")
    private Long clienteId;

    @Field("correo_cliente")
    private String correoCliente;

    private LocalDateTime fecha;

    @Field("estado_anterior")
    private EstadoType estadoAnterior;

    @Field("estado_nuevo")
    private EstadoType estadoNuevo;

    @Field("id_empleado")
    private Long empleadoId;

    @Field("correo_empleado")
    private String correoEmpleado;

}
