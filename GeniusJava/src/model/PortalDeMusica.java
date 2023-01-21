package model;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class PortalDeMusica {
    private static final PortalDeMusica instaciaUnica = new PortalDeMusica();

    public static PortalDeMusica getInstance() {
        return instaciaUnica;
    }

    private final Map<Integer, Musica> musicas;
    private final Map<Integer, Album> albums;
    private final Map<Integer, Artista> artistas;
    private final Map<Integer, Produtor> produtores;
    private final Map<Integer, GeneroMusical> generosMusicais;

    private int musicaId = 1;
    private int albumId = 1;
    private int artistaId = 1;
    private int generoMusicalId = 1;
    private int produtorId = 1;

    private PortalDeMusica() {
        this.produtores = new HashMap<>();
        this.musicas = new HashMap<>();
        this.albums = new HashMap<>();
        this.artistas = new HashMap<>();
        this.generosMusicais = new HashMap<>();
    }

//    public static PortalDeMusica getInstance() {
//        return instaciaUnica;
//    }

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
        HashSet<GeneroMusical> generoMusicalsSet = new HashSet<>();
        generoMusicalsSet.add(genero);
        HashSet<Artista> artistaSet = new HashSet<>(artistas);
        HashSet<Produtor> produtorHashSet = new HashSet<>(produtores);
        musicas.put(musicaId, new Musica(
                musicaId,
                nome,
                generoMusicalsSet,
                letra,
                artistaSet,
                produtorHashSet));
        musicaId++;
    }

    public void cadastrarArtista(
            String nome,
            LocalDate dataDeNascimento,
            String descricao,
            String generoMusical
    ) {
        artistas.put(artistaId, new Artista(
                artistaId,
                nome,
                dataDeNascimento,
                descricao,
                generoMusical
        ));
        artistaId++;
    }

    public void cadastrarProdutor(
            String nome,
            LocalDate dataDeNascimento,
            String descricao,
            Atribuicao atribuicao
    ) {
        produtores.put(produtorId, new Produtor(
                produtorId,
                nome,
                dataDeNascimento,
                descricao,
                atribuicao
        ));
        produtorId++;
    }

    public void cadastrarAlbum(
            String nome,
            LocalDate dataDeLancamento,
            ArrayList<Musica> musicas
    ) {
        albums.put(albumId, new Album(
                albumId,
                nome,
                dataDeLancamento,
                musicas));
        albumId++;
    }


    public Artista[] getAllArtistas() {
        return artistas.values().toArray(new Artista[0]);
    }

    public Musica[] getAllMusicas() {
        return musicas.values().toArray(new Musica[0]);
    }

    public Produtor[] getAllProdutores() {
        return produtores.values().toArray(new Produtor[0]);
    }

    // Retorna o próximo id

    public int getProximoIdMusica() {
        return musicaId;
    }

    public int getProximoIdArtista() {
        return artistaId;
    }

    public int getProximoIdProdutor() {
        return produtorId;
    }

    public int getProximoIdAlbum() {
        return albumId;
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

    public Optional<Musica> getMusicaPeloNome(String nome) {
        return musicas.values().stream().filter(m -> m.getNome().equals(nome)).findFirst();
    }

    public List<Artista> getArtistasPeloNome(String nome) {
        return artistas.values().stream().filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase())).collect(Collectors.toList());
    }

    public List<Produtor> getProdutoresPeloNome(String nome) {
        return produtores.values().stream().filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase())).collect(Collectors.toList());
    }

    public Optional<Album> getAlbumPeloNome(String nome) {
        return albums.values().stream().filter(a -> a.getNome().equals(nome)).findFirst();
    }

    public Optional<GeneroMusical> getGeneroPeloNome(String nome) {
        return generosMusicais.values().stream().filter(g -> g.getNome().toLowerCase().equals(nome.toLowerCase())).findFirst();
    }


    // Excluir

    public void exlcuirMusica(Musica musica) {
        musicas.remove(musica.getId());
    }

    public void excluirArtista(Artista artista) {
        artistas.remove(artista.getId());
    }

    public void excluirAlbum(Album album) {
        albums.remove(album.getId());
    }

    public void excluirProdutor(Produtor produtor) {
        produtores.remove(produtor.getId());
    }



}
