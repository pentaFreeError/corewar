package graphique;

import java.awt.GridLayout;
import javax.swing.*;
import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import algo.*;
import graphique.mvc.*;

public class Algorithme extends CoreWarJFrame implements EcouteurModele {
    private JLabel temperatureActuel;
    private SimulatedAnnealing a;
    private int maxMutation;
    private String bestCode = "";
    private boolean isStarted = false;
    private WelcomePage wp;
    private JProgressBar progressBar;
    private JButton start;

    public Algorithme(String scriptName, int nMutation, WelcomePage wp) {
        super("Algorithme page");
        this.a = null;
        this.wp = wp;
        this.maxMutation = nMutation;
        if (scriptName.equals("défaut")) {
            a = new SimulatedAnnealing(nMutation);
        } else {
            a = new SimulatedAnnealing(scriptName, true, nMutation);
        }
        a.N_VALIDE_MUTATION = nMutation;
        a.ajoutEcouteur(this);

        JPanel info = new JPanel(new GridLayout(3, 1, 0, 10));
        JLabel initTemp = new JLabel(
                "La temperature de départ de votre solution est : " + String.format("%.1f", a.INIT_TEMPERATURE * 1000));
        styleText(initTemp, blackF, new Font("Comic Sans MS", Font.BOLD, 20), orangeF);
        this.temperatureActuel = new JLabel("La temperature actuel est : " + a.getTemperature() * 1000);

        styleText(temperatureActuel, blackF, new Font("Comic Sans MS", Font.BOLD, 20), orangeF);
        info.add(temperatureActuel);
        info.add(initTemp);
        info.setBackground(blackF);
        add(info, BorderLayout.CENTER);
        this.progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setBackground(orangeF);
        this.start = createWarPageButton("START", 50);
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isStarted) {
                    isStarted = true;
                    remove(start);
                    add(progressBar, BorderLayout.SOUTH);

                    new Thread(() -> {
                        bestCode = a.getBestCode();

                        setVisible(false);
                        AddCodePage adp = new AddCodePage(wp);
                        adp.code.setText(bestCode);
                        adp.setVisible(true);
                    }).start();

                }
            }
        });
        add(start, BorderLayout.SOUTH);
    }

    public void modeleMisAJour(Object source) {
        this.temperatureActuel
                .setText("La temperature actuel est : " + String.format("%.1f", a.getTemperature() * 1000));
        double pourcentage = ((double) (a.getNValideMutation()) / (double) maxMutation) * 100;
        this.progressBar.setValue((int) pourcentage);

    }
}
