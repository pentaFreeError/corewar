package graphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

public class SelectFileFrame extends CoreWarJFrame {
    private JComboBox<String> optionNMutation;
    private JComboBox<String> optioncode;
    private WelcomePage wp;
    private String lastSelectedValueCode1 = "";
    private String lastSelectedValueCode2 = "";

    public SelectFileFrame(WelcomePage wp) {
        super("choice file page");
        this.wp = wp;
        JPanel erreurPanel = new JPanel();
        JLabel erreur = new JLabel("");
        erreurPanel.add(erreur, BorderLayout.CENTER);
        erreurPanel.setBackground(blackF);
        add(erreurPanel);
        File directory = new File("code/");

        ArrayList<String> allFiles = new ArrayList<>();
        Dimension size = getSize();
        int width = (int) size.getWidth();

        JButton back = getBackJButton(this, this.wp, 50);

        if (directory.exists() && directory.isDirectory()) {
            // Récupérez la liste des fichiers dans le dossier
            File[] files = directory.listFiles();

            // Parcourez la liste des fichiers et affichez leurs noms
            allFiles.add("défaut");
            for (File file : files) {
                if (file.isFile()) {
                    allFiles.add(file.getName());
                }
            }
            String[] allFiles2 = allFiles.toArray(new String[allFiles.size()]);
            String[] allNMutation = new String[] { "100", "10", "15", "20", "30", "50", "70", "150", "200",
                    "300",
                    "400", "500" };

            this.optioncode = new JComboBox<>(allFiles2);

            this.optionNMutation = new JComboBox<>(allNMutation);
            JPanel choixCode = new JPanel(new GridLayout(3, 1, 0, 10));
            JPanel choixCode1 = new JPanel(new GridLayout(1, 2, 10, 0));
            JPanel choixCode2 = new JPanel(new GridLayout(1, 2, 10, 0));

            JLabel code1 = new JLabel("Choix du code : ");
            code1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            code1.setForeground(orangeF);

            JLabel code2 = new JLabel("Combien de mutation valide ? ");
            code2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            code2.setForeground(orangeF);

            choixCode1.add(code1);
            choixCode1.add(optioncode);
            choixCode2.add(code2);
            choixCode2.add(optionNMutation);

            choixCode.add(choixCode1);
            choixCode.add(choixCode2);

            choixCode.setBackground(blackF);
            choixCode1.setBorder(BorderFactory.createEmptyBorder(10, width / 8, 10, width / 8));
            choixCode2.setBorder(BorderFactory.createEmptyBorder(10, width / 8, 10, width / 8));

            optioncode.setBackground(blackF);
            optioncode.setForeground(orangeF);
            optioncode.setFont(new Font("Comic Sans MS", Font.BOLD, 20));

            optionNMutation.setBackground(blackF);
            optionNMutation.setForeground(orangeF);
            optionNMutation.setFont(new Font("Comic Sans MS", Font.BOLD, 20));

            choixCode1.setBackground(blackF);
            choixCode2.setBackground(blackF);

            choixCode.add(new JLabel());

            add(choixCode, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
            JButton ok = createWarPageButton("OK", 50);

            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    lastSelectedValueCode1 = (String) optioncode.getSelectedItem();
                    lastSelectedValueCode2 = (String) optionNMutation.getSelectedItem();
                    Algorithme a = new Algorithme(lastSelectedValueCode1, Integer.parseInt(lastSelectedValueCode2), wp);
                    a.setVisible(true);
                }
            });
            buttonPanel.add(back);
            buttonPanel.add(ok);
            add(buttonPanel, BorderLayout.SOUTH);

        } else {
            erreur.setText("Le dossier code n'est pas trouvable !!!");
            erreur.setAlignmentX(CENTER_ALIGNMENT);
            erreur.setAlignmentY(CENTER_ALIGNMENT);
            erreur.setForeground(Color.RED);
            erreur.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
            add(back, BorderLayout.SOUTH);
        }

    }
}
