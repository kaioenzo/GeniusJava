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
 * @param <K> generic que possibilita esse classe ser extendida tanto por ArtistaPanel como por ProdutorPanl
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
        this.controller = controller;
        setVisible(true);
    }

    protected void setupLayout() {
        // layout code here
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        searchPanel.add(label);
        searchPanel.add(textField);
        add(searchPanel);

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

        table.setModel(popularDados());
        table.setBounds(40, 40, 200, 300);
        table.setDragEnabled(false);
        table.setAlignmentX(CENTER_ALIGNMENT);
        table.setDefaultEditor(Object.class, null);

        JScrollPane jScrollPane1 = new JScrollPane(table);
        add(jScrollPane1);

        excluirBtn.addActionListener(e -> excluir());
        visualizarBtn.addActionListener(e -> visualizar());
        editarBtn.addActionListener(e -> alterarDados(true));
        adicionarBtn.addActionListener(e -> alterarDados(false));

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        visualizarBtn.setAlignmentY(CENTER_ALIGNMENT);
        buttonsPanel.add(visualizarBtn);

        adicionarBtn.setAlignmentY(CENTER_ALIGNMENT);
        buttonsPanel.add(adicionarBtn);

        editarBtn.setAlignmentY(CENTER_ALIGNMENT);
        buttonsPanel.add(editarBtn);

        excluirBtn.setAlignmentY(CENTER_ALIGNMENT);
        buttonsPanel.add(excluirBtn);

        add(buttonsPanel);
    }

    abstract protected void visualizar();

    abstract protected void alterarDados(boolean editar);

    protected void excluir() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
            controller.excluir(id);
            model.removeRow(selectedRow);
            popularDados();
        }
        else {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma linha para deletar!",
                    "No row selected",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    abstract protected void pesquisar(String text);

    abstract protected DefaultTableModel popularDados();

    protected void showJOptionPaneMessage(String message, String title, String className) {

        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }
}
