package es.home.service.postgres.repository.model.pet;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class CategoryMO {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categories_id_gen")
  @SequenceGenerator(name = "categories_id_gen", sequenceName = "categories_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(mappedBy = "category")
  private Set<PetMO> pets = new LinkedHashSet<>();

}