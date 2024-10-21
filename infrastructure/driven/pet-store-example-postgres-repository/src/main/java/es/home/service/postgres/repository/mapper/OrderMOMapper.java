package es.home.service.postgres.repository.mapper;

import es.home.service.domain.bussines.order.Order;
import es.home.service.postgres.repository.model.store.OrderMO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PetMOMapper.class})
public interface OrderMOMapper {

  OrderMO toModel(Order source);
  @InheritInverseConfiguration
  Order fromModel(OrderMO source);
}
