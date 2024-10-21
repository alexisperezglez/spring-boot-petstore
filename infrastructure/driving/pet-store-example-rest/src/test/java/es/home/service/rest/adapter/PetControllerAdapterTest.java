package es.home.service.rest.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.home.service.application.ports.driving.pet.*;
import es.home.service.domain.bussines.enums.PetStatusEnum;
import es.home.service.domain.bussines.pet.Category;
import es.home.service.domain.bussines.pet.Pet;
import es.home.service.domain.bussines.pet.Tag;
import es.home.service.pet_store_example_api.model.CategoryDTO;
import es.home.service.pet_store_example_api.model.PetDTO;
import es.home.service.pet_store_example_api.model.TagDTO;
import es.home.service.rest.mapper.PetMapper;
import es.home.service.rest.mapper.PetMapperImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@SpringBootConfiguration
class PetControllerAdapterTest {

  public static final String PET_NAME = "pet-01";
  public static final String CATEGORY_NAME = "category-01";
  public static final String TAG_NAME = "tag-01";
  public static final String STATUS_AVAILABLE = "available";

  MockMvc mockMvc;

  @Mock
  GetPetByIdUseCasePort getPetByIdUseCasePort;
  @Mock
  AddPetUseCasePort addPetUseCasePort;
  @Mock
  FindPetsByStatusUseCasePort findPetsByStatusUseCasePort;
  @Mock
  UpdatePetUseCasePort updatePetUseCasePort;
  @Mock
  DeletePetUseCasePort deletePetUseCasePort;
  @Mock
  UploadFileUseCasePort uploadFileUseCasePort;
  @Mock
  FindPetsByTagsUseCasePort findPetsByTagsUseCasePort;

  @Mock
  PetMapper mapper;

  private Pet pet;
  private PetDTO petDTO;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(new PetControllerAdapter(
        getPetByIdUseCasePort,
        addPetUseCasePort,
        findPetsByStatusUseCasePort,
        updatePetUseCasePort,
        deletePetUseCasePort,
        uploadFileUseCasePort,
        findPetsByTagsUseCasePort,
        mapper
    )).build();

    pet = Pet.builder()
        .id(1L)
        .name(PET_NAME)
        .category(Category.builder().id(1L).name(CATEGORY_NAME).build())
        .tags(List.of(Tag.builder().id(1L).name(TAG_NAME).build()))
        .status(PetStatusEnum.AVAILABLE)
        .photoUrls(Collections.emptyList())
        .build();

