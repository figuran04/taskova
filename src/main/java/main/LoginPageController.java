/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * @author User
 */
public class LoginPageController {

  @FXML
  private Label titleApp;

  @FXML
  private void initialize() {
    titleApp.setText("TASKOVA");
    titleApp.setStyle("-fx-font-weight: bold;");
  }

  @FXML
  private void loginButton() {
    try {
      App.setRoot("dashboardpage");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
