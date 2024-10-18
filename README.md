### Generate API

[openapi-generator doc](https://openapi-generator.tech/docs/generators/spring)

```shell
docker run --rm -v $PWD:/local openapitools/openapi-generator-cli generate \
-i /local/infrastructure/driving/pet-store-example-rest/contract/apispec.yaml \
-g spring -o /local/out --api-package es.home.service.pet-store-example-api \
--model-package es.home.service.pet-store-example-api.model \
--model-name-suffix DTO --api-name-suffix API --library spring-boot \
--group-id es.home.service --artifact-id pet-store-example-api \
-c /local/infrastructure/driving/pet-store-example-rest/contract/config.yaml
```

### Build API

```shell
cd out
mvn clean install
```

### API USAGE

```shell
<dependency>
  <groupId>es.home.service</groupId>
  <artifactId>pet-store-example-api</artifactId>
  <version>1.0.11</version>
  <systemPath>fullpathto/out/target/pet-store-example-api-1.0.11.jar</systemPath>
  <scope>system</scope>
</dependency>
```