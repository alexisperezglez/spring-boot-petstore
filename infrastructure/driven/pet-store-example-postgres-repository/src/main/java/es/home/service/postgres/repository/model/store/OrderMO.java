package es.home.service.postgres.repository.model.store;

import es.home.service.domain.bussines.enums.OrderStatusEnum;
import es.home.service.postgres.repository.model.Auditable;
import es.home.service.postgres.repository.model.pet.PetMO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@NamedQuery(name = "OrderMO.findAllByPetId", query = "SELECT o FROM OrderMO o WHERE o.pet.id = :petId")
@NamedQuery(name = "OrderMO.findAllByStatus", query = "SELECT o FROM OrderMO o WHERE o.status = :status")
@NamedQuery(name = "OrderMO.findAllCompleted", query = "SELECT o FROM OrderMO o WHERE o.completed = true ")
public class OrderMO extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_seq")
  @SequenceGenerator(name = "orders_id_seq", sequenceName = "orders_id_seq", allocationSize = 1)
  private Long id;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pet_id", referencedColumnName = "id")
  private PetMO pet;
  @Column(name = "quantity")
  private Integer quantity;
  @Column(name = "ship_date")
  private LocalDateTime shipDate;
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private OrderStatusEnum status;
  @Column(name = "completed")
  private Boolean completed;

}
