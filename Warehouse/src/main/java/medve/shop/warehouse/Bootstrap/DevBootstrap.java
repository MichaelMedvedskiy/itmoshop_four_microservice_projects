package medve.shop.warehouse.Bootstrap;

import medve.shop.warehouse.model.ItemWarehouse;
import medve.shop.warehouse.repositories.ItemWarehouseRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private ItemWarehouseRepository itemRepository;

    public DevBootstrap(ItemWarehouseRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData(){


        ItemWarehouse anal_zond = new ItemWarehouse("СТАЛИН 3000", 100L, 3000.);

        itemRepository.save(anal_zond);

    }
}