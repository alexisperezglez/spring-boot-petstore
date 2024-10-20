package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.FileStorageClientPort;
import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.domain.bussines.pet.Pet;
import es.home.service.domain.exceptions.PetStoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UploadFileUseCaseTest {

  @InjectMocks
  UploadFileUseCase useCase;

  @Mock
  PetRepositoryPort petRepositoryPort;
  @Mock
  FileStorageClientPort fileStorageClientPort;

  @Captor
  ArgumentCaptor<Pet> petCaptor;

  @Test
  @DisplayName("Should throw exception when pet not found")
  void uploadFileThrowException() {
    Long petId = 1L;
    String additionalMetadata = "file";
    Resource body = mock(Resource.class);
    given(petRepositoryPort.findPetById(anyLong()))
        .willReturn(Optional.empty());
    assertThrows(PetStoreException.class, () -> useCase.uploadFile(petId, additionalMetadata, body));
    verifyNoInteractions(fileStorageClientPort);
    verify(petRepositoryPort, never()).updatePet(any(Pet.class));
  }

  static Stream<Arguments> provideArguments() {
    ArrayList<String> photoUrls = new ArrayList<String>();
    photoUrls.add("http://path/to/old_file1");
    photoUrls.add("http://path/to/old_file2");
    return Stream.of(
        Arguments.of(Collections.emptyList()),
        null,
        Arguments.of(photoUrls)
    );
  }

  @ParameterizedTest(name = "Should upload file: {0}")
  @MethodSource("provideArguments")
  void uploadFile(List<String> photoUrls) {
    Pet pet = Pet.builder().id(1L).photoUrls(photoUrls).build();
    given(petRepositoryPort.findPetById(anyLong()))
        .willReturn(Optional.of(pet));
    given(fileStorageClientPort.uploadFile(anyLong(), anyString(), any(Resource.class)))
        .willReturn("http://path/to/file");
    given(petRepositoryPort.updatePet(any(Pet.class)))
        .willReturn(pet);
    assertDoesNotThrow(() -> useCase.uploadFile(1L, "file", mock(Resource.class)));
    verify(petRepositoryPort, atLeastOnce()).updatePet(petCaptor.capture());
    assertEquals(1L, petCaptor.getValue().getId(), "Pet id should be 1");
    assertTrue(petCaptor.getValue().getPhotoUrls().contains("http://path/to/file"), "Pet photoUrls should contain http://path/to/file");
  }
}