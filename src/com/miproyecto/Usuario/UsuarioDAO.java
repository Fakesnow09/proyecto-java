package com.miproyecto.Usuario;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {
    private Connection conn;
    public UsuarioDAO() {
    try {
        this.conn = Conexion.getConexion();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }

    // Registro: Hashea la contraseña antes de guardar
    public boolean registrar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre_completo, tipo_documento, numero_documento, contrasena) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String hashedPassword = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt());

            stmt.setString(1, usuario.getNombreCompleto());
            stmt.setString(2, usuario.getTipoDocumento());
            stmt.setString(3, usuario.getNumeroDocumento());
            stmt.setString(4, hashedPassword);

            return stmt.executeUpdate() > 0;
        }
    }

    // Login usando BCrypt para verificar la contraseña
    public Usuario login(String documento, String contrasena) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE numero_documento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, documento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("contrasena");

                // Verifica si la contraseña ingresada coincide con la hasheada
                if (BCrypt.checkpw(contrasena, hashedPassword)) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNombreCompleto(rs.getString("nombre_completo"));
                    u.setTipoDocumento(rs.getString("tipo_documento"));
                    u.setNumeroDocumento(rs.getString("numero_documento"));
                    return u;
                }
            }
        }
        return null;
    }

    // Puedes eliminar este si no usarás login por nombre y documento
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
