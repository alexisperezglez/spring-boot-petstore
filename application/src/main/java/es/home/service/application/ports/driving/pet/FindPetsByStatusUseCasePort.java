package es.home.service.application.ports.driving.pet;

import es.home.service.domain.bussines.pet.Pet;
import org.springframework.lang.NonNull;

import java.util.List;

public interface FindPetsByStatusUseCasePort {
  List<Pet> findPetsByStatus(@NonNull String status);
}
