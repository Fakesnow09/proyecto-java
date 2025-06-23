package com.miproyecto.Productos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@MultipartConfig
public class ProductoServlet extends HttpServlet {
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Producto> productos = productoDAO.listarProductos();
        String json = gson.toJson(productos);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        String accion = request.getParameter("accion");

        try {
            if ("insertar".equalsIgnoreCase(accion)) {
                Producto producto = new Producto();
                producto.setNombre(request.getParameter("nombre"));
                producto.setCategoria(request.getParameter("categoria"));
                producto.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                producto.setFechaVencimiento(request.getParameter("fecha_vencimiento"));
                producto.setMarca(request.getParameter("marca"));
                producto.setPrecioUnitario(Double.parseDouble(request.getParameter("precio_unitario"))); // ← CAMBIO aquí

                productoDAO.insertarProducto(producto);

                jsonResponse.addProperty("mensaje", "✅ Producto insertado correctamente");

            } else if ("actualizar".equalsIgnoreCase(accion)) {
                Producto producto = new Producto();
                producto.setIdProducto(Integer.parseInt(request.getParameter("id")));
                producto.setNombre(request.getParameter("nombre"));
                producto.setCategoria(request.getParameter("categoria"));
                producto.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                producto.setFechaVencimiento(request.getParameter("fecha_vencimiento"));
                producto.setMarca(request.getParameter("marca"));
                producto.setPrecioUnitario(Double.parseDouble(request.getParameter("precio_unitario"))); // ← CAMBIO aquí

                productoDAO.actualizarProducto(producto);

                jsonResponse.addProperty("mensaje", "✅ Producto actualizado correctamente");

            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                productoDAO.eliminarProducto(id);

                jsonResponse.addProperty("mensaje", "✅ Producto eliminado correctamente");

            } else {
                jsonResponse.addProperty("error", "❌ Acción no reconocida");
            }

        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.addProperty("error", "❌ Error: " + e.getMessage());
        }

        out.print(gson.toJson(jsonResponse));
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