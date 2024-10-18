package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.application.ports.driving.pet.DeletePetUseCasePort;
import es.home.service.domain.exceptions.PetStoreException;
import es.home.service.domain.exceptions.errorcodes.PetErrorEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeletePetUseCase implements DeletePetUseCasePort {

  private final PetRepositoryPort petRepositoryPort;

  @Override
  public void deletePet(Long petId) {
    log.info("Delete pet: {}", petId);
    petRepositoryPort.findPetById(petId)
        .ifPresentOrElse(
            pet -> petRepositoryPort.deletePetById(pet.getId()),
            () -> {
              throw new PetStoreException(PetErrorEnum.PET_NOT_FOUND);
            }
    );
  }
}
