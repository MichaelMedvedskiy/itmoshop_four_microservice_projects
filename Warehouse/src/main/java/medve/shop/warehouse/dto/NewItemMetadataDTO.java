package medve.shop.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class NewItemMetadataDTO implements Serializable {

    @NotNull
    @JsonProperty("name")
    private String name;
    @NotNull
    @JsonProperty("availableAmount")
    private Long availableAmount;
    @NotNull
    @JsonProperty("price")
    private Double price;

    public NewItemMetadataDTO() {
    }

    public NewItemMetadataDTO(String name, Long availableAmount, Double price) {
        this.name = name;
        this.availableAmount = availableAmount;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(Long availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
