package es.home.service.application.ports.driving.pet;

import org.springframework.core.io.Resource;

public interface UploadFileUseCasePort {
  void uploadFile(Long petId, String additionalMetadata, Resource body);
}
