package Entity;

import db.DBconection;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Appointment {

    private final int doctorId;//encapsulate the doctorId
    TextField patientIdField = new TextField();
    TextField dateField = new TextField();

    public Appointment(int doctorId) {
        this.doctorId = doctorId;
    }

    public void start(Stage stage) {
        stage.setTitle("Book Appointment");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Patient ID:"), 0, 0);
        grid.add(patientIdField, 1, 0);

        grid.add(new Label("Appointment Date (YYYY-MM-DD):"), 0, 1);
        grid.add(dateField, 1, 1);

        Button bookBtn = new Button("Book Appointment");
        bookBtn.setOnAction(e -> bookAppointment(stage));

        grid.add(bookBtn, 1, 2);

        stage.setScene(new Scene(grid, 400, 200));
        stage.show();
    }

    private void bookAppointment(Stage stage) {//encapsulate the bookAppointment method
        try {
            int patientId = Integer.parseInt(patientIdField.getText().trim());
            String date = dateField.getText().trim();

            try (Connection conn = DBconection.getConnection()) {
                String query = "INSERT INTO appointment (patient_id, doctor_id, appointment_date, status) VALUES (?, ?, ?, 'Scheduled')";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, patientId);
                stmt.setInt(2, doctorId);
                stmt.setString(3, date);
                stmt.executeUpdate();
            }

            showAlert(Alert.AlertType.INFORMATION, "Appointment booked successfully!");
            stage.close();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Failed to book appointment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
