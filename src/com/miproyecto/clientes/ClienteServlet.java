package com.miproyecto.clientes;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.List;

@MultipartConfig
public class ClienteServlet extends HttpServlet {
    private ClienteDAO clienteDAO = new ClienteDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");

        List<Cliente> clientes = clienteDAO.listarClientes();
        String json = gson.toJson(clientes);

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
                Cliente cliente = new Cliente();
                cliente.setNombre(request.getParameter("nombre"));
                cliente.setApellido(request.getParameter("apellido"));
                cliente.setCorreo(request.getParameter("correo"));
                cliente.setTelefono(request.getParameter("telefono"));
                cliente.setDireccion(request.getParameter("direccion"));
                cliente.setCiudad(request.getParameter("ciudad"));
                clienteDAO.insertarCliente(cliente);
                out.print("{\"status\":\"ok\",\"mensaje\":\"Cliente insertado correctamente\"}");

            } else if ("actualizar".equalsIgnoreCase(accion)) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(Integer.parseInt(request.getParameter("id")));
                cliente.setNombre(request.getParameter("nombre"));
                cliente.setApellido(request.getParameter("apellido"));
                cliente.setCorreo(request.getParameter("correo"));
                cliente.setTelefono(request.getParameter("telefono"));
                cliente.setDireccion(request.getParameter("direccion"));
                cliente.setCiudad(request.getParameter("ciudad"));
                clienteDAO.actualizarCliente(cliente);
                out.print("{\"status\":\"ok\",\"mensaje\":\"Cliente actualizado correctamente\"}");

            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                clienteDAO.eliminarCliente(id);
                out.print("{\"status\":\"ok\",\"mensaje\":\"Cliente eliminado correctamente\"}");

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