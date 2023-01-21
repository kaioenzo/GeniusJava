package view;

import controller.ArtistasController;
import model.Artista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Classe principal de artista, onde lista-se os artistas e possibilita a sua criação, exclusão, edição e detalhamento.
 */
public class ArtistaPanel extends BasePessoaPanel<Artista> {


    final String[] columnNames = {"Id", "Artista", "Gênero", "Data de Nascimento"};
    private boolean dialongIsOpen;
    private ArtistaDialog artistDialong;

    public ArtistaPanel() {
        super(new ArtistasController());
        setupLayout();
        setVisible(true);
    }

    /**
     * Método para abrir um dialog de artista, contendo suas informações e músicas
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

            if(!dialongIsOpen){
                artistDialong = new ArtistaDialog((ArtistasController) controller, id, nome, dataDeNascimento, descricao);
                dialongIsOpen = true;
            } else {
                artistDialong.setVisible(false);
                artistDialong = new ArtistaDialog((ArtistasController) controller, id, nome, dataDeNascimento, descricao);
                dialongIsOpen = true;
                artistDialong.setVisible(true);
                artistDialong.toFront();
            }

        } else {
            showJOptionPaneMessage("Selecione um artista para visualizar", "Erro ao visualizar artista", "artista");
        }
    }

    /**
     * @param editar parametro para definir se está editando um artista ou criando um novo
     *  Esse método simplifica e unifica a criação/edição de um artista
     */
    @Override
    protected void alterarDados(boolean editar) {
        boolean finished = false;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        boolean dataNascimentoValida = false;
        int id;
        LocalDate date;
        String nome = null;
        String genero = null;
        String dataDeNascimento = null;
        String descricao = null;


        if (editar && selectedRow != -1) {
            nome = model.getValueAt(selectedRow, 1).toString();
            genero = model.getValueAt(selectedRow, 2).toString();
            dataDeNascimento = model.getValueAt(selectedRow, 3).toString();
            descricao = controller.get(Integer.parseInt(model.getValueAt(selectedRow, 0).toString())).getDescricao();

        } else if (editar && selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma linha para deletar!",
                    "No row selected",
                    JOptionPane.INFORMATION_MESSAGE);
            finished = true;
        }
        if (!finished) {
            nome = (String) JOptionPane.showInputDialog(
                    this,
                    "Insira o nome do artista:",
                    "Criação de artista",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    nome);
            if (nome.isBlank()) {
                showJOptionPaneMessage("Nome não pode ser vazio!", "Erro na criação de artista", "artista");
            } else {
                genero = (String) JOptionPane.showInputDialog(
                        this,
                        "Insira o gênero musical do artista:",
                        "Criação de artista",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        genero);
                if (genero.isBlank()) {
                    showJOptionPaneMessage("Gênero não pode ser vazio!", "Erro na criação de artista", "artista");
                } else {
                    while (!dataNascimentoValida) {
                        dataDeNascimento = (String) JOptionPane.showInputDialog(
                                this,
                                "Insira a data de nascimento do artista no formato  dd/mm/yyyy:",
                                "Criação de artista",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                null,
                                dataDeNascimento);
                        if (dataDeNascimento.isBlank()) {
                            showJOptionPaneMessage("Data de nascimento não pode ser vazio!", "Erro na criação de artista", "artista");

                        } else {
                            try {
                                // tenta parsear a data
                                LocalDate.parse(dataDeNascimento, controller.formatter());
                                dataNascimentoValida = true;

                            } catch (DateTimeParseException e) {
                                showJOptionPaneMessage("Data inválida", "Erro na criação de artista", "artista");

                            }
                        }
                    }
                    descricao = (String) JOptionPane.showInputDialog(
                            this,
                            "Insira a descrição do artista:",
                            "Criação de artista",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            descricao);
                    if (descricao.isBlank()) {
                        showJOptionPaneMessage("Descricão não pode ser vazio!", "Erro na criação de artista", "artista");
                    } else {
                        date = LocalDate.parse(dataDeNascimento, controller.formatter());
                        if (editar) {
                            id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

                            controller.editar(id, new Artista(id, nome, date, descricao, genero));
                            var artista = controller.get(id);

                            model.setValueAt(artista.getNome(), selectedRow, 1);
                            model.setValueAt(artista.getGeneroMusical(), selectedRow, 2);
                            model.setValueAt(artista.getDataDeNascimento().format(controller.formatter()), selectedRow, 3);
                            table.repaint();
                        } else {
                            id = controller.getProximoId();
                            controller.adicionar(new Artista(id, nome, date, descricao, genero));
                            var artista = controller.get(id);
                            model.addRow(
                                    new Object[]{
                                            artista.getId(),
                                            artista.getNome(),
                                            artista.getGeneroMusical(),
                                            artista.getDataDeNascimentoFormatada()});
                        }
                    }
                }
            }

        }
    }

    /**
     * @param text recebe o nome do artista procurado
     * Este método procura uma string parecida na lista de artistas e atualiza a tabela com os resultados
     */
    @Override
    protected void pesquisar(String text) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        var artistasList = controller.getListaPeloNome(text);
        if (!artistasList.isEmpty()) {
            table.setModel(model);
            for (Artista artista : artistasList) {
                model.addRow(
                        new Object[]{
                                artista.getId(),
                                artista.getNome(),
                                artista.getGeneroMusical(),
                                artista.getDataDeNascimento().format(controller.formatter())});
            }

        }
    }

    /**
     * @return modelo padrão para a tabela de artistas
     */
    @Override
    protected DefaultTableModel popularDados() {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        var artistaList = super.controller.get();

        if (artistaList == null || artistaList.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nenhum artista está cadastrado!",
                    "No data",
                    JOptionPane.INFORMATION_MESSAGE);
            return model;
        }

        Object[] rowData = new Object[4];

        for (Artista artista : artistaList) {
            rowData[0] = artista.getId();
            rowData[1] = artista.getNome();
            rowData[2] = artista.getGeneroMusical();
            rowData[3] = artista.getDataDeNascimento().format(super.controller.formatter());
            model.addRow(rowData);
        }

        return model;

    }

}
