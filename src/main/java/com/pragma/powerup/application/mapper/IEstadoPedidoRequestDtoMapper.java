package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.EstadoPedidoRequestDto;
import com.pragma.powerup.domain.model.EstadoPedidoModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IEstadoPedidoRequestDtoMapper {

    EstadoPedidoModel toModel(EstadoPedidoRequestDto estadoPedidoRequestDto);

}
