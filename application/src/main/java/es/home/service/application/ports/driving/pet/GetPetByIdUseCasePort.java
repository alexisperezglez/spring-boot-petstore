package es.home.service.application.ports.driving.pet;

import es.home.service.domain.bussines.pet.Pet;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface GetPetByIdUseCasePort {
  Optional<Pet> getPetById(@NonNull Long petId);
}
