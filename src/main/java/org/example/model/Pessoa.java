package org.example.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe abstrata que representa uma pessoa. Com id, nome e data de nascimento. Esta classe serve de base par as
 * classes de Produtor e Artista.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 * @see Produtor
 * @see Artista
 */
public abstract class Pessoa {
    private int id;

    private String nome;
    private LocalDate dataDeNascimento;

    /**
     * @param id que representa o ID único da pessoa
     * @param nome que representa o nome da pessoa
     * @param dataDeNascimento que representa a data de nascimento da pessoa
     */
    public Pessoa(int id, String nome, LocalDate dataDeNascimento) {
        this.id = id; this.nome = nome; this.dataDeNascimento = dataDeNascimento;
    }

    public int getId() {
        return id;
    }

    public String getInfoPessoa() {
        return "Pessoa{" + "id=" + id + ", nome='" + nome + '\'' + ", dataDeNascimento=" + dataDeNascimento + '}';
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getDataDeNascimentoFormatada() {

        return dataDeNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}

