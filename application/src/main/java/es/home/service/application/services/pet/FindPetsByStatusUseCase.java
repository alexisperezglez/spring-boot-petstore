package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.application.ports.driving.pet.FindPetsByStatusUseCasePort;
import es.home.service.domain.bussines.pet.Pet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindPetsByStatusUseCase implements FindPetsByStatusUseCasePort {

  private final PetRepositoryPort petRepositoryPort;

  @Override
  public List<Pet> findPetsByStatus(String status) {
    log.info("Find pets by status: {}", status);
    return petRepositoryPort.findPetsByStatus(status);
  }
}
