package view;

import controller.ArtistasController;
import controller.MusicaController;
import controller.ProdutorController;
import model.Artista;
import model.Musica;
import model.Produtor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MusicasPanel extends BasePanel<Musica> {
    private final MusicaController controller = new MusicaController();
    private final ArtistasController artistasController = new ArtistasController();
    private final ProdutorController produtorController = new ProdutorController();
    private final String[] columnNames = {"Id", "Nome", "Artistas", "Produtores"};
    private boolean dialogIsOpen = false;
    private MusicaDialog musicaDialog;

    public MusicasPanel() {
        super(new MusicaController());
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

            var musica = controller.get(id);
            if (!dialogIsOpen) {
                musicaDialog = new MusicaDialog(controller, id, controller.artistasStrings(musica), controller.produtoresString(musica));
                dialogIsOpen = true;
            }
            else {
                musicaDialog.setVisible(false);
                musicaDialog = new MusicaDialog(controller, id, controller.artistasStrings(musica), controller.produtoresString(musica));
                dialogIsOpen = true;
                musicaDialog.setVisible(true);
                musicaDialog.toFront();
            }

        }
        else {
            showJOptionPaneMessage("Selecione uma música para visualizar", "Erro ao visualizar música", "musica");
        }
    }

    /**
     * @param editar booleano que indica a ação a ser executada, edição ou deleção
     *               Este método abre uma série de telas para o cadastro/atualização de uma música
     */
    @Override
    protected void alterarDados(boolean editar) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Integer selectedRow = table.getSelectedRow();
        Integer id = null;
        LocalDate date;
        String nome = null;
        String letra = null;
        String genero = null;
        HashSet<String> hashSetGenero = new HashSet<>();
        HashSet<Artista> hashSetArtistas = new HashSet<>();
        HashSet<Produtor> hashSetProdutores = new HashSet<>();

        if (editar && selectedRow != -1) {
            id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
            nome = model.getValueAt(selectedRow, 1).toString();
            letra = controller.get(Integer.parseInt(model.getValueAt(selectedRow, 0).toString())).getLetra();
            hashSetGenero.add(controller.get(id).getProdutores().toString());

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
                "Insira o nome do musica:",
                "Criação de musica",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                nome);
        if (nome == null) {
            return;
        }
        if (nome.isBlank()) {
            showJOptionPaneMessage("Nome não pode ser vazio!", "Erro na criação de musica", "musica");
            return;
        }

        // genero da musica
        if (id != null) {
            genero = (String) JOptionPane.showInputDialog(
                    this,
                    "Insira o genero do musica:",
                    "Criação de musica",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    Arrays.toString(controller.get(id).
                                    getGeneros().
                                    toArray()).
                            replace("[", "").
                            replace("]", "")
            );
        }
        else {
            genero = (String) JOptionPane.showInputDialog(
                    this,
                    "Insira o genero do musica:",
                    "Criação de musica",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null
            );

        }
        if (genero == null) {
            return;
        }
        if (genero.isBlank()) {
            showJOptionPaneMessage("Genero não pode ser vazio!", "Erro na criação de musica", "musica");
            return;
        }
        else {
            hashSetGenero.add(genero);
        }

        // letra da musica
        JTextArea textArea = new JTextArea(5, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(letra);
        JScrollPane scrollPane = new JScrollPane(textArea);

        Object letraResult = JOptionPane.showConfirmDialog(
                this,
                scrollPane,
                "Escreva a letra da música:",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.CANCEL_OPTION);

        if (textArea.getText() == null || textArea.getText().isBlank()) {
            showJOptionPaneMessage("A letra da musica nâo pode ser vazia", "Erro na criação de música", "música");
            return;
        }
        else {
            letra = textArea.getText();
        }

        // seleciona os artistas da música
        boolean artistaValido = false;
        while (!artistaValido) {
            List<Artista> artistas = artistasController.get();
            String[] arrayNomesArtistas = artistas.stream().map(Artista::getNome).toArray(String[]::new);

            JList<String> jListArtistas = new JList<>(arrayNomesArtistas);
            jListArtistas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            JScrollPane scrollPaneArtistaList = new JScrollPane(jListArtistas);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    scrollPaneArtistaList,
                    "Selecione os artistas:",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                List<String> artistasSelecionados = jListArtistas.getSelectedValuesList();
                for (Artista artista : artistas) {
                    if (artistasSelecionados.contains(artista.getNome())) {
                        hashSetArtistas.add(artista);
                    }
                }
                if (!hashSetArtistas.isEmpty()) {
                    artistaValido = true;
                }
            }
            else if (result == JOptionPane.CANCEL_OPTION) {
                return;
            }

        }

        // seleciona os produtores da música
        boolean produtorValido = false;
        while (!produtorValido) {
            var produtores = produtorController.get();
            String[] arrayNomesProdutores = produtores.stream().map(Produtor::getNome).toArray(String[]::new);

            JList<String> listProdutores = new JList<>(arrayNomesProdutores);
            listProdutores.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JScrollPane scrollPaneListProdutores = new JScrollPane(listProdutores);

            int produtoresResult = JOptionPane.showConfirmDialog(
                    this,
                    scrollPaneListProdutores,
                    "Selecione os produtores da música " + nome,
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            listProdutores.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            if (produtoresResult == JOptionPane.OK_OPTION) {
                List<String> produtoresSelecionados = listProdutores.getSelectedValuesList();
                for (Produtor produtor : produtores) {
                    if (produtoresSelecionados.contains(produtor.getNome())) {
                        hashSetProdutores.add(produtor);
                    }
                }
                if (!hashSetProdutores.isEmpty()) {
                    produtorValido = true;
                }
            }
        }

        if (editar) {
            id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

            controller.editar(id, new Musica(id, nome, hashSetGenero, letra, hashSetArtistas, hashSetProdutores));
            var musica = controller.get(id);

            model.setValueAt(musica.getNome(), selectedRow, 1);
            model.setValueAt(controller.artistasStrings(musica), selectedRow, 2);
            model.setValueAt(controller.produtoresString(musica), selectedRow, 3);
            table.repaint();
        }
        else {
            id = controller.getProximoId();
            controller.adicionar(new Musica(id, nome, hashSetGenero, letra, hashSetArtistas, hashSetProdutores));
            var musica = controller.get(id);

            model.addRow(
                    new Object[]{
                            musica.getId(),
                            musica.getNome(),
                            controller.artistasStrings(musica),
                            controller.produtoresString(musica)});
        }
    }

    /**
     * @param text nome da música procurada
     *             Este método altera  a tabela com os resultados encontrados
     */
    @Override
    protected void pesquisar(String text) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        var musicaList = controller.get(text);
        if (!musicaList.isEmpty()) {
            table.setModel(model);
            for (Musica musica : musicaList) {
                model.addRow(
                        new Object[]{
                                musica.getId(),
                                musica.getNome(),
                                controller.artistasStrings(musica),
                                controller.produtoresString(musica)
                        }
                );
            }

        }
    }

    /**
     * @return modelo da tabela com os dados das músicas
     */
    @Override
    protected DefaultTableModel popularDados() {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        var musicaList = this.controller.get();

        if (musicaList == null || musicaList.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nenhuma música está cadastrado!",
                    "No data",
                    JOptionPane.INFORMATION_MESSAGE);
            return model;
        }

        Object[] rowData = new Object[4];

        for (Musica musica : musicaList) {

            rowData[0] = musica.getId();
            rowData[1] = musica.getNome();
            rowData[2] = controller.artistasStrings(musica);
            rowData[3] = controller.produtoresString(musica);
            model.addRow(rowData);
        }

        return model;
    }

}
