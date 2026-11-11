package br.com.projeto.model;

public abstract class Funcionario { // ou public class Funcionario
    protected int matricula;
    protected String nome;
    protected int idade;

    // Construtor vazio necessário para as subclasses
    public Funcionario() {
    }

    public Funcionario(int matricula, String nome, int idade) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    // ... getters e setters
    
}