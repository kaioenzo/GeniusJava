package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Album {
    private int id;
    private String nome;
    private LocalDate dataDeLancamento;
    private ArrayList<Musica> musicas;

    public Album(int id, String nome, LocalDate dataDeLancamento, ArrayList<Musica> musica) {
        this.id = id;
        this.nome = nome;
        this.dataDeLancamento = dataDeLancamento;
        this.musicas = musica;
    }

    public Album() {
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataDeLancamento() {
        return dataDeLancamento;
    }

    public ArrayList<Musica> getMusicas() {
        return musicas;
    }

    public String getAlbumInfo() {
        var musicas = this.musicas.toString();
        return "Album{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataDeLancamento=" + dataDeLancamento +
                ", musica=" + musicas +
                '}';
    }
    public void adicionarMusica(Musica musica){
        musicas.add(musica);
    }
    public void deletarMusica(Musica muisca){
        musicas.remove(muisca);
    }
    public List<Artista> getArtistas() {
        List<Artista> artistasList = new ArrayList<>();
        musicas.forEach(m -> artistasList.addAll(m.getArtistas()));
        return artistasList;
    }

    public List<Produtor> getProdutores() {
        List<Produtor> produtoresList =  new ArrayList<>();
        musicas.forEach(m -> produtoresList.addAll(m.getProdutores()));
        return produtoresList;
    }
}
