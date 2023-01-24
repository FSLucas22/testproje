package controller;

import model.IModelo;
import model.Ordem;
import model.entities.IEntidade;
import view.IVisualizador;

import java.sql.SQLException;
import java.util.List;

public interface IControlador<T extends IEntidade, R extends IModelo<T>, S extends IVisualizador<T>> {
    R getModelo();
    S getVisualizador();
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
