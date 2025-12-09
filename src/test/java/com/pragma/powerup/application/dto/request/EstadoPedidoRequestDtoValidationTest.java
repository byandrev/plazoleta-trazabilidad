package com.pragma.powerup.application.dto.request;

import com.pragma.powerup.domain.model.EstadoType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class EstadoPedidoRequestDtoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private EstadoPedidoRequestDto createValidEstadoPedidoDto() {
        return EstadoPedidoRequestDto.builder()
                .pedidoId(1L)
                .clienteId(10L)
                .correoCliente("test@gmail.com")
                .estadoAnterior(EstadoType.PENDIENTE)
                .estadoNuevo(EstadoType.EN_PREPARACION)
                .empleadoId(5L)
                .correoEmpleado("empleado@gmail.com")
                .build();
    }

    private void assertViolation(EstadoPedidoRequestDto dto, String expectedMessage) {
        Set<ConstraintViolation<EstadoPedidoRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Debería haber violaciones de validación.");

        boolean found = violations.stream()
                .anyMatch(v -> v.getMessage().equals(expectedMessage));

        assertTrue(found, "No se encontró el mensaje de violación esperado: " + expectedMessage);
    }

    @Test
    @DisplayName("EstadoPedido válido debe pasar la validación")
    void saveValidate_Success() {
        EstadoPedidoRequestDto dto = createValidEstadoPedidoDto();
        Set<ConstraintViolation<EstadoPedidoRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "No deberían existir violaciones con datos válidos.");
    }

    @Test
    @DisplayName("pedidoId nulo debe fallar la validación")
    void saveValidatePedidoId_Null_FailsValidation() {
        EstadoPedidoRequestDto dto = createValidEstadoPedidoDto();
        dto.setPedidoId(null);
        assertViolation(dto, "Es necesario el pedidoId");
    }

    @Test
    @DisplayName("clienteId nulo debe fallar la validación")
    void saveValidateClienteId_Null_FailsValidation() {
        EstadoPedidoRequestDto dto = createValidEstadoPedidoDto();
        dto.setClienteId(null);
        assertViolation(dto, "Es necesario el clienteId");
    }

    @Test
    @DisplayName("correoCliente vacío debe fallar la validación")
    void saveValidateCorreoCliente_Empty_FailsValidation() {
        EstadoPedidoRequestDto dto = createValidEstadoPedidoDto();
        dto.setCorreoCliente("");
        assertViolation(dto, "El correoCliente no puede estar vacio");
    }

    @Test
    @DisplayName("correoCliente nulo debe fallar la validación")
    void saveValidateCorreoCliente_Null_FailsValidation() {
        EstadoPedidoRequestDto dto = createValidEstadoPedidoDto();
        dto.setCorreoCliente(null);
        assertViolation(dto, "El correoCliente no puede estar vacio");
    }

    @Test
    @DisplayName("correoCliente con formato inválido debe fallar la validación")
    void saveValidateCorreoCliente_InvalidFormat_FailsValidation() {
        EstadoPedidoRequestDto dto = createValidEstadoPedidoDto();
        dto.setCorreoCliente("correo-invalido");
        assertViolation(dto, "El correo del cliente es inválido");
    }

    @Test
    @DisplayName("estadoAnterior nulo debe fallar la validación")
    void saveValidateEstadoAnterior_Null_FailsValidation() {
        EstadoPedidoRequestDto dto = createValidEstadoPedidoDto();
        dto.setEstadoAnterior(null);
        assertViolation(dto, "Es necesario el estadoAnterior");
    }

    @Test
    @DisplayName("estadoNuevo nulo debe fallar la validación")
    void saveValidateEstadoNuevo_Null_FailsValidation() {
        EstadoPedidoRequestDto dto = createValidEstadoPedidoDto();
        dto.setEstadoNuevo(null);
        assertViolation(dto, "Es necesario el estadoNuevo");
    }

    @Test
    @DisplayName("empleadoId nulo debe fallar la validación")
    void saveValidateEmpleadoId_Null_FailsValidation() {
        EstadoPedidoRequestDto dto = createValidEstadoPedidoDto();
        dto.setEmpleadoId(null);
        assertViolation(dto, "Es necesario el empleadoId");
    }

    @Test
    @DisplayName("correoEmpleado con formato inválido debe fallar la validación")
    void saveValidateCorreoEmpleado_InvalidFormat_FailsValidation() {
        EstadoPedidoRequestDto dto = createValidEstadoPedidoDto();
        dto.setCorreoEmpleado("correo-invalido");
        assertViolation(dto, "El correo del empleado es inválido");
    }

}

