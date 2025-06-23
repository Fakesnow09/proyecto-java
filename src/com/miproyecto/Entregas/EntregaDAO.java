package com.miproyecto.Entregas;

import java.sql.*;
import java.util.*;

public class EntregaDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/proyecto_logistica";
    private String jdbcUser = "root";
    private String jdbcPassword = "NALAH";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
    }

    public List<Entrega> listarEntregas() {
        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM entregas";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Entrega e = new Entrega();
                e.setIdEntrega(rs.getInt("Id_Entrega"));
                e.setIdPedido(rs.getInt("Id_Pedido"));
                e.setIdTransporte(rs.getInt("Id_Transporte"));
                e.setFechaSalida(rs.getDate("Fecha_salida"));
                e.setFechaLlegada(rs.getDate("Fecha_llegada"));
                e.setEstado(rs.getString("Estado"));
                lista.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarEntrega(Entrega e) {
        String sql = "INSERT INTO entregas (Id_Pedido, Id_Transporte, Fecha_salida, Fecha_llegada, Estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, e.getIdPedido());
            stmt.setInt(2, e.getIdTransporte());
            stmt.setDate(3, e.getFechaSalida());
            stmt.setDate(4, e.getFechaLlegada());
            stmt.setString(5, e.getEstado());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizarEntrega(Entrega e) {
        String sql = "UPDATE entregas SET Id_Pedido=?, Id_Transporte=?, Fecha_salida=?, Fecha_llegada=?, Estado=? WHERE Id_Entrega=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, e.getIdPedido());
            stmt.setInt(2, e.getIdTransporte());
            stmt.setDate(3, e.getFechaSalida());
            stmt.setDate(4, e.getFechaLlegada());
            stmt.setString(5, e.getEstado());
            stmt.setInt(6, e.getIdEntrega());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminarEntrega(int id) {
        String sql = "DELETE FROM entregas WHERE Id_Entrega=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}