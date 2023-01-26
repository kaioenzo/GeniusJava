package model;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que representa o banco de dados. Essa classe mapeia os objetos ao seu ID e facilita a busca desse objetos
 * em um lugar centralizado. Esta classe está implementaada com o padrão singleton, que garante que somente uma única
 * instância dessa classe exista, assim garantindo a centralização dos dados. Esta classe é ativamente utilizada
 * pelas controllers.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 */
public class PortalDeMusica {
    private static final PortalDeMusica instaciaUnica = new PortalDeMusica();

    /**
     * Este método retorna a instância única de PortalDeMusica e é por meio dele que o padrão singleton é implementado
     *
     * @return instância única da classe PortalDeMusica
     */
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
        this.produtores = new HashMap<>(); this.musicas = new HashMap<>(); this.albums = new HashMap<>();
        this.artistas = new HashMap<>(); this.generosMusicais = new HashMap<>();
    }

    /**
     * Este método cadastra um novo gênero musical e armazena no seu map correspondente, para futuras operações.
     *
     * @param nome      do gênero musical
     * @param descricao do gênero musical
     */
    public void cadastrarGenero(String nome, String descricao) {
        generosMusicais.put(generoMusicalId, new GeneroMusical(generoMusicalId, nome, descricao)); generoMusicalId++;
    }

    /**
     * Este método cadastra uma nova música e armazena no seu map correspondente, para futuras operações.
     *
     * @param nome       da música
     * @param genero     da música
     * @param letra      da música
     * @param artistas   da música
     * @param produtores da música
     */
    public void cadastrarMusica(String nome, List<String> genero, String letra, List<Artista> artistas, List<Produtor> produtores) {
        HashSet<Artista> artistaSet = new HashSet<>(artistas);
        HashSet<Produtor> produtorHashSet = new HashSet<>(produtores);
        HashSet<String> generosHashSet = new HashSet<>(genero); System.out.println(musicaId);
        System.out.println("bd cadastra musica");
        musicas.put(musicaId, new Musica(musicaId, nome, generosHashSet, letra, artistaSet, produtorHashSet));
        musicaId++;
    }

    /**
     * Este método cadastra um novo artista e armazena no seu map correspondente, para futuras operações.
     *
     * @param nome             do artista
     * @param dataDeNascimento do artista
     * @param descricao        do artista
     * @param generoMusical    do artista
     */
    public void cadastrarArtista(String nome, LocalDate dataDeNascimento, String descricao, String generoMusical) {
        artistas.put(artistaId, new Artista(artistaId, nome, dataDeNascimento, descricao, generoMusical)); artistaId++;
    }

    /**
     * Este método cadastra um produtor e armazena no seu map correspondente, para futuras operações
     *
     * @param nome             do produtor
     * @param dataDeNascimento do produtor
     * @param descricao        do produtor
     * @param atribuicao       do produtor
     */
    public void cadastrarProdutor(String nome, LocalDate dataDeNascimento, String descricao, Atribuicao atribuicao) {
        produtores.put(produtorId, new Produtor(produtorId, nome, dataDeNascimento, descricao, atribuicao));
        produtorId++;
    }

    /**
     * Este método cadastra um álbum e armazena no seu mao correspodente, para futuras operações
     *
     * @param nome             do album
     * @param dataDeLancamento do album
     * @param musicas          do album
     */
    public void cadastrarAlbum(String nome, LocalDate dataDeLancamento, ArrayList<Musica> musicas) {
        albums.put(albumId, new Album(albumId, nome, dataDeLancamento, musicas)); albumId++;
    }


    /**
     * Retorna todos os artistas cadastrados, referente ao map de artistas {@link #artistas}
     *
     * @return array de artistas
     */
    public Artista[] getAllArtistas() {
        return artistas.values().toArray(new Artista[0]);
    }

    /**
     * Retorna todos as músicas cadastradas, referente ao map de músicas {@link #musicas}
     *
     * @return array de artistas
     */
    public Musica[] getAllMusicas() {
        return musicas.values().toArray(new Musica[0]);
    }

    /**
     * Retorna todos os produtores cadastrados, referente ao map de produtores {@link #produtores}
     *
     * @return array de artistas
     */
    public Produtor[] getAllProdutores() {
        return produtores.values().toArray(new Produtor[0]);
    }

    /**
     * Retorna todos os álbums cadastrados, referente ao map de álbums {@link #albums}
     *
     * @return array de artistas
     */
    public Album[] getAllAlbums() {
        return albums.values().toArray(new Album[0]);
    }


    /**
     * Esta método retorna o id da próxima música a ser cadastrada.
     *
     * @return int referente ao ID da próxima música
     */
    public int getProximoIdMusica() {
        return musicaId;
    }

    /**
     * Esta método retorna o id do próxima artista a ser cadastrada.
     *
     * @return int referente ao ID do próximo artista
     */
    public int getProximoIdArtista() {
        return artistaId;
    }

    /**
     * Esta método retorna o id do próxima produtor a ser cadastrada.
     *
     * @return int referente ao ID do próximo produtor
     */
    public int getProximoIdProdutor() {
        return produtorId;
    }

    /**
     * Esta método retorna o id do próxima álbum a ser cadastrada.
     *
     * @return int referente ao ID do próximo álbum
     */
    public int getProximoIdAlbum() {
        return albumId;
    }


    /**
     * Este método busca uma música pelo seu ID e retorna essa instância.
     *
     * @param id da música procurada
     * @return música procurada
     */
    public Musica getMusicaPeloId(int id) {
        return musicas.get(id);
    }

    /**
     * Este método busca um artista pelo seu ID e retorna essa instância.
     *
     * @param id da artista procurada
     * @return artista procurada
     */
    public Artista getArtistaPeloId(int id) {
        return artistas.get(id);
    }

    /**
     * Este método busca um álbum pelo seu ID e retorna essa instância.
     *
     * @param id do álbum procurado
     * @return álbum procurado
     */
    public Album getAlbumPeloId(int id) {
        return albums.get(id);
    }

    /**
     * Este método busca um produtor pelo seu ID e retorna essa instância.
     *
     * @param id do produtor procurado
     * @return produtor procurado
     */
    public Produtor getProdutorPeloId(int id) {
        return produtores.get(id);
    }


    /**
     * Este método busca todas as músicas que contêm o nome parecido com o parâmetro informado. É utilizado
     * principalmente para implementar a busca pelo nome de artistas na controller de música
     * {@link controller.MusicaController#get(String)}
     *
     * @param nome da(s) músicas(s) procurada(s)
     * @return instáncia de música
     * @see controller.MusicaController
     */
    public List<Musica> getMusicaPeloNome(String nome) {
        return musicas.values().stream().filter(m -> m.getNome().toLowerCase().contains(nome.toLowerCase())).collect(Collectors.toList());
    }

    /**
     * Este método busca todas os artistas que contêm o nome parecido com o parâmetro informado. É utilizado
     * principalmente para implementar a busca pelo nome de artistas na controller de artistas
     * {@link controller.ArtistasController#get(String)}
     *
     * @param nome do(s) artista(s) procurado(s)
     * @return instáncia de artista
     * @see controller.ArtistasController
     */
    public List<Artista> getArtistasPeloNome(String nome) {
        return artistas.values().stream().filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase())).collect(Collectors.toList());
    }

    /**
     * Este método busca todas os produtores que contêm o nome parecido com o parâmetro informado. É utilizado
     * principalmente para implementar a busca pelo nome de produtores na controller de produtores
     * {@link controller.ProdutorController#get(String)}
     *
     * @param nome do(s) produtor(s) procurado(s)
     * @return instáncia de produtor
     * @see controller.ProdutorController
     */
    public List<Produtor> getProdutoresPeloNome(String nome) {
        return produtores.values().stream().filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase())).collect(Collectors.toList());
    }

    /**
     * Este método busca todas os álbums que contêm o nome parecido com o parâmetro informado. É utilizado
     * principalmente para implementar a busca pelo nome de álbums na controller de álbums
     * {@link controller.AlbumController#get(String)}
     *
     * @param nome do(s) album(s) procurado(s)
     * @return instáncia de album
     * @see controller.AlbumController
     */
    public List<Album> getAlbumPeloNome(String nome) {
        return albums.values().stream().filter(a -> a.getNome().toLowerCase().contains(nome.toLowerCase())).collect(Collectors.toList());
    }

    /**
     * Este metodo busca todas os generos musicais que contêe o nome parecido com o parâmetro informado
     *
     * @param nome do(s) gênero(s) musical(is) procurado(s)
     * @return instáncia de de genero
     */
    public Optional<GeneroMusical> getGeneroPeloNome(String nome) {
        return generosMusicais.values().stream().filter(g -> g.getNome().equalsIgnoreCase(nome)).findFirst();
    }

    /**
     * Este método remove uma música do sistema;
     *
     * @param musica isntância de musica a ser excluída
     */
    public void exlcuirMusica(Musica musica) {
        musicas.remove(musica.getId());
    }

    /**
     * Este método remove um artista do sistema;
     *
     * @param artista isntância de artista a ser excluído
     */
    public void excluirArtista(Artista artista) {
        artistas.remove(artista.getId());
    }

    /**
     * Este método remove um álbum do sistema;
     *
     * @param album isntância de álbum a ser excluído
     */
    public void excluirAlbum(Album album) {
        albums.remove(album.getId());
    }

    /**
     * Este método remove um produtor do sistema;
     *
     * @param produtor isntância de álbum a ser excluído
     */
    public void excluirProdutor(Produtor produtor) {
        produtores.remove(produtor.getId());
    }

}
