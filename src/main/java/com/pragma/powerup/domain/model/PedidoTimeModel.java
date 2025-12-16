package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PedidoTimeModel {

    private Long pedido;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    private Float tiempo;

}
