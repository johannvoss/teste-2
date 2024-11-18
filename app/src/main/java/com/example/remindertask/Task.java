package com.example.remindertask;

public class Task {
    private int id; // ID da tarefa
    private String title; // Título da tarefa
    private String description; // Descrição da tarefa
    private String category; // Categoria da tarefa
    private String tag; // Tag da tarefa
    private String dueDate; // Data de vencimento
    private String reminderTime; // Horário do lembrete
    private boolean isCompleted; // Status de conclusão da tarefa

    // Construtor
    public Task(int id, String title, String description, String category, String tag, String dueDate, String reminderTime, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.tag = tag;
        this.dueDate = dueDate;
        this.reminderTime = reminderTime;
        this.isCompleted = isCompleted;
    }

    // Métodos getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getTag() {
        return tag;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    // Métodos setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
