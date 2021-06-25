package com.etip.app.handlers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.etip.app.model.EndPointsErrorsModel;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        EndPointsErrorsModel endpointError = new EndPointsErrorsModel(HttpStatus.BAD_REQUEST.value(),
                ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, endpointError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";

        EndPointsErrorsModel endpointError = new EndPointsErrorsModel(HttpStatus.BAD_REQUEST.value(),
                ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(endpointError, new HttpHeaders(), endpointError.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
                    + violation.getMessage());
        }

        EndPointsErrorsModel endpointError = new EndPointsErrorsModel(HttpStatus.BAD_REQUEST.value(),
                ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(endpointError, new HttpHeaders(), endpointError.getStatus());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                   WebRequest request) {
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

        EndPointsErrorsModel endpointError = new EndPointsErrorsModel(HttpStatus.BAD_REQUEST.value(),
                ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(endpointError, new HttpHeaders(), endpointError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        EndPointsErrorsModel endpointError = new EndPointsErrorsModel(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage(),
                error);
        return new ResponseEntity<Object>(endpointError, new HttpHeaders(), endpointError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

        EndPointsErrorsModel endpointError = new EndPointsErrorsModel(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        return new ResponseEntity<Object>(endpointError, new HttpHeaders(), endpointError.getStatus());
    }
    
    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> handleInvalidCredential(Exception ex, WebRequest request) {
        EndPointsErrorsModel endpointError = new EndPointsErrorsModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getLocalizedMessage(), "Credential not valid");
        return new ResponseEntity<Object>(endpointError, new HttpHeaders(), endpointError.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        EndPointsErrorsModel endpointError = new EndPointsErrorsModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<Object>(endpointError, new HttpHeaders(), endpointError.getStatus());
    }
}


