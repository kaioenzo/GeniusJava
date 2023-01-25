package controller;

import model.Musica;
import model.Produtor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProdutorController implements BaseController<Produtor> {

    /**
     * @return Lista com todos os produtores
     */
    @Override
    public List<Produtor> get() {
        return Arrays.stream(bd.getAllProdutores()).toList();
    }

    /**
     * @param id do produtor
     */
    @Override
    public void excluir(int id) {
        bd.excluirProdutor(get(id));
    }

    /**
     * @param pessoa info atualizada do produtor
     */
    @Override
    public void adicionar(Produtor pessoa) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(pessoa.getDataDeNascimentoFormatada(), formatter);
        bd.cadastrarProdutor(pessoa.getNome(), localDate, pessoa.getDescricao(), pessoa.getFuncao());
    }

    /**
     * @param id do produtor
     * @param infoAtualizada info atualizada do produtor
     */
    @Override
    public void editar(int id, Produtor infoAtualizada) {
        var produtor = get(id);
        produtor.setNome(infoAtualizada.getNome());
        produtor.setDataDeNascimento(LocalDate.parse(infoAtualizada.getDataDeNascimentoFormatada(), formatter()));
        produtor.setFuncao(infoAtualizada.getFuncao());
        produtor.setDescricao(infoAtualizada.getDescricao());
    }

    /**
     * @param id do produtor
     * @return produtor pelo id
     */
    @Override
    public Produtor get(int id) {
        return bd.getProdutorPeloId(id);
    }

    /**
     * @param nome do produtor
     * @return lista de produtores com o nome pesquisado
     */
    @Override
    public List<Produtor> get(String nome) {
        return bd.getProdutoresPeloNome(nome);
    }

    /**
     * @return id do proximo produtor a ser cadastrado
     */
    @Override
    public int getProximoId() {
        return bd.getProximoIdProdutor();
    }

    /**
     * @param id do produtor
     * @return lista de musicas que aquele produtor participa
     */
    @Override
    public List<Musica> getMusicasAssociadas(int id) {
        var musicas = bd.getAllMusicas();
        List<Musica> musicasFiltradas = new ArrayList<>();

        for (Musica musica : musicas) {
            for(Produtor prod: musica.getProdutores()){
                if (prod.getId() == id) {
                    musicasFiltradas.add(musica);
                    break;
                }
            }
        }
        return musicasFiltradas;
    }

}
