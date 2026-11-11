package dao;

import conexao.Conexao;
import model.Funcionario;
import model.Gerente;
import model.Operador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void cadastrar(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (matricula, nome, idade, tipo, departamento, funcao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, funcionario.getMatricula());
            stmt.setString(2, funcionario.getNome());
            stmt.setInt(3, funcionario.getIdade());

            if (funcionario instanceof Gerente) {
                stmt.setString(4, "GERENTE");
                stmt.setString(5, ((Gerente) funcionario).getDepartamento());
                stmt.setString(6, null);
            } else if (funcionario instanceof Operador) {
                stmt.setString(4, "OPERADOR");
                stmt.setString(5, null);
                stmt.setString(6, ((Operador) funcionario).getFuncao());
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    public List<Funcionario> listarTodos() {
        String sql = "SELECT * FROM funcionario";
        List<Funcionario> lista = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(instanciarFuncionario(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários: " + e.getMessage());
        }
        return lista;
    }

    public Funcionario buscarPorMatricula(int matricula) {
        String sql = "SELECT * FROM funcionario WHERE matricula = ?";
        Funcionario funcionario = null;

        try (Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    funcionario = instanciarFuncionario(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário: " + e.getMessage());
        }
        return funcionario;
    }

    public void alterar(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nome = ?, idade = ?, departamento = ?, funcao = ? WHERE matricula = ?";

        try (Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setInt(2, funcionario.getIdade());

            if (funcionario instanceof Gerente) {
                stmt.setString(3, ((Gerente) funcionario).getDepartamento());
                stmt.setString(4, null);
            } else if (funcionario instanceof Operador) {
                stmt.setString(3, null);
                stmt.setString(4, ((Operador) funcionario).getFuncao());
            }

            stmt.setInt(5, funcionario.getMatricula());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar funcionário: " + e.getMessage());
        }
    }

    public void excluir(int matricula) {
        String sql = "DELETE FROM funcionario WHERE matricula = ?";

        try (Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir funcionário: " + e.getMessage());
        }
    }

    // Método auxiliar para converter ResultSet em Objeto
    private Funcionario instanciarFuncionario(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        if ("GERENTE".equals(tipo)) {
            Gerente g = new Gerente();
            g.setMatricula(rs.getInt("matricula"));
            g.setNome(rs.getString("nome"));
            g.setIdade(rs.getInt("idade"));
            g.setDepartamento(rs.getString("departamento"));
            return g;
        } else {
            Operador o = new Operador();
            o.setMatricula(rs.getInt("matricula"));
            o.setNome(rs.getString("nome"));
            o.setIdade(rs.getInt("idade"));
            o.setFuncao(rs.getString("funcao"));
            return o;
        }
    }
}