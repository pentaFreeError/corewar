package graphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import redcode.parser.*;
import java.io.File;

public class GestionPage extends CoreWarJFrame {
    private WelcomePage wp;
    private JScrollPane scrollPane;

    public GestionPage(WelcomePage wp) {
        super("Gestion page");

        this.wp = wp;
        this.scrollPane = null;
        miseAJour();

        JButton back = getBackJButton(this, wp);
        this.add(back, BorderLayout.SOUTH);
    }

    public static void throwAlerte(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void miseAJour() {
        File directory = new File("code/");
        ArrayList<String> allFilesName = new ArrayList<>();

        Dimension size = getSize();
        int width = (int) size.getWidth();
        int height = (int) size.getHeight();

        if (directory.exists() && directory.isDirectory()) {
            // Récupérez la liste des fichiers dans le dossier
            File[] files = directory.listFiles();

            // Parcourez la liste des fichiers et affichez leurs noms
            for (File file : files) {
                if (file.isFile()) {
                    allFilesName.add(file.getName());
                }
            }
            String[] allFilesNameTab = allFilesName.toArray(new String[allFilesName.size()]);
            JPanel gestionPanel = new JPanel(new GridLayout(allFilesNameTab.length, 1));
            gestionPanel.setBackground(blackF);

            for (String fileName : allFilesNameTab) {
                JPanel gestionLine = new JPanel();
                gestionLine.setPreferredSize(new Dimension(width / 2, height / 5));

                gestionLine.setBackground(blackF);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 0.8;

                String code = RedCodeParser.scriptToString("code/" + fileName);
                JLabel fichier = new JLabel();
                styleText(fichier, 20);

                JButton edit = createWarPageButton("MODIFIER", 20);

                JButton delet = createWarPageButton("SUPPRIMER", 20);

                gestionLine.add(fichier, gbc);

                gbc = new GridBagConstraints();
                gbc.gridx = 2;
                gbc.gridy = 0;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 0.1;
                gestionLine.add(edit, gbc);

                gbc = new GridBagConstraints();
                gbc.gridx = 3;
                gbc.gridy = 0;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 0.1;
                gestionLine.add(delet, gbc);
                GestionPage gp = this;
                if (code.equals("Erreur dans le fichier")) {
                    fichier.setText("fichier " + fileName + " introuvable");
                    fichier.setForeground(Color.RED);
                } else {
                    fichier.setText(fileName);

                    edit.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            setVisible(false);
                            EditCodePage ecp = new EditCodePage(wp, gp, fileName, code);
                            ecp.setVisible(true);
                        }
                    });

                    delet.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            File fichierASupprimer = new File("code/" + fileName);
                            boolean suppressionReussie = fichierASupprimer.delete();
                            if (suppressionReussie) {
                                throwAlerte("Le fichier a été supprimé avec succès.");
                                miseAJour();
                                setVisible(false);
                                wp.setVisible(true);
                            } else {
                                throwAlerte("La suppression du fichier a échoué.");
                            }
                        }
                    });

                }
                gestionPanel.add(gestionLine);
            }
            if (this.scrollPane != null) {
                this.remove(this.scrollPane);
            }
            this.scrollPane = new JScrollPane(gestionPanel);

            this.add(scrollPane, BorderLayout.CENTER);

        } else {
            JPanel erreurConainer = new JPanel();
            JLabel erreur = new JLabel("Le dossier code est introuvable !");
            erreur.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
            erreur.setForeground(Color.RED);
            erreur.setBackground(blackF);
            erreurConainer.setBackground(blackF);
            erreurConainer.add(erreur);
            add(erreurConainer, BorderLayout.CENTER);
        }
    }
}
