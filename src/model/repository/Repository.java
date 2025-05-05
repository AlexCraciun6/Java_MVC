package model.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private Connection connection;

    // Constructor
    public Repository() {
        connectToDatabase();
    }

    // Metoda pentru conectarea la baza de date
    private void connectToDatabase() {
        try {
            // Încarcă driverul JDBC pentru SQLite
            Class.forName("org.sqlite.JDBC");
            // Conectează-te la baza de date (crează fișierul dacă nu există)
            connection = DriverManager.getConnection("jdbc:sqlite:d:/AN3/sem2/PS/tema1_java/muzeu.db");
//            System.out.println("Conexiunea la baza de date a fost deschisă.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Metoda pentru deschiderea conexiunii
    public void openConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connectToDatabase();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metoda pentru închiderea conexiunii
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metoda pentru executarea unei comenzi SQL (INSERT, UPDATE, DELETE)
    public boolean executeSQLCommand(String sqlCommand) {
        boolean result = true;
        try {
            openConnection();
            Statement statement = connection.createStatement();
            result = statement.executeUpdate(sqlCommand) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        } finally {
            closeConnection();
        }
        return result;
    }

    // Metoda pentru obținerea unui ResultSet (SELECT)
    public ResultSet executeSQLQuery(String sqlQuery) {
        ResultSet resultSet = null;
        try {
            openConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
