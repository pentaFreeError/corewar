package graphique;

import javax.swing.*;
import java.awt.*;

import redcode.battle.*;
import memory.*;
import warior.*;

import graphique.mvc.*;

/*
 * Ceci est la partie "vue" de notre application, elle fait l'affichage la machine MARS lors de la batail entre 
 * les deux warior, cette partie dépend du model(la batail), il y a deux mode de lancement, 
 * mode 1 :
 *      avec deux fichier écrit en redCode, dans le cas d'une erreur dans l'écriture de l'un des deux 
 *      codes une éxecption est lancé par la classe RedCodeParser selon le type d'erreur.
 * mode 2 :
 *      avec le Générateur (deux codes générer).
 * 
 * nous avons utilisé le pattern MVC du module swing, notre but était que le model marche sans tenir compte 
 * de la vue et c'est le cas.
 */
public class WarPage extends JPanel implements EcouteurModele {

    public static int GENERATED_CODE_SIZE = 5;// (InstructionBoard.SIZE_X * InstructionBoard.SIZE_Y) / 8

    private InstructionBoard board;
    protected Battle battle;
    private Warior warior1;
    private Warior warior2;
    private ButtonCell[] grid;
    private JLabel instructionInfo;
    public JPanel basGauche;
    private JLabel nProcessusW1;
    private JLabel nNextProcessusToExecuteW1;

    private JLabel nProcessusW2;
    private JLabel nNextProcessusToExecuteW2;

    private JLabel nCycleInfo;

    public JLabel end;
    public JLabel result;

    /**
     * 
     * Constructeur de la classe WarPage qui prend deux noms de fichier en
     * paramètres.
     * 
     * si le nom des fichiers est noFile, on utilise le mode 2 (générateur)
     * 
     * @param file1Name Le nom du premier fichier.
     * @param file2Name Le nom du deuxième fichier.
     */
    public WarPage(Battle battle) {
        super();
        Color orangeF = new Color(247, 142, 5);
        Color blackF = new Color(47, 48, 46);
        this.battle = battle;
        battle.ajoutEcouteur(this);
        this.board = battle.getInstructionBoard();
        this.warior1 = (this.battle).getWarior1();
        this.warior2 = (this.battle).getWarior2();

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(blackF);

        GridBagConstraints cl = new GridBagConstraints();
        cl.fill = GridBagConstraints.BOTH;
        cl.weightx = 1.0;

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(InstructionBoard.SIZE_Y, InstructionBoard.SIZE_X));
        container.setBackground(blackF);
        this.instructionInfo = new JLabel("");
        instructionInfo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        instructionInfo.setForeground(orangeF);
        this.grid = new ButtonCell[InstructionBoard.SIZE_Y * InstructionBoard.SIZE_X];
        for (int i = 0; i < (InstructionBoard.SIZE_X * InstructionBoard.SIZE_Y); i++) {
            ButtonCell button = new ButtonCell(instructionInfo, i);
            button.addActionListener(button);

            this.grid[i] = button;
            container.add(button);
        }

        cl.gridy = 0;
        cl.weighty = 0.9;
        leftPanel.add(container, cl);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(blackF);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;

        c.gridy = 0;
        c.weighty = 0.05;
        JLabel label1 = new JLabel("COREWAR");
        label1.setBackground(blackF);
        label1.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        label1.setForeground(orangeF);
        label1.setBorder(BorderFactory.createEtchedBorder());

        label1.setHorizontalAlignment(JLabel.CENTER);
        rightPanel.add(label1, c);

        JPanel warior1Information = new JPanel();
        JLabel teamWarior1 = new JLabel("L'équipe : " + (this.warior1).getTeam());
        this.nProcessusW1 = new JLabel("Nombre de processus : " + (this.warior1).getNProcesses());

        this.nNextProcessusToExecuteW1 = new JLabel(
                "Prochin processus à éxecuter : " + ((this.warior1).getProcessePointer() + 1));

        nNextProcessusToExecuteW1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        nNextProcessusToExecuteW1.setForeground(orangeF);

        JPanel zoneContainerW1 = new JPanel(new BorderLayout());
        zoneContainerW1.setLayout(new GridLayout(1, 2));
        JLabel zoneW1 = new JLabel("Zone controlé : ");
        JButton colorW1 = new JButton();
        colorW1.setBackground(Color.BLUE);
        zoneContainerW1.add(zoneW1);
        zoneContainerW1.add(colorW1);

        warior1Information.setLayout(new GridLayout(5, 1));

        JLabel w1 = new JLabel("Warior 1 :");
        w1.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        w1.setForeground(orangeF);

        w1.setHorizontalAlignment(SwingConstants.CENTER);

        warior1Information.add(w1);
        warior1Information.add(teamWarior1);
        warior1Information.add(this.nProcessusW1);
        warior1Information.add(this.nNextProcessusToExecuteW1);
        warior1Information.add(zoneContainerW1);

        JPanel warior2Information = new JPanel();
        JLabel teamWarior2 = new JLabel("L'équipe : " + (this.warior2).getTeam());
        this.nProcessusW2 = new JLabel("Nombre de processus : " + (this.warior2).getNProcesses());
        nProcessusW2.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        nProcessusW2.setForeground(orangeF);

        nProcessusW1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        nProcessusW1.setForeground(orangeF);

        this.nNextProcessusToExecuteW2 = new JLabel(
                "Prochin processus à éxecuter : " + ((this.warior2).getProcessePointer() + 1));

