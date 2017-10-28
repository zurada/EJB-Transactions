package edu.pjwstk.sri.lab2.dto;

import edu.pjwstk.sri.lab2.model.Product;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OrderItem implements Serializable {
    private Product product;
    private Integer amount = 0;

    public void increaseAmount() {
        amount += 1;
    }
}
