package model.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Pessoa implements Entity {
    private int id;
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(int id, String nome, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    @Override
    public int getId() {
        return id;
    }
    public String toString() {
        return "Pessoa(" +
                "nome=" + nome + "," +
                "dataNascimento=" + dataNascimento.toString() + ")";
    }
}
