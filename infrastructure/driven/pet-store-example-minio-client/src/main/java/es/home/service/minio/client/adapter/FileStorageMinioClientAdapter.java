package es.home.service.minio.client.adapter;

import es.home.service.application.ports.driven.FileStorageClientPort;
import es.home.service.domain.exceptions.PetStoreException;
import es.home.service.domain.exceptions.errorcodes.PetErrorEnum;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileStorageMinioClientAdapter implements FileStorageClientPort {

  @Value("${minio.bucket.name}")
  private String bucketName;
  private final MinioClient minioClient;

  @Override
  public String uploadFile(Long petId, String additionalMetadata, Resource body) {
    try (InputStream is = body.getInputStream()) {
      String contentType = URLConnection.guessContentTypeFromStream(is);
      String extension = MimeTypes.getDefaultMimeTypes()
          .forName(contentType)
          .getExtension();
      String fileName = String.format("%d/%s%s", petId, getFormattedDateTime(), extension);
      PutObjectArgs putObjectArgs = PutObjectArgs.builder()
          .contentType(contentType)
          .stream(is, body.contentLength(), -1)
          .object(fileName)
          .bucket(bucketName)
          .build();
      minioClient.putObject(putObjectArgs);
      return fileName;
    } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
             NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
             InternalException | MimeTypeException e) {
      log.error("Error uploading: ", e);
      throw new PetStoreException(PetErrorEnum.MINIO_UPLOAD_ERROR);
    }
  }

  private String getFormattedDateTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    return formatter.format(LocalDateTime.now(ZoneId.systemDefault()));
  }
}
