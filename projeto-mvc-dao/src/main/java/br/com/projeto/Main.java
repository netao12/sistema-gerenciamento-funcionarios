package br.com.projeto;

// Importando as classes que estão em outras pastas
import br.com.projeto.model.Operador;
import br.com.projeto.dao.FuncionarioDAO;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando o sistema...");

        // 1. Criando o objeto Operador
        Operador op = new Operador(1, "João", 25, "Caixa");
        
        // 2. Instanciando o DAO
        FuncionarioDAO dao = new FuncionarioDAO();
        
        // 3. Cadastrando no banco de dados
        try {
            dao.cadastrar(op);
            System.out.println("Sucesso: Operador cadastrado no banco de dados!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }
}