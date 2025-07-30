package com.miproyecto.Usuario;

import jakarta.servlet.ServletException;
import java.sql.SQLException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO dao = new UsuarioDAO();

    // Método para añadir headers CORS
    private void agregarEncabezadosCORS(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*"); // Puedes restringirlo a tu dominio frontend
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        agregarEncabezadosCORS(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        agregarEncabezadosCORS(response);
        String tipo = request.getParameter("tipo");
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            if ("registro".equalsIgnoreCase(tipo)) {
                String nombre = request.getParameter("nombreCompleto");
                String tipoDoc = request.getParameter("tipoDocumento");
                String doc = request.getParameter("numeroDocumento");
                String pass = request.getParameter("contrasena");

                Usuario u = new Usuario(nombre, tipoDoc, doc, pass);

                try {
                    boolean ok = dao.registrar(u);
                    out.println(ok ? "✅ Registrado correctamente" : "❌ Error al registrar");
                } catch (SQLException e) {
                    e.printStackTrace();
                    out.println("❌ Error en la base de datos durante el registro.");
                }

            } else if ("login".equalsIgnoreCase(tipo)) {
                String numeroDocumento = request.getParameter("numeroDocumento");
                String contrasenaInput = request.getParameter("contrasena");

                try {
                    Usuario u = dao.login(numeroDocumento, contrasenaInput);
                    if (u != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("usuario", u);
                        out.println("✅ Inicio de sesión exitoso");
                    } else {
                        out.println("❌ Credenciales inválidas");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    out.println("❌ Error en la base de datos durante el login.");
                }

            } else {
                out.println("❌ Tipo de operación no reconocido.");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        agregarEncabezadosCORS(response);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h3>🚫 Este servlet solo acepta POST. Usa un formulario o Postman.</h3>");
    }
}
