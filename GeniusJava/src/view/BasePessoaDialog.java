package view;

import controller.PessoaBaseController;
import model.Musica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class BasePessoaDialog<K> extends JDialog {
    private JLabel nome;
    private JLabel dataDeNascimento;
    private JTextArea labelEspecial;
    private JTable table;

    protected String[] columnNames = {"Id", "Musica"};
    private int id;
    private PessoaBaseController<K> controller;

    public BasePessoaDialog(
            PessoaBaseController<K> controller,

            int id, String nome,
            String dataDeNascimento,
            String descricao) {
        setTitle("Informações de: " + nome);
        this.nome = new JLabel(nome);
        this.dataDeNascimento = new JLabel(dataDeNascimento);
        this.labelEspecial = new JTextArea(descricao);
        this.controller = controller;
        this.id = id;

        setUpLayout();

        this.pack();
        this.setVisible(true);
    }

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


        // Data de nascimento

        constraints.gridx = 1;
        constraints.gridy = 1;
        add(labelEspecial, constraints);

        JLabel birthDateLabel = new JLabel("Data de nascimento:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(birthDateLabel, constraints);

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
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        add(scrollPane, constraints);

    }

    DefaultTableModel popularDados() {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        var list = controller.getMusicas(id);

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

    public void setController(PessoaBaseController<K> controller) {
        this.controller = controller;
    }
}
