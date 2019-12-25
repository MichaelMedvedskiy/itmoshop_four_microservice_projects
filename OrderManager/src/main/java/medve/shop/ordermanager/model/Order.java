package medve.shop.ordermanager.model;

import medve.shop.ordermanager.enums.OrderState;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jt on 5/16/17.
 */
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean status;

    private OrderState orderState;

    @OneToMany
    private Set<Item> items = new HashSet<>();

    public Order() {
        this.orderState = OrderState.STARTED;
    }

    public Order(Boolean status) {
        this.status = status;
        this.orderState = OrderState.STARTED;
    }

    public Order(Boolean status, Set<Item> items) {
        this.status = status;
        this.items = items;
        this.orderState = OrderState.STARTED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {this.items.add(item);}

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
}
