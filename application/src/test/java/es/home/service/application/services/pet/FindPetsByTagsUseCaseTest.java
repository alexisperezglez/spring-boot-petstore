package es.home.service.application.services.pet;

import es.home.service.application.ports.driven.PetRepositoryPort;
import es.home.service.domain.bussines.pet.Pet;
import es.home.service.domain.exceptions.PetStoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FindPetsByTagsUseCaseTest {

  @InjectMocks
  FindPetsByTagsUseCase useCase;

  @Mock
  PetRepositoryPort petRepositoryPort;

  static Stream<Arguments> getTagsParam() {
    return Stream.of(
        Arguments.of(Collections.emptyList()),
        null
    );
  }

  @ParameterizedTest(name = "Should throw exception when tags is: {0}")
  @MethodSource("getTagsParam")
  void findPetsByTags(List<String> tags) {
    assertThrows(PetStoreException.class, () -> useCase.findPetsByTags(tags));
    BDDMockito.verifyNoInteractions(petRepositoryPort);
  }

  @Test
  @DisplayName("Should return list of pets")
  void findPetsByTags() {
    given(petRepositoryPort.findPetsByTags(anyList()))
        .willReturn(Collections.singletonList(BDDMockito.mock(Pet.class)));

    List<Pet> pets = useCase.findPetsByTags(Collections.singletonList("available"));
    assertNotNull(pets, "Pets should not be null");
    assertFalse(pets.isEmpty(), "Pets should not be empty");
    verify(petRepositoryPort, atLeastOnce()).findPetsByTags(anyList());
  }
}