    petDTO = PetDTO.builder()
        .id(1L)
        .name(PET_NAME)
        .category(CategoryDTO.builder().id(1L).name(CATEGORY_NAME).build())
        .tags(List.of(TagDTO.builder().id(1L).name(TAG_NAME).build()))
        .status(PetDTO.StatusEnum.AVAILABLE)
        .photoUrls(Collections.emptyList())
        .build();
  }

  @Test
  @SneakyThrows
  @DisplayName("Should add pet")
  void addPet() {

    given(mapper.fromDTO(ArgumentMatchers.any(PetDTO.class)))
        .willAnswer(inv -> new PetMapperImpl().fromDTO(petDTO));
    given(addPetUseCasePort.addPet(ArgumentMatchers.any(Pet.class)))
        .willReturn( new PetMapperImpl().fromDTO(petDTO));
    given(mapper.fromDomain(ArgumentMatchers.any(Pet.class)))
        .willAnswer(inv -> new PetMapperImpl().fromDomain(inv.getArgument(0)));

    ObjectMapper objectMapper = new ObjectMapper();
    String content = objectMapper.writeValueAsString(petDTO);

    mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/pet")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
            .characterEncoding(Charset.defaultCharset())
        .content(content)
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", equalTo(1)))
        .andExpect(jsonPath("$.name", equalTo(PET_NAME)))
        .andExpect(jsonPath("$.status", equalTo(PetDTO.StatusEnum.AVAILABLE.getValue())))
        .andExpect(jsonPath("$.photoUrls", empty()))
        .andExpect(jsonPath("$.category.id", equalTo(1)))
        .andExpect(jsonPath("$.category.name", equalTo(CATEGORY_NAME)))
        .andExpect(jsonPath("$.tags", not(empty())));

    verifyNoInteractions(
        getPetByIdUseCasePort,
        findPetsByStatusUseCasePort,
        updatePetUseCasePort,
        deletePetUseCasePort,
        uploadFileUseCasePort,
        findPetsByTagsUseCasePort);
  }

  @Test
  @SneakyThrows
  @DisplayName("Should get pet by id")
  void getPetById() {

    given(getPetByIdUseCasePort.getPetById(ArgumentMatchers.anyLong()))
        .willReturn(Optional.of(pet));
    given(mapper.fromDomain(ArgumentMatchers.any(Pet.class)))
        .willAnswer(inv -> new PetMapperImpl().fromDomain(inv.getArgument(0)));

    mockMvc.perform(MockMvcRequestBuilders.get("/pet/{petId}", 1)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .characterEncoding(Charset.defaultCharset())
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", equalTo(1)))
        .andExpect(jsonPath("$.name", equalTo(PET_NAME)))
        .andExpect(jsonPath("$.status", equalTo(PetDTO.StatusEnum.AVAILABLE.getValue())))
        .andExpect(jsonPath("$.photoUrls", empty()))
        .andExpect(jsonPath("$.category.id", equalTo(1)))
        .andExpect(jsonPath("$.category.name", equalTo(CATEGORY_NAME)))
        .andExpect(jsonPath("$.tags", not(empty())));

    verifyNoInteractions(
        addPetUseCasePort,
        findPetsByStatusUseCasePort,
        updatePetUseCasePort,
        deletePetUseCasePort,
        uploadFileUseCasePort,
        findPetsByTagsUseCasePort);
  }

  @Test
  @SneakyThrows
  @DisplayName("Should find pets by status")
  void findPetsByStatus() {

    given(findPetsByStatusUseCasePort.findPetsByStatus(anyString()))
        .willReturn(List.of(pet));
    given(mapper.fromDomainList(ArgumentMatchers.anyList()))
        .willAnswer(inv -> new PetMapperImpl().fromDomainList(inv.getArgument(0)));

    mockMvc.perform(MockMvcRequestBuilders.get("/pet/findByStatus")
            .queryParam("status", STATUS_AVAILABLE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .characterEncoding(Charset.defaultCharset())
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].id", equalTo(1)))
        .andExpect(jsonPath("$.[0].name", equalTo(PET_NAME)))
        .andExpect(jsonPath("$.[0].status", equalTo(PetDTO.StatusEnum.AVAILABLE.getValue())))
        .andExpect(jsonPath("$.[0].photoUrls", empty()))
        .andExpect(jsonPath("$.[0].category.id", equalTo(1)))
        .andExpect(jsonPath("$.[0].category.name", equalTo(CATEGORY_NAME)))
        .andExpect(jsonPath("$.[0].tags", not(empty())));

    verifyNoInteractions(
        addPetUseCasePort,
        getPetByIdUseCasePort,
        updatePetUseCasePort,
        deletePetUseCasePort,
        uploadFileUseCasePort,
        findPetsByTagsUseCasePort);
  }

  @Test
  @SneakyThrows
  @DisplayName("Should update pet")
  void updatePet() {

    given(mapper.fromDTO(ArgumentMatchers.any(PetDTO.class)))
        .willAnswer(inv -> new PetMapperImpl().fromDTO(petDTO));
    given(updatePetUseCasePort.updatePet(ArgumentMatchers.any(Pet.class)))
        .willReturn( new PetMapperImpl().fromDTO(petDTO));
    given(mapper.fromDomain(ArgumentMatchers.any(Pet.class)))
        .willAnswer(inv -> new PetMapperImpl().fromDomain(inv.getArgument(0)));

    ObjectMapper objectMapper = new ObjectMapper();
    String content = objectMapper.writeValueAsString(petDTO);

    mockMvc.perform(MockMvcRequestBuilders.put("/pet")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .characterEncoding(Charset.defaultCharset())
            .content(content)
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", equalTo(1)))
        .andExpect(jsonPath("$.name", equalTo(PET_NAME)))
        .andExpect(jsonPath("$.status", equalTo(PetDTO.StatusEnum.AVAILABLE.getValue())))
        .andExpect(jsonPath("$.photoUrls", empty()))
        .andExpect(jsonPath("$.category.id", equalTo(1)))
        .andExpect(jsonPath("$.category.name", equalTo(CATEGORY_NAME)))
        .andExpect(jsonPath("$.tags", not(empty())));

    verifyNoInteractions(
        addPetUseCasePort,
        getPetByIdUseCasePort,
        findPetsByStatusUseCasePort,
        deletePetUseCasePort,
        uploadFileUseCasePort,
        findPetsByTagsUseCasePort);
  }

  @Test
  @SneakyThrows
  @DisplayName("Should delete pet")
  void deletePet() {

    willDoNothing().given(deletePetUseCasePort).deletePet(ArgumentMatchers.anyLong());

    mockMvc.perform(MockMvcRequestBuilders.delete("/pet/{petId}", 1)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .characterEncoding(Charset.defaultCharset())
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent());

    verifyNoInteractions(
        getPetByIdUseCasePort,
        addPetUseCasePort,
        findPetsByStatusUseCasePort,
        updatePetUseCasePort,
        uploadFileUseCasePort,
        findPetsByTagsUseCasePort);
  }

  @Test
  @SneakyThrows
  @DisplayName("Should upload file")
  void uploadFile() {
    willDoNothing().given(uploadFileUseCasePort).uploadFile(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString(), ArgumentMatchers.any());
    mockMvc.perform(MockMvcRequestBuilders.post("/pet/{petId}/uploadImage", 1)
            .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)
            .content(new byte[0])
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(200)))
        .andExpect(jsonPath("$.type", equalTo("file")))
        .andExpect(jsonPath("$.message", equalTo("File uploaded")));

    verifyNoInteractions(
        getPetByIdUseCasePort,
        addPetUseCasePort,
        findPetsByStatusUseCasePort,
        updatePetUseCasePort,
        deletePetUseCasePort,
        findPetsByTagsUseCasePort);
  }

  @Test
  @SneakyThrows
  @DisplayName("Should find pets by tags")
  void findPetsByTags() {

    given(findPetsByTagsUseCasePort.findPetsByTags(ArgumentMatchers.anyList()))
        .willReturn(List.of(pet));
    given(mapper.fromDomainList(ArgumentMatchers.anyList()))
        .willAnswer(inv -> new PetMapperImpl().fromDomainList(inv.getArgument(0)));

    mockMvc.perform(MockMvcRequestBuilders.get("/pet/findByTags")
            .queryParam("tags", TAG_NAME)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .characterEncoding(Charset.defaultCharset())
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].id", equalTo(1)))
        .andExpect(jsonPath("$.[0].name", equalTo(PET_NAME)))
        .andExpect(jsonPath("$.[0].status", equalTo(PetDTO.StatusEnum.AVAILABLE.getValue())))
        .andExpect(jsonPath("$.[0].photoUrls", empty()))
        .andExpect(jsonPath("$.[0].category.id", equalTo(1)))
        .andExpect(jsonPath("$.[0].category.name", equalTo(CATEGORY_NAME)))
        .andExpect(jsonPath("$.[0].tags", not(empty())));

    verifyNoInteractions(
        addPetUseCasePort,
        getPetByIdUseCasePort,
        updatePetUseCasePort,
        deletePetUseCasePort,
        uploadFileUseCasePort,
        findPetsByStatusUseCasePort);
  }

  @Test
  @SneakyThrows
  @DisplayName("Should update pet with form")
  void updatePetWithForm() {

    willDoNothing().given(updatePetUseCasePort)
        .updatePetWithForm(ArgumentMatchers.anyLong(), anyString(), anyString());

    mockMvc.perform(MockMvcRequestBuilders.post("/pet/{petId}", 1)
            .queryParam("status", STATUS_AVAILABLE)
            .queryParam("name", "name-01")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .characterEncoding(Charset.defaultCharset())
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent());

    verifyNoInteractions(
        addPetUseCasePort,
        getPetByIdUseCasePort,
        findPetsByTagsUseCasePort,
        deletePetUseCasePort,
        uploadFileUseCasePort,
        findPetsByStatusUseCasePort);
  }
}