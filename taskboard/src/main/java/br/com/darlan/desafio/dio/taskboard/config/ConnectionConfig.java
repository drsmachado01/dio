package br.com.darlan.desafio.dio.taskboard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionConfig {

    @Value("${connection.username}")
    private String username;
    @Value("${connection.password}")
    private String password;
    @Value("${connection.url}")
    private String url;

    public Connection getConnection() throws SQLException {
        var connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(false);
        return connection;
    }
}
