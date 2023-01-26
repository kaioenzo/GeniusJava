package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe que representa um artista e extende a classe Pessoa. Com descrição e gênero musical.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 * @see Pessoa
 */
public class Artista extends Pessoa {
    final private int id;
    private String descricao;
    private String generoMusical;

    /**
     * @param id que representa o ID único do artista
     * @param nome que representa o nome do artista
     * @param dataDeNascimento que representa a data de nascimento do artista
     * @param descricao que representa a descrição do artista
     * @param generoMusical que representa o gênero musical do artista
     */
    public Artista(int id, String nome, LocalDate dataDeNascimento, String descricao, String generoMusical) {
        super(id, nome, dataDeNascimento);
        this.id = id;
        this.descricao = descricao;
        this.generoMusical = generoMusical;
    }

    /**
     * Este método retorna as informações do artista
     *
     * @return ‘string’ com as informações do artista.
     */
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
