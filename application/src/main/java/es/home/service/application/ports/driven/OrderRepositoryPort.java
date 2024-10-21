package es.home.service.application.ports.driven;

import es.home.service.domain.bussines.order.Order;
import org.springframework.lang.NonNull;

public interface OrderRepositoryPort {
  Order save(@NonNull Order order);
}
