package view;

import controller.ProdutorController;
import model.Atribuicao;
import model.Produtor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.time.LocalDate;

public class ProdutorPanel extends BasePanel<Produtor> {
    final String[] columnNames = {"Id", "Produtor", "Função", "Data de Nascimento"};
    final String[] produtorFuncoes = {"EXECUTIVO", "ENGENHEIRO DE MIXAGEM", "ENGENHEIRO DE SOM", "EDITOR", "VOCAL"};
    private boolean dialogIsOpen;
    private ProdutorDialog produtorDialog;

    public ProdutorPanel() {
        super(new ProdutorController());
        super.columnNames = this.columnNames;
        super.setupLayout();
    }

    /**
     * Método para abrir um dialog de produtor, contendo suas informações e músicas
     */
    @Override
    protected void visualizar() {
        var model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            String nome = model.getValueAt(selectedRow, 1).toString();
            String dataDeNascimento = model.getValueAt(selectedRow, 3).toString();
            String descricao = controller.get(Integer.parseInt(model.getValueAt(selectedRow, 0).toString())).getDescricao();

            if (!dialogIsOpen) {
                produtorDialog = new ProdutorDialog(controller, id, nome, dataDeNascimento, descricao);
                dialogIsOpen = true;
            }
            else {
                produtorDialog.setVisible(false);
                produtorDialog = new ProdutorDialog(controller, id, nome, dataDeNascimento, descricao);
                dialogIsOpen = true;
                produtorDialog.setVisible(true);
                produtorDialog.toFront();
            }

        }
        else {
            showJOptionPaneMessage("Selecione um artista para visualizar", "Erro ao visualizar artista", "artista");
        }
    }

    /**
     * @param editar booleano que indica a ação a ser executada, edição ou deleção
     */
    @Override
    protected void alterarDados(boolean editar) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        boolean dataNascimentoValida = false;
        int id;
        LocalDate dataNascimento;
        String nome = null;
        String funcao;
        String stringDataNascimento = null;
        String descricao = null;

        if (editar && selectedRow != -1) {
            nome = model.getValueAt(selectedRow, 1).toString();
            funcao = model.getValueAt(selectedRow, 2).toString();
            stringDataNascimento = model.getValueAt(selectedRow, 3).toString();
            descricao = controller.get(Integer.parseInt(model.getValueAt(selectedRow, 0).toString())).getDescricao();

        }
        else if (editar && selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma linha para deletar!",
                    "No row selected",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        nome = (String) JOptionPane.showInputDialog(
                this,
                "Insira o nome do produtor:",
                "Criação de produtor",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                nome);
        if (nome == null) {
            return;
        }
        if (nome.isBlank()) {
            showJOptionPaneMessage("Nome do produtor não pode ser vazio!", "Erro na criação de produtor", "produtor");
            return;
        }

        JComboBox<String> funcaoBox = new JComboBox<>();
        for (String produtorFuncoe : produtorFuncoes) {
            funcaoBox.addItem(produtorFuncoe);
        }
        funcaoBox.setSelectedIndex(0);

         funcao = (String) JOptionPane.showInputDialog(
                this,
                "Insira o gênero musical do produtor:",
                "Criação de produtor",
                JOptionPane.PLAIN_MESSAGE,
                null,
                produtorFuncoes,
                funcaoBox);

        if (funcao == null) {
            return;
        }
        if (funcao.isBlank()) {
            showJOptionPaneMessage("Função não pode ser vazio!", "Erro na criação de produtor", "produtor");
            return;
        }

        while (!dataNascimentoValida) {
            try {
                MaskFormatter dateMask = new MaskFormatter("##/##/####");
                dateMask.setPlaceholderCharacter('_');
                JFormattedTextField dateField = new JFormattedTextField(dateMask);
                dateField.setValue(stringDataNascimento);
                JPanel panel = new JPanel();
                panel.add(new JLabel("Insira a data de nascimento do produtor:"));
                panel.add(dateField);
                int resultDialog = JOptionPane.showConfirmDialog(null, panel, "Criação de produtor",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (resultDialog == JOptionPane.OK_OPTION) {
                    stringDataNascimento = dateField.getText();
                    dataNascimentoValida = true;
                }
                else {
                    return;
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Error parsing date", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        descricao = (String) JOptionPane.showInputDialog(
                this,
                "Insira a descrição do produtor:",
                "Criação de produtor",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                descricao);
        if (descricao == null) {
            return;
        }
        if (descricao.isBlank()) {
            showJOptionPaneMessage("Descricão não pode ser vazio!", "Erro na criação de produtor", "produtor");
            return;
        }

        dataNascimento = LocalDate.parse(stringDataNascimento, controller.formatter());
        if (editar) {
            id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

            controller.editar(id, new Produtor(id, nome, dataNascimento, descricao, Atribuicao.valueOf(funcao)));
            var produtor = controller.get(id);

            model.setValueAt(produtor.getNome(), selectedRow, 1);
            model.setValueAt(produtor.getFuncao(), selectedRow, 2);
            model.setValueAt(produtor.getDataDeNascimento().format(controller.formatter()), selectedRow, 3);
            table.repaint();
        }
        else {
            id = controller.getProximoId();
            super.controller.adicionar(new Produtor(id, nome, dataNascimento, descricao, Atribuicao.valueOf(funcao)));
            var produtor = controller.get(id);
            model.addRow(
                    new Object[]{
                            produtor.getId(),
                            produtor.getNome(),
                            produtor.getFuncao(),
                            produtor.getDataDeNascimentoFormatada()});
        }
    }

    /**
     * @param text da pesquisa de produtores
     */
    @Override
    protected void pesquisar(String text) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        var artistasList = controller.get(text);
        if (!artistasList.isEmpty()) {
            table.setModel(model);
            for (Produtor produtor : artistasList) {
                model.addRow(
                        new Object[]{
                                produtor.getId(),
                                produtor.getNome(),
                                produtor.getFuncao(),
                                produtor.getDataDeNascimento().format(controller.formatter())});
            }

        }
    }

    /**
     * @return modelo da tabela
     */
    @Override
    protected DefaultTableModel popularDados() {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        var produtorList = super.controller.get();

        if (produtorList == null || produtorList.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nenhum produtor está cadastrado!",
                    "No data",
                    JOptionPane.INFORMATION_MESSAGE);
            return model;
        }

        Object[] rowData = new Object[4];

        for (Produtor produtor : produtorList) {
            rowData[0] = produtor.getId();
            rowData[1] = produtor.getNome();
            rowData[2] = produtor.getFuncao();
            rowData[3] = produtor.getDataDeNascimento().format(super.controller.formatter());
            model.addRow(rowData);
        }

        return model;
    }

}