package com.miproyecto.Entregas;

import com.google.gson.Gson; 
import jakarta.servlet.*; 
import jakarta.servlet.annotation.MultipartConfig; 
import jakarta.servlet.http.*; 
import java.io.*; 
import java.sql.Date; 
import java.util.List;

@MultipartConfig public class EntregaServlet extends HttpServlet { 
    private final EntregaDAO entregaDAO = new EntregaDAO(); 
    private final Gson gson = new Gson();

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setContentType("application/json");

    List<Entrega> entregas = entregaDAO.listarEntregas();
    String json = gson.toJson(entregas);

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
            Entrega entrega = new Entrega();
            entrega.setIdPedido(Integer.parseInt(request.getParameter("idPedido")));
            entrega.setIdTransporte(Integer.parseInt(request.getParameter("idTransporte")));
            entrega.setFechaSalida(Date.valueOf(request.getParameter("fechaSalida")));
            entrega.setFechaLlegada(Date.valueOf(request.getParameter("fechaLlegada")));
            entrega.setEstado(request.getParameter("estado"));

            entregaDAO.insertarEntrega(entrega);
            out.print("{\"status\":\"ok\",\"mensaje\":\"Entrega insertada correctamente\"}");

        } else if ("actualizar".equalsIgnoreCase(accion)) {
            Entrega entrega = new Entrega();
            entrega.setIdEntrega(Integer.parseInt(request.getParameter("id")));
            entrega.setIdPedido(Integer.parseInt(request.getParameter("idPedido")));
            entrega.setIdTransporte(Integer.parseInt(request.getParameter("idTransporte")));
            entrega.setFechaSalida(Date.valueOf(request.getParameter("fechaSalida")));
            entrega.setFechaLlegada(Date.valueOf(request.getParameter("fechaLlegada")));
            entrega.setEstado(request.getParameter("estado"));

            entregaDAO.actualizarEntrega(entrega);
            out.print("{\"status\":\"ok\",\"mensaje\":\"Entrega actualizada correctamente\"}");

        } else if ("eliminar".equalsIgnoreCase(accion)) {
            int id = Integer.parseInt(request.getParameter("id"));
            entregaDAO.eliminarEntrega(id);
            out.print("{\"status\":\"ok\",\"mensaje\":\"Entrega eliminada correctamente\"}");

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
