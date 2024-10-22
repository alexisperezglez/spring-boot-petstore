package es.home.service.domain.bussines.order;

import es.home.service.domain.bussines.enums.OrderStatusEnum;
import es.home.service.domain.bussines.pet.Pet;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order {
  private Long id;
  private Pet pet;
  private Integer quantity;
  private LocalDateTime shipDate;
  private OrderStatusEnum status;
  private Boolean completed;
}
