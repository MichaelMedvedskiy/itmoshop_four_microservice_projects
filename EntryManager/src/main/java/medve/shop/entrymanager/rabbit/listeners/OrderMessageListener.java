package medve.shop.entrymanager.rabbit.listeners;


import medve.shop.entrymanager.rabbit.config.ApplicationConfigReader;
import medve.shop.entrymanager.dto.OrderDTO;
import medve.shop.entrymanager.util.ApplicationConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Message Listener for RabbitMQ
 *
 * @author deepak.af.kumar
 */

@Service
public class OrderMessageListener {

    private static final Logger log = LoggerFactory.getLogger(OrderMessageListener.class);
    private final URI orderAddURI = new URI("http://localhost:8081/addOrder");
    private final URI orderPayURI = new URI("http://localhost:8081/payOrder");

    private final static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    ApplicationConfigReader applicationConfigReader;

    public OrderMessageListener() throws URISyntaxException {
    }


    /**
     * Message listener for app1
     *
     * @param Long a user defined object used for deserialization of message
     */
    @RabbitListener(queues = "${OrderManagerPaid.queue.name}")
    public void receiveMessageForOrderManagerPaid(final OrderDTO id) {
        log.info("Received message: {} from OrderManagerPaid queue.", id);

        try {
            log.info("Making REST call to the API");
            //TODO: Code to make REST call
            restTemplate.put(orderPayURI, id);
            log.info("<< Exiting receiveMessageForApp1() after API call.");
        } catch (HttpClientErrorException ex) {

            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.info("Delay...");
                try {
                    Thread.sleep(ApplicationConstant.MESSAGE_RETRY_DELAY);
                } catch (InterruptedException e) {
                }

                log.info("Throwing exception so that message will be requed in the queue.");
                // Note: Typically Application specific exception should be thrown below
                throw new RuntimeException();
            } else {
                throw new AmqpRejectAndDontRequeueException(ex);
            }

        } catch (Exception e) {
            log.error("Internal server error occurred in API call. Bypassing message requeue {}", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }

    }



    /**
     * Message listener for Add order
     */

    @RabbitListener(queues = "${OrderManagerAdd.queue.name}")
    public void receiveMessageForOrderManagerAdd(OrderDTO data) {
        log.info("Received message: {} from OrderManagerAdd queue.", data);

        try {
            log.info("Making REST call to the API");
            //TODO: Code to make REST call

            restTemplate.postForEntity(orderAddURI, data, OrderDTO.class);
            log.info("<< POSTED TO URI: {}", orderAddURI);
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
