package yurii.karpliuk.marketplace.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import yurii.karpliuk.marketplace.dto.response.ErrorResponse;
import yurii.karpliuk.marketplace.exception.NotEnoughMoneyException;
import yurii.karpliuk.marketplace.exception.NotFoundException;

import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException notFoundException) {
        return ErrorResponse.builder()
                .message(notFoundException.getMessage())
                .status(NOT_FOUND)
                .timestamp(now())
                .build();
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleNotEnoughMoneyException(NotEnoughMoneyException notEnoughMoneyException) {
        return ErrorResponse.builder()
                .message(notEnoughMoneyException.getMessage())
                .status(BAD_REQUEST)
                .timestamp(now())
                .build();
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }
}
