package model;

import model.entities.IEntidade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public interface IModelo<T extends IEntidade> {

    // Cadastra a entidade no banco
    T cadastrar(T entidade) throws SQLException;

    // Deleta todas as entidades que tenham o nome especificado
    void deletarPorNome(String nome) throws SQLException;

    // Retorna todos os registros da tabela
    List<T> listar() throws SQLException;

    // Retorna os registros em ordem crescente (listarPorNome(Ordem.ASC))
    // ou decrescente (listarPorNome(Ordem.DESC)) ordenados pelo nome
    List<T> listarPorNome(Ordem order) throws SQLException;
    default List<T> listarPorNome() throws SQLException {
        return listarPorNome(Ordem.ASC);
    }
    // Retorna como uma entidade o registro da tabela associado ao parametro id
    T retornarPorId(int id) throws SQLException;

    // Altera na tabela os registros com base nos dados contidos na entidade T
    // Somente alterará o registro associado ao id da entidade
    T atualizar(T entidade) throws SQLException;

    // Atualiza vários registros da tabela e retorna uma lista com as entidades atualizadas
    default List<T> atualizar(List<T> entidades) throws SQLException {
        List<T> listaAtualizada = new ArrayList<>();
        for (T entidade : entidades) {
            listaAtualizada.add(atualizar(entidade));
        }
        return listaAtualizada;
    }
}
