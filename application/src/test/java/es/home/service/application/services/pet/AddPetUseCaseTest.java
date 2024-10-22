package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.domain.bussines.pet.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddPetUseCaseTest {

  @InjectMocks
  AddPetUseCase useCase;

  @Mock
  PetRepositoryPort petRepositoryPort;


  @Test
  @DisplayName("Should add pet")
  void addPet() {
    BDDMockito.given(petRepositoryPort.savePet(BDDMockito.any(Pet.class)))
        .willReturn(BDDMockito.mock(Pet.class));

    Pet pet = useCase.addPet(BDDMockito.mock(Pet.class));
    assertNotNull(pet, "Pet should not be null");
    BDDMockito.verify(petRepositoryPort, BDDMockito.atLeastOnce()).savePet(BDDMockito.any(Pet.class));
  }
}