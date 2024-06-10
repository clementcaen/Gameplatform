package fr.isencaen.gameplatform.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AccountFunctionalException.class)
    public ResponseEntity<Object> handleAccountFunctionalException(AccountFunctionalException ex) {
        String errorMessage = "An error occurred related to account functionality. Details: " + ex.getMessage();
        return new ResponseEntity<>(Map.of("code", ex.getCode(), "message", errorMessage), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GamesFunctionalException.class)
    public ResponseEntity<Object> handleGamesFunctionalException(GamesFunctionalException ex) { 
        String errorMessage = "An error occurred related to game functionality. Details: " + ex.getMessage();
        return new ResponseEntity<>(Map.of("code", ex.getCode(), "message", errorMessage), HttpStatus.CONFLICT);
    }
}
