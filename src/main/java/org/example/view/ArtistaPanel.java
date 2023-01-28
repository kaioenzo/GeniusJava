package org.example.view;

import org.example.controller.ArtistasController;
import org.example.model.Artista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import java.text.ParseException;
import java.time.LocalDate;

/**
 * Essa classe implementa a classe BasePanel com o Generics de artista e aprensenta as informações de Artista, além de
 * possibilitar o CRUD. Usando a sua controller correspondente {@link ArtistasController} para realizar as operações.
 * Usando a sua dialog correspondente para apresentar as informações {@link ArtistaDialog}
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 * @see Artista
 * @see ArtistasController
 * @see ArtistaDialog
 */
public class ArtistaPanel extends BasePanel<Artista> {


    final String[] columnNames = {"Id", "Artista", "Gênero", "Data de Nascimento"};
    private boolean dialongIsOpen;
    private ArtistaDialog artistDialong;

    public ArtistaPanel() {
        super(new ArtistasController());
        setupLayout();
        setVisible(true);
    }

    /**
     * Este método sobreescreve o método visualizar e implementa a lógica de visualização dos dados do artista. Se uma
     * linha da tabela estiver sido selecionada, este método exbibe as informações da música a partir da classe
     * MusicaDialog que recebe as informações a serem exibidas.
     *
     * @see MusicaDialog
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
     * Este método abre uma série de telas para o cadastro/atualização de um artista. O retorno da entrada do
     * utilizador é armazenado e logo em seguida é verficado se está vazio, caso não esteja continua com o fluxo de
     * validação de dados. Utilizando a ArtistaController para realizar o cadastro ou atualização. Nota-se que para a
     * edição deve haver uma linha da tabela {@link #table} selecionada.
     *
     * @param editar booleano que indica a ação a ser executada, edição ou deleção
     * @see ArtistasController
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
            if(nome == null){
                return;
            }
            if (nome.isBlank()) {
                showJOptionPaneMessage("Nome não pode ser vazio!", "Erro na criação de artista", "artista");
                return;
            }

            genero = (String) JOptionPane.showInputDialog(
                    this,
                    "Insira o gênero musical do artista:",
                    "Criação de artista",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    genero);
            if(genero ==  null){
                return;
            }
            if (genero.isBlank()) {
                showJOptionPaneMessage("Gênero não pode ser vazio!", "Erro na criação de artista", "artista");
                return;
            }

            while (!dataNascimentoValida) {
                try {
                    MaskFormatter dateMask = new MaskFormatter("##/##/####");
                    dateMask.setPlaceholderCharacter('_');
                    JFormattedTextField dateField = new JFormattedTextField(dateMask);
                    dateField.setValue(dataDeNascimento);
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("Insira a data de nascimento do artista:"));
                    panel.add(dateField);
                    int resultDialog = JOptionPane.showConfirmDialog(null, panel, "Criação de artista",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (resultDialog == JOptionPane.OK_OPTION) {
                        dataDeNascimento = dateField.getText();
                        dataNascimentoValida = true;
                    }
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null, "Error parsing date", "Error", JOptionPane.ERROR_MESSAGE);
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
            if(descricao == null){
                return;
            }
            if (descricao.isBlank()) {
                showJOptionPaneMessage("Descricão não pode ser vazio!", "Erro na criação de artista", "artista");
                return;
            }

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

    /**
     * Este método pesquisa um artista, pelo nome, alterando a tabela com os resultados encontrados. Faz utilização do
     * método de busca de ArtistaController {@link ArtistasController#get(String)}
     *
     * @param text nome do artista sendo procurado
     */
    @Override
    protected void pesquisar(String text) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        var artistasList = controller.get(text);
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
     * Este método preenche a tabela com os dados.
     *
     * @return o modelo padrão da tabela com os produtores cadastrados
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
