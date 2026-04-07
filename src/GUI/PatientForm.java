
package GUI;

import DAO.PatientDAOo;
import Entity.Patient;
import db.DBconection;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PatientForm extends Application {

    TextField nameField = new TextField();
    TextField ageField = new TextField();
    ComboBox<String> genderCombo = new ComboBox<>();
    TextField contactField = new TextField();
    ComboBox<String> doctorList = new ComboBox<>();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Patient Registration");

        genderCombo.getItems().addAll("Male", "Female");
        genderCombo.getSelectionModel().selectFirst();

        loadAvailableDentists();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);

        grid.add(new Label("Age:"), 0, 1);
        grid.add(ageField, 1, 1);

        grid.add(new Label("Gender:"), 0, 2);
        grid.add(genderCombo, 1, 2);

        grid.add(new Label("Contact:"), 0, 3);
        grid.add(contactField, 1, 3);

        grid.add(new Label("Choose Dentist:"), 0, 4);
        grid.add(doctorList, 1, 4);

        Button submit = new Button("Register Patient");
        submit.setOnAction(e -> addPatient());

        grid.add(submit, 1, 5);

        stage.setScene(new Scene(grid, 400, 300));
        stage.show();
    }

    private void loadAvailableDentists() { 
        ObservableList<String> doctors = FXCollections.observableArrayList();
        try (Connection conn = DBconection.getConnection();
             Statement stmt = conn.createStatement()) {
            String query = "SELECT doctor_id, name FROM doctor WHERE specialization = 'Dentist' AND availability = TRUE";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("doctor_id");
                String name = rs.getString("name");
                doctors.add(id + ": " + name);
            }
            doctorList.setItems(doctors);
            if (!doctors.isEmpty()) doctorList.getSelectionModel().selectFirst();
        } catch (Exception e) {
            showAlert("Error loading doctors: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addPatient() {
        try {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String gender = genderCombo.getSelectionModel().getSelectedItem();
            String contact = contactField.getText().trim();
            String selectedDoctor = doctorList.getSelectionModel().getSelectedItem();

            if (name.isEmpty() || ageText.isEmpty() || contact.isEmpty() || selectedDoctor == null) {
                showAlert("Please fill all fields and select a doctor.");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
                if (age <= 0) {
                    showAlert("Age must be a positive number.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("Age must be a valid number.");
                return;
            }

            if (!contact.matches("^09\\d{8}$")) {
                showAlert("Contact must be exactly 10 digits and start with '09'.");
                return;
            }

            // Optionally, extract only the doctor's name or ID if needed
            // Example: String doctorName = selectedDoctor.split(": ", 2)[1];

            Patient patient = new Patient(name, age, gender, contact, selectedDoctor);
            PatientDAOo dao = new PatientDAOo();
            dao.addPatient(patient);

            showAlert("Patient registered successfully!");
            ((Stage) nameField.getScene().getWindow()).close();
        } catch (Exception e) {
            showAlert("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
}