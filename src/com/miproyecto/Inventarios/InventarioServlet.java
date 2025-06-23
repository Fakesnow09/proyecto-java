 package com.miproyecto.Inventarios;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@MultipartConfig
public class InventarioServlet extends HttpServlet {
    private InventarioDAO inventarioDAO = new InventarioDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");

        List<Inventario> inventarios = inventarioDAO.listarInventarios();
        String json = gson.toJson(inventarios);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String accion = request.getParameter("accion");

        try {
            if ("insertar".equalsIgnoreCase(accion)) {
                Inventario inv = new Inventario();
                inv.setNombre(request.getParameter("nombre"));
                inv.setCategoria(request.getParameter("categoria"));
                inv.setCantidadStock(Integer.parseInt(request.getParameter("cantidad")));
                inv.setFechaIngreso(request.getParameter("fecha"));

                inventarioDAO.insertarInventario(inv);
                out.print("{\"status\":\"ok\",\"mensaje\":\"Inventario insertado correctamente\"}");

            } else if ("actualizar".equalsIgnoreCase(accion)) {
                Inventario inv = new Inventario();
                inv.setIdInventario(Integer.parseInt(request.getParameter("id")));
                inv.setNombre(request.getParameter("nombre"));
                inv.setCategoria(request.getParameter("categoria"));
                inv.setCantidadStock(Integer.parseInt(request.getParameter("cantidad")));
                inv.setFechaIngreso(request.getParameter("fecha"));

                inventarioDAO.actualizarInventario(inv);
                out.print("{\"status\":\"ok\",\"mensaje\":\"Inventario actualizado correctamente\"}");

            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                inventarioDAO.eliminarInventario(id);
                out.print("{\"status\":\"ok\",\"mensaje\":\"Inventario eliminado correctamente\"}");

            } else {
                out.print("{\"status\":\"error\",\"mensaje\":\"Acción no reconocida\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\",\"mensaje\":\"Error del servidor: " + e.getMessage() + "\"}");
        }

        out.flush();
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}