package controller;

import model.IModeloFuncionario;
import view.IVisualizadorFuncionario;

public class ControladorFuncionario implements IControladorFuncionario {
    private final IModeloFuncionario modelo;
    private final IVisualizadorFuncionario visualizador;
    public ControladorFuncionario(IModeloFuncionario modelo, IVisualizadorFuncionario visualizador) {
        this.modelo = modelo;
        this.visualizador = visualizador;
    }
    @Override
    public IModeloFuncionario getModelo() {
        return modelo;
    }
    @Override
    public IVisualizadorFuncionario getVisualizador() {
        return visualizador;
    }

    // Demais métodos implementados por default nas interfaces
}
