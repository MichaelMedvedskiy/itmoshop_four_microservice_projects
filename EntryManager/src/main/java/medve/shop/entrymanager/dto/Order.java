package medve.shop.entrymanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jt on 5/16/17.
 */

public class Order implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("items")
    private Set<Item> items = new HashSet<>();

    public Order() {
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", items=" + items +
                '}';
    }
}