        JPanel zoneContainerW2 = new JPanel(new BorderLayout());
        zoneContainerW2.setLayout(new GridLayout(1, 2));
        JLabel zoneW2 = new JLabel("Zone controlé : ");
        zoneW2.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        zoneW2.setForeground(orangeF);

        zoneW1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        zoneW1.setForeground(orangeF);
        zoneContainerW2.setBackground(blackF);
        zoneContainerW1.setBackground(blackF);

        JButton colorW2 = new JButton();
        colorW2.setBackground(Color.green);
        zoneContainerW2.add(zoneW2);
        zoneContainerW2.add(colorW2);

        warior2Information.setLayout(new GridLayout(5, 1));
        JLabel w2 = new JLabel("Warior 2 :");
        w2.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        w2.setForeground(orangeF);

        w2.setHorizontalAlignment(SwingConstants.CENTER);

        warior2Information.add(w2);
        warior2Information.add(teamWarior2);
        warior2Information.add(this.nProcessusW2);
        warior2Information.add(this.nNextProcessusToExecuteW2);
        warior2Information.add(zoneContainerW2);
        nNextProcessusToExecuteW2.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        nNextProcessusToExecuteW2.setForeground(orangeF);
        c.gridy = 1;
        c.weighty = 0.3;
        warior1Information.setBorder(BorderFactory.createEtchedBorder());

        rightPanel.add(warior1Information, c);

        c.gridy = 2;
        c.weighty = 0.3;
        warior1Information.setBackground(blackF);
        warior2Information.setBackground(blackF);
        warior2Information.setBorder(BorderFactory.createEtchedBorder());

        rightPanel.add(warior2Information, c);

        JLabel forelse = new JLabel("Le reste");
        forelse.setForeground(Color.BLACK);

        JPanel containerCycle2 = new JPanel();
        containerCycle2.setLayout(new GridLayout(2, 1));

        this.nCycleInfo = new JLabel("Nombre de cycle : " + (this.battle).getNCycle());
        nCycleInfo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        nCycleInfo.setForeground(orangeF);
        containerCycle2.add(this.nCycleInfo);
        containerCycle2.add(instructionInfo);

        c.gridy = 3;
        c.weighty = 0.2;
        containerCycle2.setBackground(blackF);
        containerCycle2.setBorder(BorderFactory.createEtchedBorder());

        rightPanel.add(containerCycle2, c);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.9;
        constraints.weighty = 1.0;

        this.end = new JLabel("");
        this.result = new JLabel();

        this.basGauche = new JPanel(new GridLayout(1, 4, 10, 0));
        basGauche.add(this.end);
        basGauche.add(this.result);
        basGauche.setBackground(blackF);

        cl.gridy = 1;
        cl.weighty = 0.1;
        leftPanel.add(basGauche, cl);

        this.add(leftPanel, constraints);

        constraints.weightx = 0.1;
        this.add(rightPanel, constraints);
    }

    public void modeleMisAJour(Object source) {
        this.loadCycle();
        this.loadWarior1Info();
        this.loadWarior2Info();
        this.loadGrid();
    }

    /**
     * 
     * Charge les informations du guerrier 1 dans l'interface graphique.
     */
    private void loadWarior1Info() {
        (this.nProcessusW1).setText("Nombre de processus : " + (this.warior1).getNProcesses());
        (this.nNextProcessusToExecuteW1)
                .setText("Prochin processus à éxecuter : " + ((this.warior1).getProcessePointer() + 1));

    }

    /**
     * 
     * Charge les informations du guerrier 2 dans l'interface graphique.
     */
    private void loadWarior2Info() {
        (this.nProcessusW2).setText("Nombre de processus : " + (this.warior2).getNProcesses());
        (this.nNextProcessusToExecuteW2)
                .setText("Prochin processus à éxecuter : " + ((this.warior2).getProcessePointer() + 1));
    }

    /**
     * 
     * Charge le nombre de cycle dans l'interface graphique.
     */
    private void loadCycle() {
        this.nCycleInfo.setText("Nombre de cycle : " + (this.battle).getNCycle());
    }

    /**
     * 
     * Charge la grille de la bataille dans l'interface graphique.
     */
    private void loadGrid() {
        for (int i = 0; i < (InstructionBoard.SIZE_X * InstructionBoard.SIZE_Y); i++) {
            ButtonCell button = this.grid[i];

            String opCode = (((this.board).getCell(i)).getInstruction()).getOpCode();
            switch (opCode) {
                case "DAT":
                    button.setForeground(Color.RED);
                    button.setText("D");
                    break;
                case "JMP":
                    button.setText("J");

                    break;
                case "JMN":
                    button.setText("J");

                    break;
                case "JMZ":
                    button.setText("J");

                    break;
                case "JMI":
                    button.setText("J");

                    break;
                case "SPL":
                    button.setText("S");

                    break;
                default:
                    button.setForeground(Color.WHITE);
            }

            if (((this.warior1).getAllNextCellPointed()).contains(i)) {
                button.setBackground(Color.blue);
            } else if (((this.warior2).getAllNextCellPointed()).contains(i)) {
                button.setBackground(new Color(107, 142, 65));

            } else if (((this.warior1).getAllCellPointed()).contains(i)) {
                button.setBackground(new Color(0, 255, 255));
            } else if (((this.warior2).getAllCellPointed()).contains(i)) {
                button.setBackground(Color.green);
            } else {
                button.setBackground(Color.black);
            }
            String instruction = ((((this.board).getCell(i)).getInstruction())).toString();

            button.setInstruction(instruction);
        }

    }

}
