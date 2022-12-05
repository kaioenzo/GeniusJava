package model;

import java.time.LocalDate;
import java.util.List;

public class Artista extends Pessoa {
    private int id;
    private String descricao;
    private GeneroMusical generoMusical;

    public Artista(int id, String nome, LocalDate dataDeNascimento, String descricao, GeneroMusical generoMusical) {
        super(id, nome, dataDeNascimento);
        this.id = id;
        this.descricao = descricao;
        this.generoMusical = generoMusical;
    }

    public String getInfoArtista() {
        return super.getInfoPessoa() + " " + this.descricao + " " + this.generoMusical.getNome();
    }

    public String getDescricao() {
        return descricao;
    }

    public GeneroMusical getGeneroMusical() {
        return generoMusical;
    }
}
