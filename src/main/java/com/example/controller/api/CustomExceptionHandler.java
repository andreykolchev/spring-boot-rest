package com.example.controller.api;

import com.example.model.dto.error.ErrorDto;
import com.example.model.error.ErrorException;
import com.example.model.error.NotFoundErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundErrorException.class)
    public @ResponseBody
    ErrorDto handleErrorException(NotFoundErrorException ex) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ErrorException.class)
    public @ResponseBody
    ErrorDto handleErrorException(ErrorException ex) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody
    ErrorDto handleGlobalServerException(Exception ex) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    public ErrorDto createErrorResponse(HttpStatus httpStatus, Exception ex) {
        final String message = ex.getMessage();
        log.debug("Returning HTTP status: {}, message: {}", httpStatus, message);
        return new ErrorDto(String.valueOf(httpStatus.value()), message);
    }

}
