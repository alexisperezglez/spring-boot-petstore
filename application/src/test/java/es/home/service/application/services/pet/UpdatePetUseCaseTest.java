package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.domain.bussines.pet.Pet;
import es.home.service.domain.exceptions.PetStoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdatePetUseCaseTest {

  @InjectMocks
  UpdatePetUseCase useCase;

  @Mock
  PetRepositoryPort petRepositoryPort;

  @Test
  @DisplayName("Should throw exception when pet not found")
  void updatePet() {
    given(petRepositoryPort.findPetById(anyLong()))
        .willReturn(Optional.empty());

    assertThrows(PetStoreException.class, () -> useCase.updatePet(Pet.builder().id(1L).build()));
    verify(petRepositoryPort, never()).updatePet(any(Pet.class));
  }

  @Test
  void updatePetWithForm() {
  }
}