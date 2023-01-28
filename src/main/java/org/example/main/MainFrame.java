package org.example.main;


import org.example.controller.AlbumController;
import org.example.controller.ArtistasController;
import org.example.controller.MusicaController;
import org.example.controller.ProdutorController;
import org.example.model.*;
import org.example.view.AlbumsPanel;
import org.example.view.ArtistaPanel;
import org.example.view.MusicasPanel;
import org.example.view.ProdutorPanel;
import org.example.view.components.RoundedButtonMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Classe de entrada para o programa, onde a ‘interface’ é carregada e os dados iniciais são cadastrados.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 */
public class MainFrame extends JFrame {

    final JPanel menuPanel = new JPanel();
    final JPanel infoPanel = new JPanel();
    final MusicasPanel musicaPanel = new MusicasPanel();
    final ArtistaPanel artistaPanel = new ArtistaPanel();
    final ProdutorPanel produtoresPanel = new ProdutorPanel();
    final AlbumsPanel albumsPanel = new AlbumsPanel();
    final CardLayout cl = new CardLayout();
    final JButton musicasButton = new RoundedButtonMenu("Músicas");
    final JButton artistasButton = new RoundedButtonMenu("Artistas");
    final JButton albumsButton = new RoundedButtonMenu("Albums");
    final JButton produtoreButton = new RoundedButtonMenu("Produtores");

    public MainFrame(String titulo) {
        super(titulo);
        setLayout();

    }

    public void setLayout() {
        setSize(1200, 600);
        setLayout(new BorderLayout());

        // menu lateral
        JLabel imageLabel;
        try {
            URL url = new URL("https://upload.wikimedia.org/wikipedia/commons/5/51/Genius-logo.png");
            BufferedImage image = ImageIO.read(url);
            ImageIcon icon = new ImageIcon(image);
            Image imageScaled = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(imageScaled));

        } catch (IOException e) {
            System.out.println(e);
            imageLabel = new JLabel("Genius");
        }
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weighty = 0.5;

