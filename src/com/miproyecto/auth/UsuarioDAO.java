package com.miproyecto.auth;

import java.sql.*;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean registrar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre_completo, tipo_documento, numero_documento, contrasena) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombreCompleto());
            stmt.setString(2, usuario.getTipoDocumento());
            stmt.setString(3, usuario.getNumeroDocumento());
            stmt.setString(4, usuario.getContrasena());
            return stmt.executeUpdate() > 0;
        }
    }

    // ✅ Método original con contraseña (por si lo quieres conservar)
    public Usuario login(String documento, String contrasena) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE numero_documento = ? AND contrasena = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, documento);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombreCompleto(rs.getString("nombre_completo"));
                u.setTipoDocumento(rs.getString("tipo_documento"));
                u.setNumeroDocumento(rs.getString("numero_documento"));
                return u;
            }
        }
        return null;
    }

    // ✅ Nuevo método: login por nombre completo y número de documento (sin contraseña)
    public Usuario loginPorNombreYDocumento(String nombre, String documento) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE nombre_completo = ? AND numero_documento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, documento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombreCompleto(rs.getString("nombre_completo"));
                u.setTipoDocumento(rs.getString("tipo_documento"));
                u.setNumeroDocumento(rs.getString("numero_documento"));
                return u;
            }
        }
        return null;
    }
}
 