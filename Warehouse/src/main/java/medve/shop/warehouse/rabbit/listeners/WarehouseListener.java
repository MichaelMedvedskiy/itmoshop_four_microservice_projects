package medve.shop.warehouse.rabbit.listeners;

import medve.shop.warehouse.dto.NewItemMetadataDTO;
import medve.shop.warehouse.dto.util_classes.NewItemMetadataDTOAndHttpServletResponse;
import medve.shop.warehouse.model.ItemWarehouse;
import medve.shop.warehouse.rabbit.config.ApplicationConfigReader;
import medve.shop.warehouse.repositories.ItemWarehouseRepository;
import medve.shop.warehouse.util.ApplicationConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;

@Service
public class WarehouseListener {
    private static final Logger log = LoggerFactory.getLogger(WarehouseListener.class);

    @Autowired
    private ItemWarehouseRepository itemWarehouseRepository;

    private final static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ApplicationConfigReader applicationConfigReader;

    @RabbitListener(queues = "${Warehouse-item.queue.name}")
    public void receiveMessageItemAdd(NewItemMetadataDTOAndHttpServletResponse itemAndResponse) throws IOException {
        log.info("Received message: {} from OrderManagerAdd queue.", itemAndResponse);

        try {
            log.info("Making REST call to the API");

            ItemWarehouse item = new ItemWarehouse(itemAndResponse.getNewItem().getName(), itemAndResponse.getNewItem().getAvailableAmount(), itemAndResponse.getNewItem().getPrice());
            itemWarehouseRepository.save(item);

        } catch (Exception e) {
            itemAndResponse.getHttpServletResponse().sendError(400);
            log.error("Internal server error occurred in python server. Bypassing message requeue {}", e);
            throw new AmqpRejectAndDontRequeueException(e);
            //return HttpStatus.NOT_ACCEPTABLE;
        }
        //response.setStatus(200);

    }

}
