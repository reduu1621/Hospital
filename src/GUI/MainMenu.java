package GUI;
import GUI.PatientForm;
import Entity.Doctor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hospital Registration System");

        Button patientBtn = new Button("I am a Patient");
        patientBtn.setOnAction(e -> {
            new PatientForm().start(new Stage());
            primaryStage.close();
        });

        Button doctorBtn = new Button("I am a Doctor");
        doctorBtn.setOnAction(e -> {
            new DoctorForm().start(new Stage());
            primaryStage.close();
        });

        VBox root = new VBox(15, patientBtn, doctorBtn);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        primaryStage.setScene(new Scene(root, 300, 150));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
