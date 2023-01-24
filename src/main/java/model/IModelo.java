package model;

import model.entities.IEntidade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IModelo<T extends IEntidade> {
    T cadastrar(T entidade) throws SQLException;
    void deletarPorNome(String nome) throws SQLException;
    List<T> listar() throws SQLException;
    List<T> listarPorNome(Ordem order) throws SQLException;
    default List<T> listarPorNome() throws SQLException {
        return listarPorNome(Ordem.ASC);
    }
    T retornarPorId(int id) throws SQLException;
    T atualizar(T entidade) throws SQLException;
    default List<T> atualizar(List<T> entidades) throws SQLException {
        List<T> listaAtualizada = new ArrayList<>();
        for (T entidade : entidades) {
            listaAtualizada.add(atualizar(entidade));
        }
        return listaAtualizada;
    }
}
