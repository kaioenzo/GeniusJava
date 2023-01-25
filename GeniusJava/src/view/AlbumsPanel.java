package view;

import controller.AlbumController;
import exceptions.MusicaJaFazParteDeAlbumException;
import model.Album;
import model.Musica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AlbumsPanel extends BasePanel<Album> {

    AlbumController controller = new AlbumController();
    private final String[] columnNames = {"Id", "Nome", "Artistas"};
    private boolean dialogIsOpen = false;
    private AlbumsDialog albumDialog;

    public AlbumsPanel() {
        super(new AlbumController());
        super.setupLayout();
    }

    /**
     *
     */
    @Override
    protected void visualizar() {
        var model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            String nome = model.getValueAt(selectedRow, 1).toString();
            String dataDeNascimento = controller.get(id).getDataDeLancamento().format(controller.formatter());
            String artistas =
                    controller.artistasStrings(controller.get(Integer.parseInt(model.getValueAt(selectedRow, 0).toString())));

            if (!dialogIsOpen) {
                albumDialog = new AlbumsDialog(controller, id, nome, dataDeNascimento, artistas);
                dialogIsOpen = true;
            }
            else {
                albumDialog.setVisible(false);
                albumDialog = new AlbumsDialog(controller, id, nome, dataDeNascimento, artistas);
                dialogIsOpen = true;
                albumDialog.setVisible(true);
                albumDialog.toFront();
            }

        }
        else {
            showJOptionPaneMessage("Selecione um Álbum para visualizar", "Erro ao visualizar Álbum", "album");
        }
    }

    /**
     * @param editar booleano que indica a ação a ser executada, edição ou deleção
     *               Este método abre uma série de telas para o cadastro/atualização de um álbum
     */
    @Override
    protected void alterarDados(boolean editar) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        int id;
        LocalDate dataLancamento;
        String nome = null;
        String stringDataLancamento = null;
        HashSet<Musica> musicaHashSet = new HashSet<>();
        boolean dataValida = false;
        boolean musicasValidas = false;

        if (editar && selectedRow != -1) {
            id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
            nome = model.getValueAt(selectedRow, 1).toString();
            stringDataLancamento = controller.get(Integer.parseInt(model.getValueAt(selectedRow, 0).toString())).getDataDeLancamento().format(controller.formatter());

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
                "Insira o nome do álbum:",
                "Criação de álbum",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                nome);
        if (nome == null) {
            return;
        }
        if (nome.isBlank()) {
            showJOptionPaneMessage("Nome do álbum não pode ser vazio!", "Erro na criação de álbum", "álbum");
            return;
        }

        // data de lançamento do álbum
        while (!dataValida) {
            try {
                MaskFormatter dateMask = new MaskFormatter("##/##/####");
                dateMask.setPlaceholderCharacter('_');
                JFormattedTextField dateField = new JFormattedTextField(dateMask);
                dateField.setValue(stringDataLancamento);
                JPanel panel = new JPanel();
                panel.add(new JLabel("Insira a data de lançamento do álbum:"));
                panel.add(dateField);
                int resultDialog = JOptionPane.showConfirmDialog(null, panel, "Criação de álbum",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (resultDialog == JOptionPane.OK_OPTION) {
                    stringDataLancamento = dateField.getText();
                    dataValida = true;
                }
                else {
                    return;
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Error parsing date", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // seleciona as músicas do álbum
        while (!musicasValidas) {
            var musicas = controller.getALlMusicas();
            List<String> arrayNomesMusicas = new ArrayList<>();
            musicas.forEach(musica -> arrayNomesMusicas.add(musica.getNome()));

            JList<String> jListMusicas = new JList<>(arrayNomesMusicas.toArray(new String[0]));
            jListMusicas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            JScrollPane scrollPaneArtistaList = new JScrollPane(jListMusicas);
            int result = JOptionPane.showConfirmDialog(
                    this,
                    scrollPaneArtistaList,
                    "Selecione as músicas do álbum:",
                    JOptionPane.OK_OPTION,
                    JOptionPane.CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                List<String> musicasSelecionadas = jListMusicas.getSelectedValuesList();
                for (Musica musica : musicas) {
                    if (musicasSelecionadas.contains(musica.getNome())) {

                        musicaHashSet.add(musica);
                    }
                }
                if (!musicaHashSet.isEmpty()) {
                    musicasValidas = true;
                }
            }
            else if (result == JOptionPane.CANCEL_OPTION) {
                return;
            }

        }

        dataLancamento = LocalDate.parse(stringDataLancamento, controller.formatter());
        ArrayList<Musica> musicaArrayList = new ArrayList<>(musicaHashSet);
        if (editar) {
            id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

            controller.editar(id, new Album(id, nome, dataLancamento, musicaArrayList));
            var album = controller.get(id);

            model.setValueAt(album.getNome(), selectedRow, 1);
            model.setValueAt(controller.artistasStrings(album), selectedRow, 2);

            table.repaint();
        }
        else {
            id = controller.getProximoId();
            try {
                controller.adicionar(new Album(id, nome, dataLancamento, musicaArrayList));

            } catch (MusicaJaFazParteDeAlbumException ex) {

                JOptionPane.showMessageDialog(null, ex.toString(), "Erro na criação de álbum",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            var album = controller.get(id);
            model.addRow(
                    new Object[]{
                            album.getId(),
                            album.getNome(),
                            controller.artistasStrings(album),
                            controller.getQtdMusicas(album)});
        }

    }

    /**
     * Este método pesquisa um álbum, alterando a tabela com os resultados encontrados.
     *
     * @param text nome da música sendo procurada
     */
    @Override
    protected void pesquisar(String text) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        var albumList = controller.get(text);
        if (!albumList.isEmpty()) {
            table.setModel(model);
            albumList.forEach(album -> model.addRow(
                    new Object[]{
                            album.getId(),
                            album.getNome(),
                            controller.artistasStrings(album),
                            controller.getQtdMusicas(album)}));
        }
    }

    /**
     * Este método popula a tabela quando o programa abre pela primeira vez.
     *
     * @return o modelo da tabela com os álbums cadastrados
     */
    @Override
    protected DefaultTableModel popularDados() {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        var albumList = this.controller.get();

        if (albumList == null || albumList.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nenhum álbum está cadastrado!",
                    "No data",
                    JOptionPane.INFORMATION_MESSAGE);
            return model;
        }

        Object[] rowData = new Object[3];

        for (Album album : albumList) {

            rowData[0] = album.getId();
            rowData[1] = album.getNome();
            rowData[2] = controller.artistasStrings(album);
            model.addRow(rowData);
        }

        return model;
    }
}
