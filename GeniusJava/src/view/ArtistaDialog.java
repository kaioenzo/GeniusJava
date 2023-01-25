package view;

import controller.ArtistasController;
import model.Artista;


public class ArtistaDialog extends BaseDialog<Artista> {
    public ArtistaDialog(
            ArtistasController controller,
            int id, String nome,
            String dataDeNascimento,
            String descricao) {

        super(controller,
                id,
                nome,
                dataDeNascimento,
                descricao,
                "nascimento"
        );
        super.setColumnNames(this.columnNames);
    }


}