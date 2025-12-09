package com.pragma.powerup.application.dto.request;

import com.pragma.powerup.domain.model.EstadoType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class EstadoPedidoRequestDto {

    @NotNull(message = "Es necesario el pedidoId")
    private Long pedidoId;

    @NotNull(message = "Es necesario el clienteId")
    private Long clienteId;

    @NotBlank(message = "El correoCliente no puede estar vacio")
    @Email(message = "El correo del cliente es inválido")
    private String correoCliente;

    @NotNull(message = "Es necesario el estadoAnterior")
    private EstadoType estadoAnterior;

    @NotNull(message = "Es necesario el estadoNuevo")
    private EstadoType estadoNuevo;

    @NotNull(message = "Es necesario el empleadoId")
    private Long empleadoId;

    @NotBlank(message = "El correoEmpleado no puede estar vacio")
    @Email(message = "El correo del empleado es inválido")
    private String correoEmpleado;

}
