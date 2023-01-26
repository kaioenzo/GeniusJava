package view;

import controller.BaseController;
import view.components.RoundedButtonPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 * Classe base para a criação do JPanel que apresenta as informações de artistas, produtores, álbums e músicas.
 *
 * @param <K> generic que possibilita essa classe ser extendida e adaptada a cada contexto.
 * @author Kaio Enzo Salgado
 * @version 1.1
 * @see ArtistaDialog
 * @see ProdutorDialog
 * @see AlbumsDialog
 * @see MusicaDialog
 */
public abstract class BasePanel<K> extends JPanel {
    protected BaseController<K> controller;
    final JButton adicionarBtn = new RoundedButtonPanel("Adicionar");
    final JButton visualizarBtn = new RoundedButtonPanel("Visualizar");
    final JButton editarBtn = new RoundedButtonPanel("Editar");
    final JButton excluirBtn = new RoundedButtonPanel("Exlcuir");
    JTextField textField = new JTextField(20);
    final JTable table = new JTable();
    final JPanel searchPanel = new JPanel();
    final JLabel label = new JLabel("Pesquisar:");
    final JPanel buttonsPanel = new JPanel();

    protected String[] columnNames = {};

    public BasePanel(BaseController<K> controller) {
        this.controller = controller; setVisible(true);
    }

    /**
     * Este método cria o layout básico de toda as panel que herdam de
     */
    protected void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        searchPanel.add(label); searchPanel.add(textField); add(searchPanel);

        textField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                pesquisar(textField.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                table.setModel(popularDados());
            }

            public void insertUpdate(DocumentEvent e) {
                pesquisar(textField.getText());
            }
        });

        table.setModel(popularDados()); table.setBounds(40, 40, 200, 300); table.setDragEnabled(false);
        table.setAlignmentX(CENTER_ALIGNMENT); table.setDefaultEditor(Object.class, null);

        JScrollPane jScrollPane1 = new JScrollPane(table); add(jScrollPane1);

        excluirBtn.addActionListener(e -> excluir()); visualizarBtn.addActionListener(e -> visualizar());
        editarBtn.addActionListener(e -> alterarDados(true)); adicionarBtn.addActionListener(e -> alterarDados(false));

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        visualizarBtn.setAlignmentY(CENTER_ALIGNMENT); buttonsPanel.add(visualizarBtn);

        adicionarBtn.setAlignmentY(CENTER_ALIGNMENT); buttonsPanel.add(adicionarBtn);

        editarBtn.setAlignmentY(CENTER_ALIGNMENT); buttonsPanel.add(editarBtn);

        excluirBtn.setAlignmentY(CENTER_ALIGNMENT); buttonsPanel.add(excluirBtn);

        add(buttonsPanel);
    }

    /**
     * Este método abstrato deve ser implementado para a visualização dos dados das classes bases do projeto. Neste
     * método deve estar contido a lógica de exibição das classes dialog
     */
    abstract protected void visualizar();

    /**
     * Este método abstrato deve ser implementado para a edição dos dados das classes bases do projeto. Neste
     * método deve estar contido a lógica de edição dos dados das classes base.
     */
    abstract protected void alterarDados(boolean editar);

    /**
     * Este método exclue um produtor, artista, música ou álbum se na tabela {@link #table} há alguma linha
     * selecionada, se não há, retorna uma mensagem informando o erro. Em caso de sucesso da deleção dos dados
     * atualiza a tabela.
     */
    protected void excluir() {
        DefaultTableModel model = (DefaultTableModel) table.getModel(); int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString()); controller.excluir(id);
            model.removeRow(selectedRow); popularDados();
        }
        else {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para deletar!", "No row selected",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Este método abstrato deve implementar a lógica para a pesquisa textual das classes base.
     *
     * @param text da pesquisa
     */
    abstract protected void pesquisar(String text);

    /**
     * Este método abstrato deve implementar a lógica para preencher a tabela {@link #table}
     *
     * @return modelo padrão da tabela
     */
    abstract protected DefaultTableModel popularDados();

    /**
     * Este método abstrato abre uma mensagem de erro. Geralmente, é utilizada para apresentar erros no CRUD das
     * classes bases do projeto.
     *
     * @param message   mensagem de erro
     * @param title     título da janela de erro
     * @param className classe referente ao erro
     */
    protected void showJOptionPaneMessage(String message, String title, String className) {

        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
