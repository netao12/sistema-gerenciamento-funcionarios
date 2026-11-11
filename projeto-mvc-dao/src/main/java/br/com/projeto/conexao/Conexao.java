package br.com.projeto.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    // Altere "nome_do_banco", "root" e "sua_senha" para os dados reais do seu MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/gerenciamento_db";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConexao() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
}