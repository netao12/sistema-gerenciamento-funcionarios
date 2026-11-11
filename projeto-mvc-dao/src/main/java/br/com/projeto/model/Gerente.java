package br.com.projeto.model;

public class Gerente extends Funcionario {
    private String departamento;

    // Construtor vazio obrigatório para o new Gerente()
    public Gerente() {
        super();
    }

    // Construtor completo (se tiveres)
    public Gerente(int matricula, String nome, int idade, String departamento) {
        super(matricula, nome, idade);
        this.departamento = departamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}