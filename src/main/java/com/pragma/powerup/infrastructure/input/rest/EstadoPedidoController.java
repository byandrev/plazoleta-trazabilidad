package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.EstadoPedidoRequestDto;
import com.pragma.powerup.application.handler.IEstadoPedidoHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/trazabilidad")
@RequiredArgsConstructor
public class EstadoPedidoController {

    private final IEstadoPedidoHandler estadoPedidoHandler;

    @Operation(summary = "Add new traceability")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Traceability created", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> save(
            @Valid @RequestBody EstadoPedidoRequestDto estadoRequest
    ) {
        estadoPedidoHandler.save(estadoRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
