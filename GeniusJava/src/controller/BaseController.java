package controller;

import model.Musica;
import model.PortalDeMusica;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Esta interface genérica cria uma base para todas as controllores implementarem, contendo os métodos básicos de
 * CRUD, e métodos utilitários que podem ser requisistados pela view que utliza essa essa controller.
 *
 * @param <K> Tipo genérico para a construção dos métodos.
 * @author Kaio Enzo Salgado
 * @version 1.0
 * @see AlbumController
 * @see ArtistasController
 * @see MusicaController
 * @see ProdutorController
 */
public interface BaseController<K> {
    /**
     * Garante que todas as controllers acessem a mesma instância na classe PortalDeMusica
     *
     * @see PortalDeMusica
     */
    PortalDeMusica bd = PortalDeMusica.getInstance();

    /**
     * Este método deve implementar uma lógica para a busca de um objeto a partir do seu ID.
     *
     * @param id do objeto que busca
     * @return aquele objeto
     */
    K get(int id);

    /**
     * Este método deve implementar uma lógica parar retornar uma lista com todos os objetos.
     *
     * @return lista de objetos
     */
    List<K> get();

    /**
     * Este método deve implementar uma lógica para  a busca de uma lista de objetos, pelo seu atributo de nome, visto
     * que todas as classes principais da model, as quais essa controller genérica suporta, possuem o atributo nome.
     *
     * @param nome atributo nome do objeto
     * @return
     * @see model.Album
     * @see model.Artista
     * @see model.Produtor
     * @see model.Pessoa
     */
    List<K> get(String nome);

    /**
     * Este método deve implementar uma lógica para excluir um objeto a partir do seu ID.
     *
     * @param id do objeto a ser excluído
     */
    void excluir(int id);

    /**
     * Este método deve implementar uma lógica para adicionar um objeto.
     *
     * @param objeto objeto a ser adicionado
     */
    void adicionar(K objeto);

    /**
     * Este método deve implementar uma lógica para alterar as informações de um objeto. Visando garantir os
     * princípios da OO e tirar o máximo de proveito dos conceitos, vê-se que é informado como parâmetro um objeto,
     * delegando a cada controller a responsabilidade de modificar o objeto buscado com as informações do objeto
     * informado por parâmetro, ao invés de ser informado cada atributo da classe na assinatura do método.
     * Ex: Para editar a classe de Produtor, teríamos de informar suas características, mas caso fosse a classe de
     * Album, seriam outros atributos, fazendo com que métodos específicos fossem criados para a edição de cada
     * classe, ferindo o princípio de generalização e reaproveitamento de código.
     *
     * @param id             do objeto a ser atualizado
     * @param infoAtualizada informação do objeto atualizado
     * @see model.Produtor
     * @see model.Album
     */
    void editar(int id, K infoAtualizada);

    /**
     * Este método de implementar uma lógica que retorne o id do próximo objeto a ser cadastrado. Geralmente, este
     * método está associado ao método adicionar {@link #adicionar(Object)} ()}
     *
     * @return id do próximo objeto a ser cadastrado
     */
    int getProximoId();

    /**
     * Este método deve implementar uma lógica para procurar uma lista de músicas associadas a um objeto, a partir do
     * seu ID.
     *
     * @param id do objeto
     * @return lista de músicas
     */
    List<Musica> getMusicasAssociadas(int id);

    /**
     * Este método default retorna todas as músicas cadastradas;
     *
     * @return lista de músicas
     */
    default List<Musica> getALlMusicas() {
        return Arrays.stream(bd.getAllMusicas()).toList();
    }

    /**
     * Este método utilitário possibilita o uso de um formatador padrão de datas no formato dia/mes/ano
     *
     * @return formatador padrão de datas
     */
    default DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

}
