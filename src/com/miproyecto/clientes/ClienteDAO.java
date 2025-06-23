package com.miproyecto.clientes;

import java.sql.*;
import java.util.*;

public class ClienteDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/proyecto_logistica";
    private String jdbcUser = "root";
    private String jdbcPassword = "NALAH";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
    }

    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("Id_Cliente"));
                c.setNombre(rs.getString("Nombre"));
                c.setApellido(rs.getString("Apellido"));
                c.setCorreo(rs.getString("Correo"));
                c.setTelefono(rs.getString("Telefono"));
                c.setDireccion(rs.getString("Direccion"));
                c.setCiudad(rs.getString("Ciudad"));
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (Nombre, Apellido, Correo, Telefono, Direccion, Ciudad) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getCorreo());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getDireccion());
            stmt.setString(6, cliente.getCiudad());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET Nombre=?, Apellido=?, Correo=?, Telefono=?, Direccion=?, Ciudad=? WHERE Id_Cliente=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getCorreo());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getDireccion());
            stmt.setString(6, cliente.getCiudad());
            stmt.setInt(7, cliente.getIdCliente());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarCliente(int idCliente) {
        String sql = "DELETE FROM clientes WHERE Id_Cliente=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
