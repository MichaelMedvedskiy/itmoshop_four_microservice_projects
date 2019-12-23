package medve.shop.warehouse.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jt on 5/16/17.
 */
@Entity
public class ItemWarehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;




    private String name;
    private Long availableAmount;
    private Double price;

    public ItemWarehouse() {
    }

    public ItemWarehouse(String name, Long availableAmount, Double price) {
        this.name = name;
        this.availableAmount = availableAmount;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
