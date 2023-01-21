package model;

import java.time.LocalDate;
import java.util.Objects;

public class Artista extends Pessoa {
    final private int id;
    private String descricao;
    private String generoMusical;

    public Artista(int id, String nome, LocalDate dataDeNascimento, String descricao, String generoMusical) {
        super(id, nome, dataDeNascimento);
        this.id = id;
        this.descricao = descricao;
        this.generoMusical = generoMusical;
    }

    public String getInfoArtista() {
        return "Artista{" +
                super.getInfoPessoa() +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", generoMusical=" + generoMusical +
                '}';
    }

    @Override
    public String toString() {
        return "Artista{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", generoMusical=" + generoMusical +
                '}';
    }

    public String getDescricao() {
        return descricao;
    }

    public String getGeneroMusical() {
        return generoMusical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artista artista)) return false;
        return id == artista.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }
}
