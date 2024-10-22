package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.application.ports.driving.pet.FindPetsByTagsUseCasePort;
import es.home.service.domain.bussines.pet.Pet;
import es.home.service.domain.exceptions.PetStoreException;
import es.home.service.domain.exceptions.errorcodes.PetErrorEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindPetsByTagsUseCase implements FindPetsByTagsUseCasePort {

  private final PetRepositoryPort petRepositoryPort;

  @Override
  public List<Pet> findPetsByTags(List<String> tags) {
    log.info("Find pets by tags: {}", tags);
    if (CollectionUtils.isEmpty(tags)) {
      throw new PetStoreException(PetErrorEnum.INVALID_TAGS);
    }
    return petRepositoryPort.findPetsByTags(tags);
  }
}
