package medve.shop.entrymanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jt on 5/16/17.
 */

public class OrderDTO implements Serializable {

//todo: here was an ID. Think of what a real order needs, how to construct it, and!! how to insert the order by its data, including the new system for item gathering from the warehouse - that the one in OrderManager is not the same as in warehouse, that it does have the ID and amount, but it goes checking from the Warehouse, and is only saved if IDs of what is in the Order and what is present in the Warehouse, match
    @JsonProperty("status")
    private Boolean status;

    @NotNull
    @JsonProperty("items")
    private Set<ItemDTO> itemDTOS = new HashSet<>();

    public OrderDTO() {
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<ItemDTO> getItems() {
        return itemDTOS;
    }

    public void setItems(Set<ItemDTO> itemDTOS) {
        this.itemDTOS = itemDTOS;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                " status=" + status +
                ", itemDTOS=" + itemDTOS +
                '}';
    }
}
