package controller;

import model.Artista;
import model.Musica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArtistasController implements PessoaBaseController<Artista> {
    /**
     * @return Lista de todos os artistas
     */
    @Override
    public List<Artista> get() {
        return Arrays.stream(bd.getAllArtistas()).toList();
    }

    /**
     * @param id do artista a ser excluido
     */
    @Override
    public void excluir(int id) {
        bd.excluirArtista(bd.getArtistaPeloId(id));
    }

    /**
     * @param pessoa objeto de artista a ser criado
     */
    @Override
    public void adicionar(Artista pessoa) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(pessoa.getDataDeNascimentoFormatada(), formatter);
        bd.cadastrarArtista(pessoa.getNome(), localDate, pessoa.getDescricao(), pessoa.getGeneroMusical());
    }

    /**
     * @param id do artista que será editado
     * @param pessoaAtualizada objeto com informações da edição
     */
    @Override
    public void editar(int id, Artista pessoaAtualizada) {
        var artista = get(id);
        artista.setNome(pessoaAtualizada.getNome());
        artista.setDataDeNascimento(LocalDate.parse(pessoaAtualizada.getDataDeNascimentoFormatada(), formatter()));
        artista.setGeneroMusical(pessoaAtualizada.getGeneroMusical());
        artista.setDescricao(pessoaAtualizada.getDescricao());
    }


    /**
     * @param id do artista procurado
     * @return artista procurado
     */
    @Override
    public Artista get(int id) {
        return bd.getArtistaPeloId(id);
    }

    /**
     * @param nome do artista procurado
     * @return retorna lista de artistas com o nome pesquisado
     */
    @Override
    public List<Artista> getListaPeloNome(String nome) {
        return bd.getArtistasPeloNome(nome);
    }

    /**
     * @return id do próximo artista a ser cadastrado
     */
    @Override
    public int getProximoId() {
        return bd.getProximoIdArtista();
    }

    /**
     * @param id do artista que se deseja obter as músicas
     * @return lista de musicas associadas aquele artista
     */
    @Override
    public List<Musica> getMusicas(int id) {
        var musicas = bd.getAllMusicas();
        List<Musica> musicasFiltradas = new ArrayList<>();

        for (Musica musica : musicas) {
            for(Artista artist: musica.getArtistas()){
                if (artist.getId() == id) {
                    musicasFiltradas.add(musica);
                    break;
                }
            }
        }
        return musicasFiltradas;
    }


}
