// Archivo: src/com/miproyecto/auth/Conexion.java
package com.miproyecto.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public static Connection getConexion() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/proyecto_logistica";
        String user = "root";
        String password = "NALAH"; // Ajusta con tu contraseña real

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    public static Connection getConnection() {
        throw new UnsupportedOperationException("Unimplemented method 'getConnection'");
    }
}