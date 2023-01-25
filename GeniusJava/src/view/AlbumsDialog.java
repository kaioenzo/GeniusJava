package view;

import controller.AlbumController;
import model.Album;

public class AlbumsDialog extends BaseDialog<Album> {
    public AlbumsDialog(AlbumController controller, int id, String nome, String dataDeNascimento,
                        String descricao) {
        super(controller, id, nome, dataDeNascimento, descricao, "lançamento");

    }

}
