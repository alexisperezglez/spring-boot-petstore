package es.home.service.domain.bussines.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatusEnum {

  PLACED("placed"),
  APPROVED("approved"),
  DELIVERED("delivered");

  private final String value;

  public static OrderStatusEnum fromValue(String value) {
    for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
      if (orderStatusEnum.value.equals(value)) {
        return orderStatusEnum;
      }
    }
    return null;
  }
}
