package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {

  private static Task selectedTaskForUpdate; // Temporary variable untuk menyimpan task yang akan diupdate
  private static String selectedCategory;   // Temporary variable untuk menyimpan kategori

  public static void setSelectedTaskForUpdate(Task task, String category) {
    selectedTaskForUpdate = task;
    selectedCategory = category;
  }

  public static Task getSelectedTaskForUpdate() {
    return selectedTaskForUpdate;
  }

  public static String getSelectedCategory() {
    return selectedCategory;
  }

  public static void clearSelectedTaskForUpdate() {
    selectedTaskForUpdate = null;
    selectedCategory = null;
  }

  // Method untuk menyimpan task baru atau mengupdate task yang ada
  public static void saveOrUpdateTask(Task task) {
    if (selectedTaskForUpdate == null) {
      addTask(task); // Menambahkan task baru
    } else {
      updateTask(task); // Mengupdate task yang ada
      clearSelectedTaskForUpdate(); // Membersihkan temporary variable setelah update
    }
  }

  // Menambahkan Task ke tabel
  public static void addTask(Task task) {
    String query = "INSERT INTO tasks (title, description, due_date, priority, is_done, category) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setString(1, task.getTitle());
      stmt.setString(2, task.getDescription());
      stmt.setDate(3, task.getDueDate() != null ? Date.valueOf(task.getDueDate()) : null);
      stmt.setString(4, task.getPriority());
      stmt.setString(5, task.isDone());
      stmt.setString(6, task.getCategory());

      stmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Error while adding task: " + e.getMessage());
      e.printStackTrace();
    }
  }

  // Membaca semua task dari tabel
  public static List<Task> getAllTasks() {
    List<Task> tasks = new ArrayList<>();
    String query = "SELECT * FROM tasks";

    try (Connection conn = DatabaseUtil.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

      while (rs.next()) {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setDueDate(rs.getDate("due_date") != null ? rs.getDate("due_date").toLocalDate() : null);
        task.setPriority(rs.getString("priority")); // Update sesuai dengan nilai ENUM yang benar
        task.setDone(rs.getString("is_done"));
        task.setCategory(rs.getString("category")); // Ensure `rs` is correctly referenced here
        task.setTimeCreated(rs.getTimestamp("time_created").toLocalDateTime());
        tasks.add(task);
      }
    } catch (SQLException e) {
      System.err.println("Error while fetching tasks: " + e.getMessage());
      e.printStackTrace();
    }

    return tasks;
  }

  // Menghapus Task berdasarkan ID dan kategori
  public static void deleteTask(int taskId, String category) {
    String query = "DELETE FROM tasks WHERE id = ? AND category = ?";

    try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setInt(1, taskId);
      stmt.setString(2, category);

      stmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Error while deleting task: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void doneTask(int id, String category) {
    String query = "UPDATE tasks SET is_done = ? WHERE id = ? AND category = ?";
    try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, "done");
      stmt.setInt(2, id);
      stmt.setString(3, category);

      stmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Error while updating task: " + e.getMessage());
      e.printStackTrace();
    }
  }

  // Memperbarui Task
  public static void updateTask(Task task) {
    String query = "UPDATE tasks SET title = ?, description = ?, due_date = ?, priority = ?, is_done = ?, time_created = ? WHERE id = ? AND category = ?";

    try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setString(1, task.getTitle());
      stmt.setString(2, task.getDescription());
      stmt.setDate(3, task.getDueDate() != null ? Date.valueOf(task.getDueDate()) : null);
      stmt.setString(4, task.getPriority());
      stmt.setString(5, task.isDone());
      stmt.setTimestamp(6, Timestamp.valueOf(task.getTimeCreated()));
      stmt.setInt(7, task.getId());
      stmt.setString(8, task.getCategory());

      stmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Error while updating task: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static String getTableName(String category) {
    if (category == null) {
      return null;
    }
    return "tasks";
  }

  public static List<Task> getTasksByCategory(String category) {
    List<Task> tasks = new ArrayList<>();
    String query = "SELECT * FROM tasks WHERE category = ?";

    try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setString(1, category);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          Task task = new Task();
          task.setId(rs.getInt("id"));
          task.setTitle(rs.getString("title"));
          task.setDescription(rs.getString("description"));
          task.setDueDate(rs.getDate("due_date").toLocalDate());
          task.setPriority(rs.getString("priority")); // Update sesuai dengan nilai ENUM yang benar
          task.setDone(rs.getString("is_done"));
          task.setTimeCreated(rs.getTimestamp("time_created").toLocalDateTime());
          tasks.add(task);
        }
      }
    } catch (SQLException e) {
      System.err.println("Error while fetching tasks from tasks: " + e.getMessage());
      e.printStackTrace();
    }

    return tasks;
  }
}
