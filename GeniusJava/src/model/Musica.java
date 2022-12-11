package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Musica {
    private int id;
    private String nome;
    private HashSet<GeneroMusical> generos;
    private String letra;

    private HashSet<Artista> artistas;
    private HashSet<Produtor> produtores;


    public Musica(int id, String nome, HashSet<GeneroMusical> generos, String letra, HashSet<Artista> artistas, HashSet<Produtor> produtores) {
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

    public HashSet<GeneroMusical> getGeneros() {
        return generos;
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

    public void AdicionarArtistas(ArrayList<Artista> artistas){
        this.artistas.addAll(artistas);
    }
    public void AdicionarProdutores(ArrayList<Produtor> produtores){
        this.produtores.addAll(produtores);
    }
    public void AdicionarGenerosMusicais(ArrayList<GeneroMusical> generos){
        this.generos.addAll(generos);
    }
    public void deletarArtista(Artista artista){
        artistas.remove(artista);
    }
    public void deletarGeneroMusical(GeneroMusical genero){
        generos.remove(genero);
    }
    public void deletarProdutor(Produtor produtor){
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
}


