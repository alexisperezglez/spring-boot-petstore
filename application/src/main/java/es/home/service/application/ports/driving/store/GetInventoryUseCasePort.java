package es.home.service.application.ports.driving.store;

import java.util.Map;

public interface GetInventoryUseCasePort {
  Map<String, Integer> getInventory();
}
