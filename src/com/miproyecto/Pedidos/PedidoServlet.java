package com.miproyecto.Pedidos;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@MultipartConfig
public class PedidoServlet extends HttpServlet {
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");

        List<Pedido> pedidos = pedidoDAO.listarPedidos();
        String json = gson.toJson(pedidos);

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
                Pedido p = new Pedido();
                p.setIdCliente(Integer.parseInt(request.getParameter("id_cliente")));
                p.setIdProducto(Integer.parseInt(request.getParameter("id_producto")));
                p.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                p.setEstado(request.getParameter("estado"));

                pedidoDAO.insertarPedido(p);
                out.print("{\"mensaje\":\"✅ Pedido insertado correctamente\"}");

            } else if ("actualizar".equalsIgnoreCase(accion)) {
                Pedido p = new Pedido();
                p.setIdPedido(Integer.parseInt(request.getParameter("id")));
                p.setIdCliente(Integer.parseInt(request.getParameter("id_cliente")));
                p.setIdProducto(Integer.parseInt(request.getParameter("id_producto")));
                p.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                p.setEstado(request.getParameter("estado"));

                pedidoDAO.actualizarPedido(p);
                out.print("{\"mensaje\":\"✅ Pedido actualizado correctamente\"}");

            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                pedidoDAO.eliminarPedido(id);
                out.print("{\"mensaje\":\"✅ Pedido eliminado correctamente\"}");

            } else {
                out.print("{\"error\":\"❌ Acción no reconocida\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"error\":\"❌ Error: " + e.getMessage() + "\"}");
        }
    }
}