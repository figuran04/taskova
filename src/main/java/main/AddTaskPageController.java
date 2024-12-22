package main;

import java.io.IOException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.TaskManager;
import model.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AddTaskPageController {

  @FXML
  private ComboBox<String> priorityComboBox;
  @FXML
  private ComboBox<String> categoryComboBox;
  @FXML
  private DatePicker dueDatePicker;
  @FXML
  private TextField titleField;
  @FXML
  private TextArea descriptionField;
  @FXML
  private Label titleApp;

  private Task currentTask; // Untuk menyimpan task yang akan diupdate

  private static String selectedValue;

  // Inisialisasi item ComboBox ketika controller dimulai
  @FXML
  public void initialize() {
    // Menambahkan item ke priorityComboBox
    ObservableList<String> priorityList = FXCollections.observableArrayList("Important and urgent", "Important but not urgent", "Urgent but not important", "Not urgent not important");
    priorityComboBox.setItems(priorityList);
    priorityComboBox.setValue("Not urgent not important");

    // Menambahkan item ke categoryComboBox
    ObservableList<String> categoryList = FXCollections.observableArrayList("Work", "Personal", "Other");
    categoryComboBox.setItems(categoryList);
    categoryComboBox.setValue(DashboardPageController.getSelectedValue());
    titleApp.setText("TASKOVA");
    titleApp.setStyle("-fx-font-weight: bold;");

    dueDatePicker.setValue(LocalDate.now());

    currentTask = TaskManager.getSelectedTaskForUpdate();
    String selectedCategory = TaskManager.getSelectedCategory();

    if (currentTask != null) {
      loadTaskData(currentTask, selectedCategory);
    }
  }
// Memuat data task untuk diupdate

  private void loadTaskData(Task task, String category) {
    titleField.setText(task.getTitle());
    descriptionField.setText(task.getDescription());
    categoryComboBox.setValue(category); // Set nilai kategori dari TaskManager
    priorityComboBox.setValue(task.getPriority());
    dueDatePicker.setValue(task.getDueDate());
  }

  @FXML
  private void saveTask() {
    if (titleField.getText().isEmpty() || descriptionField.getText().isEmpty() || categoryComboBox.getValue() == null
            || priorityComboBox.getValue() == null || dueDatePicker.getValue() == null) {
      showErrorAlert("Error", "Please fill in all fields.");
      return;
    }

    if (dueDatePicker.getValue().isBefore(LocalDate.now()) || dueDatePicker.getValue().isEqual(LocalDate.now())) {
      showErrorAlert("Error", "Please fill date after this day.");
      return;
    }

    Task task = TaskManager.getSelectedTaskForUpdate();
    if (task == null) {
      task = new Task();
      task.setDone("in progress");
    }

    task.setTitle(titleField.getText());
    task.setDescription(descriptionField.getText());
    task.setCategory(categoryComboBox.getValue());
    task.setDueDate(dueDatePicker.getValue());
    task.setPriority(priorityComboBox.getValue());

    TaskManager.saveOrUpdateTask(task);

    try {
      selectedValue = categoryComboBox.getValue();
      App.setRoot("dashboardPage");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getSelectedValue() {
    return selectedValue; // Getter for the selected value
  }

  // Menangani tombol Cancel
  @FXML
  private void cancel() {
    TaskManager.clearSelectedTaskForUpdate();
    try {
      selectedValue = categoryComboBox.getValue();
      App.setRoot("dashboardPage");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Menampilkan alert error
  private void showErrorAlert(String title, String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
