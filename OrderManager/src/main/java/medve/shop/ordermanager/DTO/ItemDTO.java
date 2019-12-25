package medve.shop.ordermanager.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by jt on 5/16/17.
 */

public class ItemDTO implements Serializable {


    @JsonProperty("warehouseId")
    private Long warehouseId;

    @JsonProperty("amount")
    private Long amount;

    public ItemDTO() {
    }

    public ItemDTO(Long warehouseId, Long amount) {
        this.warehouseId = warehouseId;
        this.amount = amount;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
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
                "warehouseId=" + warehouseId +
                ", amount=" + amount +
                '}';
    }
}


