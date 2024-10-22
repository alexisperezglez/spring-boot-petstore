package es.home.service.postgres.repository.adapter;

import es.home.service.domain.bussines.enums.PetStatusEnum;
import es.home.service.domain.bussines.pet.Category;
import es.home.service.domain.bussines.pet.Pet;
import es.home.service.domain.bussines.pet.Tag;
import es.home.service.domain.exceptions.PetStoreException;
import es.home.service.postgres.repository.PetMOJpaRepository;
import es.home.service.postgres.repository.mapper.PetMOMapperImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(bootstrapMode = BootstrapMode.DEFAULT, properties = {
    "spring.main.allow-bean-definition-overriding=true",
    "spring.flyway.enabled=false",
    "spring.flyway.default-schema=public",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=password",
    "spring.jpa.ddl-auto=create-drop",
    "spring.jpa.show-sql=true"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ContextConfiguration(classes = {PetRepositoryPortAdapter.class, PetMOJpaRepository.class, PetMOMapperImpl.class})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "es.home.service.postgres.repository")
@EntityScan(basePackages = "es.home.service.postgres.repository.model")
class PetRepositoryPortAdapterTest {

  @Autowired
  PetRepositoryPortAdapter adapter;
  @Autowired
  PetMOJpaRepository jpaRepository;
  @Autowired
  EntityManager entityManager;

  private Pet pet;

  @BeforeEach
  void setUp() {
    entityManager.createNativeQuery("insert into categories (id, name) VALUES (1, 'category')")
        .executeUpdate();
    entityManager.createNativeQuery("insert into pets (id, name, status, category_id, photo_urls) values (1, 'name', 'AVAILABLE', 1, array [])")
        .executeUpdate();
    entityManager.createNativeQuery("insert into pets (id, name, status, category_id, photo_urls) values (2, 'name2', 'PENDING', 1, array [])")
        .executeUpdate();
    entityManager.createNativeQuery("insert into pets (id, name, status, category_id, photo_urls) values (3, 'name3', 'SOLD', 1, array [])")
        .executeUpdate();
    entityManager.createNativeQuery("""
        insert into tags (id, name)
            VALUES (1, 'tag01'), (2, 'tag02'), (3, 'tag03')
    """)
        .executeUpdate();
    entityManager.createNativeQuery("""
            insert into pets_tags (pet_id, tag_id)
                VALUES (1, 1), (1, 2), (1, 3),
                       (2, 1), (2, 2), (2, 3),
                       (3, 1), (3, 2), (3, 3)
        """)
        .executeUpdate();
    entityManager.flush();

    pet = Pet.builder()
        .id(1L)
        .name("name")
        .category(Category.builder().id(1L).name("category").build())
        .photoUrls(Collections.emptyList())
        .status(PetStatusEnum.AVAILABLE)
        .tags(List.of(Tag.builder().id(1L).name("tag").build()))
        .build();
  }

  @AfterEach
  void tearDown() {
    entityManager.createNativeQuery("delete from pets_tags where pet_id is not null or tag_id is not null").executeUpdate();
    entityManager.createNativeQuery("delete from pets where id is not null").executeUpdate();
    entityManager.createNativeQuery("delete from categories where id is not null").executeUpdate();
    entityManager.createNativeQuery("delete from tags where id is not null").executeUpdate();
    entityManager.flush();
  }

  @Test
  @DisplayName("Should get pet by id")
  void getById() {
    Optional<Pet> foundPet = adapter.getById(1L);
    assertTrue(foundPet.isPresent(), "Pet should not be null");
  }


  @Test
  @DisplayName("Shouldn't find pet by id")
  void getByIdNotFound() {
    jpaRepository.deleteAll();
    Optional<Pet> foundPet = adapter.getById(1L);
    assertFalse(foundPet.isPresent(), "Pet should be null");
  }

  @Test
  @DisplayName("Should save pet")
  @Sql(statements = {
      "delete from pets where id is not null",
      "delete from categories where id is not null",
      "delete from tags where id is not null",
  }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  void savePet() {

    Pet savedPet = adapter.savePet(pet);
    assertNotNull(savedPet, "Pet should not be null");
    assertEquals(pet.getId(), savedPet.getId(), "Id should be 1");
    assertEquals(pet.getName(), savedPet.getName(), "Name should be name");
    assertEquals(pet.getStatus(), savedPet.getStatus(), "Status should be available");
    assertEquals(pet.getTags().getFirst().getName(), savedPet.getTags().getFirst().getName(), "Tag name should be \"tag\"");
    assertEquals(pet.getCategory().getId(), savedPet.getCategory().getId(), "Category id should be 1");
    assertEquals(pet.getCategory().getName(), savedPet.getCategory().getName(), "Category name should be \"category\"");
  }

  @ParameterizedTest(name = "Should find pets by status: {0}")
  @ValueSource(strings = {"available", "pending", "sold"})
  void findPetsByStatus(String status) {
    List<Pet> pets = adapter.findPetsByStatus(status);
    assertFalse(pets.isEmpty());
    assertTrue(pets.getFirst().getStatus().getValue().equalsIgnoreCase(status));
  }

  @Test
  @DisplayName("Should update pet")
  void updatePet() {
    Pet updatedPet = adapter.updatePet(pet);
    assertNotNull(updatedPet, "Pet should not be null");
    assertEquals(updatedPet.getName(), pet.getName(), "Name should be name");
    assertEquals(updatedPet.getStatus(), pet.getStatus(), "Status should be pending");
  }

  @Test
  @DisplayName("Should throw exception on update pet")
  void updatePetNotFound() {
    jpaRepository.deleteAll();

    assertThrows(PetStoreException.class, () -> adapter.updatePet(pet), "Should Throws exception on update pet");
  }

  @Test
  @DisplayName("Should find pet by id")
  void findById() {
    Optional<Pet> foundPet = adapter.findPetById(1L);
    assertTrue(foundPet.isPresent(), "Pet should not be null");
  }


  @Test
  @DisplayName("Shouldn't find pet by id")
  void findByIdNotFound() {
    jpaRepository.deleteAll();
    Optional<Pet> foundPet = adapter.findPetById(1L);
    assertFalse(foundPet.isPresent(), "Pet should be null");
  }

  @Test
  @DisplayName("Should delete pet by id")
  void deletePetById() {

    assertDoesNotThrow(() -> adapter.deletePetById(1L));
    Optional<Pet> foundPet = adapter.findPetById(1L);
    assertFalse(foundPet.isPresent(), "Shouldn't find pet");
  }

  static Stream<Arguments> getTags() {
    return Stream.of(
        Arguments.of(List.of("tag01", "tag02", "tag03")),
        Arguments.of(List.of("tag01", "tag02")),
        Arguments.of(List.of("tag01"))
    );
  }

  @ParameterizedTest(name = "Should find pets by tags: {0}")
  @MethodSource("getTags")
  void findPetsByTags(List<String> tags) {
    List<Pet> pets = adapter.findPetsByTags(tags);
    assertFalse(pets.isEmpty());
  }

  @Test
  @DisplayName("Should get inventory")
  void getInventory() {
    Map<String, Integer> inventory = adapter.getInventory();
    assertNotNull(inventory, "Inventory should not be null");
    assertEquals(3, inventory.size(), "Inventory size should be 3");
    assertEquals(1, inventory.get("available"), "Inventory AVAILABLE should be 1");
    assertEquals(1, inventory.get("pending"), "Inventory PENDING should be 1");
    assertEquals(1, inventory.get("sold"), "Inventory SOLD should be 1");
  }
}