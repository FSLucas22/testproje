package model;

import model.entities.Entidade;
import model.entities.Funcionario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface Modelo<T extends Entidade> {
    T cadastrar(T entidade) throws SQLException;
    void deletarPorNome(String nome) throws SQLException;
    List<T> listar() throws SQLException;
    List<T> listarPorNome(Order order) throws SQLException;
    default List<T> listarPorNome() throws SQLException {
        return listarPorNome(Order.ASC);
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
