package tech.claudioed.crm.domain.repository;

import org.springframework.data.repository.CrudRepository;
import tech.claudioed.crm.domain.Order;

/** @author claudioed on 2019-03-05. Project crm */
public interface OrderRepository extends CrudRepository<Order, String> {}
