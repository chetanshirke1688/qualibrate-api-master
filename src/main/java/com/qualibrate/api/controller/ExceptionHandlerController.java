package com.qualibrate.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qualibrate.api.exceptions.ErrorCodes;
import com.qualibrate.api.exceptions.InvalidRequestException;
import com.qualibrate.api.exceptions.ResourceNotAvailableException;
import com.qualibrate.api.exceptions.ResourceNotFoundException;
import com.qualibrate.api.exceptions.UnAuthorizedException;
import com.qualibrate.api.exceptions.UnSupportedFormatException;
import com.qualibrate.api.exceptions.model.Error;
import com.qualibrate.api.exceptions.model.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * This class added advice in controller for handling exception scenario from
 * API.
 *
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 */
@Slf4j
@ControllerAdvice(basePackages = "com.qualibrate.api")
public class ExceptionHandlerController {

    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(Throwable throwable) {
        log.error("Unexpected exception", throwable);
        return buildErrorResponse(ErrorCodes.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(Exception throwable) {
        log.error("Unexpected exception", throwable);
        return buildErrorResponse(ErrorCodes.INTERNAL_SERVER_ERROR, throwable.getMessage());
    }

    @ExceptionHandler(value = ResourceNotAvailableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(ResourceNotAvailableException notAvailableException) {
        log.error("Unexpected exception", notAvailableException);
        String errorCode = notAvailableException.getCode() == null
              ? ErrorCodes.INTERNAL_SERVER_ERROR : notAvailableException.getCode();
        return buildErrorResponse(errorCode, notAvailableException.getMessage());
    }

    @ExceptionHandler(value = UnSupportedFormatException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public ErrorResponse handleUnSupportedFormatException(UnSupportedFormatException throwable) {
        log.error("Unexpected exception", throwable);
        return buildErrorResponse(ErrorCodes.FORMAT_NOT_SUPPORTED, throwable.getMessage());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundException(ResourceNotFoundException notFoundException) {
        String errorCode = notFoundException.getCode() == null
                 ? ErrorCodes.NOT_FOUND_EXCEPTION : notFoundException.getCode();
        return buildErrorResponse(errorCode, notFoundException.getMessage());
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidationException(ValidationException validationException) {
        return buildErrorResponse(ErrorCodes.VALIDATION_EXCEPTION, validationException.getMessage());
    }

    @ExceptionHandler(value = UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleAccessDeniedException(UnAuthorizedException accessDeniedException) {
        String errorCode = accessDeniedException.getCode() == null
              ? ErrorCodes.UNAUTHOIRZED : accessDeniedException.getCode();
        return buildErrorResponse(errorCode, accessDeniedException.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidationException(ConstraintViolationException validationException) {
        return buildErrorResponse(ErrorCodes.VALIDATION_EXCEPTION, validationException);
    }

    @ExceptionHandler(value = InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidationException(InvalidRequestException validationException) {
        return buildErrorResponse(ErrorCodes.VALIDATION_EXCEPTION, validationException.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        Throwable rootCause = exception.getRootCause();
        if (rootCause instanceof JsonProcessingException) {
            return buildErrorResponse(ErrorCodes.INPUT_NOT_READABLE,
                  ((JsonProcessingException) rootCause).getOriginalMessage());
        } else {
            return buildErrorResponse(ErrorCodes.INPUT_NOT_READABLE, HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> exceptions = ex.getBindingResult().getAllErrors();
        List<Error> errors = exceptions.stream().map(this::buildError).collect(Collectors.toList());
        return ErrorResponse.builder().errors(errors).build();
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return buildErrorResponse(ErrorCodes.METHOD_NOT_SUPPORTED, ex.getMessage());
    }

    @ExceptionHandler(value = ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleServletRequestBindingException(ServletRequestBindingException ex) {
        return buildErrorResponse(ErrorCodes.VALIDATION_EXCEPTION, ex.getMessage());
    }

    private Error buildError(ObjectError objectError) {
        String message = objectError.getDefaultMessage();
        if (objectError instanceof FieldError) {
            message = objectError.getObjectName() + "." + ((FieldError) objectError).getField() + " " + message;
        }
        Error error = new Error();
        error.setCode(objectError.getCode());
        error.setMessage(message);
        return error;
    }

    private ErrorResponse buildErrorResponse(String code, String message) {
        Error error = new Error();
        error.setMessage(message);
        error.setCode(code);
        return ErrorResponse.builder().error(error).build();
    }

    private ErrorResponse buildErrorResponse(String code, ConstraintViolationException validationException) {
        List<Error> errors = new ArrayList<>();
        for (@SuppressWarnings("rawtypes")
            ConstraintViolation constraintViolation : validationException.getConstraintViolations()) {
            Error error = new Error();
            error.setMessage(constraintViolation.getMessage());
            error.setCode(code);
            errors.add(error);
        }
        return ErrorResponse.builder().errors(errors).build();
    }
}
