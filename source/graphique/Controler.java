package graphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import redcode.battle.*;
import generateur.Generateur;

public class Controler extends CoreWarJFrame {
    private Battle battle;
    private WarPage wp;
    private int winner;
    private boolean isStarted;

    public Controler(String file1Name, String file2Name) {
        super("WAR");

        this.isStarted = false;
        if (file1Name == "noFile" && file2Name == "noFile") {
            // cas générateur
            this.battle = new Battle(Generateur.generateValideCode(),
                    Generateur.generateValideCode(), CodeMode.ONLY_STRING);
        } else if (file2Name == "noFile") {
            // cas 1 script
            this.battle = new Battle(file1Name, Generateur.generateValideCode(), CodeMode.ALL);
        } else if (file1Name == "noFile") {
            this.battle = new Battle(file2Name, Generateur.generateValideCode(), CodeMode.ALL);

        } else {
            // cas 2 script
            this.battle = new Battle(file1Name, file2Name, CodeMode.ONLY_FILE);
        }

        this.wp = new WarPage(this.battle);

        this.add(wp, BorderLayout.CENTER);

        JButton startb = createWarPageButton("START", 14);
        new JButton("start");
        startb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isStarted) {
                    isStarted = true;
                    new Thread(() -> {
                        try {
                            // Mise à jour du texte du JLabel dans le thread de l'interface graphique
                            winner = (battle).startBattle();

                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        (wp.end).setText("La partie est finit !");
                        (wp.end).setForeground(Color.RED);
                        (wp.end).setFont((wp.end).getFont().deriveFont(Font.BOLD, 18f));
                        (wp.end).setHorizontalAlignment(JLabel.CENTER);

                        (wp.result).setForeground(Color.RED);
                        (wp.result).setFont((wp.result).getFont().deriveFont(Font.BOLD, 18f));
                        (wp.result).setHorizontalAlignment(JLabel.CENTER);

                        if (winner == -1) {
                            // cas égalité
                            (wp.result).setText("Resultat : Egalité entre les deux !");
                        } else {
                            (wp.result).setText("Resultat : le warior " + winner + " a gagné !");
                        }
                    }).start();
                }

            }
        });

        this.wp.basGauche.add(startb);

    }

    public Controler(String fil1Name) {
        this(fil1Name, "noFile");
    }

    public Controler() {
        this("noFile", "noFile");
    }

}
