package ait.cohort5860.accounting.dto.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        Map<String, Object> body = Map.of(
                "timestamp", ZonedDateTime.now(),
                "status", ex.getStatusCode().value(),
                "error", ex.getReason(),
                "path", request.getRequestURI()
        );

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(body);
    }
}
