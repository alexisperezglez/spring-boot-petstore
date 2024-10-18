package es.home.service.rest.handler;

import es.home.service.domain.exceptions.PetStoreException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandlerController {

  @ExceptionHandler(PetStoreException.class)
  public ResponseEntity<ProblemDetail> handlePetStoreExceptions(PetStoreException ex) {
    log.error("Error: ", ex);
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(ex.getStatus()), ex.getMessage());
    return ResponseEntity.status(ex.getStatus()).body(problem);
  }
}
