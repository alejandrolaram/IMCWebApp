package org.example.imcwebapp.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.example.imcwebapp.model.Persona;
import org.springframework.stereotype.Repository;

@Repository
public class PersonaRepository {

    // Configuración de la conexión a XAMPP MySQL
    private final String url = "jdbc:mysql://localhost:3306/imc_db?useSSL=false&serverTimezone=UTC";
    private final String user = "root";
    private final String password = "";

    // Método para establecer la conexión nativa JDBC
    private Connection getConnection() throws SQLException {
        try {
            // Forzar la carga del driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MySQL no encontrado", e);
        }
        return DriverManager.getConnection(url, user, password);
    }

    // Método para guardar una persona con su IMC calculado
    public boolean guardar(Persona persona) {
        String sql = "INSERT INTO personas (nombre, peso, estatura, imc, resultado) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, persona.getNombre());
            ps.setDouble(2, persona.getPeso());
            ps.setDouble(3, persona.getEstatura());
            ps.setDouble(4, persona.getImc());
            ps.setString(5, persona.getResultado());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar en la base de datos: " + e.getMessage());
            return false;
        }
    }
}