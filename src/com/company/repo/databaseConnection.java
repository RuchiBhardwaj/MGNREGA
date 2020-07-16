package com.company.repo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class databaseConnection {

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mgnrega", "root", "hello123");

            return con;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}




