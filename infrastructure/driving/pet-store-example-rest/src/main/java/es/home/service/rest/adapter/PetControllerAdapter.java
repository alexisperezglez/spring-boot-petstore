package es.home.service.rest.adapter;

import es.home.service.application.ports.driving.pet.*;
import es.home.service.domain.bussines.pet.Pet;
import es.home.service.pet_store_example_api.PetAPI;
import es.home.service.pet_store_example_api.model.ApiResponseDTO;
import es.home.service.pet_store_example_api.model.PetDTO;
import es.home.service.rest.mapper.PetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class PetControllerAdapter implements PetAPI {

  private final GetPetByIdUseCasePort getPetByIdUseCasePort;
  private final AddPetUseCasePort addPetUseCasePort;
  private final FindPetsByStatusUseCasePort findPetsByStatusUseCasePort;
  private final UpdatePetUseCasePort updatePetUseCasePort;
  private final DeletePetUseCasePort deletePetUseCasePort;
  private final UploadFileUseCasePort uploadFileUseCasePort;
  private final FindPetsByTagsUseCasePort findPetsByTagsUseCasePort;

  private final PetMapper mapper;

  @Override
  public ResponseEntity<PetDTO> addPet(@Valid PetDTO petDTO) {
    log.info("Add pet: {}", petDTO);
    Pet pet = mapper.fromDTO(petDTO);
    pet = addPetUseCasePort.addPet(pet);
    return ResponseEntity.ok(mapper.fromDomain(pet));
  }
  @Override
  public ResponseEntity<PetDTO> getPetById(Long petId) {
    log.info("Get pet by id: {}", petId);
    return getPetByIdUseCasePort.getPetById(petId)
        .map(pet -> {
          log.info("Pet found: {}", pet);
          return ResponseEntity.ok(mapper.fromDomain(pet));
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @Override
  public ResponseEntity<List<PetDTO>> findPetsByStatus(@Valid String status) {
    log.info("Find pets by status: {}", status);
    final List<Pet> pets = findPetsByStatusUseCasePort.findPetsByStatus(status);
    return ResponseEntity.ok(mapper.fromDomainList(pets));
  }

  @Override
  public ResponseEntity<PetDTO> updatePet(@Valid PetDTO petDTO) {
    log.info("Update pet: {}", petDTO);
    Pet pet = mapper.fromDTO(petDTO);
    pet = updatePetUseCasePort.updatePet(pet);
    return ResponseEntity.ok(mapper.fromDomain(pet));
  }

  @Override
  public ResponseEntity<Void> deletePet(Long petId, String apiKey) {
    log.info("Delete pet: {}", petId);
    log.info("API key: {}", apiKey);
    deletePetUseCasePort.deletePet(petId);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<ApiResponseDTO> uploadFile(Long petId, @Valid String additionalMetadata, @Valid Resource body) {
    log.info("Upload file: {}", petId);
    log.info("Additional metadata: {}", additionalMetadata);
    uploadFileUseCasePort.uploadFile(petId, additionalMetadata, body);
    return ResponseEntity.ok(ApiResponseDTO.builder()
            .code(200)
            .message("File uploaded")
            .type("file")
        .build());
  }

  @Override
  public ResponseEntity<List<PetDTO>> findPetsByTags(@Valid List<String> tags) {
    log.info("Find pets by tags: {}", tags);
    final List<Pet> pets = findPetsByTagsUseCasePort.findPetsByTags(tags);
    return ResponseEntity.ok(mapper.fromDomainList(pets));
  }
}
