package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.application.ports.driving.pet.FindPetsByStatusUseCasePort;
import es.home.service.domain.bussines.pet.Pet;
import es.home.service.domain.exceptions.PetStoreException;
import es.home.service.domain.exceptions.errorcodes.PetErrorEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindPetsByStatusUseCase implements FindPetsByStatusUseCasePort {

  private final PetRepositoryPort petRepositoryPort;

  @Override
  public List<Pet> findPetsByStatus(@NonNull String status) {
    log.info("Find pets by status: {}", status);
    if (StringUtils.hasText(status)) {
      return petRepositoryPort.findPetsByStatus(status);
    }
    throw new PetStoreException(PetErrorEnum.INVALID_STATUS);
  }
}
