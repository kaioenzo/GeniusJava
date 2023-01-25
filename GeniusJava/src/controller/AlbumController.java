package controller;

import exceptions.MusicaJaFazParteDeAlbumException;
import model.Album;
import model.Musica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Classe controller de álbum. Aqui estão todos os métodos utilizados pela view para acessar os dados na classe @PortalDeMusica
 */
public class AlbumController implements BaseController<Album> {

    /**
     * @param id do álbum buscado
     * @return álbum com o id informado
     */
    @Override
    public Album get(int id) {
        return bd.getAlbumPeloId(id);

    }

    /**
     * @return lista de todos os álbums cadastrados
     */
    @Override
    public List<Album> get() {
        return List.of(bd.getAllAlbums());
    }

    /**
     * @param nome do álbum a ser buscado
     * @return lista de álbums com aquele nome
     */
    @Override
    public List<Album> get(String nome) {
        return bd.getAlbumPeloNome(nome);
    }

    /**
     * @param id do álbum a ser excluído
     *           Exclue o álbum pelo id
     */
    @Override
    public void excluir(int id) {
        bd.excluirAlbum(get(id));
    }

    /**
     * Este método adiciona
     *
     * @param pessoa informações do álbum a ser cadastrado
     */
    @Override
    public void adicionar(Album pessoa) throws MusicaJaFazParteDeAlbumException {
         MusicaController musicaController = new MusicaController();
        for (Musica musica : pessoa.getMusicas()) {
            if (musica.getFazParteAlbum()) {
                throw new MusicaJaFazParteDeAlbumException(musica.getNome(),
                        musicaController.getNomeAlbumAssociado(musica.getId()));
            }
        }
        pessoa.getMusicas().forEach(musica -> musica.setFazParteAlbum(true));
        bd.cadastrarAlbum(pessoa.getNome(), pessoa.getDataDeLancamento(), pessoa.getMusicas());
    }

    /**
     * @param id             do álbuma ser editado
     * @param infoAtualizada informações atualizadas do álbum
     *                       Este método edita a informações de um álbum
     */
    @Override
    public void editar(int id, Album infoAtualizada) {
        var album = bd.getAlbumPeloId(id);
        album.setMusicas(infoAtualizada.getMusicas());
        album.setNome(infoAtualizada.getNome());
    }

    /**
     * @return id do próximo álbum
     */
    @Override
    public int getProximoId() {
        return bd.getProximoIdAlbum();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<Musica> getMusicasAssociadas(int id) {
        return get(id).getMusicas();
    }

    public int getQtdMusicas(Album album) {
        return album.getMusicas().size();
    }

    /**
     * Este método devolve uma string com todos os produtores do álbum informado, usando um HashSet para evitar
     * repetições.
     *
     * @param album uma insância com as informações
     * @return string com o nome dos artistas
     */
    public String artistasStrings(Album album) {
        HashSet<String> artistas = new HashSet<>();
        album.getArtistas().forEach(artista -> artistas.add(artista.getNome()));
        return Arrays.toString(artistas.toArray()).replace("[", "").replace("]", "");
    }

    /**
     * Este álbum devolve uma string com todos os produtores do álbum informado, usando um HashSet para evitar
     * repetições.
     *
     * @param album uma insância com as informações
     * @return string com o nome dos produtores
     */
    public String produtoresString(Album album) {
        List<String> produtores = new ArrayList<>();
        album.getProdutores().forEach(produtor -> produtores.add(produtor.getNome()));
        return Arrays.toString(produtores.toArray()).replace("[", "").replace("]", "");
    }
}
