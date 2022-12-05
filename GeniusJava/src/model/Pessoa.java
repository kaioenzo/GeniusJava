package model;

import java.time.LocalDate;

public abstract class Pessoa {
    private int id;

    private String nome;
    private LocalDate dataDeNascimento;

    public Pessoa(int id, String nome, LocalDate dataDeNascimento) {
        this.id = id;
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
    }

    public int getId() {
        return id;
    }

    public String getInfoPessoa() {
        return this.nome + " " + this.getDataDeNascimento().toString() + " ";
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }
}

