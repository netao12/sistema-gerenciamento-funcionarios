package br.com.projeto.view;

import javax.swing.JOptionPane;
import br.com.projeto.controller.FuncionarioController;
import br.com.projeto.model.Funcionario;
import br.com.projeto.model.Gerente;
import br.com.projeto.model.Operador;

import java.util.List;

public class FuncionarioView {

    private FuncionarioController controller;

    public FuncionarioView() {
        this.controller = new FuncionarioController();
    }

    public void exibeMenu() {
        int opcao = -1;

        while (opcao != 0) {
            String menu = "=== SISTEMA DE GERENCIAMENTO DE FUNCIONÁRIOS ===\n\n"
                    + "1 - Cadastrar Funcionário\n"
                    + "2 - Listar Todos os Funcionários\n"
                    + "3 - Pesquisar Funcionário por Matrícula\n"
                    + "4 - Alterar Funcionário\n"
                    + "5 - Excluir Funcionário\n"
                    + "0 - Sair\n\n"
                    + "Escolha uma opção:";

            String entrada = JOptionPane.showInputDialog(null, menu, "Menu Principal", JOptionPane.QUESTION_MESSAGE);

            if (entrada == null) {
                break;
            }

            try {
                opcao = Integer.parseInt(entrada);

                switch (opcao) {
                    case 1:
                        cadastrar();
                        break;
                    case 2:
                        listarTodos();
                        break;
                    case 3:
                        pesquisar();
                        break;
                    case 4:
                        alterar();
                        break;
                    case 5:
                        excluir();
                        break;
                    case 0:
                        JOptionPane.showMessageDialog(null, "Encerrando o sistema...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida!", "Atenção", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cadastrar() {
        try {
            String[] tipos = {"Gerente", "Operador"};
            int tipoEscolhido = JOptionPane.showOptionDialog(
                    null,
                    "Selecione o tipo de funcionário:",
                    "Cadastro de Funcionário",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    tipos,
                    tipos[0]
            );

            if (tipoEscolhido == JOptionPane.CLOSED_OPTION) return;

            int matricula = Integer.parseInt(JOptionPane.showInputDialog("Digite a Matrícula:"));
            String nome = JOptionPane.showInputDialog("Digite o Nome:");
            int idade = Integer.parseInt(JOptionPane.showInputDialog("Digite a Idade:"));

            if (tipoEscolhido == 0) {
                String departamento = JOptionPane.showInputDialog("Digite o Departamento:");
                Gerente gerente = new Gerente();
                gerente.setMatricula(matricula);
                gerente.setNome(nome);
                gerente.setIdade(idade);
                gerente.setDepartamento(departamento);

                controller.cadastrar(gerente);
            } else {
                String funcao = JOptionPane.showInputDialog("Digite a Função:");
                Operador operador = new Operador();
                operador.setMatricula(matricula);
                operador.setNome(nome);
                operador.setIdade(idade);
                operador.setFuncao(funcao);

                controller.cadastrar(operador);
            }

            JOptionPane.showMessageDialog(null, "Funcionário cadastrado com sucesso!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro: Matrícula e Idade devem ser valores numéricos.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch ( RuntimeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarTodos() {
        try {
            List<Funcionario> lista = controller.listarTodos();

            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum funcionário cadastrado.", "Lista de Funcionários", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder sb = new StringBuilder("=== LISTA DE FUNCIONÁRIOS ===\n\n");
            for (Funcionario f : lista) {
                sb.append("Matrícula: ").append(f.getMatricula())
                .append(" | Nome: ").append(f.getNome())
                .append(" | Idade: ").append(f.getIdade());

                if (f instanceof Gerente) {
                    sb.append(" | Cargo: Gerente | Departamento: ").append(((Gerente) f).getDepartamento());
                } else if (f instanceof Operador) {
                    sb.append(" | Cargo: Operador | Função: ").append(((Operador) f).getFuncao());
                }
                sb.append("\n");
            }

            JOptionPane.showMessageDialog(null, sb.toString(), "Lista de Funcionários", JOptionPane.INFORMATION_MESSAGE);

        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de Leitura", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pesquisar() {
        try {
            int matricula = Integer.parseInt(JOptionPane.showInputDialog("Digite a Matrícula do funcionário a ser pesquisado:"));
            Funcionario f = controller.buscarPorMatricula(matricula);

            if (f == null) {
                JOptionPane.showMessageDialog(null, "Funcionário não encontrado.", "Pesquisa", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder info = new StringBuilder("Matrícula: ").append(f.getMatricula())
                    .append("\nNome: ").append(f.getNome())
                    .append("\nIdade: ").append(f.getIdade());

            if (f instanceof Gerente) {
                info.append("\nCargo: Gerente").append("\nDepartamento: ").append(((Gerente) f).getDepartamento());
            } else if (f instanceof Operador) {
                info.append("\nCargo: Operador").append("\nFunção: ").append(((Operador) f).getFuncao());
            }

            JOptionPane.showMessageDialog(null, info.toString(), "Funcionário Encontrado", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "A matrícula deve ser um valor numérico.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterar() {
        try {
            int matricula = Integer.parseInt(JOptionPane.showInputDialog("Digite a Matrícula do funcionário que deseja alterar:"));
            Funcionario existente = controller.buscarPorMatricula(matricula);

            if (existente == null) {
                JOptionPane.showMessageDialog(null, "Erro: Funcionário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String novoNome = JOptionPane.showInputDialog("Digite o novo Nome:", existente.getNome());
            int novaIdade = Integer.parseInt(JOptionPane.showInputDialog("Digite a nova Idade:", existente.getIdade()));

            if (existente instanceof Gerente) {
                String novoDepartamento = JOptionPane.showInputDialog("Digite o novo Departamento:", ((Gerente) existente).getDepartamento());
                Gerente gerente = new Gerente();
                gerente.setMatricula(matricula);
                gerente.setNome(novoNome);
                gerente.setIdade(novaIdade);
                gerente.setDepartamento(novoDepartamento);

                controller.alterar(gerente);
            } else {
                String novaFuncao = JOptionPane.showInputDialog("Digite a nova Função:", ((Operador) existente).getFuncao());
                Operador operador = new Operador();
                operador.setMatricula(matricula);
                operador.setNome(novoNome);
                operador.setIdade(novaIdade);
                operador.setFuncao(novaFuncao);

                controller.alterar(operador);
            }

            JOptionPane.showMessageDialog(null, "Dados do funcionário alterados com sucesso!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Matrícula e Idade devem ser numéricas.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro na Alteração", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        try {
            int matricula = Integer.parseInt(JOptionPane.showInputDialog("Digite a Matrícula do funcionário a ser excluído:"));
            Funcionario existente = controller.buscarPorMatricula(matricula);

            if (existente == null) {
                JOptionPane.showMessageDialog(null, "Erro: Funcionário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(
                    null,
                    "Tem certeza de que deseja excluir o funcionário " + existente.getNome() + " (Matrícula: " + matricula + ")?",
                    "Confirmação de Exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) {
                controller.excluir(matricula);
                JOptionPane.showMessageDialog(null, "Funcionário excluído com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Operação de exclusão cancelada.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "A matrícula deve ser numérica.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch ( RuntimeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro na Exclusão", JOptionPane.ERROR_MESSAGE);
        }
    }
}
