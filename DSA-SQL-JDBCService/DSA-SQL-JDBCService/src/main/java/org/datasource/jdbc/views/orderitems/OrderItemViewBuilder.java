package org.datasource.jdbc.views.orderitems;

import org.datasource.jdbc.JDBCDataSourceConnector;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemViewBuilder {

    private String SQL_ORDER_ITEMS_SELECT =
        "SELECT order_item_id, order_id, product_id, quantity, unit_price " +
        "FROM order_items";

    private List<OrderItemView> orderItemViewList = new ArrayList<>();

    public List<OrderItemView> getViewList() {
        return this.orderItemViewList;
    }

    public OrderItemViewBuilder build() {
        try (Connection jdbcConnection = jdbcConnector.getConnection()) {
            Statement selectStmt = jdbcConnection.createStatement();
            ResultSet rs = selectStmt.executeQuery(SQL_ORDER_ITEMS_SELECT);

            orderItemViewList = new ArrayList<>();
            while (rs.next()) {
                this.orderItemViewList.add(new OrderItemView(
                    rs.getInt("order_item_id"),
                    rs.getInt("order_id"),
                    rs.getInt("product_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price")
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    private JDBCDataSourceConnector jdbcConnector;
    public OrderItemViewBuilder(JDBCDataSourceConnector jdbcConnector) {
        this.jdbcConnector = jdbcConnector;
    }
}
