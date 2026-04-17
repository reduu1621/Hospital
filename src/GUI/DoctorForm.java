package GUI;
import GUI.DoctorForm;
import db.DBconection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.text.html.parser.Entity;

import Entity.Appointment;





public class DoctorForm extends Application {

    ComboBox<String> doctorList = new ComboBox<>();



    @Override
    public void start(Stage stage) {
        stage.setTitle("Available Dentists");



        loadAvailableDentists();

        Button nextBtn = new Button("Next (Book Appointment)");
        nextBtn.setOnAction(e -> {
            String selectedDoctor = doctorList.getSelectionModel().getSelectedItem();
          if (selectedDoctor != null) {
            int doctorId = Integer.parseInt(selectedDoctor.split(":")[0]);
            new Appointment(doctorId).start(new Stage());
           stage.close();
          }

        });

        VBox root = new VBox(15, new Label("Choose Dentist:"), doctorList, nextBtn);
        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 400, 200));
        stage.show();
    }







    private void loadAvailableDentists() {
        ObservableList<String> doctors = FXCollections.observableArrayList();
        try (Connection conn = DBconection.getConnection();
             Statement stmt = conn.createStatement()) {
            String query = "SELECT doctor_id, name FROM doctor WHERE specialization = 'Dentist' AND availability = TRUE";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("doctor_id"); //redult set(rs)
                String name = rs.getString("name");
                doctors.add(id + ": " + name);
            }
            doctorList.setItems(doctors);
            if (!doctors.isEmpty()) doctorList.getSelectionModel().selectFirst();
        } catch (Exception e) {
            showAlert("Failed to load dentists: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }
}


