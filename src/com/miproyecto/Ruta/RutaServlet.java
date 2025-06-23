 package com.miproyecto.Ruta;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@MultipartConfig
public class RutaServlet extends HttpServlet {

    private RutaDAO rutaDAO = new RutaDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");

        List<Ruta> rutas = rutaDAO.listarRutas();
        String json = gson.toJson(rutas);

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
                Ruta ruta = new Ruta();
                ruta.setOrigen(request.getParameter("origen"));
                ruta.setDestino(request.getParameter("destino"));
                ruta.setTiempoEstimado(request.getParameter("tiempoEstimado")); // CORREGIDO
                ruta.setTipoVehiculo(request.getParameter("tipoVehiculo"));     // CORREGIDO
                ruta.setEstado(request.getParameter("estado"));
                rutaDAO.insertarRuta(ruta);
                out.print("{\"mensaje\":\"✅ Ruta insertada correctamente\"}");

            } else if ("actualizar".equalsIgnoreCase(accion)) {
                Ruta ruta = new Ruta();
                ruta.setIdRuta(Integer.parseInt(request.getParameter("id")));
                ruta.setOrigen(request.getParameter("origen"));
                ruta.setDestino(request.getParameter("destino"));
                ruta.setTiempoEstimado(request.getParameter("tiempoEstimado")); // CORREGIDO
                ruta.setTipoVehiculo(request.getParameter("tipoVehiculo"));     // CORREGIDO
                ruta.setEstado(request.getParameter("estado"));
                rutaDAO.actualizarRuta(ruta);
                out.print("{\"mensaje\":\"✅ Ruta actualizada correctamente\"}");

            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                rutaDAO.eliminarRuta(id);
                out.print("{\"mensaje\":\"✅ Ruta eliminada correctamente\"}");

            } else {
                out.print("{\"error\":\"❌ Acción no reconocida\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"error\":\"❌ Error: " + e.getMessage() + "\"}");
        }
    }
}