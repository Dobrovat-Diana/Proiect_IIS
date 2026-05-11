package org.datasource.jdbc.views.orders;

import org.datasource.jdbc.JDBCDataSourceConnector;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderViewBuilder {

    private String SQL_ORDERS_SELECT =
        "SELECT order_id, customer_id, " +
        "TO_CHAR(order_date, 'YYYY-MM-DD') as order_date, " +
        "total_amount, payment_method, shipping_country " +
        "FROM orders";

    private List<OrderView> orderViewList = new ArrayList<>();

    public List<OrderView> getViewList() {
        return this.orderViewList;
    }

    public OrderViewBuilder build() {
        try (Connection jdbcConnection = jdbcConnector.getConnection()) {
            Statement selectStmt = jdbcConnection.createStatement();
            ResultSet rs = selectStmt.executeQuery(SQL_ORDERS_SELECT);

            orderViewList = new ArrayList<>();
            while (rs.next()) {
                this.orderViewList.add(new OrderView(
                    rs.getInt("order_id"),
                    rs.getInt("customer_id"),
                    rs.getString("order_date"),
                    rs.getDouble("total_amount"),
                    rs.getString("payment_method"),
                    rs.getString("shipping_country")
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    private JDBCDataSourceConnector jdbcConnector;
    public OrderViewBuilder(JDBCDataSourceConnector jdbcConnector) {
        this.jdbcConnector = jdbcConnector;
    }
}
