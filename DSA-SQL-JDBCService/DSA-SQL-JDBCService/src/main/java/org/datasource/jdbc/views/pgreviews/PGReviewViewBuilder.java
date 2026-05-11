package org.datasource.jdbc.views.pgreviews;

import org.datasource.jdbc.JDBCPostgreSQLConnector;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class PGReviewViewBuilder {

    private String SQL_PG_REVIEWS_SELECT = "SELECT review_id, product_id, customer_id, rating, " +
            "review_text, CAST(review_date AS VARCHAR) as review_date " +
            "FROM crm_src.product_reviews";

    private List<PGReviewView> pgReviewViewList = new ArrayList<>();

    public List<PGReviewView> getViewList() {
        return this.pgReviewViewList;
    }

    public PGReviewViewBuilder build() {
        try (Connection jdbcConnection = pgConnector.getConnection()) {
            Statement selectStmt = jdbcConnection.createStatement();
            ResultSet rs = selectStmt.executeQuery(SQL_PG_REVIEWS_SELECT);

            pgReviewViewList = new ArrayList<>();
            while (rs.next()) {
                this.pgReviewViewList.add(new PGReviewView(
                        rs.getInt("review_id"),
                        rs.getInt("product_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("rating"),
                        rs.getString("review_text"),
                        rs.getString("review_date")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    private JDBCPostgreSQLConnector pgConnector;

    public PGReviewViewBuilder(JDBCPostgreSQLConnector pgConnector) {
        this.pgConnector = pgConnector;
    }
}
