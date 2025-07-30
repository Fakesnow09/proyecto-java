package com.miproyecto.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TestConexion extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String url = "jdbc:mysql://localhost:3306/proyecto_logistica";
        String user = "root";
        String password = "NALAH"; // ← CAMBIA esto por tu contraseña real

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            out.println("<h2 style='color: green;'>✅ Conexión exitosa a la base de datos</h2>");
            conn.close();
        } catch (ClassNotFoundException e) {
            out.println("<h2 style='color: red;'>❌ No se encontró el driver JDBC</h2>");
            e.printStackTrace(out);
        } catch (SQLException e) {
            out.println("<h2 style='color: red;'>❌ Error al conectar con la base de datos</h2>");
            e.printStackTrace(out);
        }
    }

    public static Connection getConexion() {
        throw new UnsupportedOperationException("Unimplemented method 'getConexion'");
    }
}