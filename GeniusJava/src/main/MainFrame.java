package main;


import view.AlbumsPanel;
import view.ArtistaPanel;
import view.MusicasPanel;
import view.ProdutorPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    final JPanel menuPanel = new JPanel();

    final JPanel infoPanel = new JPanel();
    final MusicasPanel musicaPanel = new MusicasPanel();
    final ArtistaPanel artistaPanel = new ArtistaPanel();
    final ProdutorPanel produtoresPanel = new ProdutorPanel();
    final AlbumsPanel albumsPanel = new AlbumsPanel();

    final CardLayout cl = new CardLayout();
    final JButton musicasButton = new JButton("Músicas");
    final JButton artistasButton = new JButton("Artistas");
    final JButton albumsButton = new JButton("Álbums");
    final JButton produtoreButton = new JButton("Produtores");

    public MainFrame(String titulo) {
        super(titulo);
        setLayout();

    }

    public void setLayout() {
        setSize(1000, 600);
        setLayout(new BorderLayout());


        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        musicasButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        artistasButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        produtoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        albumsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.add(musicasButton);
        menuPanel.add(artistasButton);
        menuPanel.add(produtoreButton);
        menuPanel.add(albumsButton);

        infoPanel.setLayout(cl);
        infoPanel.setSize(600, 800);


        infoPanel.add(musicaPanel, "1");
        infoPanel.add(artistaPanel, "2");
        infoPanel.add(produtoresPanel, "3");
        cl.show(infoPanel, "1");


        add(menuPanel, BorderLayout.WEST);

        add(infoPanel, BorderLayout.CENTER);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        artistasButton.addActionListener(e -> cl.show(infoPanel, "2"));
        musicasButton.addActionListener(e -> cl.show(infoPanel, "1"));
        produtoreButton.addActionListener(e -> cl.show(infoPanel, "3"));
    }

    public static void main(String[] args) {

        Main.iniciarGeniusJava();
        MainFrame frame = new MainFrame("Genius Java");
    }

}
