package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.EstadoPedidoResponseDto;
import com.pragma.powerup.domain.model.EstadoPedidoModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IEstadoPedidoResponseDtoMapper {

    EstadoPedidoResponseDto toResponse(EstadoPedidoModel estadoPedidoModel);

}
