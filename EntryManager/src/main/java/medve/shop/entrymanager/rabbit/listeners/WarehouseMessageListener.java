package medve.shop.entrymanager.rabbit.listeners;

import medve.shop.entrymanager.dto.OrderDTO;
import medve.shop.entrymanager.util.ApplicationConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class WarehouseMessageListener {


    private static final Logger log = LoggerFactory.getLogger(OrderMessageListener.class);
    private final static RestTemplate restTemplate = new RestTemplate();
    private final URI warehouseTakeURI = new URI("http://localhost:8082/takeItems");

    public WarehouseMessageListener() throws URISyntaxException {
    }

    @RabbitListener(queues = "${Warehouse.queue.name}")
    public void receiveMessageForWarehouseTake(OrderDTO orderDTO) {
        log.info("Received WAREHOUSE message: {} from Warehouse queue.", orderDTO);

        try {
            log.info("Making REST call to the API");
            //TODO: Code to make REST call
            HttpStatus response = restTemplate.postForObject(warehouseTakeURI, orderDTO, HttpStatus.class);
            if(!(response==HttpStatus.OK)) throw new Exception("ITEM TAKE ERROR!! (From Warehouse service)");
            //restTemplate.put(warehouseTakeURI, orderDTO);

        } catch (HttpClientErrorException ex) {

            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.info("Delay...");
                try {
                    Thread.sleep(ApplicationConstant.MESSAGE_RETRY_DELAY);
                } catch (InterruptedException e) {
                }

                log.info("Throwing exception so that message will be requed in the queue.");
                // Note: Typically Application specific exception can be thrown below
                throw new RuntimeException();
            } else {
                throw new AmqpRejectAndDontRequeueException(ex);
            }

        } catch (Exception e) {
            log.error("Internal server error occurred in python server. Bypassing message requeue {}", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }

    }

}
