package es.home.service.domain.bussines.pet;

import es.home.service.domain.bussines.enums.PetStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Pet {
  private Long id;
  private String name;
  private Category category;
  private List<String> photoUrls;
  private List<Tag> tags;
  private PetStatusEnum status;
}
