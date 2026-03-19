package com.generation.blogpessoal.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erros de validação de campos (@NotBlank, @Size, @NotNull, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        String mensagem = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new HttpResponse(400, "Validation Error", mensagem, request.getRequestURI())
        );
    }

    // Erros lançados manualmente com ResponseStatusException (ex: "Tema não encontrado")
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<HttpResponse> handleResponseStatusException(
            ResponseStatusException ex, HttpServletRequest request) {

        return ResponseEntity.status(ex.getStatusCode()).body(
                new HttpResponse(
                        ex.getStatusCode().value(),
                        ex.getStatusCode().toString(),
                        ex.getReason() != null ? ex.getReason() : "Erro interno",
                        request.getRequestURI()
                )
        );
    }

    // JSON malformado ou tipo de dado errado no corpo da requisição
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HttpResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new HttpResponse(400, "Bad Request", "Corpo da requisição inválido ou formato de dado incorreto.", request.getRequestURI())
        );
    }

    // Fallback: qualquer outra exceção não tratada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new HttpResponse(500, "Internal Server Error", "Ocorreu um erro inesperado no servidor.", request.getRequestURI())
        );
    }
}
