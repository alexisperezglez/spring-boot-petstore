package es.home.service.domain.exceptions;


import es.home.service.domain.exceptions.errorcodes.PetStoreError;
import lombok.Getter;

@Getter
public class PetStoreException extends RuntimeException {

  private String code;
  private int status;

  public PetStoreException(PetStoreError error) {
    super(error.getMessage());
    this.status = error.getStatus();
    this.code = error.getCode();
  }
}
