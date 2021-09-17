package ru.zheleznov.web.exceptions.handlers;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.zheleznov.web.dto.ExceptionDto;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler  {

  @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
  protected ResponseEntity<Object> handleExceptions(RuntimeException ex, WebRequest request) {
    ex.printStackTrace();
    return handleExceptionInternal(ex,
            ExceptionDto.builder()
                    .message(ex.getMessage())
                    .statusCode(HttpStatus.CONFLICT)
                    .build(),
            new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    ex.printStackTrace();
    List<String> validationList =
            ex.getBindingResult().getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

    return new ResponseEntity<>(
            ExceptionDto.builder()
                    .message(validationList.toString())
                    .statusCode(HttpStatus.valueOf(422))
                    .build(), status);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    ex.printStackTrace();
    ExceptionDto exceptionDto =
            ExceptionDto.builder()
                    .statusCode(status)
                    .message(ex.getLocalizedMessage())
                    .build();

    return handleExceptionInternal(ex, exceptionDto, headers, status, request);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public final ResponseEntity<ExceptionDto> handleEntityNotFoundException(EntityNotFoundException ex) {
    ex.printStackTrace();
    ExceptionDto exceptionDto = ExceptionDto.builder()
            .message(ex.getLocalizedMessage())
            .statusCode(HttpStatus.NOT_FOUND)
            .build();

    return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
  }
}
