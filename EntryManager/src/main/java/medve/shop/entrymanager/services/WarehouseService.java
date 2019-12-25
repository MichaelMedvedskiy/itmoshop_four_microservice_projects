package medve.shop.entrymanager.services;


import medve.shop.entrymanager.Exceptions.ItemAddException;
import medve.shop.entrymanager.dto.ItemDTO;
import medve.shop.entrymanager.dto.NewItemMetadataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping(path = "/warehouse")
public class WarehouseService {

    private static final Logger log = LoggerFactory.getLogger(WarehouseService.class);

    public static RestTemplate rt = new RestTemplate();




    @GetMapping(path = "/items/{name_part}/{min_price}/{max_price}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Set<ItemDTO> getItems(@NotNull @PathVariable String name_part,@NotNull @PathVariable Double min_price,@NotNull @PathVariable Double max_price) {
        /* Sending to Message Queue */
        try {
            URI getOrder = new URI( "http://localhost:8082/items/"+name_part+"/"+min_price+"/"+max_price+"/");
            Set<ItemDTO> itemDTOS = rt.getForObject(getOrder,Set.class);
            return itemDTOS;
        } catch (Exception ex) {
            log.error("Exception occurred while sending message to the queue. Exception= {}", ex);
            return new HashSet<>();
        }
    }


    @PostMapping(path = "/insert/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addItem(@Valid @RequestBody NewItemMetadataDTO item) throws URISyntaxException, ItemAddException {
        URI postOrder = new URI( "http://localhost:8082/addItem/");
        HttpStatus response = rt.postForObject(postOrder,item, HttpStatus.class);
        if(!(response.value() == HttpStatus.OK.value())){
            return new ResponseEntity<Object>(item, HttpStatus.BAD_REQUEST);
            //throw new ItemAddException();
        }

        return response;
    }

}
