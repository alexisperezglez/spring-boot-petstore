package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.application.ports.driving.pet.AddPetUseCasePort;
import es.home.service.domain.bussines.pet.Pet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddPetUseCase implements AddPetUseCasePort {

  private final PetRepositoryPort petRepositoryPort;

  @Override
  public Pet addPet(Pet pet) {
    return petRepositoryPort.savePet(pet);
  }
}
