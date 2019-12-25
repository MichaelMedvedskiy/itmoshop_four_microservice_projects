package medve.shop.warehouse.controllers;


import medve.shop.warehouse.dto.ItemDTO;
import medve.shop.warehouse.dto.NewItemMetadataDTO;
import medve.shop.warehouse.dto.OrderDTO;
import medve.shop.warehouse.dto.util_classes.NewItemMetadataDTOAndHttpServletResponse;
import medve.shop.warehouse.model.ItemWarehouse;
import medve.shop.warehouse.rabbit.config.ApplicationConfigReader;
import medve.shop.warehouse.rabbit.utils.MessageSendUtils;
import medve.shop.warehouse.repositories.ItemWarehouseRepository;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.StreamSupport;

@RestController
public class WarehouseController {
    ItemWarehouseRepository itemWarehouseRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MessageSendUtils messageSender;
    @Autowired
    private ApplicationConfigReader applicationConfig;

    public WarehouseController(ItemWarehouseRepository itemWarehouseRepository) {
        this.itemWarehouseRepository = itemWarehouseRepository;
    }


    private static final Logger log = LoggerFactory.getLogger(WarehouseController.class);


    /*
    * For the 'pay' experience
    * */
    @Transactional
    @RequestMapping(value = "/takeItems", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    HttpStatus decrementItemsOfOrder(@RequestBody OrderDTO order) {

        log.info("GOT THE ORDER HERE, IN WAREHOUSE: {}", order);


        for (ItemDTO itemDTO :
                order.getItems()) {
            Optional op = itemWarehouseRepository.findById(itemDTO.getId());
            if (!op.isPresent() || ((ItemWarehouse) op.get()).getAvailableAmount() < itemDTO.getAmount())
                return HttpStatus.BAD_REQUEST;
        }
        for (ItemDTO itemDTO :
                order.getItems()) {
            Optional op = itemWarehouseRepository.findById(itemDTO.getId());

            ItemWarehouse item = (ItemWarehouse) op.get();
            item.setAvailableAmount(item.getAvailableAmount() - itemDTO.getAmount());
            itemWarehouseRepository.save(item);
        }

        return HttpStatus.OK;
    }

    @RequestMapping(value = "/items/{name_part}/{min_price}/{max_price}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Set<ItemWarehouse> getItems(@PathVariable String name_part, @PathVariable Double min_price, @PathVariable Double max_price) {

        Iterable<ItemWarehouse> items = itemWarehouseRepository.findAll();
        ItemWarehouse[] resultItems = StreamSupport.stream(items.spliterator(), false)
                .filter((item) -> item.getName().contains(name_part))
                .filter(item -> item.getPrice() > min_price)
                .filter(item -> item.getPrice() < max_price).toArray(ItemWarehouse[]::new);
        return new HashSet<ItemWarehouse>(Arrays.asList(resultItems));
    }

    @PostMapping(value = "/addItem/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus addItem(@Valid @RequestBody NewItemMetadataDTO newItemDTO, HttpServletResponse response) {

        String exchange = applicationConfig.getWarehouseItemExchange();
        String routingKey = applicationConfig.getWarehouseItemRoutingKey();


//            log.debug("Started sending to the exchange: {}",exchange);
//            messageSender.sendMessage(rabbitTemplate, exchange, routingKey, new NewItemMetadataDTOAndHttpServletResponse(newItemDTO, response) );

        try {
            log.info("Making REST call to the API");

            ItemWarehouse item = new ItemWarehouse(newItemDTO.getName(), newItemDTO.getAvailableAmount(), newItemDTO.getPrice());
            itemWarehouseRepository.save(item);

        } catch (Exception e) {
            log.info(e.toString());
            return HttpStatus.NOT_ACCEPTABLE;
        }
        return HttpStatus.OK;
    }


    /*
    * Validate item ids set to be a part of database
    *It's post, cause im not sure in put's capabilities as the demarshaller can not to properly transfer the returning type
    * */
    @PostMapping (value = "/validateItems/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean isItemSetValid(@RequestBody Set<Long> itemIds){

            if(size(itemWarehouseRepository.findAllById(itemIds))!=size(itemIds)) return false;
            return true;

    }

    public static int size(Iterable data) {

        if (data instanceof Collection) {
            return ((Collection<?>) data).size();
        }
        int counter = 0;
        for (Object i : data) {
            counter++;
        }
        return counter;
    }

}
