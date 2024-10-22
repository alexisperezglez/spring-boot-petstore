package es.home.service.postgres.repository.model.store;

import es.home.service.domain.bussines.enums.PetStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@SqlResultSetMapping(name = "InventoryRecordMapping", entities = {
    @EntityResult(entityClass = InventoryRecord.class, fields = {@FieldResult(name = "status", column = "status"), @FieldResult(name = "quantity", column = "quantity")})
})
@AllArgsConstructor
public class InventoryRecord {
  @Column(name = "status")
  private PetStatusEnum status;

  @Column(name = "quantity")
  private Long quantity;
}
