package controller;

import model.Album;
import model.Musica;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MusicaController implements BaseController<Musica> {
    private AlbumController albumController = new AlbumController();

    /**
     * @param id da música procurada
     * @return musica buscada pelo id
     */
    @Override
    public Musica get(int id) {
        return bd.getMusicaPeloId(id);
    }

    /**
     * @return lista com todas as músicas cadastradas
     */
    @Override
    public List<Musica> get() {
        return List.of(bd.getAllMusicas());
    }

    /**
     * @param nome
     * @return lista com musicas com aquele nome
     */
    @Override
    public List<Musica> get(String nome) {
        return bd.getMusicaPeloNome(nome);
    }

    /**
     * Este método exclue uma música a partir do seu id e também remove de um álbum, caso a música esteja
     * asscoiada a algum álbum
     *
     * @param id da música a ser excluída
     */
    @Override
    public void excluir(int id) {
        var optionalAlbum = getAlbumAssocieado(id);
        if (optionalAlbum.isPresent()) {
            var album = optionalAlbum.get();
            var musica = get(id);
            album.removerMusica(musica);
        }
        bd.exlcuirMusica(get(id));
    }

    /**
     * @param infoAtualizada Este método adiciona novas músicas
     */
    @Override
    public void adicionar(Musica infoAtualizada) {

        bd.cadastrarMusica(
                infoAtualizada.getNome(),
                infoAtualizada.getGeneros(),
                infoAtualizada.getLetra(),
                infoAtualizada.getArtistas(),
                infoAtualizada.getProdutores());
    }

    /**
     * @param id
     * @param infoAtualizada
     */
    @Override
    public void editar(int id, Musica infoAtualizada) {
        var musica = get(id);
        musica.setNome(infoAtualizada.getNome());
        musica.setLetra(infoAtualizada.getLetra());
        musica.adicionarArtistas(infoAtualizada.getArtistas());
        musica.adicionarProdutores(infoAtualizada.getProdutores());
        musica.adicionargenerosmusicais(infoAtualizada.getGeneros());
    }

    /**
     * @return id da próxima música
     */
    @Override
    public int getProximoId() {
        return bd.getProximoIdMusica();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<Musica> getMusicasAssociadas(int id) {
        return null;
    }

    /**
     * @return formatador padrão de datas para o padrão dd/MM/yyyy
     */
    @Override
    public DateTimeFormatter formatter() {
        return BaseController.super.formatter();
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
     * @return string com o nome dos produtores presentes na música informada
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
     * @return String com o nome do álbum ou mensagem padrão
     */
    public String getNomeAlbumAssociado(int id) {
        var musica = get(id);
        var albums = albumController.get();
        var optional = albums.stream().filter(album -> album.getMusicas().contains(musica)).findFirst();
        if (optional.isPresent()) {
            return optional.get().getNome();
        }
        return "Não faz parte de nenhum álbum";
    }

    /**
     * Este método procura um álbum associado a uma música
     *
     * @param id da música
     * @return instância  do álbum
     */
    public Optional<Album> getAlbumAssocieado(int id) {
        var musica = get(id);
        var albums = albumController.get();
        var optional = albums.stream().filter(album -> album.getMusicas().contains(musica)).findFirst();
        if (optional.isPresent()) {
            return optional;
        }
        return optional;
    }
}
