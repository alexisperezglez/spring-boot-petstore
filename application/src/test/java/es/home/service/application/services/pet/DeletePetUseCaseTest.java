package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.domain.bussines.pet.Pet;
import es.home.service.domain.exceptions.PetStoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePetUseCaseTest {

  @InjectMocks
  DeletePetUseCase useCase;

  @Mock
  PetRepositoryPort petRepositoryPort;

  @Test
  @DisplayName("Should throw exception when pet not found")
  void deletePetNotFound() {
    given(petRepositoryPort.findPetById(anyLong()))
        .willReturn(Optional.empty());

    assertThrows(PetStoreException.class, () -> useCase.deletePet(1L));
    verify(petRepositoryPort, never()).deletePetById(anyLong());
  }

  @Test
  @DisplayName("Should delete pet")
  void deletePet() {
    given(petRepositoryPort.findPetById(anyLong()))
        .willReturn(Optional.of(Pet.builder().id(1L).build()));
    willDoNothing().given(petRepositoryPort).deletePetById(anyLong());

    assertDoesNotThrow(() -> useCase.deletePet(1L));
    verify(petRepositoryPort, atLeastOnce()).deletePetById(anyLong());
  }
}