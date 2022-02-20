package com.musala.assessment.drone.common;

import com.google.common.base.Throwables;
import com.musala.assessment.drone.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    private final ResponseUtil responseUtil;

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Method Argument Not Valid Exception : ", ex);
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(ex.getMessage());
        return handleExceptionInternal(ex, errorMessage,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

    @ExceptionHandler(value={  DataIntegrityViolationException.class })
    public DroneServiceResponse handleDataIntegrityViolationException (DataIntegrityViolationException  ex, WebRequest request) {
        log.error("Data Integrity Violation Exception : ", ex);
        Throwable rootCause = Throwables.getRootCause(ex);
        String errorMessage = rootCause.getMessage();
        return responseUtil.buildErrorResponse(HttpStatus.CONFLICT, errorMessage);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        super.handleExceptionInternal(ex,body,headers,status, request);
        log.error(ex.toString());
        String message = (body != null)?body.toString(): ex.getMessage();
        DroneServiceResponse response = responseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, message);
        return new ResponseEntity<>(response, status);

    }
}
