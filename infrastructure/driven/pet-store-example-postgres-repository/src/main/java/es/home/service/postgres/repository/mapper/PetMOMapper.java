package es.home.service.postgres.repository.mapper;

import es.home.service.domain.bussines.pet.Pet;
import es.home.service.postgres.repository.model.pet.PetMO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMOMapper {

  @Mapping(target = "photoUrls", source = "photoUrls", qualifiedByName = "stringArrayToStringList")
  PetMO toModel(Pet source);
  @InheritInverseConfiguration
  @Mapping(target = "photoUrls", source = "photoUrls", qualifiedByName = "stringListToStringArray")
  Pet fromModel(PetMO source);

  @Named("stringArrayToStringList")
  static String[] stringArrayToStringList(List<String> stringList) {
    if ( stringList == null ) {
      return new String[0];
    }

    return stringList.toArray(new String[0]);
  }

  @Named("stringListToStringArray")
  static List<String> stringListToStringArray(String[] stringArray) {
    if ( stringArray == null ) {
      return new ArrayList<>();
    }

    return Arrays.asList(stringArray);
  }

  List<Pet> fromModelList(List<PetMO> entities);
}
