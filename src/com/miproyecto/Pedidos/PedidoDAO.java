package com.miproyecto.Pedidos;

import java.sql.*;
import java.util.*;

public class PedidoDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/proyecto_logistica";
    private String jdbcUser = "root";
    private String jdbcPassword = "NALAH";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
    }

    public List<Pedido> listarPedidos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getInt("id_pedido"));
                p.setIdCliente(rs.getInt("id_cliente"));
                p.setIdProducto(rs.getInt("id_producto"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setEstado(rs.getString("estado"));
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarPedido(Pedido pedido) {
        String sql = "INSERT INTO pedidos (id_cliente, id_producto, cantidad, estado) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setInt(2, pedido.getIdProducto());
            stmt.setInt(3, pedido.getCantidad());
            stmt.setString(4, pedido.getEstado());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarPedido(Pedido pedido) {
        String sql = "UPDATE pedidos SET id_cliente=?, id_producto=?, cantidad=?, estado=? WHERE id_pedido=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setInt(2, pedido.getIdProducto());
            stmt.setInt(3, pedido.getCantidad());
            stmt.setString(4, pedido.getEstado());
            stmt.setInt(5, pedido.getIdPedido());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPedido(int id) {
        String sql = "DELETE FROM pedidos WHERE id_pedido=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}