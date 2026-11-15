package br.com.projeto.conexao;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {
        // Tenta abrir a conexão. O try-with-resources fecha automaticamente após o bloco.
        try (Connection conn = Conexao.getConexao()) {
            System.out.println("Conexão com o banco estabelecida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados!");
            e.printStackTrace(); // Imprime os detalhes técnicos do erro
        }
    }
}