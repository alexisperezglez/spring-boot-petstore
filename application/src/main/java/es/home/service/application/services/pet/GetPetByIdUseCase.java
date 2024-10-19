package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.application.ports.driving.pet.GetPetByIdUseCasePort;
import es.home.service.domain.bussines.pet.Pet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetPetByIdUseCase implements GetPetByIdUseCasePort {

  private final PetRepositoryPort petRepositoryPort;

  @Override
  public Optional<Pet> getPetById(@NonNull Long petId) {
    log.info("Get pet by id {}", petId);
    return petRepositoryPort.getById(petId);
  }
}
