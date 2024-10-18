package es.home.service.application.ports.driven;

import org.springframework.core.io.Resource;

public interface FileStorageClientPort {
  String uploadFile(Long petId, String additionalMetadata, Resource body);
}
