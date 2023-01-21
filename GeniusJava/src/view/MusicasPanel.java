package view;

import model.GeneroMusical;
import model.Musica;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class MusicasPanel extends JPanel {
    JList<Musica> musicaJList;
    HashSet<GeneroMusical> generoMusicalsSet = new HashSet<GeneroMusical>();

    Musica musica;

    public MusicasPanel(){
        generoMusicalsSet.add(new GeneroMusical(1, "pop","ok"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.CYAN);
        setVisible(true);
    }
}
