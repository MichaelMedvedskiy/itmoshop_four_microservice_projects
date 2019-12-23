package medve.shop.ordermanager.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by jt on 5/16/17.
 */

public class ItemDTO implements Serializable {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("amount")
    private Long amount;


    public ItemDTO() {
    }

    public ItemDTO(Long id, Long amount) {
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
        return "ItemDTO{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}


