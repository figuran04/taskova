package main;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Task;
import model.TaskManager;

public class DashboardPageController {

  @FXML
  private ComboBox<String> category; // ComboBox untuk memilih kategori

  @FXML
  private PieChart pieChartInProgressVsDone; // PieChart untuk menampilkan statistik progress vs done

  @FXML
  private Label countInProgressTask; // Label untuk menampilkan jumlah task In Progress

  @FXML
  private Label countDoneTask; // Label untuk menampilkan jumlah task Done

  @FXML
  private Label inProgressLabel;

  @FXML
  private Label doneLabel;

  @FXML
  private Label titleApp;

  @FXML
  private Button addTaskButton;

  @FXML
  private VBox inProgressUrgentImportant;
  @FXML
  private VBox inProgressImportantNotUrgent;
  @FXML
  private VBox inProgressUrgentNotImportant;
  @FXML
  private VBox inProgressNotUrgentNotImportant;
  @FXML
  private VBox doneUrgentImportant;
  @FXML
  private VBox doneImportantNotUrgent;
  @FXML
  private VBox doneUrgentNotImportant;
  @FXML
  private VBox doneNotUrgentNotImportant;

  private static String selectedValue;

  // Method untuk menginisialisasi
  @FXML
  private void initialize() {
    String value = AddTaskPageController.getSelectedValue();
    // Mengisi kategori di ComboBox
    category.getItems().addAll("Personal", "Work", "Other");

    // Set default category
    if (value == null) {
      category.setValue("Personal");
      loadDashboardData("Personal");

    } else {
      category.setValue(value);
      loadDashboardData(value);
    }

    addTaskButton.setText("\u271A");

    // Load data dashboard berdasarkan kategori yang dipilih
    // Ketika kategori berubah, muat ulang data
    category.setOnAction(event -> {
      String selectedCategory = category.getValue();
      if (selectedCategory != null) {
        loadDashboardData(selectedCategory);
      }
    });
  }

  // Method untuk memuat data dashboard berdasarkan kategori
  private void loadDashboardData(String category) {
    // Ambil semua task berdasarkan kategori
    List<Task> tasks = TaskManager.getTasksByCategory(category);

    // Hitung jumlah task yang sedang berlangsung dan selesai
    long inProgressCount = tasks.stream().filter(task -> !task.isDone().equals("done")).count();
    long doneCount = tasks.stream().filter(task -> task.isDone().equals("done")).count();

    // Update statistik di Label
    countInProgressTask.setText(String.valueOf(inProgressCount));
    countDoneTask.setText(String.valueOf(doneCount));
    titleApp.setText("TASKOVA");
    titleApp.setStyle("-fx-font-weight: bold;");

    // Update data di PieChart
    pieChartInProgressVsDone.getData().clear();
    pieChartInProgressVsDone.getData().addAll(
            new PieChart.Data("In Progress", inProgressCount),
            new PieChart.Data("Done", doneCount)
    );

    // Update Accordion untuk tugas yang sedang In Progress
    updateAccordion(inProgressUrgentImportant, tasks, "Important and urgent", "in progress");
    updateAccordion(inProgressImportantNotUrgent, tasks, "Important but not urgent", "in progress");
    updateAccordion(inProgressUrgentNotImportant, tasks, "Urgent but not important", "in progress");
    updateAccordion(inProgressNotUrgentNotImportant, tasks, "Not urgent not important", "in progress");

    // Update Accordion untuk tugas yang sudah Done
    updateAccordion(doneUrgentImportant, tasks, "Important and urgent", "done");
    updateAccordion(doneImportantNotUrgent, tasks, "Important but not urgent", "done");
    updateAccordion(doneUrgentNotImportant, tasks, "Urgent but not important", "done");
    updateAccordion(doneNotUrgentNotImportant, tasks, "Not urgent not important", "done");
  }

