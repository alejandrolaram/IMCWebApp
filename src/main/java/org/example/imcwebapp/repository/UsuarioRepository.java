package org.example.imcwebapp.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.imcwebapp.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {

    // Se añaden parámetros para asegurar la compatibilidad con el MySQL de XAMPP desde GlassFish
    private final String url = "jdbc:mysql://localhost:3306/imc_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private final String user = "root";
    private final String password = "";

    private Connection getConnection() throws SQLException {
        try {
            // Forzamos la carga explícita de la clase del Driver en GlassFish
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MySQL no encontrado en el servidor", e);
        }
        return DriverManager.getConnection(url, user, password);
    }

    // Registra un nuevo usuario en la base de datos
    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre_completo, nombre_usuario, contrasenia, edad, sexo, estatura) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombreCompleto());
            ps.setString(2, usuario.getNombreUsuario());
            ps.setString(3, usuario.getContrasenia());
            ps.setInt(4, usuario.getEdad());
            ps.setString(5, usuario.getSexo());
            ps.setDouble(6, usuario.getEstatura());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    // Verifica si un nombre de usuario ya existe (para evitar duplicados)
    public boolean existeUsuario(String nombreUsuario) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE nombre_usuario = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de usuario: " + e.getMessage());
        }
        return false;
    }

    // Valida las credenciales y retorna el objeto Usuario si es correcto
    public Usuario login(String nombreUsuario, String contrasenia) {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasenia = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasenia);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre_completo"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasenia"),
                        rs.getInt("edad"),
                        rs.getString("sexo"),
                        rs.getDouble("estatura")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
        }
        return null;
    }
}