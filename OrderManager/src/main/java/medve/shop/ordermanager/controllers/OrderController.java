package medve.shop.ordermanager.controllers;

import medve.shop.ordermanager.DTO.ItemDTO;
import medve.shop.ordermanager.DTO.OrderDTO;
import medve.shop.ordermanager.enums.OrderState;
import medve.shop.ordermanager.model.Item;
import medve.shop.ordermanager.model.Order;
import medve.shop.ordermanager.repositories.ItemRepository;
import medve.shop.ordermanager.repositories.OrderRepository;
import org.apache.tomcat.jni.Status;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    private OrderRepository orderRepository;
    private ItemRepository itemRepository;
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    RestTemplate rt = new RestTemplate();

    public OrderController(ItemRepository itemRepository, OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }
//TODO:
//this implementation is complete shit - in reality it takes the item data not from the WAREHOUSE DB, but from the OrderManager DB,
// so it can only have instances
    //of the "ITEM+AMOUNT" present at the OM DB. The amount is not changable! It needs to be One-To-Many, with the "Items" being created
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus addOrder(@RequestBody OrderDTO orderDTO) throws MalformedURLException, URISyntaxException {

        Set<Long> itemIds = orderDTO.getItems().stream().map((itemDTO)->  itemDTO.getWarehouseId()).collect(Collectors.toSet());
        URI warehouseValidateIds = new URI("http://localhost:8082/validateItems/");
        if(!rt.postForObject(warehouseValidateIds, itemIds, Boolean.class))
            {return HttpStatus.NOT_ACCEPTABLE;}
        Order orderToAdd = new Order();
        orderToAdd.setOrderState(OrderState.STARTED);
        orderToAdd.setStatus(false);
        orderToAdd.setItems(new HashSet<>());
        orderDTO.getItems().stream().forEach(itemDTO -> {orderToAdd.addItem(new Item(itemDTO.getWarehouseId(), itemDTO.getAmount()));});
        //Todo: the "transient instances" - is items that are One To many -> they should be in the database, but they are not saved yet!
        orderRepository.save(orderToAdd);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/payOrder", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addOrder(@RequestBody Long orderId){

        log.info("PAYING FOR THE ORDER WITH ID: {}",orderId);
        Optional optional = orderRepository.findById(orderId);
        if(optional.isPresent()){
            Order order = (Order) optional.get();
            order.setOrderState(OrderState.FINISHED);
            orderRepository.save(order);
        }

    }

    @RequestMapping(value = "/getOrder/{orderId}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDTO getOrder(@PathVariable Long orderId) throws Exception {

        log.info("GIVING THE ORDER BY ID: {}",orderId);
        Optional optional = orderRepository.findById(orderId);

        if(optional.isPresent()){
            return orderToOrderDTO(orderRepository.findById(orderId).get());
        }
        throw new Exception("OrderNotFount");

    }

    private OrderDTO orderToOrderDTO (Order order){
        return new OrderDTO( order.getId(), order.getStatus(), itemsToItemDTOs( order.getItems()));
    }

    private Set<ItemDTO> itemsToItemDTOs(Set<Item>items){
        Set<ItemDTO> dtos = new HashSet<>();
        items.forEach(item -> dtos.add(new ItemDTO(item.getWarehouseId(), item.getAmount())));
        return dtos;
    }
}
