package es.home.service.application.ports.driving.pet;

import es.home.service.domain.bussines.pet.Pet;

public interface AddPetUseCasePort {
  Pet addPet(Pet pet);
}
