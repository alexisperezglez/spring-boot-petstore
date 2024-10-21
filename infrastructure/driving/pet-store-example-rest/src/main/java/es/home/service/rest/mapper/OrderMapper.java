package es.home.service.rest.mapper;

import es.home.service.domain.bussines.order.Order;
import es.home.service.pet_store_example_api.model.OrderDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {java.time.OffsetDateTime.class, java.time.ZoneOffset.class})
public interface OrderMapper {

  @Mapping(target = "completed", source = "complete", defaultExpression = "java(false)")
  @Mapping(target = "pet.id", source = "petId")
  @Mapping(target = "shipDate", expression = "java(source.getShipDate() == null ? null : source.getShipDate().toLocalDateTime())")
  Order toDomain(OrderDTO source);
  @InheritInverseConfiguration
  @Mapping(target = "shipDate", expression = "java(source.getShipDate() == null ? null : OffsetDateTime.of(source.getShipDate(), ZoneOffset.UTC))")
  OrderDTO fromDomain(Order source);
}
