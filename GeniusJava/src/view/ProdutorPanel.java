package view;

import controller.ProdutorController;
import model.Atribuicao;
import model.Produtor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ProdutorPanel extends BasePessoaPanel<Produtor> {
    final String[] columnNames = {"Id", "Produtor", "Função", "Data de Nascimento"};
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

            if(!dialogIsOpen){
                produtorDialog = new ProdutorDialog(controller, id, nome, dataDeNascimento,descricao);
                dialogIsOpen = true;
            } else {
                produtorDialog.setVisible(false);
                produtorDialog = new ProdutorDialog(controller, id, nome, dataDeNascimento,descricao );
                dialogIsOpen = true;
                produtorDialog.setVisible(true);
                produtorDialog.toFront();
            }

        } else {
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
        LocalDate date;
        String nome = null;
        String funcao = null;
        String dataDeNascimento = null;
        String descricao = null;


        if (editar && selectedRow != -1) {
            nome = model.getValueAt(selectedRow, 1).toString();
            funcao = model.getValueAt(selectedRow, 2).toString();
            dataDeNascimento = model.getValueAt(selectedRow, 3).toString();
            descricao = controller.get(Integer.parseInt(model.getValueAt(selectedRow, 0).toString())).getDescricao();

        } else if (editar && selectedRow == -1) {
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
        if (nome.isBlank()) {
            showJOptionPaneMessage("Nome não pode ser vazio!", "Erro na criação de produtor", "produtor");
            return;
        }
        JComboBox<String> funcaoBox = new JComboBox<>();
        funcaoBox.addItem("EXECUTIVO");
        funcaoBox.addItem("ENGENHEIRO DE MIXAGEM");
        funcaoBox.addItem("ENGENHEIRO DE SOM");
        funcaoBox.addItem("EDITOR");
        funcaoBox.addItem("VOCAL");
        funcaoBox.setSelectedIndex(0);

        Object result = JOptionPane.showInputDialog(
                this,
                "Insira o gênero musical do artista:",
                "Criação de artista", JOptionPane.PLAIN_MESSAGE,
                null,
                new String[] {"EXECUTIVO","ENGENHEIRO DE MIXAGEM","ENGENHEIRO DE SOM","EDITOR","VOCAL"},
                funcaoBox);


        if (result != null) {
            funcao = (String) result;
        }

        if (funcao != null && funcao.isBlank()) {
            showJOptionPaneMessage("Função não pode ser vazio!", "Erro na criação de produtor", "produtor");
            return;
        }

        while (!dataNascimentoValida) {
            dataDeNascimento = (String) JOptionPane.showInputDialog(
                    this,
                    "Insira a data de nascimento do produtor no formato  dd/mm/yyyy:",
                    "Criação de produtor",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    dataDeNascimento);
            if (dataDeNascimento.isBlank()) {
                showJOptionPaneMessage("Data de nascimento não pode ser vazio!", "Erro na criação de produtor", "produtor");

            }
            try {
                // tenta parsear a data
                LocalDate.parse(dataDeNascimento, controller.formatter());
                dataNascimentoValida = true;

            } catch (DateTimeParseException e) {
                showJOptionPaneMessage("Data inválida", "Erro na criação de produtor", "produtor");

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
        if (descricao.isBlank()) {
            showJOptionPaneMessage("Descricão não pode ser vazio!", "Erro na criação de produtor", "produtor");
        }
        date = LocalDate.parse(dataDeNascimento, controller.formatter());
        if (editar) {
            id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

            controller.editar(id, new Produtor(id, nome, date, descricao, Atribuicao.valueOf(funcao)));
            var produtor = controller.get(id);

            model.setValueAt(produtor.getNome(), selectedRow, 1);
            model.setValueAt(produtor.getFuncao(), selectedRow, 2);
            model.setValueAt(produtor.getDataDeNascimento().format(controller.formatter()), selectedRow, 3);
            table.repaint();
        } else {
            id = controller.getProximoId();
            System.out.println(id);
            super.controller.adicionar(new Produtor(id, nome, date, descricao, Atribuicao.valueOf(funcao)));
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
        var artistasList = controller.getListaPeloNome(text);
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