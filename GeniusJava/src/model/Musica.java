package model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Musica {
    private int id;
    private String nome;
    private HashSet<String> generos;
    private String letra;
    private HashSet<Artista> artistas;
    private HashSet<Produtor> produtores;
    private boolean fazParteAlbum;


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

    public void adicionarArtistas(List<Artista> artistas) {
        this.artistas.clear();
        this.artistas.addAll(artistas);
    }

    public void adicionarProdutores(List<Produtor> produtores) {

        this.produtores.clear();
        this.produtores.addAll(produtores);
    }

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


