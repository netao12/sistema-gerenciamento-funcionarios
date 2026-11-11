package br.com.projeto.model;

public class Operador extends Funcionario {
    private String funcao;

    // Construtor vazio obrigatório para o new Operador()
    public Operador() {
        super();
    }

    // Construtor completo (se tiveres)
    public Operador(int matricula, String nome, int idade, String funcao) {
        super(matricula, nome, idade);
        this.funcao = funcao;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
}