package graphique;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileWriter;

public class EditCodePage extends AddCodePage {
    public EditCodePage(WelcomePage wp, GestionPage gp, String fileName, String texte) {
        super(wp);

        code.setText(texte);

        ActionListener[] actionListeners = addButton.getActionListeners();
        for (ActionListener actionListener : actionListeners) {
            addButton.removeActionListener(actionListener);
        }

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isParst && parsetText.equals(code.getText())) {
                    String contenu = code.getText();
                    String nomFichier = fileName;
                    if (nomFichier != null) {
                        try {
                            FileWriter fichier = new FileWriter("code/" + nomFichier);
                            fichier.write(contenu);
                            fichier.close();
                            gp.miseAJour();
                            throwAlerte("Sauvegarde réussie !");
                        } catch (Exception e1) {
                            throwAlerte("Erreur lors de la sauvegarde : " + e1.getMessage());
                        }
                    }
                } else {
                    throwAlerte("Vous ne pouvez pas sauvgarder un fichier non parser !");
                }
            }
        });

        ActionListener[] actionListenersBack = back.getActionListeners();
        for (ActionListener actionListener : actionListenersBack) {
            back.removeActionListener(actionListener);
        }

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isParst = false;
                setVisible(false);
                gp.setVisible(true);
            }
        });

    }
}
