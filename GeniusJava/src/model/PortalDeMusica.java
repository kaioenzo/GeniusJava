package model;

import java.time.LocalDate;
import java.util.*;

public class PortalDeMusica {
    private static final PortalDeMusica instaciaUnica = new PortalDeMusica();

    private final Map<Integer, Musica> musicas;
    private final Map<Integer, Album> albums;
    private final Map<Integer, Artista> artistas;
    private final Map<Integer, Produtor> produtores;
    private final Map<Integer, GeneroMusical> generosMusicais;

    private int musicaId = 0;
    private int albumId = 0;
    private int artistaId = 0;
    private int generoMusicalId = 0;
    private int produtorId = 0;

    private PortalDeMusica() {
        this.produtores = new HashMap<>();
        this.musicas = new HashMap<>();
        this.albums = new HashMap<>();
        this.artistas = new HashMap();
        this.generosMusicais = new HashMap<>();
    }

    public static PortalDeMusica getInstance() {
        return instaciaUnica;
    }

    public void cadastrarGenero(String nome, String descricao) {
        generosMusicais.put(generoMusicalId, new GeneroMusical(generoMusicalId, nome, descricao));
        generoMusicalId++;
    }

    public void cadastrarMusica(
            String nome,
            GeneroMusical genero,
            String letra,
            ArrayList<Artista> artistas,
            ArrayList<Produtor> produtores
    ) {
        musicas.put(musicaId, new Musica(
                musicaId,
                nome,
                genero,
                letra,
                artistas,
                produtores));
        musicaId++;
    }

    public void cadastrarArtista(
            String nome,
            LocalDate dataDeNascimento,
            String descricao,
            GeneroMusical generoMusical
    ) {
        artistas.put(artistaId, new Artista(
                artistaId,
                nome,
                dataDeNascimento,
                descricao,
                generoMusical
        ));
    }

    public void cadastrarAlbum(
            String nome,
            LocalDate dataDeNascimento,
            ArrayList<Musica> musicas
    ) {
        albums.put(albumId, new Album(
                albumId,
                nome,
                dataDeNascimento,
                musicas));
    }

    // Retorna o próximo id

    public int getProximoIdMusica() {
        return musicaId;
    }

    public int getProximoIdArtista() {
        return artistaId;
    }

    public int getProximoIdAlbum() {
        return albumId;
    }

    public int getProximoIdProdutor() {
        return produtorId;
    }

    // Busca pelo id

    public Musica getMusicaPeloId(int id) {
        return musicas.get(id);
    }

    public Artista getArtistaPeloId(int id) {
        return artistas.get(id);
    }

    public Album getAlbumPeloId(int id) {
        return albums.get(id);
    }

    public Produtor getProdutorPeloId(int id) {
        return produtores.get(id);
    }


    // Busca pelo nome

    public Optional<Musica> getrMusicaPeloNome(String nome) {
        return musicas.values().stream().filter(m -> m.getNome().equals(nome)).findFirst();
    }

    public Optional<Artista> getArtistaPeloNome(String nome) {
        return artistas.values().stream().filter(a -> a.getNome().equals(nome)).findFirst();
    }

    public Optional<Album> getAlbumPeloNome(String nome) {
        return albums.values().stream().filter(a -> a.getNome().equals(nome)).findFirst();
    }

    public Optional<GeneroMusical> getGeneroPeloNome(String nome) {
        return generosMusicais.values().stream().filter(g -> g.getNome().toLowerCase().equals(nome.toLowerCase())).findFirst();
    }


    // Excluir

    public void exlcuirMusica(Musica musica){
        musicas.remove(musica.getId());
    }
    public void excluirArtista(Artista artista){
        artistas.remove(artista.getId());
    }
    public void excluirAlbum(Album album){
        albums.remove(album.getId());
    }
    public void excluirProdutor(Produtor produtor) {
        produtores.remove(produtor.getId());
    }
}
