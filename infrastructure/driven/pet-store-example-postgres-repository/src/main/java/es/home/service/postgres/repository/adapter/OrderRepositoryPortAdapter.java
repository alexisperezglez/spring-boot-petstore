package es.home.service.postgres.repository.adapter;

import es.home.service.application.ports.driven.OrderRepositoryPort;
import es.home.service.postgres.repository.OrderMOJpaRepository;
import es.home.service.postgres.repository.mapper.OrderMOMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderRepositoryPortAdapter implements OrderRepositoryPort {

  private final OrderMOJpaRepository jpaRepository;
  private final OrderMOMapper mapper;
}
