package es.home.service.application.services.store;

import es.home.service.application.ports.driven.OrderRepositoryPort;
import es.home.service.domain.bussines.order.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceOrderUseCaseTest {

  @InjectMocks
  private PlaceOrderUseCase placeOrderUseCase;

  @Mock
  OrderRepositoryPort orderRepositoryPort;

  @Test
  @DisplayName("Should save order")
  void placeOrder() {
    given(orderRepositoryPort.save(any(Order.class)))
        .willReturn(mock(Order.class));

    Order order = placeOrderUseCase.placeOrder(mock(Order.class));
    assertNotNull(order, "Order should not be null");
    verify(orderRepositoryPort, atLeastOnce()).save(any(Order.class));
  }
}