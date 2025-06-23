package com.miproyecto.Ruta;


import java.sql.*;
import java.util.*;

public class RutaDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/proyecto_logistica";
    private String jdbcUser = "root";
    private String jdbcPassword = "NALAH";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
    }

    public List<Ruta> listarRutas() {
        List<Ruta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ruta";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Ruta r = new Ruta();
                r.setIdRuta(rs.getInt("Id_Ruta"));
                r.setOrigen(rs.getString("Origen"));
                r.setDestino(rs.getString("Destino"));
                r.setTiempoEstimado(rs.getString("Tiempo_estimado"));
                r.setTipoVehiculo(rs.getString("Tipo_vehiculo"));
                r.setEstado(rs.getString("Estado"));
                lista.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarRuta(Ruta r) {
        String sql = "INSERT INTO ruta (Origen, Destino, Tiempo_estimado, Tipo_vehiculo, Estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, r.getOrigen());
            stmt.setString(2, r.getDestino());
            stmt.setString(3, r.getTiempoEstimado());
            stmt.setString(4, r.getTipoVehiculo());
            stmt.setString(5, r.getEstado());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarRuta(Ruta r) {
        String sql = "UPDATE ruta SET Origen=?, Destino=?, Tiempo_estimado=?, Tipo_vehiculo=?, Estado=? WHERE Id_Ruta=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, r.getOrigen());
            stmt.setString(2, r.getDestino());
            stmt.setString(3, r.getTiempoEstimado());
            stmt.setString(4, r.getTipoVehiculo());
            stmt.setString(5, r.getEstado());
            stmt.setInt(6, r.getIdRuta());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarRuta(int id) {
        String sql = "DELETE FROM ruta WHERE Id_Ruta=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
