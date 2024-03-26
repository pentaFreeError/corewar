package graphique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import redcode.parser.*;
import java.io.FileWriter;
import redcode.instruction.*;

public class AddCodePage extends CoreWarJFrame {
    protected WelcomePage wp;
    protected boolean isParst;
    protected String parsetText;
    protected JButton back;
    protected JButton addButton;
    public JTextArea code;

    public AddCodePage(WelcomePage wp) {
        super("Add code Page");
        this.wp = wp;

        this.isParst = false;

        this.code = new JTextArea();
        styleText(code);
        code.setCaretColor(orangeF);

        JScrollPane scrollPane = new JScrollPane(code);

        JPanel buttonContainer = new JPanel(new GridLayout(1, 4, 10, 0));

        this.back = createWarPageButton("BACK");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isParst = false;
                setVisible(false);
                wp.setVisible(true);
            }
        });

        JButton parser = createWarPageButton("PARSER");
        parser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String script = code.getText();
                isParst = true;
                treatEditorCode(script);
                if (isParst) {
                    parsetText = code.getText();
                }
            }
        });

        this.addButton = createWarPageButton("SAUVGARDER");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isParst && parsetText.equals(code.getText())) {
                    String contenu = code.getText();
                    String nomFichier = JOptionPane.showInputDialog("Entrez le nom du fichier : ");
                    if (nomFichier != null) {
                        try {
                            FileWriter fichier = new FileWriter("code/" + nomFichier + ".txt");
                            fichier.write(contenu);
                            fichier.close();
                            wp.setPagControle(new FileControler(wp));
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

        JButton effacer = createWarPageButton("EFFACER");
        effacer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isParst = false;
                code.setText("");
            }
        });
        buttonContainer.setBackground(blackF);
        buttonContainer.add(back);
        buttonContainer.add(effacer);
        buttonContainer.add(parser);
        buttonContainer.add(addButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonContainer, BorderLayout.SOUTH);

    }

    public static void throwAlerte(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public Instruction treatEditorLine(String line, int nLine) {
        line = RedCodeParser.removeSpace(line);
        line = RedCodeParser.removeComment(line);

        String[] commande = null;
        // Le cas ou la ligne traité est juste un commentaire
        if (line.isEmpty() || line.length() < 2) {
            return null;
        }
        Instruction instruction = null;

        try {
            commande = RedCodeParser.getCommande(line);
            instruction = RedCodeParser.getInstructionFromArray(commande);

        } catch (Exception e) {
            isParst = false;
            throwAlerte(e.getMessage() + "\nNuméro de ligne : " + nLine);
        }

        return instruction;
    }

    public ArrayList<Instruction> treatEditorCode(String code) {
        ArrayList<Instruction> instructionListe = new ArrayList<Instruction>();
        String[] codeArray = RedCodeParser.splitLinesIntoArray(code);
        int i = 0;
        for (String line : codeArray) {
            i++;
            Instruction instruction = treatEditorLine(line, i);
            if (instruction != null) {
                instructionListe.add(instruction);
            }
        }
        return instructionListe;
    }
}
