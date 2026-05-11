package org.datasource.mongodb.views.products;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ProductView implements Serializable {
    private Integer productId;
    private String productName;
    private String category;
    private Double price;
    private Integer stockQuantity;
    private String brand;
    // Campurile = exact fieldurile din MongoDB Compass:
    // product_id, product_name, category, price, stock_quantity, brand
}
