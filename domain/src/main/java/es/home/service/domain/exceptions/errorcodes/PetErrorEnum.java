package es.home.service.domain.exceptions.errorcodes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetErrorEnum implements PetStoreError {
  PET_NOT_FOUND("Pet not found", "P01", 404),
  MINIO_UPLOAD_ERROR("Minio upload error", "P02", 400);

  private final String message;
  private final String code;
  private final int status;
}
