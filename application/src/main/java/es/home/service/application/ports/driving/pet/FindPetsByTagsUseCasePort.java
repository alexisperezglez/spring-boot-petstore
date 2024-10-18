package es.home.service.application.ports.driving.pet;

import es.home.service.domain.bussines.pet.Pet;

import java.util.List;

public interface FindPetsByTagsUseCasePort {
  List<Pet> findPetsByTags(List<String> tags);
}
