package org.example.view;

import org.example.controller.BaseController;
import org.example.model.Musica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Esta classe abstrata exibe uma janela quando herdada pelas classes dialog, exibindo assim as informações das
 * classes Album, Artista e Produtor.
 *
 * @param <K> generics que possibilita a generalização da classe.
 * @author Kaio Enzo Salgado
 * @version 1.0
 * @see ArtistaDialog
 * @see AlbumsDialog
 * @see ProdutorDialog
 */

public abstract class BaseDialog<K> extends JDialog {
    private JLabel nome;
    private JLabel dataDeNascimento;
    private JTextArea labelEspecial;
    private JTable table;

    private String dataLabel;

    protected String[] columnNames = {"Id", "Musica"};
    private int id;
    private BaseController<K> controller;

    /**
     * @param controller       que representa a controller associada a dialog
     * @param id               que representa o ID do objeto
     * @param nome             que representa o nome do objeto
     * @param dataDeNascimento que representa a data de nascimento ou lançamento do objeto
     * @param descricao        que representa a descrição do objeto
     * @param dataLabel        que representa a label a ser exibida para a dataDeNascimento
     */
    public BaseDialog(
            BaseController<K> controller,
            int id, String nome,
            String dataDeNascimento,
            String descricao,
            String dataLabel) {
        setTitle("Informações de: " + nome);
        this.nome = new JLabel(nome);
        this.dataDeNascimento = new JLabel(dataDeNascimento);
        this.labelEspecial = new JTextArea(descricao);
        this.controller = controller;
        this.dataLabel = dataLabel;
        this.id = id;

        setUpLayout();
        pack();
        setVisible(true);
    }

    /**
     * Este método implementa o layout básico de todas as dialogs que herdam da classe BaseDialog
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

        constraints.gridx = 1;
        constraints.gridy = 0;
        add(nome, constraints);

        // Data
        JLabel birthDateLabel = new JLabel("Data de " + dataLabel + ":");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(birthDateLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(labelEspecial, constraints);

        // especial label
        JLabel especialLabel = new JLabel("Descrição");
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(especialLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        add(dataDeNascimento, constraints);

        // table
        table = new JTable(popularDados());
        table.setDefaultEditor(Object.class, null);
        table.setBounds(40, 40, 200, 300);

        JScrollPane scrollPane = new JScrollPane(table);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        add(scrollPane, constraints);

    }

    /**
     * Este método preenche a tabela das dialogs, com os dados resgatados da controller.
     *
     * @return modelo padrão da tabela
     */
    protected DefaultTableModel popularDados() {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        var list = controller.getMusicasAssociadas(id);

        if (list == null || list.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nenhuma música de " + nome.getText() + " está cadastrado!",
                    "No data found",
                    JOptionPane.INFORMATION_MESSAGE);
            return model;
        }

        Object[] rowData = new Object[2];

        for (Musica musica : list) {
            rowData[0] = musica.getId();
            rowData[1] = musica.getNome();
            model.addRow(rowData);
        }

        return model;

    }

    public void setNome(JLabel nome) {
        this.nome = nome;
    }

    public void setDataDeNascimento(JLabel dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public void setLabelEspecial(JTextArea labelEspecial) {
        this.labelEspecial = labelEspecial;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setController(BaseController<K> controller) {
        this.controller = controller;
    }
}
