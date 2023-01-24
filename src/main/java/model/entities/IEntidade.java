package model.entities;


// Representada uma tabela do banco de dados
public interface IEntidade {
    int getId();
    void setId(int id);
    String getNome();
    void setNome(String nome);
}
