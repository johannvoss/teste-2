package com.example.remindertask;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity { // Certifique-se de estender AppCompatActivity ou Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task); // Certifique-se de que activity_add_task.xml existe
        // Sua lógica aqui
    }
}

