package es.home.service.domain.bussines.pet;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tag {

  private Long id;
  private String name;
}
