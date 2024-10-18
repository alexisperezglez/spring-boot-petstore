package es.home.service.postgres.repository.model;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class Auditable {
  @CreatedBy
  @Column(name = "created_by")
  private String createdBy;
  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdDate;
  @LastModifiedBy
  @Column(name = "updated_by")
  private String lastModifiedBy;
  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime lastModifiedDate;
}
