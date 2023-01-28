package org.example.view;

import org.example.controller.ArtistasController;
import org.example.model.Artista;

/**
 * Esta classe implementa a classe BaseDialog com o Generics de Artista. Abre uma dialog com informações do artista.
 * Esta classe é construída nos métodos de visualizar das classes que herdam de BasePanel.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 */
public class ArtistaDialog extends BaseDialog<Artista> {

    /**
     * @param controller       aue representa a controller do artista
     * @param id               que representa o ID único do artista
     * @param nome             que representa o nome do artista
     * @param dataDeNascimento que representa a data de nascimento do artista
     * @param descricao        que representa a descrição do artista
     */
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