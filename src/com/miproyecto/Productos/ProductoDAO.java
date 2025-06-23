package com.miproyecto.Productos;

import java.sql.*;
import java.util.*;

public class ProductoDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/proyecto_logistica";
    private String jdbcUser = "root";
    private String jdbcPassword = "NALAH";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
    }

    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("Id_Producto"));
                p.setNombre(rs.getString("Nombre"));
                p.setCategoria(rs.getString("Categoria"));
                p.setCantidad(rs.getInt("Cantidad"));
                p.setFechaVencimiento(rs.getString("Fecha_vencimiento"));
                p.setMarca(rs.getString("Marca"));
                p.setPrecioUnitario(rs.getDouble("Precio_Unitario"));
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarProducto(Producto producto) {
        String sql = "INSERT INTO productos (Nombre, Categoria, Cantidad, Fecha_vencimiento, Marca, Precio_Unitario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setInt(3, producto.getCantidad());
            stmt.setString(4, producto.getFechaVencimiento());
            stmt.setString(5, producto.getMarca());
            stmt.setDouble(6, producto.getPrecioUnitario());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET Nombre=?, Categoria=?, Cantidad=?, Fecha_vencimiento=?, Marca=?, Precio_Unitario=? WHERE Id_Producto=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setInt(3, producto.getCantidad());
            stmt.setString(4, producto.getFechaVencimiento());
            stmt.setString(5, producto.getMarca());
            stmt.setDouble(6, producto.getPrecioUnitario());
            stmt.setInt(7, producto.getIdProducto());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarProducto(int idProducto) {
        String sql = "DELETE FROM productos WHERE Id_Producto=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProducto);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
