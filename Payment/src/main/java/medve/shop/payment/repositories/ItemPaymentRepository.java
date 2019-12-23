package medve.shop.payment.repositories;

import medve.shop.payment.model.ItemPayment;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jt on 5/16/17.
 */
public interface ItemPaymentRepository extends CrudRepository<ItemPayment, Long> {
}
