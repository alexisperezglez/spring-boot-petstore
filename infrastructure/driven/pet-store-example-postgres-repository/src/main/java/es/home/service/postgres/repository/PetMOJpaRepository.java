package es.home.service.postgres.repository;

import es.home.service.domain.bussines.enums.PetStatusEnum;
import es.home.service.postgres.repository.model.pet.PetMO;
import es.home.service.postgres.repository.model.store.InventoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetMOJpaRepository extends JpaRepository<PetMO, Long> {

  List<PetMO> findAllByStatus(PetStatusEnum status);

  @Query("SELECT p FROM PetMO p INNER JOIN p.tags t WHERE lower(t.name) IN (:tags)")
  List<PetMO> findAllByTags(List<String> tags);

  @Query(name = "PetMO.getInventory")
  List<InventoryRecord> getInventory();
}
