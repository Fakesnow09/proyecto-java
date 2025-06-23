 package com.miproyecto.Carga;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@MultipartConfig
public class CargaServlet extends HttpServlet {
    private CargaDAO cargaDAO = new CargaDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");

        List<Carga> cargas = cargaDAO.listarCargas();
        String json = gson.toJson(cargas);

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
                Carga c = new Carga();
                c.setIdTransporte(Integer.parseInt(request.getParameter("idTransporte")));
                c.setDescripcion(request.getParameter("descripcion"));
                c.setPeso(Double.parseDouble(request.getParameter("peso")));
                c.setEstado(request.getParameter("estado"));
                cargaDAO.insertarCarga(c);
                out.print("{\"status\":\"ok\", \"mensaje\": \"✅ Carga insertada correctamente\"}");

            } else if ("actualizar".equalsIgnoreCase(accion)) {
                Carga c = new Carga();
                c.setIdCarga(Integer.parseInt(request.getParameter("id")));
                c.setIdTransporte(Integer.parseInt(request.getParameter("idTransporte")));
                c.setDescripcion(request.getParameter("descripcion"));
                c.setPeso(Double.parseDouble(request.getParameter("peso")));
                c.setEstado(request.getParameter("estado"));
                cargaDAO.actualizarCarga(c);
                out.print("{\"status\":\"ok\", \"mensaje\": \"✅ Carga actualizada correctamente\"}");

            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                cargaDAO.eliminarCarga(id);
                out.print("{\"status\":\"ok\", \"mensaje\": \"✅ Carga eliminada correctamente\"}");

            } else {
                out.print("{\"status\":\"error\", \"mensaje\": \"⚠️ Acción no válida\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\", \"mensaje\": \"❌ Error del servidor: " + e.getMessage() + "\"}");
        }

        out.flush();
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}