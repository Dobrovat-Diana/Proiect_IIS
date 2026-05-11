package org.datasource.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Logger;

@Service
public class JDBCPostgreSQLConnector {
    private static Logger logger = Logger.getLogger(JDBCPostgreSQLConnector.class.getName());

    @Value("${jdbc.pg.DB_URL}")
    private String DB_URL;
    @Value("${jdbc.pg.USER}")
    private String USER;
    @Value("${jdbc.pg.PASS}")
    private String PASS;

    public Connection getConnection() throws Exception {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        return DriverManager.getConnection(DB_URL, props);
    }
}
