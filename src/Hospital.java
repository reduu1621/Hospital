import java.sql.*;

public class Hospital {
    public static void main(String[] args) {
        // Connect without specifying database first (to create it)
        String url = "jdbc:mysql://localhost:3306/hospital_db";
        String user = "root"; 
        String password = ""; 

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // Create the database if not exists
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS hospital_db");
            stmt.executeUpdate("USE hospital_db");

            // Create doctor table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS doctor (
                    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100),
                    specialization VARCHAR(50),
                    availability BOOLEAN
                )
            """);

            // Create patient table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS patient (
                    patient_id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100),
                    age INT,
                    gender VARCHAR(10),
                    contact VARCHAR(10) NOT NULL,
                    selectedDoctor VARCHAR(50)
                )
            """);

            // Create appointment table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS appointment (
                    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
                    patient_id INT,
                    doctor_id INT,
                    appointment_date DATE,
                    status VARCHAR(20),
                    FOREIGN KEY (patient_id) REFERENCES patient(patient_id),
                    FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id)
                )
            """);

            // Insert dentists - NOTE: this will throw an error if run multiple times without checking duplicates.
            stmt.executeUpdate("""
                INSERT INTO doctor (name, specialization, availability) VALUES
                ('Dr. Rediet', 'Dentist', TRUE),
                ('Dr. Natnael', 'Dentist', TRUE),
                ('Dr. Hafize', 'Dentist', TRUE),
                ('Dr. Yiferu', 'Dentist', TRUE),
                ('Dr. Dawit', 'Dentist', TRUE),
                ('Dr. Elbetel', 'Dentist', TRUE),
                ('Dr. Genet', 'Dentist', TRUE),
                ('Dr. Abera', 'Dentist', TRUE),
                ('Dr. Tsdeniya', 'Dentist', TRUE),
                ('Dr. Lidiya', 'Dentist', TRUE)
            """);

            System.out.println("✅ Database and tables initialized successfully.");

            // Query available dentists
            ResultSet rs = stmt.executeQuery("SELECT doctor_id, name FROM doctor WHERE specialization = 'Dentist' AND availability = TRUE");
            while (rs.next()) {
                int id = rs.getInt("doctor_id");
                String name = rs.getString("name");
                System.out.println("Loaded Doctor: " + id + ": " + name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Database initialization failed: " + e.getMessage());
        }
    }
}
