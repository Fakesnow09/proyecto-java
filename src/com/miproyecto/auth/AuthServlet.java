 package com.miproyecto.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // Permitir CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("text/plain;charset=UTF-8");

        PrintWriter out = response.getWriter();
        String tipo = request.getParameter("tipo");

        System.out.println("🚨 SERVLET NUEVO - ACTUALIZADO [" + System.currentTimeMillis() + "]");

        try {
            // Conectar a MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proyecto_logistica", "root", "NALAH"
            );

            UsuarioDAO dao = new UsuarioDAO(conn);

            if ("registro".equalsIgnoreCase(tipo)) {
                String nombre = request.getParameter("nombreCompleto");
                String tipoDoc = request.getParameter("tipoDocumento");
                String doc = request.getParameter("numeroDocumento");
                String pass = request.getParameter("contrasena");

                Usuario u = new Usuario(nombre, tipoDoc, doc, pass);
                boolean ok = dao.registrar(u);
                out.println(ok ? "✅ Registrado correctamente" : "❌ Error al registrar");

            } else if ("login".equalsIgnoreCase(tipo)) {
                // CAMBIO: ahora usamos nombreCompleto y numeroDocumento (como contraseña)
                String nombre = request.getParameter("nombreCompleto");
                String documento = request.getParameter("numeroDocumento");

                Usuario u = dao.loginPorNombreYDocumento(nombre, documento); // usa el nuevo método
                if (u != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", u);
                    out.println("✅ Inicio de sesión exitoso");
                } else {
                    out.println("❌ Credenciales inválidas");
                }

            } else {
                out.println("⚠️ Tipo de operación no reconocida");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace(out);
            out.println("❌ Error del servidor: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h3>🚫 Este servlet solo acepta POST. Usa un formulario o Postman.</h3>");
    }
}