  // Method untuk memperbarui Accordion dengan tugas sesuai statusnya (In Progress / Done)
  private void updateAccordion(VBox vbox, List<Task> tasks, String priority, String isDone) {
    if (vbox != null) {
      vbox.getChildren().clear();
      tasks.stream()
              .filter(task -> priority.equals(task.getPriority()))
              .filter(task -> isDone.equals(task.isDone()))
              .forEach(task -> {
                VBox taskDetails = new VBox();
                Label taskLabel = new Label(task.getTitle());
                taskLabel.setStyle("-fx-padding: 5;");
                ProgressBar taskProgressBar = new ProgressBar();
                taskProgressBar.setPrefWidth(300);
                taskProgressBar.setPrefHeight(12);
                taskProgressBar.setMinHeight(12);
                taskProgressBar.setMaxHeight(12);
                if ("done".equals(task.isDone())) {
                  taskProgressBar.setVisible(false);
                } else {
                  taskProgressBar.setVisible(true);

                  // Hitung sisa waktu sampai duedate dalam jam
                  LocalDateTime currentDateTime = LocalDateTime.now();
                  LocalDateTime timeCreated = task.getTimeCreated();
                  LocalDateTime dueDateTime = task.getDueDate().atStartOfDay(); // Mengkonversi LocalDate ke LocalDateTime
                  long totalHours = ChronoUnit.HOURS.between(timeCreated, dueDateTime);
                  long hoursLeft = ChronoUnit.HOURS.between(currentDateTime, dueDateTime);
                  double progress = (double) hoursLeft / totalHours;
//                            long sekarang = System.currentTimeMillis();
//                            long duedate = task.getDueDate().atStartOfDay().toInstant(java.time.ZoneOffset.UTC).toEpochMilli();
//                            double progress = (double) (duedate - sekarang) / (24 * 60 * 60 * 1000);

                  taskProgressBar.progressProperty().addListener((observable, oldValue, newValue) -> {
                    if (progress <= 0) {
                      TaskManager.doneTask(task.getId(), category.getValue());
                      loadDashboardData(category.getValue());
                    } else if (progress <= 0.33) {
                      // Warna merah gelap
                      taskProgressBar.setStyle(
                              "-fx-accent: #D32F2F; "
                              + "-fx-control-inner-background: #0F0E1F; "
                      );
                    } else if (progress <= 0.66) {
                      // Warna kuning
                      taskProgressBar.setStyle("-fx-accent: #E19418; "
                              + "-fx-control-inner-background: #0F0E1F; ");
                    } else {
                      // Warna hijau
                      taskProgressBar.setStyle("-fx-accent: #4EA44E; "
                              + "-fx-control-inner-background: #0F0E1F; ");
                    }
                  });
                  taskProgressBar.setProgress(progress);
                }

                taskDetails.getChildren().addAll(taskLabel, taskProgressBar);

                // HBox untuk menampung tombol Edit dan Hapus
                HBox buttonBox = new HBox(5);
                buttonBox.setAlignment(Pos.CENTER_RIGHT);
                buttonBox.setPrefWidth(195);
                // Buat tombol dengan ikon
                Button doneButton = new Button("\u2713");
                doneButton.getStyleClass().add("done-button");
                Button editButton = new Button("\u270E");
                editButton.getStyleClass().add("edit-button");
                Button deleteButton = new Button("\ue872");
                deleteButton.getStyleClass().add("delete-button");

                deleteButton.setOnAction(event -> {
                  Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                  confirmationDialog.setTitle("Konfirmasi Penghapusan");
                  confirmationDialog.setHeaderText("Anda akan menghapus tugas: " + task.getTitle());
                  confirmationDialog.setContentText("Apakah Anda yakin ingin menghapus tugas ini?");

                  // Menunggu respons pengguna
                  Optional<ButtonType> result = confirmationDialog.showAndWait();
                  if (result.isPresent() && result.get() == ButtonType.OK) {
                    TaskManager.deleteTask(task.getId(), category.getValue());
                    loadDashboardData(category.getValue());
                    System.out.println("Task deleted: " + task.getTitle());
                  } else {
                    // Jika pengguna memilih Cancel
                    System.out.println("Task deletion canceled.");
                  }
                });

                doneButton.setOnAction(event -> {
                  // Memastikan bahwa tugas adalah "done"
                  if (task.isDone().equals("in progress")) {
                    // Membuat kotak dialog konfirmasi
                    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationDialog.setTitle("Konfirmasi Selesainya Tugas");
                    confirmationDialog.setHeaderText("Apakah tugas: " + task.getTitle() + " sudah selesai?");
                    confirmationDialog.setContentText("Anda yakin tugas ini sudah selesai?");

                    // Menunggu respons pengguna
                    Optional<ButtonType> result = confirmationDialog.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                      // Ubah status tugas menjadi "done" dalam database
                      TaskManager.doneTask(task.getId(), category.getValue());
                      loadDashboardData(category.getValue());
                      System.out.println("Task marked as done: " + task.getTitle());
                    } else {
                      // Jika pengguna memilih Cancel
                      System.out.println("Marking task as done canceled.");
                    }
                  } else {
                    // Jika tugas belum selesai, beri tahu pengguna
                    System.out.println("Task is not finished yet and cannot be marked as done.");
                  }
                });

//                        editButton.setOnAction(event -> {
//                            if (task != null) {
//                                TaskManager.setSelectedTaskForUpdate(task, category.getValue());
//                                try {
//                                    App.setRoot("addtaskpage"); // Mengarahkan ke halaman addtaskpage
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
                if ("done".equals(task.isDone())) {
                  buttonBox.getChildren().addAll(deleteButton);
                } else {
                  buttonBox.getChildren().addAll(doneButton, deleteButton);
                }

                // HBox utama untuk menyusun VBox dan HBox button secara horizontal
                HBox taskRow = new HBox(5);
                taskRow.getChildren().addAll(taskDetails, buttonBox);
                taskRow.setStyle("-fx-background-color: #13122F; -fx-cursor: hand; -fx-padding: 5; -fx-border-radius: 5; -fx-background-radius: 5;");
                taskRow.setOnMouseClicked(event -> {
                  if (task != null) {
                    System.out.println("Task row clicked!");
                    TaskManager.setSelectedTaskForUpdate(task, category.getValue());
                    try {
                      App.setRoot("addtaskpage"); // Mengarahkan ke halaman addtaskpage
                    } catch (IOException e) {
                      e.printStackTrace();
                    }
                  }
                });
                taskRow.setOnMouseEntered(event -> taskRow.setStyle("-fx-background-color: #1F1E3B; -fx-cursor: hand; -fx-padding: 5; -fx-border-radius: 5; -fx-background-radius: 5;"));
                taskRow.setOnMouseExited(event -> taskRow.setStyle("-fx-background-color: #13122F; -fx-cursor: hand; -fx-padding: 5; -fx-border-radius: 5; -fx-background-radius: 5;"));

                vbox.getChildren().addAll(taskRow);
              });
    }
  }

  // Method untuk berpindah ke halaman profil
  @FXML
  private void goToProfilePage() {
    try {
      App.setRoot("profilepage");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Method untuk berpindah ke halaman tugas
  @FXML
  private void goToTaskPage() {
    try {
      selectedValue = category.getValue();
      App.setRoot("addtaskpage");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getSelectedValue() {
    return selectedValue; // Getter for the selected value
  }
}
