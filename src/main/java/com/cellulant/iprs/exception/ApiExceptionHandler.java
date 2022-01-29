package com.cellulant.iprs.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

/**
 * Global exception handler
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = ResourceFoundException.class)
    public ResponseEntity<Object> resourceFoundException(ResourceFoundException exception) {
        return buildResponseEntity(new ApiException(NOT_FOUND, exception.getMessage(), ZonedDateTime.now(ZoneId.of("Africa/Nairobi"))));
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException exception) {
        return buildResponseEntity(new ApiException(NOT_FOUND, exception.getMessage(), ZonedDateTime.now(ZoneId.of("Africa/Nairobi"))));
    }

    @ExceptionHandler(value = UnprocessedResourceException.class)
    public ResponseEntity<Object> unprocessedEntityException(UnprocessedResourceException exception) {
        return buildResponseEntity(new ApiException(UNPROCESSABLE_ENTITY, exception.getMessage(), ZonedDateTime.now(ZoneId.of("Africa/Nairobi"))));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> conflict(DataIntegrityViolationException exception) {
        return buildResponseEntity(new ApiException(INTERNAL_SERVER_ERROR, exception.getMessage(), ZonedDateTime.now(ZoneId.of("Africa/Nairobi"))));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " -> parameter is missing in request";
        System.out.println("error =? " + error);
//        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
//        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);

        ApiException apiException = new ApiException(BAD_REQUEST, ex.getMessage(), ZonedDateTime.now(ZoneId.of("Africa/Nairobi")));
        // If you want to throw apiError directly, uncomment this
        // return new ResponseEntity<Object>(apiError, apiError.getStatus());
        //return handleExceptionInternal(ex, apiException, headers, apiException.getHttpStatus(), request);
        return buildResponseEntity(new ApiException(BAD_REQUEST, ex.getMessage(), ZonedDateTime.now(ZoneId.of("Africa/Nairobi"))));
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException apiError) {
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }
}
