package es.home.service.application.ports.driving.pet;

import es.home.service.domain.bussines.pet.Pet;

public interface UpdatePetUseCasePort {
  Pet updatePet(Pet pet);

  void updatePetWithForm(Long petId, String name, String status);
}
