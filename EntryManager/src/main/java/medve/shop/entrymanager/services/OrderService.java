package medve.shop.entrymanager.services;


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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@RestController
@RequestMapping(path = "/ordermanager")
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final RabbitTemplate rabbitTemplate;
    private ApplicationConfigReader applicationConfig;
    private MessageSender messageSender;

    public ApplicationConfigReader getApplicationConfig() {
        return applicationConfig;
    }

    @Autowired
    public void setApplicationConfig(ApplicationConfigReader applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Autowired
    public OrderService(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }


    @RequestMapping(path = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessageAdd(@RequestBody Order order) {

        String exchange = getApplicationConfig().getOrderManagerExchange();
        String routingKey = getApplicationConfig().getOrderManagerAddRoutingKey();

        /* Sending to Message Queue */
        try {
            messageSender.sendMessage(rabbitTemplate, exchange, routingKey, order);
            return new ResponseEntity<String>(ApplicationConstant.IN_QUEUE, HttpStatus.OK);

        } catch (Exception ex) {
            log.error("Exception occurred while sending message to the queue. Exception= {}", ex);
            return new ResponseEntity(ApplicationConstant.MESSAGE_QUEUE_SEND_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    @RequestMapping(path = "/paid/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessagePaid(@PathVariable("id") Long orderId) {

        String exchange = getApplicationConfig().getOrderManagerExchange();
        String routingKey = getApplicationConfig().getOrderManagerPaidRoutingKey();

        /* Sending to Message Queue */
        try {
            messageSender.sendMessage(rabbitTemplate, exchange, routingKey, orderId);


        } catch (Exception ex) {
            log.error("Exception occurred while sending message to the queue. Exception= {}", ex);
            return new ResponseEntity(ApplicationConstant.MESSAGE_QUEUE_SEND_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        exchange = getApplicationConfig().getWarehouseExchange();
        routingKey = getApplicationConfig().getWarehouseRoutingKey();

        try{
            RestTemplate rt =  new RestTemplate();
            URI getOrder = new URI("http://localhost:8081/getOrder/"+orderId);
            Order orderDTO =rt.getForObject(getOrder, Order.class);
            messageSender.sendMessage(rabbitTemplate, exchange, routingKey, orderDTO);
        }catch (Exception ex){
            log.error("Exception occurred while sending message to the queue. Exception= {}", ex);
            return new ResponseEntity(ApplicationConstant.MESSAGE_QUEUE_SEND_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>(ApplicationConstant.IN_QUEUE, HttpStatus.OK);

    }


}
