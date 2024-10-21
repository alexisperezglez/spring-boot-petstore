package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.domain.bussines.pet.Pet;
import es.home.service.domain.exceptions.PetStoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindPetsByStatusUseCaseTest {

  @InjectMocks
  FindPetsByStatusUseCase useCase;

  @Mock
  PetRepositoryPort petRepositoryPort;

  @Captor
  ArgumentCaptor<String> statusCaptor;

  static String[] getStatusParam() {
    return new String[]{"", null};
  }

  @ParameterizedTest(name = "Should throw exception when status is: {0}")
  @MethodSource("getStatusParam")
  void findPetsByStatus(String status) {
    assertThrows(PetStoreException.class, () -> useCase.findPetsByStatus(status));
    BDDMockito.verifyNoInteractions(petRepositoryPort);
  }

  @Test
  @DisplayName("Should find pets by status")
  void findPetsByStatus() {
    given(petRepositoryPort.findPetsByStatus(anyString()))
        .willReturn(List.of(mock(Pet.class)));

    String status = "available";
    List<Pet> pets = useCase.findPetsByStatus(status);
    assertFalse(pets.isEmpty(), "Pets should not be empty");
    verify(petRepositoryPort, times(1))
        .findPetsByStatus(statusCaptor.capture());
    assertEquals(status, statusCaptor.getValue(), "Status should be equals");
  }
}