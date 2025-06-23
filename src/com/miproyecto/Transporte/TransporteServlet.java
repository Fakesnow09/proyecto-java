 package com.miproyecto.Transporte;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

@MultipartConfig
public class TransporteServlet extends HttpServlet {
    private TransporteDAO transporteDAO = new TransporteDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");

        List<Transporte> lista = transporteDAO.listarTransportes();
        String json = gson.toJson(lista);

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
                Transporte t = new Transporte();
                t.setVehiculo(request.getParameter("vehiculo"));
                t.setIdRuta(Integer.parseInt(request.getParameter("ruta")));
                t.setEstado(request.getParameter("estado"));
                t.setFechaSalida(Date.valueOf(request.getParameter("salida")));
                t.setFechaLlegada(Date.valueOf(request.getParameter("llegada")));
                transporteDAO.insertarTransporte(t);
                out.print("{\"mensaje\":\"✅ Transporte insertado correctamente\"}");

            } else if ("actualizar".equalsIgnoreCase(accion)) {
                Transporte t = new Transporte();
                t.setIdTransporte(Integer.parseInt(request.getParameter("id")));
                t.setVehiculo(request.getParameter("vehiculo"));
                t.setIdRuta(Integer.parseInt(request.getParameter("ruta")));
                t.setEstado(request.getParameter("estado"));
                t.setFechaSalida(Date.valueOf(request.getParameter("salida")));
                t.setFechaLlegada(Date.valueOf(request.getParameter("llegada")));
                transporteDAO.actualizarTransporte(t);
                out.print("{\"mensaje\":\"✅ Transporte actualizado correctamente\"}");

            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                transporteDAO.eliminarTransporte(id);
                out.print("{\"mensaje\":\"✅ Transporte eliminado correctamente\"}");

            } else {
                out.print("{\"error\":\"❌ Acción no reconocida\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"error\":\"❌ Error: " + e.getMessage() + "\"}");
        }
    }
}