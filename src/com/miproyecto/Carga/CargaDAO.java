package com.miproyecto.Carga;

import java.sql.*;
import java.util.*;

public class CargaDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/proyecto_logistica";
    private String jdbcUser = "root";
    private String jdbcPassword = "NALAH";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
    }

    public List<Carga> listarCargas() {
        List<Carga> lista = new ArrayList<>();
        String sql = "SELECT * FROM carga";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Carga c = new Carga();
                c.setIdCarga(rs.getInt("Id_Carga"));
                c.setIdTransporte(rs.getInt("Id_Transporte"));
                c.setDescripcion(rs.getString("Descripcion"));
                c.setPeso(rs.getDouble("Peso"));
                c.setEstado(rs.getString("Estado"));
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarCarga(Carga carga) {
        String sql = "INSERT INTO carga (Id_Transporte, Descripcion, Peso, Estado) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, carga.getIdTransporte());
            stmt.setString(2, carga.getDescripcion());
            stmt.setDouble(3, carga.getPeso());
            stmt.setString(4, carga.getEstado());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarCarga(Carga carga) {
        String sql = "UPDATE carga SET Id_Transporte=?, Descripcion=?, Peso=?, Estado=? WHERE Id_Carga=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, carga.getIdTransporte());
            stmt.setString(2, carga.getDescripcion());
            stmt.setDouble(3, carga.getPeso());
            stmt.setString(4, carga.getEstado());
            stmt.setInt(5, carga.getIdCarga());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarCarga(int id) {
        String sql = "DELETE FROM carga WHERE Id_Carga=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}