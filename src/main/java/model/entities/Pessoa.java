package model.entities;

import java.time.LocalDate;

public class Pessoa implements Entidade {
    private int id;
    private String nome;
    private LocalDate dataNascimento;
    public Pessoa(){}
    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }
    public Pessoa(int id, String nome, LocalDate dataNascimento) {
        this(nome, dataNascimento);
        this.id = id;
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

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "Pessoa(" +
                "nome=" + nome + "," +
                "dataNascimento=" + dataNascimento.toString() + ")";
    }
}
