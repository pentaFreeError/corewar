package redcode.instruction;

import memory.*;
import warior.*;

/**
 * 
 * Classe qui représente l'instruction DJN.
 * Elle hérite de la classe abstraite Instruction.
 */
public class Djn extends Instruction {
    /**
     * Constructeur de la classe Djn. Il prend en entrée deux paramètres de
     * type String pour les modes des opérandes A et B et deux paramètres
     * de type int pour les valeurs des opérandes A et B.
     *
     * @param operandAMode Le mode de l'opérande A
     * @param operandA     La valeur de l'opérande A
     * @param operandBMode Le mode de l'opérande B
     * @param operandB     La valeur de l'opérande B
     */
    public Djn(String operandAMode, int operandA, String operandBMode, int operandB) {
        super(operandAMode, operandA, operandBMode, operandB);
    }

    /**
     * La méthode execute() de la classe Djn effectue les opérations suivantes :
     * - Elle récupère le prochain processus à exécuter et la cellule associée.
     * - Elle récupère le mode et la valeur de l'opérande B de l'instruction de la
     * cellule.
     * - Selon le mode de l'opérande B, elle décrémente la valeur de l'opérande B
     * - soit directement si c'est un mode immédiat,
     * - soit en récupérant la valeur de l'opérande B de la cellule pointée par B.
     * - Si la valeur obtenue est différente de zéro, elle ajoute l'adresse de la
     * cellule pointée par A à la fin de la file d'attente des processus.
     *
     * @param warior le guerrier qui execute cette instruction
     * @param board  le plateau de jeu
     */
    public void execute(Warior warior, InstructionBoard board) {

        Processe processToExecute = warior.getNextProcesses();
        Cell cellToExecute = processToExecute.getCellToExecute();

        String operandBMode = (cellToExecute.getInstruction()).getOperandBMode();
        int operandB = (cellToExecute.getInstruction()).getOperandB();
        boolean isZero = false;
        Cell[] pointedCells = (board.getCellsPointed(cellToExecute));
        Cell pointedACell = pointedCells[0];
        Cell pointedBCell = pointedCells[1];

        switch (operandBMode) {
            case "#":
                (cellToExecute.getInstruction()).setOperandB(operandB - 1);
                if ((operandB - 1) == 0) {
                    isZero = true;
                }
                break;
            default:

                int operandB_pointedBCell = (pointedBCell.getInstruction()).getOperandB();

                (pointedBCell.getInstruction()).setOperandB(operandB_pointedBCell - 1);

                if ((operandB_pointedBCell - 1) == 0) {
                    isZero = true;
                }
        }
        if (!isZero) {
            processToExecute.addAddress(pointedACell.getPositionAsInt());
        }
    }

    /**
     * 
     * Cette méthode retourne le code opération de cette instruction, qui est "DJN"
     * pour l'instruction Djn.
     * 
     * @return le code opération de cette instruction.
     */
    public String getOpCode() {
        return "DJN";
    }
}