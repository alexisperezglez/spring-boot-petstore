package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.domain.bussines.enums.PetStatusEnum;
import es.home.service.domain.bussines.pet.Pet;
import es.home.service.domain.exceptions.PetStoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePetUseCaseTest {

  @InjectMocks
  UpdatePetUseCase useCase;

  @Mock
  PetRepositoryPort petRepositoryPort;

  @Captor
  ArgumentCaptor<Pet> petCaptor;

  @Test
  @DisplayName("Should throw exception when pet not found")
  void updatePetThrowException() {
    given(petRepositoryPort.findPetById(anyLong()))
        .willReturn(Optional.empty());

    Pet pet = Pet.builder().id(1L).build();
    assertThrows(PetStoreException.class, () -> useCase.updatePet(pet));
    verify(petRepositoryPort, never()).updatePet(any(Pet.class));
  }

  @Test
  @DisplayName("Should update pet")
  void updatePet() {
    Pet pet = Pet.builder().id(1L).build();
    given(petRepositoryPort.findPetById(anyLong()))
        .willReturn(Optional.of(pet));
    given(petRepositoryPort.updatePet(any(Pet.class)))
        .willReturn(pet);

    Pet updatedPet = useCase.updatePet(pet);

    assertNotNull(updatedPet, "Pet should not be null");
    verify(petRepositoryPort, atLeastOnce()).updatePet(any(Pet.class));
  }

  @Test
  @DisplayName("Should throw exception when pet not found")
  void updatePetWithFormThrowException() {
    given(petRepositoryPort.findPetById(anyLong()))
        .willReturn(Optional.empty());
    assertThrows(PetStoreException.class, () -> useCase.updatePetWithForm(1L, "name", "available"));
    verify(petRepositoryPort, never()).updatePet(any(Pet.class));
  }

  @Test
  @DisplayName("Should throw exception when status is invalid")
  void updatePetWithFormInvalidStatusThrowException() {
    given(petRepositoryPort.findPetById(anyLong()))
        .willReturn(Optional.of(mock(Pet.class)));
    assertThrows(PetStoreException.class, () -> useCase.updatePetWithForm(1L, "name", "anyStatus"));
    verify(petRepositoryPort, never()).updatePet(any(Pet.class));
  }

  @Test
  @DisplayName("Should update pet with form")
  void updatePetWithForm() {
    Pet pet = Pet.builder().id(1L).build();
    given(petRepositoryPort.findPetById(anyLong()))
        .willReturn(Optional.of(pet));
    given(petRepositoryPort.updatePet(any(Pet.class)))
        .willReturn(pet);

    assertDoesNotThrow(() -> useCase.updatePetWithForm(1L, "name", "available"));

    verify(petRepositoryPort, atLeastOnce()).updatePet(petCaptor.capture());
    assertEquals(1L, petCaptor.getValue().getId(), "Pet id should be 1");
    assertEquals("name", petCaptor.getValue().getName(), "Pet name should be name");
    assertEquals(PetStatusEnum.AVAILABLE, petCaptor.getValue().getStatus(), "Pet status should be available");

  }
}