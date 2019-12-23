package medve.shop.entrymanager.services;


import medve.shop.entrymanager.dto.Item;
import medve.shop.entrymanager.rabbit.MessageSender;
import medve.shop.entrymanager.config.ApplicationConfigReader;
import medve.shop.entrymanager.dto.Order;
import medve.shop.entrymanager.util.ApplicationConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping(path = "/warehouse")
public class WarehouseService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public static RestTemplate rt = new RestTemplate();




    @GetMapping(path = "/items/{name_part}/{min_price}/{max_price}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Set<Item> getItems(@PathVariable String name_part, @PathVariable Double min_price, @PathVariable Double max_price) {



        /* Sending to Message Queue */
        try {
            URI getOrder = new URI( "http://localhost:8082/items/"+name_part+"/"+min_price+"/"+max_price+"/");
            Set<Item> items = rt.getForObject(getOrder,Set.class);
            return items;

        } catch (Exception ex) {
            log.error("Exception occurred while sending message to the queue. Exception= {}", ex);
            return new HashSet<>();
        }

    }



}
