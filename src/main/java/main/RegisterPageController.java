/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author User
 */
public class RegisterPageController {

    @FXML
    private Label titleApp;

    @FXML
    private void initialize() {
        titleApp.setText("TASKOVA");
        titleApp.setStyle("-fx-font-weight: bold;");
    }

    @FXML
    private void registerButton() {
        try {
            App.setRoot("loginpage");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
