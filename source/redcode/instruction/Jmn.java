package redcode.instruction;

import memory.*;
import warior.*;

/**
 * 
 * Classe JMN qui hérite de la classe Instruction.
 * 
 * Cette instruction permet de sauter à l'adresse spécifiée par l'opérande A si
 * l'opérande B n'est pas nul mais dans la documentation donnée sur ecampus
 * voici la définition :JMN A B : Si le champ B de l'opérande B n'est pas égal à
 * zéro, alors l'adresse de la cellule mémoire spécifiée par l'opérande A est
 * placée à la fin de la file d'attente des processus associée au programme en
 * cours d'exécution.
 */
public class Jmn extends Instruction {
    /**
     * 
     * Constructeur de la classe Jmn qui prend en paramètre le mode et la valeur des
     * opérandes A et B.
     * 
     * @param operandAMode Le mode de l'opérande A
     * @param operandA     La valeur de l'opérande A
     * @param operandBMode Le mode de l'opérande B
     * @param operandB     La valeur de l'opérande B
     */
    public Jmn(String operandAMode, int operandA, String operandBMode, int operandB) {
        super(operandAMode, operandA, operandBMode, operandB);
    }

    /**
     * 
     * Méthode execute qui effectue l'instruction JMN.
     * 
     * Elle prend en paramètre un objet Warior (qui représente le programme en cours
     * d'exécution) et un objet InstructionBoard (qui représente la mémoire de jeu).
     * 
     * @param warior L'objet Warior qui représente le programme en cours d'exécution
     * 
     * @param board  L'objet InstructionBoard qui représente la mémoire de jeu
     */
    public void execute(Warior warior, InstructionBoard board) {
        Processe processToExecute = warior.getNextProcesses();
        Cell cellToExecute = processToExecute.getCellToExecute();

        boolean isZero = false;

        String operandBMode = (cellToExecute.getInstruction()).getOperandBMode();
        int operandB = (cellToExecute.getInstruction()).getOperandB();
        Cell[] cellsPointed = (board.getCellsPointed(cellToExecute));
        Cell cellPointedByA = cellsPointed[0];
        Cell cellPointedByB = cellsPointed[1];

        switch (operandBMode) {
            case "#":
                if (operandB == 0) {
                    isZero = true;
                }
                break;
            default:
                if ((cellPointedByB.getInstruction()).getOperandB() == 0) {
                    isZero = true;
                }

        }
        if (!isZero) {
            processToExecute.addAddress(cellPointedByA.getPositionAsInt());
        }
    }

    /**
     * 
     * Méthode getOpCode qui retourne le code opération de l'instruction JMN.
     * 
     * @return Le code opération de l'instruction JMN (String)
     */
    public String getOpCode() {
        return "JMN";
    }
}