        constraints.gridx = 0;
        constraints.gridy = 0;
        menuPanel.add(imageLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        musicasButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(musicasButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        artistasButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(artistasButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        produtoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(produtoreButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        albumsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(albumsButton, constraints);

        infoPanel.setLayout(cl);
        infoPanel.setSize(600, 800);
        infoPanel.add(musicaPanel, "1");
        infoPanel.add(artistaPanel, "2");
        infoPanel.add(produtoresPanel, "3");
        infoPanel.add(albumsPanel, "4");
        cl.show(infoPanel, "1");

        add(menuPanel, BorderLayout.WEST);

        add(infoPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        artistasButton.addActionListener(e -> cl.show(infoPanel, "2"));
        musicasButton.addActionListener(e -> cl.show(infoPanel, "1"));
        produtoreButton.addActionListener(e -> cl.show(infoPanel, "3"));
        albumsButton.addActionListener(e -> cl.show(infoPanel, "4"));
    }

    public static void main(String[] args) {
        iniciarGeniusJava();
        MainFrame frame = new MainFrame("Genius Java");
    }

    /**
     * Método que adiciona informações ao sistema.
     */
    public static void iniciarGeniusJava() {
        // Única instancia do objeto PortalDeMusica
        PortalDeMusica bd = PortalDeMusica.getInstance();

        // Controllers
        AlbumController albumController = new AlbumController();
        MusicaController musicaController = new MusicaController();
        ArtistasController artistasController = new ArtistasController();
        ProdutorController produtorController = new ProdutorController();

        // cadastra o genero e recupera pelo nome, e cadastra artista
        bd.cadastrarGenero("Pop", "Bom pra chorar");
        var genero = bd.getGeneroPeloNome("Pop");
        genero.
                ifPresent(generoMusical ->
                        artistasController.adicionar(new Artista(artistasController.getProximoId(), "Taylor Swift",
                                LocalDate.now(),
                                "A loirinha manda muito",
                                "pop")));

        // recupera artista pelo nome
        var artista = artistasController.get("Taylor Swift");

        // cadastra produtor e recupera pelo nome
        produtorController.adicionar(new Produtor(produtorController.getProximoId(), "Shellback", LocalDate.now(), "Karl " +
                "Johan " +
                "Schuster, known " +
                "famously " +
                "as Shellback, " +
                "is a Swedish songwriter, record producer, and musician.", Atribuicao.EXECUTIVO));
        produtorController.adicionar(new Produtor(produtorController.getProximoId(), "metro", LocalDate.now(), "Karl " +
                "Johan " +
                "Schuster, known famously " +
                "as " +
                "Shellback, is a " +
                "Swedish songwriter, record producer, and musician.", Atribuicao.EXECUTIVO));
        var produtor = produtorController.get("Shellback");

        // cadastra música
        ArrayList<Artista> artistas = new ArrayList<>(artista);
        ArrayList<Produtor> produtores = new ArrayList<>(produtor);
        ArrayList<String> generos = new ArrayList<>(Collections.singleton("pop"));

        musicaController.adicionar(new Musica(musicaController.getProximoId(), "Shake it off", new HashSet<>(generos), """

                I stay out too late
                Got nothing in my brain
                That's what people say
                That's what people say
                I go on too many dates
                But I can't make them stay
                At least that's what people say
                That's what people say
                But I keep cruising
                Can't stop, won't stop moving
                It's like I got this music in my mind
                Saying it's gonna be alright
                I never miss a beat
                I'm lightning on my feet
                And that's what they don't see
                That's what they don't see
                Players gonna play, play, play, play, play
                And the haters gonna hate, hate, hate, hate, hate (haters gonna hate)
                Baby, I'm just gonna shake, shake, shake, shake, shake
                I shake it off, I shake it off
                Heartbreakers gonna break
                Fakers gonna fake
                I'm just gonna shake
                I shake it off, I shake it off
                I shake it off, I shake it off
                I, I, I shake it off, I shake it off
                I, I, I shake it off, shake it off
                I, I, I shake it off, I shake it off
                I, I, I shake it off, I shake it off
                I, I, I shake it off, I shake it off
                I, I, I, shake it off, I shake it off
                I, I, I, shake it off, I shake it off""",
                new HashSet<>(artistas),
                new HashSet<>(produtores)));
        musicaController.adicionar(new Musica(musicaController.getProximoId(), "Lover", new HashSet<>(generos), """

                I stay out too late
                Got nothing in my brain
                That's what people say
                That's what people say
                I go on too many dates
                But I can't make them stay
                At least that's what people say
                That's what people say
                But I keep cruising
                Can't stop, won't stop moving
                It's like I got this music in my mind
                Saying it's gonna be alright
                I never miss a beat
                I'm lightning on my feet
                And that's what they don't see
                That's what they don't see
                Players gonna play, play, play, play, play
                And the haters gonna hate, hate, hate, hate, hate (haters gonna hate)
                Baby, I'm just gonna shake, shake, shake, shake, shake
                I shake it off, I shake it off
                Heartbreakers gonna break
                Fakers gonna fake
                I'm just gonna shake
                I shake it off, I shake it off
                I shake it off, I shake it off
                I, I, I shake it off, I shake it off
                I, I, I shake it off, shake it off
                I, I, I shake it off, I shake it off
                I, I, I shake it off, I shake it off
                I, I, I shake it off, I shake it off
                I, I, I, shake it off, I shake it off
                I, I, I, shake it off, I shake it off""",
                new HashSet<>(artistas),
                new HashSet<>(produtores)));

        var musica = musicaController.get("Shake it off");

        var musicas = new ArrayList<Musica>();
        musicas.add(musica.get(0));

        // cadastra album e recupera pelo nome
        albumController.adicionar(new Album(albumController.getProximoId(), "1989", LocalDate.now(), musicas));
    }

}
