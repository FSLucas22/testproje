package view;

import model.entities.IEntidade;

import java.util.List;

public interface IVisualizador<T extends IEntidade> {

    // Exibe mensagem de cadastro de entidade
    void cadastrar(T entidade);

    // Exibe mensagen de exclusão de entidade
    void deletarPorNome(String nome);

    // Exibe todos as entidades cadastrados
    void listar(List<T> entidades);

    // Exibe as entidades ordenadas pelo nome
    void listarPorNome(List<T> entidades);

    // Exibe mensagem de atualização de entidade
    void atualizar(T entidadeAntiga, T entidadeNova);

    // Exibe mensagem de atualização de multiplas entidades
    default void atualizar(List<T> entidadesAntigas, List<T> entidadesNovas) {
        for (int i=0; i<entidadesAntigas.size(); i++) {
            atualizar(entidadesAntigas.get(i), entidadesNovas.get(i));
        }
    }
}
