package org.datasource.jdbc.views.orderitems;

import lombok.Value;

@Value
public class OrderItemView {
    private Integer orderItemId;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Double unitPrice;
}
