package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.domain.bussines.pet.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GetPetByIdUseCaseTest {

  @InjectMocks
  GetPetByIdUseCase useCase;

  @Mock
  PetRepositoryPort petRepositoryPort;

  @Captor
  ArgumentCaptor<Long> idCaptor;

  @Test
  @DisplayName("Should get pet by id")
  void getPetById() {
    given(petRepositoryPort.getById(anyLong()))
        .willReturn(Optional.of(mock(Pet.class)));

    Optional<Pet> pet = useCase.getPetById(1L);
    assertTrue(pet.isPresent(), "Pet should not be null");
    BDDMockito.verify(petRepositoryPort, BDDMockito.atLeastOnce()).getById(idCaptor.capture());
    assertEquals(1L, idCaptor.getValue(), "Pet id should be 1");
  }
}