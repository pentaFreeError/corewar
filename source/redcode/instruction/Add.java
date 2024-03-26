package redcode.instruction;

import memory.*;
import warior.*;

/**
 * 
 * Classe qui représente l'instruction ADD.
 * Elle hérite de la classe abstraite Instruction.
 */
public class Add extends Instruction {
    /**
     * Constructeur qui prend en paramètres les modes et les valeurs des opérandes A
     * et B.
     * 
     * @param operandAMode Le mode de l'opérande A
     * @param operandA     La valeur de l'opérande A
     * @param operandBMode Le mode de l'opérande B
     * @param operandB     La valeur de l'opérande B
     */
    public Add(String operandAMode, int operandA, String operandBMode, int operandB) {
        super(operandAMode, operandA, operandBMode, operandB);
    }

    /**
     * 
     * La méthode execute exécute l'instruction de la cellule associée au processus
     * en cours d'exécution.
     * 
     * Elle effectue cette exécution en utilisant les informations de l'instruction
     * de la cellule et les opérandes associés.
     * 
     * Elle met à jour la valeur des opérandes de la cellule en fonction de ces
     * informations.
     * 
     * si l'opérand A est immédiat, alors on l'ajoute à l'opérand B de la case
     * pointé par l'opérand B.
     * sinon, on ajoute l'opérand A et l'opérand B de la case pointé par l'opérand A
     * respectivement à l'opérand A et l'opérand B de la case pointé par l'opérand
     * B.
     * 
     * @param warior le warrior qui possède le processus en cours d'exécution
     * 
     * @param board  le plateau d'instructions associé au jeu
     */
    public void execute(Warior warior, InstructionBoard board) {

        Processe processToExecute = warior.getNextProcesses();
        Cell cellToExecute = processToExecute.getCellToExecute();

        String operandAMode = (cellToExecute.getInstruction()).getOperandAMode();
        int operandA = (cellToExecute.getInstruction()).getOperandA();

        switch (operandAMode) {
            case "#":
                // La case pointé par l'opérand B
                Cell operandBCell = (board.getCellsPointed(cellToExecute))[1];
                // La valeur de l'operand B de la case pointé par l'opérand B
                int operandB_operandBCell = (operandBCell.getInstruction()).getOperandB();
                (operandBCell.getInstruction()).setOperandB(operandB_operandBCell + operandA);
                break;
            default:
                Cell[] pointedCells = (board.getCellsPointed(cellToExecute));
                Cell pointedACell = pointedCells[0];
                Cell pointedBCell = pointedCells[1];

                int operandA_pointedACell = (pointedACell.getInstruction()).getOperandA();
                int operandB_pointedACell = (pointedACell.getInstruction()).getOperandB();

                int operandA_pointedBCell = (pointedBCell.getInstruction()).getOperandA();
                int operandB_pointedBCell = (pointedBCell.getInstruction()).getOperandB();

                (pointedBCell.getInstruction()).setOperandA(operandA_pointedACell + operandA_pointedBCell);
                (pointedBCell.getInstruction()).setOperandB(operandB_pointedACell + operandB_pointedBCell);
        }
    }

    /**
     * 
     * Cette méthode retourne le code opération de cette instruction, qui est "ADD"
     * pour l'instruction Add.
     * 
     * @return le code opération de cette instruction.
     */
    public String getOpCode() {
        return "ADD";
    }
}
