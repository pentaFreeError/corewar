package graphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

/**
 * 
 * Cette classe représente une cellule de la grille de la bataille, implémentée
 * comme un bouton.
 */
public class ButtonCell extends JButton implements ActionListener {
    /**
     * 
     * L'instruction stockée dans cette cellule.
     */
    private String instruction;
    private JLabel textInfo;
    private int nCell;

    public ButtonCell(JLabel textInfo, int nCell) {
        super();
        this.textInfo = textInfo;
        this.nCell = nCell;
        setBorder(null);
        setText("O");
        setForeground(Color.WHITE);

    }

    /**
     * 
     * Définit l'instruction stockée dans cette cellule.
     * 
     * @param instruction L'instruction à stocker.
     */
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    /**
     * 
     * Renvoie l'instruction stockée dans cette cellule.
     * 
     * @return L'instruction stockée dans cette cellule.
     */
    public String getInstruction() {
        return this.instruction;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        (textInfo).setText("L'instruction de la case " + nCell + " est " + getInstruction());

    }

}
