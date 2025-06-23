package com.miproyecto.Transporte;

import java.sql.*;
import java.util.*;

public class TransporteDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/proyecto_logistica";
    private String jdbcUser = "root";
    private String jdbcPassword = "NALAH";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
    }

    public List<Transporte> listarTransportes() {
        List<Transporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM transporte";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transporte t = new Transporte();
                t.setIdTransporte(rs.getInt("Id_Transporte"));
                t.setVehiculo(rs.getString("Vehiculo"));
                t.setIdRuta(rs.getInt("Id_Ruta"));
                t.setEstado(rs.getString("Estado"));
                t.setFechaSalida(rs.getDate("Fecha_salida"));
                t.setFechaLlegada(rs.getDate("Fecha_llegada"));
                lista.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarTransporte(Transporte t) {
        String sql = "INSERT INTO transporte (Vehiculo, Id_Ruta, Estado, Fecha_salida, Fecha_llegada) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getVehiculo());
            stmt.setInt(2, t.getIdRuta());
            stmt.setString(3, t.getEstado());
            stmt.setDate(4, t.getFechaSalida());
            stmt.setDate(5, t.getFechaLlegada());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarTransporte(Transporte t) {
        String sql = "UPDATE transporte SET Vehiculo=?, Id_Ruta=?, Estado=?, Fecha_salida=?, Fecha_llegada=? WHERE Id_Transporte=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getVehiculo());
            stmt.setInt(2, t.getIdRuta());
            stmt.setString(3, t.getEstado());
            stmt.setDate(4, t.getFechaSalida());
            stmt.setDate(5, t.getFechaLlegada());
            stmt.setInt(6, t.getIdTransporte());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarTransporte(int id) {
        String sql = "DELETE FROM transporte WHERE Id_Transporte=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
