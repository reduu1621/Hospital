package DAO;//data accses object

import db.DBconection;
import Entity.Patient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientDAOo {
    public void addPatient(Patient patient) {
        String sql = "INSERT INTO patient (name, age, gender, contact, SelectedDoctor) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBconection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
                // accesing the database connection in encapsulated DBconection class
            stmt.setString(1, patient.getName());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getGender());
            stmt.setString(4, patient.getContact());
            stmt.setString(5, patient.getSelectedDoctor());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
