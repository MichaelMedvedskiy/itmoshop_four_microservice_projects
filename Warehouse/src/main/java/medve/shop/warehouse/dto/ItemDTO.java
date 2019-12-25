package medve.shop.warehouse.dto;

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


    @JsonProperty("warehouseId")
    private Long warehouseId;

    public ItemDTO() {
    }

    public ItemDTO(Long id, Long amount) {
        this.id = id;
        this.amount = amount;
    }

    public ItemDTO(Long id, Long amount, Long warehouseId) {
        this.id = id;
        this.amount = amount;
        this.warehouseId = warehouseId;
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


    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", amount=" + amount +
                ", warehouseId=" + warehouseId +
                '}';
    }
}


