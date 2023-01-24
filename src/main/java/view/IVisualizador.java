package view;

import model.entities.Entidade;

import java.util.List;

public interface IVisualizador<T extends Entidade> {
    void cadastrar(T entidade);
    void deletarPorNome(String nome);
    void listar(List<T> entidades);
    void listarPorNome(List<T> entidades);
    void atualizar(T entidadeAntiga, T entidadeNova);
    default void atualizar(List<T> entidadesAntigas, List<T> entidadesNovas) {
        for (int i=0; i<entidadesAntigas.size(); i++) {
            atualizar(entidadesAntigas.get(i), entidadesNovas.get(i));
        }
    }
}
