package com.example.remindertask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton; // Certifique-se que esta importação está presente
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskList;
    private DatabaseHelper databaseHelper;
    private int userId; // ID do usuário logado, passado da tela de login
    private ActivityResultLauncher<Intent> addTaskActivityResultLauncher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializando o FloatingActionButton
        FloatingActionButton myFab = findViewById(R.id.fab); // Certifique-se de que 'fab' está correto
        myFab.setOnClickListener(view -> {
            // Seu código aqui para o que acontece ao clicar no FAB
        });

        addTaskActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Atualiza a lista de tarefas após adicionar/editar
                        taskList.clear(); // Limpa a lista atual
                        loadTasksFromDatabase(); // Recarrega as tarefas
                    }
                }
        );


        // Inicializar as views
        RecyclerView recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        Button btnAddTask = findViewById(R.id.btnAddTask); // Isso deve corresponder a um Button no layout XML

        // Inicializar o DatabaseHelper e a lista de tarefas
        databaseHelper = new DatabaseHelper(this);
        taskList = new ArrayList<>();

        // Pegar o userId da Activity anterior (Login)
        userId = getIntent().getIntExtra("USER_ID", -1);




        // Configurar RecyclerView
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(this, taskList); // Passando o contexto
        recyclerViewTasks.setAdapter(taskAdapter);


        // Carregar as tarefas do banco de dados
        loadTasksFromDatabase();

        // Adicionar nova tarefa
        btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            intent.putExtra("USER_ID", userId); // Passa o ID do usuário para a próxima Activity
            addTaskActivityResultLauncher.launch(intent); // Inicia AddTaskActivity com request code 1
        });
    }

    // Carregar tarefas do banco de dados
    @SuppressLint("NotifyDataSetChanged")
    private void loadTasksFromDatabase() {
        try (Cursor cursor = databaseHelper.obterTodasTarefasPorUsuario(userId)) {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    String description = cursor.getString(2);
                    String category = cursor.getString(3);
                    String tag = cursor.getString(4);
                    String dueDate = cursor.getString(5);
                    String reminderTime = cursor.getString(6);
                    boolean isCompleted = cursor.getInt(7) == 1;

                    taskList.add(new Task(id, title, description, category, tag, dueDate, reminderTime, isCompleted));
                }
            } else {
                Toast.makeText(this, "Nenhuma tarefa encontrada", Toast.LENGTH_SHORT).show();
            }
        }
        // Certifique-se de fechar o cursor para evitar vazamentos de memória
        taskAdapter.notifyDataSetChanged();
    }

    // Método chamado quando voltamos da AddTaskActivity para atualizar a lista
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Atualiza a lista de tarefas após adicionar/editar
            taskList.clear(); // Limpa a lista atual
            loadTasksFromDatabase(); // Recarrega as tarefas
        }
    }
}
