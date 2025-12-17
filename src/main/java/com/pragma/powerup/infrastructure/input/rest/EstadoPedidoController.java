package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.EstadoPedidoRequestDto;
import com.pragma.powerup.application.dto.request.PaginationRequestDto;
import com.pragma.powerup.application.dto.response.EmpleadoTiempoResponseDto;
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
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Trazabilidad de Pedidos", description = "Endpoints para el seguimiento de estados")
public class EstadoPedidoController {

    private final IEstadoPedidoHandler estadoPedidoHandler;

    @Operation(
            summary = "Registrar un cambio de estado",
            description = "Guarda un nuevo registro en el historial de trazabilidad de un pedido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro de trazabilidad creado exitosamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de trazabilidad inválidos", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> save(
            @Valid @RequestBody EstadoPedidoRequestDto estadoRequest
    ) {
        estadoPedidoHandler.save(estadoRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Consultar línea de tiempo de un pedido",
            description = "Permite al cliente ver todos los hitos y estados por los que ha pasado su pedido. **Solo accesible por CLIENTE.**"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estados obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró historial para el ID de pedido proporcionado", content = @Content)
    })
    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{pedidoId}")
    public ResponseEntity<CustomResponse<List<EstadoPedidoResponseDto>>> getAll(
            @Parameter(description = "ID del pedido a consultar", example = "1")
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

    @Operation(
            summary = "Reporte de eficiencia de pedidos por restaurante",
            description = "Calcula el tiempo transcurrido entre el inicio y el fin de cada pedido para un restaurante específico. **Solo accesible por PROPIETARIO.**"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte de tiempos generado exitosamente")
    })
    @PreAuthorize("hasRole('PROPIETARIO')")
    @GetMapping("/pedidos")
    public ResponseEntity<CustomResponse<PaginationResponseDto<PedidoTimeResponseDto>>> getTimePedidos(
            @Valid PaginationRequestDto paginationRequest,
            @Parameter(description = "ID del restaurante para filtrar", example = "1")
            @RequestParam Long restauranteId
    ) {
        CustomResponse<PaginationResponseDto<PedidoTimeResponseDto>> response = CustomResponse.<PaginationResponseDto<PedidoTimeResponseDto>>builder()
                .status(HttpStatus.OK.value())
                .data(estadoPedidoHandler.getTimePedidos(restauranteId, paginationRequest))
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Ranking de empleados (Tiempo promedio)",
            description = "Muestra el promedio de tiempo de entrega de cada empleado del restaurante. **Solo accesible por PROPIETARIO.**"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking de empleados obtenido exitosamente")
    })
    @PreAuthorize("hasRole('PROPIETARIO')")
    @GetMapping("/empleados")
    public ResponseEntity<CustomResponse<PaginationResponseDto<EmpleadoTiempoResponseDto>>> getTimeEmpleados(
            @Valid PaginationRequestDto paginationRequest,
            @Parameter(description = "ID del restaurante", example = "1")
            @RequestParam Long restauranteId
    ) {
        CustomResponse<PaginationResponseDto<EmpleadoTiempoResponseDto>> response = CustomResponse.<PaginationResponseDto<EmpleadoTiempoResponseDto>>builder()
                .status(HttpStatus.OK.value())
                .data(estadoPedidoHandler.getRankingEmpleados(restauranteId, paginationRequest))
                .build();

        return ResponseEntity.ok(response);
    }

}
