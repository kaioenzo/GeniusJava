package view;

import controller.AlbumController;
import model.Album;

/**
 * Esta classe implementa a classe BaseDialog com o Generics de Album. Abre uma dialog com informações do álbum. Está
 * classe é construída nos métodos de visualizar das classes que herdam de BasePanel.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 */
public class AlbumsDialog extends BaseDialog<Album> {
    /**
     * @param controller       que representa a controller do álbum
     * @param id               que representa o ID único do álbum
     * @param nome             que representa o nome do álbum
     * @param dataDeNascimento que representa a data de lançamento do álbum
     * @param descricao        que representa a descrição do álbum
     */
    public AlbumsDialog(AlbumController controller, int id, String nome, String dataDeNascimento,
                        String descricao) {
        super(controller, id, nome, dataDeNascimento, descricao, "lançamento");

    }

}
