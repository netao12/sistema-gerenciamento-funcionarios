package br.com.projeto.controller;


import java.util.List;

import br.com.projeto.dao.FuncionarioDAO;
import br.com.projeto.model.Funcionario;
import br.com.projeto.model.Gerente;
import br.com.projeto.model.Operador;

public class FuncionarioController {

    private FuncionarioDAO dao;

    public FuncionarioController() {
        this.dao = new FuncionarioDAO();
    }


    public void cadastrar(Funcionario funcionario) throws IllegalArgumentException {
        validarDadosBasicos(funcionario);

        
        if (dao.buscarPorMatricula(funcionario.getMatricula()) != null) {
            throw new IllegalArgumentException("Erro: Já existe um funcionário cadastrado com a matrícula " + funcionario.getMatricula() + ".");
        }
        dao.cadastrar(funcionario);
    }


    public List<Funcionario> listarTodos() {
        return dao.listarTodos();
    }

    public Funcionario buscarPorMatricula(int matricula) {
        return dao.buscarPorMatricula(matricula);
    }


    public void alterar(Funcionario funcionario) throws IllegalArgumentException {
        validarDadosBasicos(funcionario);


        if (dao.buscarPorMatricula(funcionario.getMatricula()) == null) {
            throw new IllegalArgumentException("Erro: Funcionário com matrícula " + funcionario.getMatricula() + " não encontrado para alteração.");
        }

        dao.alterar(funcionario);
    }


    public void excluir(int matricula) throws IllegalArgumentException {

        if (dao.buscarPorMatricula(matricula) == null) {
            throw new IllegalArgumentException("Erro: Funcionário com matrícula " + matricula + " não encontrado para exclusão.");
        }

        dao.excluir(matricula);
    }


    private void validarDadosBasicos(Funcionario funcionario) throws IllegalArgumentException {

        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro: O nome do funcionário é obrigatório.");
        }


        if (funcionario.getIdade() <= 0) {
            throw new IllegalArgumentException("Erro: A idade deve ser maior que zero.");
        }


        if (funcionario instanceof Gerente) {
            Gerente g = (Gerente) funcionario;

            if (g.getDepartamento() == null || g.getDepartamento().trim().isEmpty()) {
                throw new IllegalArgumentException("Erro: O departamento é obrigatório para Gerentes.");
            }
        } else if (funcionario instanceof Operador) {
            Operador o = (Operador) funcionario;

            if (o.getFuncao() == null || o.getFuncao().trim().isEmpty()) {
                throw new IllegalArgumentException("Erro: A função é obrigatória para Operadores.");
            }
        } else {
            throw new IllegalArgumentException("Erro: Tipo de funcionário inválido.");
        }
    }
}