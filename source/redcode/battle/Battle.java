package redcode.battle;

import memory.InstructionBoard;
import warior.*;
import java.util.*;
import graphique.mvc.*;

/**
 * 
 * La classe Battle gère le déroulement d'un combat entre deux guerriers.
 * 
 * @author Auteur
 */
public class Battle extends AbstractModeleEcoutable {
    public static int MAX_CYCLE = (InstructionBoard.SIZE_X * InstructionBoard.SIZE_Y) * 10;
    private InstructionBoard board;
    private ArrayList<Warior> wariorArray;
    private int nCycle = 0;

    /**
     * Constructeur de Battle.
     * 
     * @param codeWarior1 Le code du premier guerrier.
     * @param codeWarior2 Le code du second guerrier.
     * @param isFile      Indique si le code des guerriers se trouve dans des
     *                    fichiers ou s'ils sont passés en arguments.
     */
    public Battle(String codeWarior1, String codeWarior2, CodeMode mode) {
        super();
        this.board = new InstructionBoard();
        ArrayList<Warior> wariorArray = null;
        if (mode == CodeMode.ONLY_FILE) {
            wariorArray = Loader.loadScript(codeWarior1, codeWarior2, this.board);
        } else if (mode == CodeMode.ONLY_STRING) {
            wariorArray = Loader.loadCode(codeWarior1, codeWarior2, this.board);
        } else {
            // mode ALL
            wariorArray = Loader.loadScriptAndCode(codeWarior1, codeWarior2, this.board);
        }
        this.wariorArray = wariorArray;
    }

    /**
     * Lance le combat entre les deux guerriers.
     * 
     * @param warPage L'objet WarPage associé au combat, pouvant être utilisé pour
     *                mettre à jour l'interface utilisateur durant le combat.
     * @return L'équipe du gagnant du combat ou -1 en cas d'égalité.
     */
    public int startBattle() {
        Warior warior1 = (this.wariorArray).get(0);
        Warior warior2 = (this.wariorArray).get(1);
        this.nCycle = 0;
        while (warior1.isAlive() && warior2.isAlive() && this.nCycle < MAX_CYCLE) {
            this.fireChangement();

            if (this.nCycle % 2 == 0) {
                warior1.execute();
            } else {
                warior2.execute();
            }
            this.nCycle++;

        }
        this.fireChangement();

        if (this.nCycle == MAX_CYCLE) {
            return -1; // cas égalité
        }
        return (warior1.isAlive() ? warior1.getTeam() : warior2.getTeam());
    }

    /**
     * 
     * Retourne l'objet InstructionBoard associé à la classe.
     * 
     * @return L'objet InstructionBoard associé à la classe.
     */
    public InstructionBoard getInstructionBoard() {
        return this.board;
    }

    /**
     * 
     * Retourne le premier guerrier associé à la classe.
     * 
     * @return Le premier guerrier associé à la classe.
     */
    public Warior getWarior1() {
        return (this.wariorArray).get(0);
    }

    /**
     * 
     * Retourne le deuxième guerrier associé à la classe.
     * 
     * @return Le deuxième guerrier associé à la classe.
     */
    public Warior getWarior2() {
        return (this.wariorArray).get(1);
    }

    /**
     * 
     * Retourne le nombre de cycles effectués durant le combat.
     * 
     * @return Le nombre de cycles effectués durant le combat.
     */
    public int getNCycle() {
        return this.nCycle;
    }
}
