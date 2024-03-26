package graphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

public class FileControler extends CoreWarJFrame {
    private WelcomePage wp;
    private String lastSelectedValueCode1 = "";
    private String lastSelectedValueCode2 = "";
    private JComboBox<String> optioncode1;
    private JComboBox<String> optioncode2;

    public FileControler(WelcomePage wp) {
        super("Select file page");
        this.wp = wp;

        JPanel erreurPanel = new JPanel();
        JLabel erreur = new JLabel("");
        erreurPanel.add(erreur, BorderLayout.CENTER);
        erreurPanel.setBackground(blackF);
        add(erreurPanel);
        File directory = new File("code/");

        Dimension size = getSize();
        int width = (int) size.getWidth();

        ArrayList<String> allFiles = new ArrayList<>();

        JButton back = getBackJButton(this, this.wp, 50);

        if (directory.exists() && directory.isDirectory()) {
            // Récupérez la liste des fichiers dans le dossier
            File[] files = directory.listFiles();

            // Parcourez la liste des fichiers et affichez leurs noms
            allFiles.add("Generateur");
            for (File file : files) {
                if (file.isFile()) {
                    allFiles.add(file.getName());
                }
            }
            String[] allFiles2 = allFiles.toArray(new String[allFiles.size()]);

            this.optioncode1 = new JComboBox<>(allFiles2);
            this.optioncode2 = new JComboBox<>(allFiles2);

            JPanel choixCode = new JPanel(new GridLayout(3, 1, 0, 10));
            JPanel choixCode1 = new JPanel(new GridLayout(1, 2, 10, 0));

            JPanel choixCode2 = new JPanel(new GridLayout(1, 2, 10, 0));

            JLabel code1 = new JLabel("Choix du premier code : ");
            code1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            code1.setForeground(orangeF);

            JLabel code2 = new JLabel("Choix du deuxiéme code : ");
            code2.setForeground(orangeF);

            code2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));

            choixCode1.add(code1);
            choixCode1.add(optioncode1);

            choixCode2.add(code2);
            choixCode2.add(optioncode2);

            choixCode.add(choixCode1);
            choixCode.add(choixCode2);
            choixCode.setBackground(blackF);
            choixCode2.setBorder(BorderFactory.createEmptyBorder(10, width / 8, 10, width / 8));
            choixCode1.setBorder(BorderFactory.createEmptyBorder(10, width / 8, 10, width / 8));
            optioncode1.setBackground(blackF);
            optioncode1.setForeground(orangeF);
            optioncode1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            optioncode2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));

            optioncode2.setBackground(blackF);
            optioncode2.setForeground(orangeF);
            choixCode2.setBackground(blackF);
            choixCode1.setBackground(blackF);
            choixCode.add(new JLabel());

            add(choixCode, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
            JButton ok = createWarPageButton("OK", 50);

            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    lastSelectedValueCode1 = (String) optioncode1.getSelectedItem();
                    lastSelectedValueCode2 = (String) optioncode2.getSelectedItem();

                    lastSelectedValueCode1 = (lastSelectedValueCode1.equals("Generateur")) ? "noFile"
                            : "code/" + lastSelectedValueCode1;
                    lastSelectedValueCode2 = (lastSelectedValueCode2.equals("Generateur")) ? "noFile"
                            : "code/" + lastSelectedValueCode2;

                    Controler c = new Controler(lastSelectedValueCode1, lastSelectedValueCode2);
                    c.setVisible(true);

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
