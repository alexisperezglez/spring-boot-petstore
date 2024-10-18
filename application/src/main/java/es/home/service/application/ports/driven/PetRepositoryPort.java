package es.home.service.application.ports.driven;

import es.home.service.domain.bussines.pet.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepositoryPort {
  Optional<Pet> getById(Long id);

  Pet savePet(Pet pet);

  List<Pet> findPetsByStatus(String status);

  Pet updatePet(Pet pet);

  Optional<Pet> findPetById(Long id);

  void deletePetById(Long id);

  List<Pet> findPetsByTags(List<String> tags);
}
