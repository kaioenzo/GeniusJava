package org.example.controller;

import org.example.model.Musica;
import org.example.model.PortalDeMusica;
import org.example.model.Produtor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe controller de produtor. Aqui estão todos os métodos utilizados pela view para acessar os dados na classe. Esta
 * classe implementa {@link BaseController}.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 * @see Produtor
 */
public class ProdutorController implements BaseController<Produtor> {

    /**
     * Este metodo retorna todos os produtores cadastrados.
     *
     * @return lista de todos os produtores cadastrados
     */
    @Override
    public List<Produtor> get() {
        return Arrays.stream(bd.getAllProdutores()).toList();
    }

    /**
     * Este método exclue um produtor a partir do seu ID.
     *
     * @param id do produtor a ser excluído
     */
    @Override
    public int excluir(int id) {
        var idExcluido = bd.getProdutorPeloId(id).getId();
        bd.excluirProdutor(get(id));
        return idExcluido;
    }

    /**
     * Este método adiciona um produtor.
     *
     * @param objeto informações do produtor a ser cadastrado
     */
    @Override
    public void adicionar(Produtor objeto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(objeto.getDataDeNascimentoFormatada(), formatter);
        bd.cadastrarProdutor(objeto.getNome(), localDate, objeto.getDescricao(), objeto.getFuncao());
    }

    /**
     * Este método edita a informações de um produtor, a partir do seu ID.
     *
     * @param id             do produtor a ser editado
     * @param infoAtualizada informações atualizadas do produtor
     */
    @Override
    public Produtor editar(int id, Produtor infoAtualizada) {
        var produtor = get(id);
        produtor.setNome(infoAtualizada.getNome());
        produtor.setDataDeNascimento(LocalDate.parse(infoAtualizada.getDataDeNascimentoFormatada(), formatter()));
        produtor.setFuncao(infoAtualizada.getFuncao());
        produtor.setDescricao(infoAtualizada.getDescricao());
        return produtor;
    }

    /**
     * Este método retorna um produtor pelo seu ID;
     *
     * @param id do produtor buscado
     * @return produtor com o id informado
     */
    @Override
    public Produtor get(int id) {
        return bd.getProdutorPeloId(id);
    }

    /**
     * Este método retorna todos os produtores com um certo nome, a lógica está implementada na classe PortalDeMusica.
     * {@link PortalDeMusica#getProdutoresPeloNome(String)}
     *
     * @param nome do produtor a ser buscado
     * @return lista de produtor com aquele nome
     */
    @Override
    public List<Produtor> get(String nome) {
        return bd.getProdutoresPeloNome(nome);
    }

    /**
     * Retorna o ID do próximo produtor a ser cadastrado.
     *
     * @return id do próximo produtor
     */
    @Override
    public int getProximoId() {
        return bd.getProximoIdProdutor();
    }

    /**
     * Este método retorna a lista de musicas associadas a um produtor com um certo ID.
     *
     * @param id do produtor
     * @return lista de músicas
     */
    @Override
    public List<Musica> getMusicasAssociadas(int id) {
        var musicas = bd.getAllMusicas();
        List<Musica> musicasFiltradas = new ArrayList<>();

        for (Musica musica : musicas) {
            for (Produtor prod : musica.getProdutores()) {
                if (prod.getId() == id) {
                    musicasFiltradas.add(musica);
                    break;
                }
            }
        }
        return musicasFiltradas;
    }

}
