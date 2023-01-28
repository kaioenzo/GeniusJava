package controller;

import exceptions.MusicaJaFazParteDeAlbumException;
import model.Album;
import model.Musica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Classe controller de álbum. Aqui estão todos os métodos utilizados pela view para acessar os dados na classe. Esta
 * classe implementa {@link controller.BaseController} e possue mais alguns métodos.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 */
public class AlbumController implements BaseController<Album> {

    /**
     * Este método retorna um álbum pelo seu ID;
     *
     * @param id do álbum buscado
     * @return álbum com o id informado
     */
    @Override
    public Album get(int id) {
        return bd.getAlbumPeloId(id);

    }

    /**
     * Este método retorna todos os álbums cadastrados.
     *
     * @return lista de todos os álbums cadastrados
     */
    @Override
    public List<Album> get() {
        return List.of(bd.getAllAlbums());
    }

    /**
     * Este método retorna todos os álbums com um certo nome, a lógica está implementar na classe
     * {@link model.PortalDeMusica#getAlbumPeloNome(String)}
     *
     * @param nome do álbum a ser buscado
     * @return lista de álbums com aquele nome
     */
    @Override
    public List<Album> get(String nome) {
        return bd.getAlbumPeloNome(nome);
    }

    /**
     * Este método exclue um álbum a partir do seu ID.
     *
     * @param id do álbum a ser excluído
     */
    @Override
    public int excluir(int id) {
        var idExcluido = bd.getAlbumPeloId(id).getId();
        bd.excluirAlbum(get(id));
        return idExcluido;
    }

    /**
     * Este método adiciona um álbum, e também verifica se as músicas informadas não fazem parte de outros álbums.
     *
     * @param objeto informações do álbum a ser cadastrado
     */
    @Override
    public void adicionar(Album objeto) throws MusicaJaFazParteDeAlbumException {
        MusicaController musicaController = new MusicaController();
        for (Musica musica : objeto.getMusicas()) {
            if (musica.getFazParteAlbum()) {
                throw new MusicaJaFazParteDeAlbumException(musica.getNome(), musicaController.getNomeAlbumAssociado(musica.getId()));
            }
        } objeto.getMusicas().forEach(musica -> musica.setFazParteAlbum(true));
        bd.cadastrarAlbum(objeto.getNome(), objeto.getDataDeLancamento(), objeto.getMusicas());
    }

    /**
     * Este método edita a informações de um álbum, a partir do seu ID.
     *
     * @param id             do álbuma ser editado
     * @param infoAtualizada informações atualizadas do álbum
     */
    @Override
    public Album editar(int id, Album infoAtualizada) {
        var album = bd.getAlbumPeloId(id);
        album.setMusicas(infoAtualizada.getMusicas());
        album.setNome(infoAtualizada.getNome());
        return album;
    }

    /**
     * Retorna o ID do próximo álbum a ser cadastrado.
     *
     * @return id do próximo álbum
     */
    @Override
    public int getProximoId() {
        return bd.getProximoIdAlbum();
    }

    /**
     * Este método retorna a lista de musicas associadas ao álbum com um certo ID.
     *
     * @param id do álbum
     * @return lista de músicas
     */
    @Override
    public List<Musica> getMusicasAssociadas(int id) {
        return get(id).getMusicas();
    }

    /**
     * Este métodor retorna a quantidade de músicas de um álbum.
     *
     * @param album instância de álbum com as informações
     * @return inteiro com a quantiadde músicas
     */
    public int getQtdMusicas(Album album) {
        return album.getMusicas().size();
    }

    /**
     * Este método retorna uma string com todos os produtores do álbum informado, usando um HashSet para evitar
     * repetições.
     *
     * @param album uma instância com as informaçõesde álbum
     * @return string com o nome dos artistas
     */
    public String artistasStrings(Album album) {
        HashSet<String> artistas = new HashSet<>();
        album.getArtistas().forEach(artista -> artistas.add(artista.getNome()));
        return Arrays.toString(artistas.toArray()).replace("[", "").replace("]", "");
    }

    /**
     * Este álbum retorna uma string com todos os produtores do álbum informado, usando um HashSet para evitar
     * repetições.
     *
     * @param album uma instância com as informações de álbum
     * @return string com o nome dos produtores
     * @see Album
     */
    public String produtoresString(Album album) {
        List<String> produtores = new ArrayList<>();
        album.getProdutores().forEach(produtor -> produtores.add(produtor.getNome()));
        return Arrays.toString(produtores.toArray()).replace("[", "").replace("]", "");
    }
}
