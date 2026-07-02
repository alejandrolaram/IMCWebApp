package org.example.imcwebapp.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.imcwebapp.model.Persona;
import org.springframework.stereotype.Repository;

@Repository
public class PersonaRepository {

    // Se añaden los parámetros de compatibilidad necesarios para el enlace con XAMPP
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

    // Guarda una nueva medición asociada a un usuario específico
    public boolean guardar(Persona persona) {
        String sql = "INSERT INTO mediciones_imc (usuario_id, peso, imc, resultado) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, persona.getUsuarioId());
            ps.setDouble(2, persona.getPeso());
            ps.setDouble(3, persona.getImc());
            ps.setString(4, persona.getResultado());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar medición IMC: " + e.getMessage());
            return false;
        }
    }

    // Obtiene todo el historial de cálculos de un usuario específico
    public List<Persona> obtenerHistorialPorUsuario(int usuarioId) {
        List<Persona> historial = new ArrayList<>();
        String sql = "SELECT m.*, u.estatura FROM mediciones_imc m " +
                     "JOIN usuarios u ON m.usuario_id = u.id " +
                     "WHERE m.usuario_id = ? ORDER BY m.fecha_registro DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, usuarioId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Persona p = new Persona();
                    p.setId(rs.getInt("id"));
                    p.setUsuarioId(rs.getInt("usuario_id"));
                    p.setPeso(rs.getDouble("peso"));
                    p.setEstatura(rs.getDouble("estatura"));
                    p.setImc(rs.getDouble("imc"));
                    p.setResultado(rs.getString("resultado"));
                    p.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                    historial.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener historial IMC: " + e.getMessage());
        }
        return historial;
    }
}