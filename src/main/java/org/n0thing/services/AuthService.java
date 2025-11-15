package org.n0thing.services;

import org.mindrot.jbcrypt.BCrypt;

import org.n0thing.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AuthService {

        //Registrar nuevo usuario
        public static boolean register (String email, String password, String nombre){
            String sql = "INSERT INTO usuarios (email, password_hash, nombre, fecha_registro) VALUES (?, ?, ?, ?)";

            try (Connection conn = DatabaseService.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                pstmt.setString(1, email);
                pstmt.setString(2, hashedPassword);
                pstmt.setString(3, nombre);
                pstmt.setString(4, LocalDateTime.now().toString());

                pstmt.executeUpdate();
                return true;

            } catch (SQLException e) {
                System.err.println("Error al registrar usuario: " + e.getMessage());
                return false;
            }
        }

        //Iniciar Sesion
        public static User login(String email, String password) {
            String sql = "SELECT * FROM usuarios WHERE email = ?";

            try (Connection conn = DatabaseService.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");

                    // Verificar contraseña
                    if (BCrypt.checkpw(password, storedHash)) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setEmail(rs.getString("email"));
                        user.setNombre(rs.getString("nombre"));
                        return user;
                    }
                }

            } catch (SQLException e) {
                System.err.println("Error al iniciar sesión: " + e.getMessage());
            }

            return null;
        }

        // Verificar si email ya existe
        public static boolean emailExists(String email) {
            String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";

            try (Connection conn = DatabaseService.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();

                return rs.next() && rs.getInt(1) > 0;

            } catch (SQLException e) {
                System.err.println("Error al verificar email: " + e.getMessage());
                return false;
            }
        }
}
