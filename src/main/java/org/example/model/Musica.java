package org.example.model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Classe que representa uma Música. Com nome, gêneros, letra, artistas e produtores.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 * @see Pessoa
 */
public class Musica {
    private int id;
    private String nome;
    private HashSet<String> generos;
    private String letra;
    private HashSet<Artista> artistas;
    private HashSet<Produtor> produtores;
    private boolean fazParteAlbum;


    /**
     * @param id que representa o ID único da música
     * @param nome que representa o nome da música
     * @param generos que representa a lista de gêneros musicais da música
     * @param letra que representa a letra da música
     * @param artistas que representa os artistas da música
     * @param produtores que representa os produtores da música
     */
    public Musica(int id, String nome, HashSet<String> generos, String letra, HashSet<Artista> artistas, HashSet<Produtor> produtores) {
        this.id = id;
        this.nome = nome;
        this.generos = generos;
        this.letra = letra;
        this.artistas = artistas;
        this.produtores = produtores;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List
            <String> getGeneros() {
        return generos.stream().toList();
    }

    public String getLetra() {
        return letra;
    }

    public List<Artista> getArtistas() {
        return artistas.stream().toList();
    }

    public List<Produtor> getProdutores() {
        return produtores.stream().toList();
    }

    /**
     * Este método adiciona os artistas da música, antes ele limpa os artistas para evitar que artistas repetidos
     * sejam adicionados
     *
     * @param artistas artistas a serem adicionados a essa música.
     */
    public void adicionarArtistas(List<Artista> artistas) {
        this.artistas.clear();
        this.artistas.addAll(artistas);
    }

    /**
     * * Este método adiciona os produtores da música, antes ele limpa os produtores da música para evitar que
     * artistas repetidos sejam adicionados
     *
     * @param produtores produtores a serem adicionados a essa música.
     */
    public void adicionarProdutores(List<Produtor> produtores) {

        this.produtores.clear();
        this.produtores.addAll(produtores);
    }

    /**
     * * Este método adiciona os gêneros da música, antes ele limpa os gêneros da música para evitar que
     * gêneros repetidos sejam adicionados
     *
     * @param generos musicais a serem adicionados a essa música.
     */
    public void adicionargenerosmusicais(List<String> generos) {

        this.generos.clear();
        this.generos.addAll(generos);
    }

    public void deletarArtista(Artista artista) {
        artistas.remove(artista);
    }

    public void deletarGeneroMusical(String genero) {
        generos.remove(genero);
    }

    public void deletarProdutor(Produtor produtor) {
        produtores.remove(produtor);
    }

    @Override
    public String toString() {
        var generosString = this.generos.stream().toList();
        return "Musica{" +
                "nome='" + nome + '\'' +
                ", generos=" + generosString +
                ", letra='" + letra + '\'' +
                ", artistas=" + artistas +
                '}';
    }

    public String getMusicaInfo() {
        var artistas = this.artistas.stream().toList();
        var produtores = this.produtores.stream().toList();
        return "Musica{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", generos=" + generos +
                ", letra='" + letra + '\'' +
                ", dataDeLancamento=" +
                ", artistas=" + artistas +
                ", produtores=" + produtores +
                '}';
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGeneros(HashSet<String> generos) {
        this.generos = generos;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public void setArtistas(HashSet<Artista> artistas) {
        this.artistas = artistas;
    }

    public void setProdutores(HashSet<Produtor> produtores) {
        this.produtores = produtores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Musica musica)) return false;
        return id == musica.id && nome.equals(musica.nome) && generos.equals(musica.generos) && letra.equals(musica.letra) && artistas.equals(musica.artistas) && produtores.equals(musica.produtores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, generos, letra, artistas, produtores);
    }


    public boolean getFazParteAlbum() {
        return fazParteAlbum;
    }

    public void setFazParteAlbum(boolean fazParteAlbum) {
        this.fazParteAlbum = fazParteAlbum;
    }
}


