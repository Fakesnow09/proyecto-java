package com.miproyecto.Almacen;

import java.sql.*;
import java.util.*;

public class AlmacenDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/proyecto_logistica";
    private String jdbcUser = "root";
    private String jdbcPassword = "NALAH";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
    }

    public List<Almacen> listarAlmacenes() {
        List<Almacen> lista = new ArrayList<>();
        String sql = "SELECT * FROM almacen";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Almacen a = new Almacen();
                a.setIdAlmacen(rs.getInt("Id_Almacen"));
                a.setNombre(rs.getString("Nombre"));
                a.setTelefono(rs.getString("Telefono"));
                a.setDireccion(rs.getString("Direccion"));
                a.setCiudad(rs.getString("Ciudad"));
                lista.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarAlmacen(Almacen a) {
        String sql = "INSERT INTO almacen (Nombre, Telefono, Direccion, Ciudad) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getNombre());
            stmt.setString(2, a.getTelefono());
            stmt.setString(3, a.getDireccion());
            stmt.setString(4, a.getCiudad());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarAlmacen(Almacen a) {
        String sql = "UPDATE almacen SET Nombre=?, Telefono=?, Direccion=?, Ciudad=? WHERE Id_Almacen=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getNombre());
            stmt.setString(2, a.getTelefono());
            stmt.setString(3, a.getDireccion());
            stmt.setString(4, a.getCiudad());
            stmt.setInt(5, a.getIdAlmacen());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarAlmacen(int id) {
        String sql = "DELETE FROM almacen WHERE Id_Almacen=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}