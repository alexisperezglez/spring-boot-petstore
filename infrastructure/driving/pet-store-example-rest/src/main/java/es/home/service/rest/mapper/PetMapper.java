package es.home.service.rest.mapper;

import es.home.service.domain.bussines.pet.Pet;
import es.home.service.pet_store_example_api.model.ApiResponseDTO;
import es.home.service.pet_store_example_api.model.PetDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMapper {

  PetDTO fromDomain(Pet pet);

  Pet fromDTO(PetDTO petDTO);

  List<PetDTO> fromDomainList(List<Pet> pets);

}
