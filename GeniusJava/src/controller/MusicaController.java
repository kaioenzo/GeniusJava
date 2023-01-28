package controller;

import model.Album;
import model.Musica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Classe controller de música. Aqui estão todos os métodos utilizados pela view para acessar os dados na classe. Esta
 * classe implementa {@link controller.BaseController}.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 * @see Musica
 */
public class MusicaController implements BaseController<Musica> {
    private AlbumController albumController = new AlbumController();

    /**
     * Este método retorna um artista pelo seu ID;
     *
     * @param id do artista buscado
     * @return artista com o id informado
     */
    @Override
    public Musica get(int id) {
        return bd.getMusicaPeloId(id);
    }

    /**
     * Este método retorna todos os artistas cadastrados.
     *
     * @return lista de todos os artistas cadastrados
     */
    @Override
    public List<Musica> get() {
        return List.of(bd.getAllMusicas());
    }

    /**
     * Este método retorna todos os artistas com um certo nome, a lógica está implementar na classe
     * {@link model.PortalDeMusica#getAlbumPeloNome(String)}
     *
     * @param nome do artista a ser buscado
     * @return lista de artistas com aquele nome
     */
    @Override
    public List<Musica> get(String nome) {
        return bd.getMusicaPeloNome(nome);
    }

    /**
     * Este método exclue um artista a partir do seu ID.
     *
     * @param id do artista a ser excluído
     */
    @Override
    public int excluir(int id) {
        var idExcluido = bd.getMusicaPeloId(id).getId();
        var optionalAlbum = getAlbumAssocieado(id);
        if (optionalAlbum.isPresent()) {
            var album = optionalAlbum.get(); var musica = get(id); album.removerMusica(musica);
        } bd.exlcuirMusica(get(id));
        return idExcluido;
    }

    /**
     * Este método adiciona um artista, e também verifica se as músicas informadas não fazem parte de outros artistas.
     *
     * @param objeto informações do artista a ser cadastrado
     */
    @Override
    public void adicionar(Musica objeto) {

        bd.cadastrarMusica(objeto.getNome(), objeto.getGeneros(), objeto.getLetra(), objeto.getArtistas(), objeto.getProdutores());
    }

    /**
     * Este método edita a informações de um artista, a partir do seu ID.
     *
     * @param id             do artistaa ser editado
     * @param infoAtualizada informações atualizadas do artista
     */
    @Override
    public Musica editar(int id, Musica infoAtualizada) {
        var musica = get(id); musica.setNome(infoAtualizada.getNome()); musica.setLetra(infoAtualizada.getLetra());
        musica.adicionarArtistas(infoAtualizada.getArtistas());
        musica.adicionarProdutores(infoAtualizada.getProdutores());
        musica.adicionargenerosmusicais(infoAtualizada.getGeneros());
        return musica;
    }

    /**
     * Retorna o ID do próximo artista a ser cadastrado.
     *
     * @return id do próximo artista
     */
    @Override
    public int getProximoId() {
        return bd.getProximoIdMusica();
    }

    /**
     * Este método retorna a lista de musicas associadas ao artista com um certo ID.
     *
     * @param id do artista
     * @return lista de músicas
     */
    @Override
    public List<Musica> getMusicasAssociadas(int id) {
        return null;
    }

    /**
     * @param musica instância
     * @return string com o nome dos artistas presentes na música informada
     */
    public String artistasStrings(Musica musica) {
        List<String> artistas = new ArrayList<>();
        musica.getArtistas().forEach(artista -> artistas.add(artista.getNome()));
        return Arrays.toString(artistas.toArray()).replace("[", "").replace("]", "");
    }

    /**
     * @param musica instância
     * @return ‘string’ com o nome dos produtores presentes na música informada
     */
    public String produtoresString(Musica musica) {
        List<String> produtores = new ArrayList<>();
        musica.getProdutores().forEach(produtor -> produtores.add(produtor.getNome()));
        return Arrays.toString(produtores.toArray()).replace("[", "").replace("]", "");
    }

    /**
     * Este método procura o nome do álbum associado a uma música
     *
     * @param id da música
     * @return ‘String’ com o nome do álbum ou mensagem padrão
     */
    public String getNomeAlbumAssociado(int id) {
        var musica = get(id); var albums = albumController.get();
        var optional = albums.stream().filter(album -> album.getMusicas().contains(musica)).findFirst();
        if (optional.isPresent()) {
            return optional.get().getNome();
        } return "Não faz parte de nenhum álbum";
    }

    /**
     * Este método procura um álbum associado a uma música
     *
     * @param id da música
     * @return instância do lbum
     */
    public Optional<Album> getAlbumAssocieado(int id) {
        var musica = get(id); var albums = albumController.get();
        return albums.stream().filter(album -> album.getMusicas().contains(musica)).findFirst();
    }
}
