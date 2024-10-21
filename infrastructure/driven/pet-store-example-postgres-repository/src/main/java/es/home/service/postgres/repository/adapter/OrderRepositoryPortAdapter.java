package es.home.service.postgres.repository.adapter;

import es.home.service.application.ports.driven.OrderRepositoryPort;
import es.home.service.domain.bussines.order.Order;
import es.home.service.postgres.repository.OrderMOJpaRepository;
import es.home.service.postgres.repository.mapper.OrderMOMapper;
import es.home.service.postgres.repository.model.store.OrderMO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderRepositoryPortAdapter implements OrderRepositoryPort {

  private final OrderMOJpaRepository jpaRepository;
  private final OrderMOMapper mapper;

  @Override
  public Order save(@NonNull Order order) {
    final OrderMO entity = mapper.toModel(order);
    return mapper.fromModel(jpaRepository.save(entity));
  }
}
