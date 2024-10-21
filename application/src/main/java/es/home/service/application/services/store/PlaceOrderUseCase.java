package es.home.service.application.services.store;

import es.home.service.application.ports.driven.OrderRepositoryPort;
import es.home.service.application.ports.driving.store.PlaceOrderUseCasePort;
import es.home.service.domain.bussines.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaceOrderUseCase implements PlaceOrderUseCasePort {

  private final OrderRepositoryPort orderRepositoryPort;

  @Override
  public Order placeOrder(@NonNull Order order) {
    log.info("Place order: {}", order);
    return orderRepositoryPort.save(order);
  }
}
