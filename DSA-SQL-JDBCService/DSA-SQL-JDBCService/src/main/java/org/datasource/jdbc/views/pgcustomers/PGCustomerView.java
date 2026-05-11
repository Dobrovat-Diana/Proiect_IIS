package org.datasource.jdbc.views.pgcustomers;

import lombok.Value;

@Value
public class PGCustomerView {
    private Integer customerId;
    private String name;
    private String email;
    private String gender;
    private String signupDate;
    private String country;
}
