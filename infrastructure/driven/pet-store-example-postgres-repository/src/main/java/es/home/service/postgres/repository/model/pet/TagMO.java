package es.home.service.postgres.repository.model.pet;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tags")
@NamedEntityGraph(name = "TagMO.pets", attributeNodes = @NamedAttributeNode("pets"))
public class TagMO {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_id_gen")
  @SequenceGenerator(name = "tags_id_gen", sequenceName = "tags_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "pets_tags",
      joinColumns = @JoinColumn(name = "tag_id"),
      inverseJoinColumns = @JoinColumn(name = "pet_id"))
  private Set<PetMO> pets = new LinkedHashSet<>();

}