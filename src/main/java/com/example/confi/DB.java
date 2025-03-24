package com.example.confi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection con;
    public static Connection getCon() throws SQLException,ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con= DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","password");

        return con;
    }
}
