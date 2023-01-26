package model;

/**
 * Classe que representa um gênero musical
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 */
public class GeneroMusical {
    private int id;
    private String nome;
    private String descricao;

    /**
     * @param id que representa o ID único do gênero musical
     * @param nome que representa o nome do gênero musical
     * @param descricao que representa a descrição do gênero musical
     */
    public GeneroMusical(int id, String nome, String descricao) {
        this.id = id; this.nome = nome; this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GeneroMusical{" + "id=" + id + ", nome='" + nome + '\'' + ", descricao='" + descricao + '\'' + '}';
    }
}
