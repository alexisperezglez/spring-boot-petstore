package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.FileStorageClientPort;
import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.application.ports.driving.pet.UploadFileUseCasePort;
import es.home.service.domain.exceptions.PetStoreException;
import es.home.service.domain.exceptions.errorcodes.PetErrorEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadFileUseCase implements UploadFileUseCasePort {

  private final PetRepositoryPort petRepositoryPort;
  private final FileStorageClientPort fileStorageClientPort;

  @Override
  @Transactional
  public void uploadFile(Long petId, String additionalMetadata, Resource body) {
    log.info("Upload file: {}", petId);
    petRepositoryPort.findPetById(petId)
            .ifPresentOrElse(
                pet -> {
                  log.info("Pet found: {}", pet);
                  String fileName = fileStorageClientPort.uploadFile(petId, additionalMetadata, body);
                  if (CollectionUtils.isEmpty(pet.getPhotoUrls())) {
                    pet.setPhotoUrls(new ArrayList<>());
                  }
                  pet.getPhotoUrls().add(fileName);
                  petRepositoryPort.updatePet(pet);
                },
                () -> {
                  throw new PetStoreException(PetErrorEnum.PET_NOT_FOUND);
                }
            );
  }
}
