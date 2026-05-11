package org.datasource.jdbc.views.orders;

import lombok.Value;

@Value
public class OrderView {
    private Integer orderId;
    private Integer customerId;
    private String orderDate;
    private Double totalAmount;
    private String paymentMethod;
    private String shippingCountry;
}
