package es.home.service.application.ports.driving.pet;

import es.home.service.domain.bussines.pet.Pet;

import java.util.Optional;

public interface GetPetByIdUseCasePort {
  Optional<Pet> getPetById(Long petId);
}
