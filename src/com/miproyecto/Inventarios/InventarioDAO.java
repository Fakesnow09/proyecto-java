package com.miproyecto.Inventarios;

import java.sql.*;
import java.util.*;

public class InventarioDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/proyecto_logistica";
    private String jdbcUser = "root";
    private String jdbcPassword = "NALAH";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
    }

    public List<Inventario> listarInventarios() {
        List<Inventario> lista = new ArrayList<>();
        String sql = "SELECT * FROM inventarios";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Inventario inv = new Inventario();
                inv.setIdInventario(rs.getInt("Id_Inventario"));
                inv.setNombre(rs.getString("Nombre"));
                inv.setCategoria(rs.getString("Categoria"));
                inv.setCantidadStock(rs.getInt("Cantidad_stock"));
                inv.setFechaIngreso(rs.getString("Fecha_ingreso"));
                lista.add(inv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarInventario(Inventario inv) {
        String sql = "INSERT INTO inventarios (Nombre, Categoria, Cantidad_stock, Fecha_ingreso) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, inv.getNombre());
            stmt.setString(2, inv.getCategoria());
            stmt.setInt(3, inv.getCantidadStock());
            stmt.setString(4, inv.getFechaIngreso());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarInventario(Inventario inv) {
        String sql = "UPDATE inventarios SET Nombre=?, Categoria=?, Cantidad_stock=?, Fecha_ingreso=? WHERE Id_Inventario=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, inv.getNombre());
            stmt.setString(2, inv.getCategoria());
            stmt.setInt(3, inv.getCantidadStock());
            stmt.setString(4, inv.getFechaIngreso());
            stmt.setInt(5, inv.getIdInventario());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarInventario(int id) {
        String sql = "DELETE FROM inventarios WHERE Id_Inventario=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
