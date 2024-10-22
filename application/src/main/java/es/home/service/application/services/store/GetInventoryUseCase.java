package es.home.service.application.services.store;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.application.ports.driving.store.GetInventoryUseCasePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetInventoryUseCase implements GetInventoryUseCasePort {

  private final PetRepositoryPort petRepositoryPort;

  @Override
  public Map<String, Integer> getInventory() {
    log.info("Get store inventory");
    return petRepositoryPort.getInventory();
  }
}
