package view;

import controller.BaseController;
import model.Produtor;

/**
 * Esta classe implementa a classe BaseDialog com o Generics de Produtor. Abre uma dialog com informações do produtor.
 * Esta classe é construída nos métodos de visualizar das classes que herdam de BasePanel.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 */
public class ProdutorDialog extends BaseDialog<Produtor> {

    /**
     * @param controller       aue representa a controller do artista
     * @param id               que representa o ID único do artista
     * @param nome             que representa o nome do artista
     * @param dataDeNascimento que representa a data de nascimento do artista
     * @param descricao        que representa a descrição do artista
     */
    public ProdutorDialog(BaseController<Produtor> controller, int id, String nome, String descricao, String dataDeNascimento) {
        super(controller, id, nome, descricao, dataDeNascimento, "nascimento");
    }
}
