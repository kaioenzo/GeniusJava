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


    public Musica(
                  int id,
                  String nome,
                  GeneroMusical genero,
                  String letra,
                  ArrayList<Artista> artistas,
                  ArrayList<Produtor> produtores) {
        this.id = id;
        this.nome = nome;
        this.artistas.addAll(artistas);
        this.letra = letra;
        this.artistas.addAll(artistas);
        this.produtores.addAll(produtores);
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
        return "Musica{" +
                "nome='" + nome + '\'' +
                ", generos=" + generos +
                ", letra='" + letra + '\'' +
                ", artistas=" + artistas +
                '}';
    }

    public String getMusicaInfo() {
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


