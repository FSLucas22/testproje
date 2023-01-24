package model;

import model.entities.Funcionario;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloFuncionario implements IModeloFuncionario{
    private final Connection conexao;
    public ModeloFuncionario(Connection conexao) {
        this.conexao = conexao;
    }

    public List<Funcionario> criarPorResultSet(ResultSet resultSet) throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nome = resultSet.getString("nome");
            LocalDate dataNascimento = resultSet.getObject("data_nascimento", LocalDate.class);
            BigDecimal salario = resultSet.getBigDecimal("salario");
            String funcao = resultSet.getString("funcao");
            Funcionario funcionario = new Funcionario(id, nome, dataNascimento, salario, funcao);
            funcionarios.add(funcionario);
        }
        return funcionarios;
    }
    @Override
    public Funcionario cadastrar(Funcionario funcionario)  throws SQLException {
        String[] colunasParaRetornar = {"id"};

        try (PreparedStatement comando = conexao.prepareStatement(
                // Prepara o comando para inserir o novo funcionário na tabela
                // e retornar o id atribuído a ele na inserção
                "INSERT INTO Funcionario(nome, data_nascimento, salario, funcao) VALUES (?, ?, ?, ?)",
                colunasParaRetornar
        )
        ) {
            var nome = funcionario.getNome();
            var dataNascimento = funcionario.getDataNascimento();
            var salario = funcionario.getSalario();
            var funcao = funcionario.getFuncao();

            // Adiciona os dados do novo funcionário ao comando
            comando.setString(1, nome);
            comando.setObject(2, dataNascimento);
            comando.setObject(3, salario);
            comando.setString(4, funcao);

            // Executa o comando e pega o id gerado
            comando.executeUpdate();
            ResultSet campos_gerados = comando.getGeneratedKeys();
            campos_gerados.next();
            int id = campos_gerados.getInt(1);

            // Retorna o funcionário gerado
            return new Funcionario(id, nome, dataNascimento, salario, funcao);
        }
    }
    @Override
    public void deletarPorNome(String nome) throws SQLException {
        // Deleta todos os funcionários com nome  igual ao do parâmetro
        try (PreparedStatement comando = conexao.prepareStatement(
                "DELETE FROM Funcionario WHERE nome = ?"
        )
        ) {
            comando.setString(1, nome);
            comando.executeUpdate();
        }
    }
    @Override
    public List<Funcionario> listar() throws SQLException{
        Statement comando = conexao.createStatement();
        // Retorna todos os funcionários cadastrados
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Funcionario"
        );
        return criarPorResultSet(resultadoQuery);
    }
    @Override
    public Funcionario retornarPorId(int id) throws SQLException {
        Statement comando = conexao.createStatement();
        // Retorna o funcionário cujo id seja igual ao do parâmetro
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Funcionario WHERE id = " + id
        );
        return criarPorResultSet(resultadoQuery).get(0);
    }
    @Override
    public List<Funcionario> listarPorNome(Ordem ordem) throws SQLException {
        Statement comando = conexao.createStatement();
        // retorna os funcionários ordernados pelo nome
        // Ordem.ASC ordena de forma crescente e Ordem.DESC de forma decrescente
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Funcionario ORDER BY nome " + ordem
        );
        return criarPorResultSet(resultadoQuery);
    }
    @Override
    public Funcionario atualizar(Funcionario funcionario) throws SQLException {
        try (PreparedStatement comando = conexao.prepareStatement(
                // Prepara o comando para alterar os dados do funcionário na tabela
                "UPDATE Funcionario SET nome=?, data_nascimento=?, salario=?, funcao=? WHERE id = ?"
        )
        ) {
            // Insere os dados do funcionário no comando
            comando.setString(1, funcionario.getNome());
            comando.setObject(2, funcionario.getDataNascimento());
            comando.setObject(3, funcionario.getSalario());
            comando.setString(4, funcionario.getFuncao());
            comando.setInt(5, funcionario.getId());

            // Executa o comando e retorna o funcionário atualizado
            comando.executeUpdate();
            return retornarPorId(funcionario.getId());
        }
    }
    @Override
    public List<Funcionario> atualizarSalarioDeTodos(double porcentagem) throws SQLException {
        // Atualiza o salário de todos os funcionários com base em uma porcentagem
        try (PreparedStatement comando = conexao.prepareStatement(
                "UPDATE Funcionario SET salario=(salario + (salario * ? / 100))"
        )
        ) {
            comando.setBigDecimal(1, new BigDecimal(porcentagem));
            comando.executeUpdate();

            // Retorna os funcionários com os salários atualizados
            return listar();
        }
    }
    @Override
    public BigDecimal retornarTotalSalarios() throws SQLException {
        Statement comando = conexao.createStatement();
        // Soma o salário de todos os funcionários e retorna o total
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT SUM(salario) FROM Funcionario"
        );
        resultadoQuery.next();
        return resultadoQuery.getBigDecimal(1);
    }
    @Override
    public Map<Funcionario, BigDecimal> listarComSalariosMinimos(double salarioMinimo) throws SQLException {
        Map<Funcionario, BigDecimal> relacaoFuncionarioSalarioMinimo = new HashMap<>();
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                // Prepara o comando para retornar os IDs dos funcionários juntamente com
                // a quantidade de salários mínimos que recebem
                "SELECT id, salario / " + salarioMinimo + " FROM Funcionario"
        );
        while (resultadoQuery.next()) {
            // pega o funcionário
            var id = resultadoQuery.getInt(1);
            var funcionario = retornarPorId(id);

            // pega a quantidade de salários mínimos que recebe
            var quantSalariosMinimos = resultadoQuery.getBigDecimal(2);

            relacaoFuncionarioSalarioMinimo.put(funcionario, quantSalariosMinimos);
        }
        return relacaoFuncionarioSalarioMinimo;
    }
    @Override
    public List<Funcionario> listarAniversariantesDosMeses(int ... meses) throws SQLException {
        // Constrói a query para retornar os funcionários cujo mês de aniversário esteja na array meses
        StringBuilder query = new StringBuilder(
                "SELECT * FROM Funcionario WHERE EXTRACT(MONTH FROM data_nascimento) in (");
        for (int i=0; i<meses.length; i++) {
            query.append(meses[i]).append((i == meses.length - 1) ? ")" : ",");
        }
        // Retorna os funcionários com base na query
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                query.toString()
        );
        return criarPorResultSet(resultadoQuery);
    }
    @Override
    public List<Funcionario> listarPorDataNascimento(Ordem ordem) throws SQLException {
        Statement comando = conexao.createStatement();
        ResultSet resultadoQuery = comando.executeQuery(
                "SELECT * FROM Funcionario ORDER BY data_nascimento " + ordem
        );
        return criarPorResultSet(resultadoQuery);
    }
}
