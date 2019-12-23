package medve.shop.warehouse.controllers;


import medve.shop.warehouse.dto.ItemDTO;
import medve.shop.warehouse.dto.OrderDTO;
import medve.shop.warehouse.model.ItemWarehouse;
import medve.shop.warehouse.repositories.ItemWarehouseRepository;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

@RestController
public class WarehouseController {
    ItemWarehouseRepository itemWarehouseRepository;

    public WarehouseController(ItemWarehouseRepository itemWarehouseRepository) {
        this.itemWarehouseRepository = itemWarehouseRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(WarehouseController.class);
    @Transactional
    @RequestMapping(value = "/takeItems", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody HttpStatus decrementItemsOfOrder(@RequestBody OrderDTO order){

        log.info("GOT THE ORDER HERE, IN WAREHOUSE: {}",order);


        for (ItemDTO itemDTO:
                order.getItems()) {
            Optional op = itemWarehouseRepository.findById(itemDTO.getId());
            if(!op.isPresent() || ((ItemWarehouse) op.get()).getAvailableAmount() < itemDTO.getAmount())
                return HttpStatus.BAD_REQUEST;
        }
        for (ItemDTO itemDTO:
             order.getItems()) {
            Optional op = itemWarehouseRepository.findById(itemDTO.getId());

            ItemWarehouse item = (ItemWarehouse) op.get();
            item.setAvailableAmount( item.getAvailableAmount()-itemDTO.getAmount());
            itemWarehouseRepository.save(item);
        }

        return HttpStatus.OK;
    }

    @RequestMapping(value = "/items/{name_part}/{min_price}/{max_price}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Set<ItemWarehouse> getItems(@PathVariable String name_part, @PathVariable Double min_price, @PathVariable Double max_price){

        Iterable<ItemWarehouse> items = itemWarehouseRepository.findAll();
        ItemWarehouse[] resultItems =  StreamSupport.stream(items.spliterator(), false)
                .filter((item)-> item.getName().contains(name_part))
                .filter(item -> item.getPrice()> min_price)
                .filter(item -> item.getPrice() < max_price).toArray(ItemWarehouse[]::new);
        return new HashSet<ItemWarehouse>(Arrays.asList(resultItems));
    }


}
