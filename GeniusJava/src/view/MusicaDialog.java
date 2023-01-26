package view;

import controller.MusicaController;
import model.Musica;

import javax.swing.*;
import java.awt.*;

/**
 * Esta classe abre uma dialog com informações do artista. Devido ao seu layout especial, está classe não herda de
 * BasePanel.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 */
public class MusicaDialog extends JDialog {
    private final JLabel produtores;
    private MusicaController controller;
    private int id;
    private Musica musica;
    private JTextArea letra;
    private JLabel nome;
    private JLabel artistas;

    /**
     * @param controller que representa a controller da música
     * @param id         que representa o ID único da música
     * @param artistas   que representa os artistas da música
     * @param produtores que representa os produtores da música
     */
    public MusicaDialog(
            MusicaController controller,
            int id,
            String artistas,
            String produtores) {
        this.id = id;
        this.controller = controller;
        this.musica = controller.get(id);
        this.nome = new JLabel(musica.getNome());
        this.artistas = new JLabel(artistas);
        this.produtores = new JLabel(produtores);

        setUpLayout();

        this.pack();
        this.setVisible(true);
    }

    /**
     * Este método altera o layout da dialog e apresentando as informações
     */
    private void setUpLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // nome
        JLabel nameLabel = new JLabel("Nome:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 5, 5);
        add(nameLabel, constraints);

        nome = new JLabel(musica.getNome());
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(nome, constraints);

        // artisas
        JLabel artistaLabel = new JLabel("Artistas:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(artistaLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        add(artistas, constraints);

        // produtores
        JLabel produtoresLabel = new JLabel("Produtores");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(produtoresLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        add(produtores, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        add(new JLabel("Albums:"), constraints);
        constraints.gridx = 1;
        constraints.gridy = 3;
        add(new JLabel(controller.getNomeAlbumAssociado(id)), constraints);

        // letra
        letra = new JTextArea(15, 40);
        letra.setText(musica.getLetra());
        letra.setEditable(false);
        letra.setLineWrap(true);
        letra.setWrapStyleWord(true);
        letra.setBounds(40, 40, 200, 300);
        JScrollPane scrollPane = new JScrollPane(letra);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        add(scrollPane, constraints);

    }

}
