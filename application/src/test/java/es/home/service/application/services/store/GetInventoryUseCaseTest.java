package es.home.service.application.services.store;

import es.home.service.application.ports.driven.PetRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetInventoryUseCaseTest {

  @InjectMocks
  GetInventoryUseCase useCase;

  @Mock
  PetRepositoryPort petRepositoryPort;

  @Test
  @DisplayName("Should get inventory")
  void getInventory() {
    given(petRepositoryPort.getInventory())
        .willReturn(Map.of(
            "available", 2,
            "pending", 3,
            "sold", 4
        ));

    Map<String, Integer> result = useCase.getInventory();
    assertEquals(2, result.get("available"), "Available should be 2");
    assertEquals(3, result.get("pending"), "Pending should be 3");
    assertEquals(4, result.get("sold"), "Sold should be 4");
  }
}