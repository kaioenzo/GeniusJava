package view.components;

import javax.swing.*;
import java.awt.*;

/**
 * Classe que padroniza os botões das classes dialog.
 *
 * @see view.BaseDialog
 */
public class RoundedButtonPanel extends JButton {
    private Color backgroundColor = new Color(111, 157, 234);
    private int arcWidth = 15;
    private int arcHeight = 15;

    public RoundedButtonPanel(String label) {
        super(label);
        super.setForeground(new Color(	18, 8, 8));
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setPreferredSize(new Dimension(70, 30));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        super.paintComponent(g);
        g2d.dispose();
    }
}
