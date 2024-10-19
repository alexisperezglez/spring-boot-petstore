package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.application.ports.driving.pet.UpdatePetUseCasePort;
import es.home.service.domain.bussines.enums.PetStatusEnum;
import es.home.service.domain.bussines.pet.Pet;
import es.home.service.domain.exceptions.PetStoreException;
import es.home.service.domain.exceptions.errorcodes.PetErrorEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdatePetUseCase implements UpdatePetUseCasePort {

  private final PetRepositoryPort petRepositoryPort;

  @Override
  public Pet updatePet(@NonNull Pet pet) {
    log.info("Update pet: {}", pet);
    return petRepositoryPort.findPetById(pet.getId())
        .map(petRepositoryPort::updatePet)
        .orElseThrow(() -> new PetStoreException(PetErrorEnum.PET_NOT_FOUND));
  }

  @Override
  public void updatePetWithForm(@NonNull Long petId, @NonNull String name, @NonNull String status) {
    log.info("Update pet with form: {}", petId);
    petRepositoryPort.findPetById(petId)
        .ifPresentOrElse(
            pet -> {
              PetStatusEnum statusEnum = PetStatusEnum.fromValue(status);
              pet.setName(name);
              pet.setStatus(statusEnum);
              petRepositoryPort.updatePet(pet);
            },
            () -> {
              throw new PetStoreException(PetErrorEnum.PET_NOT_FOUND);
            }
        );
  }
}
