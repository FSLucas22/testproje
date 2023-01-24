package controller;

import model.IModelo;
import model.Ordem;
import model.entities.IEntidade;
import view.IVisualizador;

import java.sql.SQLException;
import java.util.List;

// Esse Generic triplo foi a única forma que encontrei de forçar que o modelo e o visualizador se referissem
// à mesma entidade e ao mesmo tempo que fosse possível reaproveitar os métodos default para diversas
// implementações de modelos e visualizações
public interface IControlador<T extends IEntidade, R extends IModelo<T>, S extends IVisualizador<T>> {
    R getModelo(); // retorna o objeto que se comunica com o banco de dados
    S getVisualizador(); // retorna o objeto responsável por exibir as informações na tela

    default T cadastrar(T entidade) throws SQLException {
        var novaEntidade = getModelo().cadastrar(entidade);
        getVisualizador().cadastrar(entidade);
        return novaEntidade;
    }
    default void deletarPorNome(String nome) throws SQLException {
        getModelo().deletarPorNome(nome);
        getVisualizador().deletarPorNome(nome);
    }
    default List<T> listar() throws SQLException {
        var entidades = getModelo().listar();
        getVisualizador().listar(entidades);
        return entidades;
    }
    default List<T> listarPorNome(Ordem ordem) throws SQLException {
        var entidades = getModelo().listarPorNome(ordem);
        getVisualizador().listarPorNome(entidades);
        return entidades;
    };
    default List<T> listarPorNome() throws SQLException {
        return listarPorNome(Ordem.ASC);
    }
    default T atualizar(T entidade) throws SQLException {
        var entidadesAtualizada = getModelo().atualizar(entidade);
        getVisualizador().atualizar(entidade, entidadesAtualizada);
        return entidadesAtualizada;
    }
    default List<T> atualizar(List<T> entidades) throws SQLException {
        var entidadesAtualizadas = getModelo().atualizar(entidades);
        getVisualizador().atualizar(entidades, entidadesAtualizadas);
        return entidadesAtualizadas;
    }
}
