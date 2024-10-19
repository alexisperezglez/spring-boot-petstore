package es.home.service.rest.adapter;

import es.home.service.application.ports.driving.store.GetInventoryUseCasePort;
import es.home.service.pet_store_example_api.StoreAPI;
import es.home.service.pet_store_example_api.model.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class StoreControllerAdapter implements StoreAPI {

  private final GetInventoryUseCasePort getInventoryUseCasePort;

  @Override
  public ResponseEntity<Void> deleteOrder(Long orderId) {
    log.info("Delete order: {}", orderId);
    // TODO: implement
    return StoreAPI.super.deleteOrder(orderId);
  }

  @Override
  public ResponseEntity<Map<String, Integer>> getInventory() {
    log.info("Get inventory");
    final Map<String, Integer> inventory = getInventoryUseCasePort.getInventory();
    return ResponseEntity.ok(inventory);
  }

  @Override
  public ResponseEntity<OrderDTO> getOrderById(Long orderId) {
    // TODO: implement
    return StoreAPI.super.getOrderById(orderId);
  }

  @Override
  public ResponseEntity<OrderDTO> placeOrder(@Valid OrderDTO orderDTO) {
    // TODO: implement
    return StoreAPI.super.placeOrder(orderDTO);
  }
}
