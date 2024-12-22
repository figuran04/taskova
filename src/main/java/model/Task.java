package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {

  private int id; // ID unik untuk task
  private String title; // Judul task
  private String description; // Deskripsi task
  private String category; // Kategori task (enum: Work, Personal, Other)
  private LocalDate dueDate; // Tanggal jatuh tempo task
  private String priority; // Prioritas task (enum: Important and urgent, Important but not urgent, Urgent but not important, Not urgent not important)
  private String isDone; // Status penyelesaian task (enum: in progress, done)
  private LocalDateTime timeCreated; // Waktu ketika task diinput atau dikirim

  // Constructor kosong
  public Task() {
  }

  // Constructor dengan parameter lengkap
  public Task(int id, String title, String description, String category, LocalDate dueDate, String priority, String isDone, LocalDateTime timeCreated) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.category = category;
    this.dueDate = dueDate;
    this.priority = priority;
    this.isDone = isDone;
    this.timeCreated = timeCreated;
  }

  // Constructor tanpa ID (untuk menambahkan task baru)
  public Task(String title, String description, String category, LocalDate dueDate, String priority, String isDone, LocalDateTime timeCreated) {
    this.title = title;
    this.description = description;
    this.category = category;
    this.dueDate = dueDate;
    this.priority = priority;
    this.isDone = isDone;
    this.timeCreated = timeCreated;
  }

  // Getter dan Setter
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public String isDone() {
    return isDone;
  }

  public void setDone(String isDone) {
    this.isDone = isDone;
  }

  public LocalDateTime getTimeCreated() {
    return timeCreated;
  }

  public void setTimeCreated(LocalDateTime timeCreated) {
    this.timeCreated = timeCreated;
  }

  @Override
  public String toString() {
    return "Task{"
            + "id=" + id
            + ", title='" + title + '\''
            + ", description='" + description + '\''
            + ", category=" + category
            + ", dueDate=" + dueDate
            + ", priority=" + priority
            + ", isDone=" + isDone
            + ", timeCreated=" + timeCreated
            + '}';
  }
}
