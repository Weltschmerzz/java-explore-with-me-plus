package ru.practicum.ewm.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        return buildError(
                HttpStatus.NOT_FOUND,
                "The required object was not found.",
                ex.getMessage(),
                List.of(ex.getMessage())
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ConflictException ex) {
        return buildError(
                HttpStatus.CONFLICT,
                "Integrity constraint has been violated.",
                ex.getMessage(),
                List.of(ex.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.toList());

        return buildError(
                HttpStatus.BAD_REQUEST,
                "Incorrectly made request.",
                "Validation failed",
                errors
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.toList());

        return buildError(
                HttpStatus.BAD_REQUEST,
                "Incorrectly made request.",
                "Constraint violation",
                errors
        );
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleOther(Throwable ex) {
        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error.",
                ex.getMessage(),
                List.of(ex.getMessage())
        );
    }

    private ResponseEntity<ApiError> buildError(HttpStatus status,
                                                String reason,
                                                String message,
                                                List<String> errors) {
        ApiError error = ApiError.builder()
                .status(status.name())
                .reason(reason)
                .message(message)
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, status);
    }
}