package redcode.instruction;

import memory.*;
import warior.*;

/**
 * 
 * La classe Mov hérite de la classe Instruction et permet de définir une
 * instruction de type "mov" pour un Corewar.
 * 
 * Elle prend en paramètre le mode d'opérande A, la valeur de l'opérande A, le
 * mode d'opérande B et la valeur de l'opérande B.
 */
public class Mov extends Instruction {
    /**
     * @param operandAMode Le mode d'opérande A
     * 
     * @param operandA     La valeur de l'opérande A
     * 
     * @param operandBMode Le mode d'opérande B
     * 
     * @param operandB     La valeur de l'opérande B
     */

    public Mov(String operandAMode, int operandA, String operandBMode, int operandB) {
        super(operandAMode, operandA, operandBMode, operandB);
    }

    /**
     * 
     * La méthode execute exécute l'instruction de la cellule associée au processus
     * en cours d'exécution.
     * Elle effectue cette exécution en utilisant les informations de l'instruction
     * de la cellule et les opérandes associés.
     * Selon le mode de l'opérande A, l'instruction sera exécutée de différentes
     * manières :
     * Si l'opérande A est immédiat, alors il est placé dans le champ B de la
     * mémoire de l'emplacement spécifié par l'opérande B.
     * Sinon, le contenu entier de la mémoire de l'emplacement spécifié par
     * l'opérande A est déplacé vers l'emplacement spécifié par l'opérande B.
     * 
     * @param warior le warrior qui possède le processus en cours d'exécution
     * @param board  le plateau d'instructions associé au jeu
     */
    public void execute(Warior warior, InstructionBoard board) {
        Processe processToExecute = warior.getNextProcesses();
        Cell cellToExecute = processToExecute.getCellToExecute();

        String operandAMode = (cellToExecute.getInstruction()).getOperandAMode();
        int operandA = (cellToExecute.getInstruction()).getOperandA();

        switch (operandAMode) {
            case "#":
                Cell cellPointedByB = board.getCellsPointed(cellToExecute)[1];
                (cellPointedByB.getInstruction()).setOperandB(operandA);
                break;
            default:
                Cell[] pointedCells = (board.getCellsPointed(cellToExecute));
                Cell pointedACell = pointedCells[0];
                Cell pointedBCell = pointedCells[1];

                pointedBCell.setInstruction(pointedACell.getInstruction());

        }
    }

    /**
     * 
     * La méthode getOpCode retourne le code opération de l'instruction MOV.
     * 
     * @return Le code opération de l'instruction MOV sous forme de chaîne de
     *         caractères.
     */
    public String getOpCode() {
        return "MOV";
    }
}