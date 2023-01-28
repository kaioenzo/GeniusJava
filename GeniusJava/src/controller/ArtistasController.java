package controller;

import model.Artista;
import model.Musica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe controller de artista. Aqui estão todos os métodos utilizados pela view para acessar os dados na classe. Esta
 * classe implementa {@link controller.BaseController}.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 * @see Artista
 */
public class ArtistasController implements BaseController<Artista> {

    /**
     * Este método retorna todos os artistas cadastrados.
     *
     * @return lista de todos os artistas cadastrados
     */
    @Override
    public List<Artista> get() {
        return Arrays.stream(bd.getAllArtistas()).toList();
    }

    /**
     * Este método exclue um artista a partir do seu ID.
     *
     * @param id do artista a ser excluído
     */
    @Override
    public int excluir(int id) {
        var idExcluid = bd.getArtistaPeloId(id).getId();
        bd.excluirArtista(bd.getArtistaPeloId(id));
        return idExcluid;
    }

    /**
     * Este método adiciona um artista.
     *
     * @param objeto informações do artista a ser cadastrado
     */
    @Override
    public void adicionar(Artista objeto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(objeto.getDataDeNascimentoFormatada(), formatter);
        bd.cadastrarArtista(objeto.getNome(), localDate, objeto.getDescricao(), objeto.getGeneroMusical());
    }

    /**
     * Este método edita a informações de um artista, a partir do seu ID.
     *
     * @param id             do artista a ser editado
     * @param infoAtualizada informações atualizadas do artista
     */
    @Override
    public Artista editar(int id, Artista infoAtualizada) {
        var artista = get(id); artista.setNome(infoAtualizada.getNome());
        artista.setDataDeNascimento(LocalDate.parse(infoAtualizada.getDataDeNascimentoFormatada(), formatter()));
        artista.setGeneroMusical(infoAtualizada.getGeneroMusical());
        artista.setDescricao(infoAtualizada.getDescricao());
        return artista;
    }

    /**
     * Este método retorna um artista pelo seu ID;
     *
     * @param id do artista buscado
     * @return artista com o id informado
     */
    @Override
    public Artista get(int id) {
        return bd.getArtistaPeloId(id);
    }

    /**
     * Este método retorna todos os artistas com um certo nome, a lógica está implementada na classe PortalDeMusica.
     * {@link model.PortalDeMusica#getAlbumPeloNome(String)}
     *
     * @param nome do artista a ser buscado
     * @return lista de artistas com aquele nome
     */
    @Override
    public List<Artista> get(String nome) {
        return bd.getArtistasPeloNome(nome);
    }

    /**
     * Retorna o ID do próximo artista a ser cadastrado.
     *
     * @return id do próximo artista
     */
    @Override
    public int getProximoId() {
        return bd.getProximoIdArtista();
    }

    /**
     * Este método retorna a lista de músicas associadas a um artista com um certo ID.
     *
     * @param id do artista
     * @return lista de músicas
     */
    @Override
    public List<Musica> getMusicasAssociadas(int id) {
        var artistasController = new MusicaController(); var musicaList = artistasController.get();
        List<Musica> artistasFiltradas = new ArrayList<>();

        for (Musica musica : musicaList) {
            for (Artista artist : musica.getArtistas()) {
                if (artist.getId() == id) {
                    artistasFiltradas.add(musica); break;
                }
            }
        } return artistasFiltradas;
    }

}
