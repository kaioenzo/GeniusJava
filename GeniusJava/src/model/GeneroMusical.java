package model;

public class GeneroMusical {
    private int id;
    private String nome;
    private String descricao;

    public GeneroMusical(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return "GeneroMusical{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
