package es.home.service.domain.bussines.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetStatusEnum {

  AVAILABLE("available"),
  PENDING("pending"),
  SOLD("sold");

  private final String value;

  public static PetStatusEnum fromValue(String value) {
    for (PetStatusEnum petStatusEnum : PetStatusEnum.values()) {
      if (petStatusEnum.value.equals(value)) {
        return petStatusEnum;
      }
    }
    return null;
  }
}
