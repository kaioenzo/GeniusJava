package view;

import controller.ArtistasController;
import model.Artista;


public class ArtistaDialog extends BasePessoaDialog<Artista> {
    public ArtistaDialog(
            ArtistasController controller,
            int id, String nome,
            String dataDeNascimento,
            String descricao) {

        super(controller,
                id,
                nome,
                dataDeNascimento,
                descricao
        );
        super.setColumnNames(this.columnNames);
    }


}