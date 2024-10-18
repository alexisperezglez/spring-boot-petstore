package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.application.ports.driving.pet.UpdatePetUseCasePort;
import es.home.service.domain.bussines.pet.Pet;
import es.home.service.domain.exceptions.PetStoreException;
import es.home.service.domain.exceptions.errorcodes.PetErrorEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdatePetUseCase implements UpdatePetUseCasePort {

  private final PetRepositoryPort petRepositoryPort;

  @Override
  public Pet updatePet(Pet pet) {
    log.info("Update pet: {}", pet);
    return petRepositoryPort.findPetById(pet.getId())
        .map(petRepositoryPort::updatePet)
        .orElseThrow(() -> new PetStoreException(PetErrorEnum.PET_NOT_FOUND));
  }
}
