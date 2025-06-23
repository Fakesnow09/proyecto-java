package com.miproyecto.Almacen;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.List;

@MultipartConfig
public class AlmacenServlet extends HttpServlet {
    private AlmacenDAO almacenDAO = new AlmacenDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");

        List<Almacen> almacen = almacenDAO.listarAlmacenes();
        String json = gson.toJson(almacen);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");

        String accion = request.getParameter("accion");
        PrintWriter out = response.getWriter();

        try {
            if ("insertar".equalsIgnoreCase(accion)) {
                Almacen a = new Almacen();
                a.setNombre(request.getParameter("nombre"));
                a.setTelefono(request.getParameter("telefono"));
                a.setDireccion(request.getParameter("direccion"));
                a.setCiudad(request.getParameter("ciudad"));
                almacenDAO.insertarAlmacen(a);
                out.print("{\"status\":\"ok\",\"mensaje\":\"Almacén insertado\"}");

            } else if ("actualizar".equalsIgnoreCase(accion)) {
                Almacen a = new Almacen();
                a.setIdAlmacen(Integer.parseInt(request.getParameter("id")));
                a.setNombre(request.getParameter("nombre"));
                a.setTelefono(request.getParameter("telefono"));
                a.setDireccion(request.getParameter("direccion"));
                a.setCiudad(request.getParameter("ciudad"));
                almacenDAO.actualizarAlmacen(a);
                out.print("{\"status\":\"ok\",\"mensaje\":\"Almacén actualizado\"}");

            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                almacenDAO.eliminarAlmacen(id);
                out.print("{\"status\":\"ok\",\"mensaje\":\"Almacén eliminado\"}");

            } else {
                out.print("{\"status\":\"error\",\"mensaje\":\"Acción no válida\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\",\"mensaje\":\"Error en el servidor\"}");
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