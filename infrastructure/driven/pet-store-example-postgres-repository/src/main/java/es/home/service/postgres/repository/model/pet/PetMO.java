package es.home.service.postgres.repository.model.pet;

import es.home.service.domain.bussines.enums.PetStatusEnum;
import es.home.service.postgres.repository.model.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pets")
public class PetMO extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pets_id_gen")
  @SequenceGenerator(name = "pets_id_gen", sequenceName = "pets_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "category_id", nullable = false)
  private CategoryMO category;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "pets_tags", 
      joinColumns = @JoinColumn(name = "pet_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
  @Builder.Default
  private Set<TagMO> tags = new LinkedHashSet<>();

//  @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
  @Column(name = "photo_urls", columnDefinition = "text[]")
  private String[] photoUrls = new String[0];

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, columnDefinition = "pet_status")
  @Builder.Default
  private PetStatusEnum status = PetStatusEnum.PENDING;

}