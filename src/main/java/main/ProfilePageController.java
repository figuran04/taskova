package main;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.User;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ProfilePageController {

    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Label titleApp;

    @FXML
    public void initialize() {
        titleApp.setText("TASKOVA");
        titleApp.setStyle("-fx-font-weight: bold;");
    }

    @FXML
    private void saveProfile() {
        User user = new User();
        user.setUsername(titleField.getText());
        user.setBio(descriptionField.getText());

        // Simpan data pengguna ke database atau session
        try {
            App.setRoot("dashboardPage");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancel() {
        try {
            App.setRoot("dashboardPage");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
