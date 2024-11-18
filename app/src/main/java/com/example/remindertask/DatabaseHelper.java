package com.example.remindertask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nome do banco de dados e versão
    private static final String NOME_BANCO_DADOS = "Reminder Task.db";
    private static final int VERSAO_BANCO_DADOS = 1;

    // Nome das tabelas
    private static final String TABELA_USUARIOS = "Usuario";
    private static final String TABELA_TAREFAS = "Tarefa";

    // Colunas da tabela Usuario
    private static final String COLUNA_ID_USUARIO = "id_usuario";
    private static final String COLUNA_NOME_USUARIO = "nome_usuario";
    private static final String COLUNA_SENHA_USUARIO = "senha_usuario";

    // Colunas da tabela Tarefa
    private static final String COLUNA_ID_TAREFA = "id_tarefa";
    private static final String COLUNA_TITULO_TAREFA = "titulo";
    private static final String COLUNA_DESCRICAO_TAREFA = "descricao";
    private static final String COLUNA_CATEGORIA_TAREFA = "categoria";
    private static final String COLUNA_TAG_TAREFA = "tag";
    private static final String COLUNA_DATA_VENCIMENTO_TAREFA = "data_vencimento";
    private static final String COLUNA_HORARIO_LEMBRETE_TAREFA = "horario_lembrete";
    private static final String COLUNA_TAREFA_COMPLETA = "tarefa_completa";
    private static final String COLUNA_ID_USUARIO_TAREFA = "id_usuario"; // Chave estrangeira

    public DatabaseHelper(Context contexto) {
        super(contexto, NOME_BANCO_DADOS, null, VERSAO_BANCO_DADOS);
    }

    // Criação das tabelas no banco de dados
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CRIAR_TABELA_USUARIOS = "CREATE TABLE " + TABELA_USUARIOS + " ("
                + COLUNA_ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNA_NOME_USUARIO + " TEXT UNIQUE, "
                + COLUNA_SENHA_USUARIO + " TEXT)";

        String CRIAR_TABELA_TAREFAS = "CREATE TABLE " + TABELA_TAREFAS + " ("
                + COLUNA_ID_TAREFA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNA_TITULO_TAREFA + " TEXT, "
                + COLUNA_DESCRICAO_TAREFA + " TEXT, "
                + COLUNA_CATEGORIA_TAREFA + " TEXT, "
                + COLUNA_TAG_TAREFA + " TEXT, "
                + COLUNA_DATA_VENCIMENTO_TAREFA + " TEXT, "
                + COLUNA_HORARIO_LEMBRETE_TAREFA + " TEXT, "
                + COLUNA_TAREFA_COMPLETA + " INTEGER, "
                + COLUNA_ID_USUARIO_TAREFA + " INTEGER, "
                + "FOREIGN KEY(" + COLUNA_ID_USUARIO_TAREFA + ") REFERENCES " + TABELA_USUARIOS + "(" + COLUNA_ID_USUARIO + "))";

        db.execSQL(CRIAR_TABELA_USUARIOS);
        db.execSQL(CRIAR_TABELA_TAREFAS);
    }

    // Atualização do banco de dados (caso haja mudanças no esquema)
    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int versaoNova) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_TAREFAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_USUARIOS);
        onCreate(db);
    }

    // Adicionar novo usuário
    public boolean adicionarUsuario(String nomeUsuario, String senhaUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_NOME_USUARIO, nomeUsuario);
        valores.put(COLUNA_SENHA_USUARIO, senhaUsuario);

        long resultado = db.insert(TABELA_USUARIOS, null, valores);
        return resultado != -1; // Se o resultado for diferente de -1, o registro foi inserido com sucesso
    }

    // Verificar se o login é válido
    public boolean verificarLoginUsuario(String nomeUsuario, String senhaUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "SELECT * FROM " + TABELA_USUARIOS + " WHERE " + COLUNA_NOME_USUARIO + " = ? AND " + COLUNA_SENHA_USUARIO + " = ?";
        Cursor cursor = db.rawQuery(consulta, new String[]{nomeUsuario, senhaUsuario});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    // Adicionar nova tarefa
    public boolean adicionarTarefa(String titulo, String descricao, String categoria, String tag, String dataVencimento, String horarioLembrete, int idUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_TITULO_TAREFA, titulo);
        valores.put(COLUNA_DESCRICAO_TAREFA, descricao);
        valores.put(COLUNA_CATEGORIA_TAREFA, categoria);
        valores.put(COLUNA_TAG_TAREFA, tag);
        valores.put(COLUNA_DATA_VENCIMENTO_TAREFA, dataVencimento);
        valores.put(COLUNA_HORARIO_LEMBRETE_TAREFA, horarioLembrete);
        valores.put(COLUNA_TAREFA_COMPLETA, 0); // 0 para incompleta
        valores.put(COLUNA_ID_USUARIO_TAREFA, idUsuario);

        long resultado = db.insert(TABELA_TAREFAS, null, valores);
        return resultado != -1;
    }

    // Obter todas as tarefas de um usuário específico
    public Cursor obterTodasTarefasPorUsuario(int idUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABELA_TAREFAS + " WHERE " + COLUNA_ID_USUARIO_TAREFA + " = ?", new String[]{String.valueOf(idUsuario)});
    }

    // Atualizar status da tarefa (marcar como completa/incompleta)
    public boolean atualizarStatusTarefa(int idTarefa, boolean estaCompleta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_TAREFA_COMPLETA, estaCompleta ? 1 : 0);

        int resultado = db.update(TABELA_TAREFAS, valores, COLUNA_ID_TAREFA + " = ?", new String[]{String.valueOf(idTarefa)});
        return resultado > 0;
    }

    // Remover tarefa
    public boolean removerTarefa(int idTarefa) {
        SQLiteDatabase db = this.getWritableDatabase();
        int resultado = db.delete(TABELA_TAREFAS, COLUNA_ID_TAREFA + " = ?", new String[]{String.valueOf(idTarefa)});
        return resultado > 0;
    }
}
