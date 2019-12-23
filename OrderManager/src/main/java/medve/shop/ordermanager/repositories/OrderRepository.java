package medve.shop.ordermanager.repositories;

import medve.shop.ordermanager.model.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jt on 5/16/17.
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
}
