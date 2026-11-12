package br.com.projeto;

// Importando as classes que estão em outras pastas
import br.com.projeto.view.FuncionarioView;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando o sistema...");
        FuncionarioView view = new FuncionarioView();
        view.exibeMenu();
    }
}