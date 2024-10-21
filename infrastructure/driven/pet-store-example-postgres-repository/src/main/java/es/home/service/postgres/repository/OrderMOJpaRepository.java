package es.home.service.postgres.repository;

import es.home.service.postgres.repository.model.store.OrderMO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMOJpaRepository extends CrudRepository<OrderMO, Long> {
}
