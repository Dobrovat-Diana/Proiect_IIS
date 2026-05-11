package org.datasource.jdbc.views.pgcustomers;

import org.datasource.jdbc.JDBCPostgreSQLConnector;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class PGCustomerViewBuilder {

    // Schema crm_src — exact cum apare in DBeaver
    private String SQL_PG_CUSTOMERS_SELECT =
        "SELECT customer_id, name, email, gender, " +
        "CAST(signup_date AS VARCHAR) as signup_date, country " +
        "FROM crm_src.customers LIMIT 500";
    // LIMIT 500 — tabela are 1.2M randuri, limitam pentru performanta

    private List<PGCustomerView> pgCustomerViewList = new ArrayList<>();

    public List<PGCustomerView> getViewList() {
        return this.pgCustomerViewList;
    }

    public PGCustomerViewBuilder build() {
        try (Connection jdbcConnection = pgConnector.getConnection()) {
            Statement selectStmt = jdbcConnection.createStatement();
            ResultSet rs = selectStmt.executeQuery(SQL_PG_CUSTOMERS_SELECT);

            pgCustomerViewList = new ArrayList<>();
            while (rs.next()) {
                this.pgCustomerViewList.add(new PGCustomerView(
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("gender"),
                    rs.getString("signup_date"),
                    rs.getString("country")
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    private JDBCPostgreSQLConnector pgConnector;
    public PGCustomerViewBuilder(JDBCPostgreSQLConnector pgConnector) {
        this.pgConnector = pgConnector;
    }
}
