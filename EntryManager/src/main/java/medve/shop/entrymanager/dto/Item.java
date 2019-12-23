package medve.shop.entrymanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jt on 5/16/17.
 */

public class Item implements Serializable {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("amount")
    private Long amount;


    public Item() {
    }

    public Item(Long id, Long amount) {
        this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}


