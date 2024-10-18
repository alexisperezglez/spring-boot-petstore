package es.home.service.domain.exceptions.errorcodes;

public interface PetStoreError {

  String getMessage();
  String getCode();
  int getStatus();
}
