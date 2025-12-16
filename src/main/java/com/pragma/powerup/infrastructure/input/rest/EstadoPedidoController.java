package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.EstadoPedidoRequestDto;
import com.pragma.powerup.application.dto.request.PaginationRequestDto;
import com.pragma.powerup.application.dto.response.EstadoPedidoResponseDto;
import com.pragma.powerup.application.dto.response.PaginationResponseDto;
import com.pragma.powerup.application.dto.response.PedidoTimeResponseDto;
import com.pragma.powerup.application.handler.IEstadoPedidoHandler;
import com.pragma.powerup.infrastructure.input.rest.response.CustomResponse;
import com.pragma.powerup.infrastructure.out.security.models.CustomUserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @Operation(summary = "Get traceability")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Traceability list")
    })
    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{pedidoId}")
    public ResponseEntity<CustomResponse<List<EstadoPedidoResponseDto>>> getAll(
            @PathVariable Long pedidoId,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetails
    ) {
        Long userId = userDetails.getId();

        CustomResponse<List<EstadoPedidoResponseDto>> response = CustomResponse.<List<EstadoPedidoResponseDto>>builder()
                .status(HttpStatus.OK.value())
                .data(estadoPedidoHandler.getAll(userId, pedidoId))
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get tiempos de pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tiempos de pedidos")
    })
    @PreAuthorize("hasRole('PROPIETARIO')")
    @GetMapping("/pedidos")
    public ResponseEntity<CustomResponse<PaginationResponseDto<PedidoTimeResponseDto>>> getTimePedidos(
            @Valid PaginationRequestDto paginationRequest
    ) {
        CustomResponse<PaginationResponseDto<PedidoTimeResponseDto>> response = CustomResponse.<PaginationResponseDto<PedidoTimeResponseDto>>builder()
                .status(HttpStatus.OK.value())
                .data(estadoPedidoHandler.getTimePedidos(paginationRequest))
                .build();

        return ResponseEntity.ok(response);
    }

}
