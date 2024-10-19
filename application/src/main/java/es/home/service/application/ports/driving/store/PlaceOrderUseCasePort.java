package es.home.service.application.ports.driving.store;

import es.home.service.domain.bussines.order.Order;
import org.springframework.lang.NonNull;

public interface PlaceOrderUseCasePort {
  Order placeOrder(@NonNull Order order);
}
