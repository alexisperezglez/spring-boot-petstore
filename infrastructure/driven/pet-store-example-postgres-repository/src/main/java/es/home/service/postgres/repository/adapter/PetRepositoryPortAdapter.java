package es.home.service.postgres.repository.adapter;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.domain.bussines.enums.PetStatusEnum;
import es.home.service.domain.bussines.pet.Pet;
import es.home.service.postgres.repository.PetMOJpaRepository;
import es.home.service.postgres.repository.mapper.PetMOMapper;
import es.home.service.postgres.repository.model.pet.PetMO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class PetRepositoryPortAdapter implements PetRepositoryPort {

  private final PetMOJpaRepository jpaRepository;
  private final PetMOMapper mapper;

  @Override
  public Optional<Pet> getById(@NonNull Long id) {
    log.info("Get pet by id: {}", id);
    return jpaRepository
        .findById(id)
        .map(mapper::fromModel);
  }

  @Override
  public Pet savePet(@NonNull Pet pet) {
    PetMO entity = mapper.toModel(pet);
    entity = jpaRepository.save(entity);
    log.info("Pet saved: {}", entity);
    return mapper.fromModel(entity);
  }

  @Override
  public List<Pet> findPetsByStatus(@NonNull String status) {
    PetStatusEnum statusEnum = PetStatusEnum.fromValue(status);
    List<PetMO> entities = jpaRepository.findAllByStatus(statusEnum);
    return mapper.fromModelList(entities);
  }

  @Override
  public Pet updatePet(Pet pet) {
    return savePet(pet);
  }

  @Override
  public Optional<Pet> findPetById(@NonNull Long id) {
    return jpaRepository.findById(id).map(mapper::fromModel);
  }

  @Override
  public void deletePetById(@NonNull Long id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public List<Pet> findPetsByTags(List<String> tags) {
    log.info("Find pets by tags: {}", tags);
    tags = tags.stream().map(String::toLowerCase).toList();
    final List<PetMO> entities = jpaRepository.findAllByTags(tags);
    return mapper.fromModelList(entities);
  }

  @Override
  public Map<String, Integer> getInventory() {
    return jpaRepository.getInventory().stream()
        .collect(Collectors.toMap(
            inventoryRecord -> inventoryRecord.getStatus().getValue(),
            inventoryRecord -> inventoryRecord.getQuantity().intValue()
        ));
  }
}
