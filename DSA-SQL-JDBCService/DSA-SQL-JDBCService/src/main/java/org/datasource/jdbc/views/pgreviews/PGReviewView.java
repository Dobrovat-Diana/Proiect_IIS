package org.datasource.jdbc.views.pgreviews;

import lombok.Value;

@Value
public class PGReviewView {
    private Integer reviewId;
    private Integer productId;
    private Integer customerId;
    private Integer rating;
    private String reviewText;
    private String reviewDate;
}
