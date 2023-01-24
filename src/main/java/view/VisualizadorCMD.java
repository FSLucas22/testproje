package view;

import model.entities.IEntidade;

import java.util.List;

public abstract class VisualizadorCMD<T extends IEntidade> implements IVisualizador<T>  {
    private final Formatador formatador;
    public VisualizadorCMD(Formatador formatador) {
        this.formatador = formatador;
    }
    public VisualizadorCMD() {
        this(new Formatador());
    }
    public Formatador getFormatador() {
        return formatador;
    }
    @Override
    public void atualizar(List<T> antigasEntidades, List<T> novasEntidades) {
        IVisualizador.super.atualizar(antigasEntidades, novasEntidades);
        System.out.println();
    }
}
