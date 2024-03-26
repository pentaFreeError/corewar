package redcode.instruction;

import memory.*;
import warior.*;

/**
 * 
 * La classe Sub étend la classe Instruction. Elle permet de soustraire la
 * valeur de l'opérande A de la valeur de l'opérande B.
 * 
 * Si l'opérande A est immédiat, il est soustrait de la valeur B de l'opérande
 * B.
 * 
 * Si l'opérande A n'est pas immédiat, les valeurs des opérandes A et B de
 * l'opérande A sont soustraites respectivement des valeurs des opérandes A et B
 * de l'opérande B.
 * 
 */
public class Sub extends Instruction {
    /**
     * 
     * Constructeur de la classe Sub
     * 
     * @param operandAMode le mode de l'opérande A
     * @param operandA     la valeur de l'opérande A
     * @param operandBMode le mode de l'opérande B
     * @param operandB     la valeur de l'opérande B
     */
    public Sub(String operandAMode, int operandA, String operandBMode, int operandB) {
        super(operandAMode, operandA, operandBMode, operandB);
    }

    /**
     * 
     * La méthode execute exécute l'instruction de la cellule associée au processus
     * en cours d'exécution.
     * Elle effectue cette exécution en utilisant les informations de l'instruction
     * de la cellule et les opérandes associés.
     * Elle met à jour la valeur des opérandes de la cellule en fonction de ces
     * informations.
     * Si l'opérande A est immédiat, alors on soustrait la valeur de l'opérande A à
     * l'opérande B de la case
     * pointée par l'opérande B. Sinon, on soustrait les valeurs de l'opérande A et
     * l'opérande B de la case pointée par l'opérande A
     * respectivement à l'opérande A et l'opérande B de la case pointée par
     * l'opérande B.
     * 
     * @param warior le warrior qui possède le processus en cours d'exécution.
     * @param board  le plateau d'instructions associé au jeu.
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
                (operandBCell.getInstruction()).setOperandB(operandB_operandBCell - operandA);
                break;

            default:
                Cell[] pointedCells = (board.getCellsPointed(cellToExecute));
                Cell pointedACell = pointedCells[0];
                Cell pointedBCell = pointedCells[1];

                int operandA_pointedACell = (pointedACell.getInstruction()).getOperandA();
                int operandB_pointedACell = (pointedACell.getInstruction()).getOperandB();

                int operandA_pointedBCell = (pointedBCell.getInstruction()).getOperandA();
                int operandB_pointedBCell = (pointedBCell.getInstruction()).getOperandB();

                (pointedBCell.getInstruction()).setOperandA(operandA_pointedBCell - operandA_pointedACell);
                (pointedBCell.getInstruction()).setOperandB(operandB_pointedBCell - operandB_pointedACell);
        }
    }

    /**
     * 
     * Cette méthode retourne le code opération de cette instruction, qui est "SUB"
     * pour l'instruction Sub.
     * 
     * @return le code opération de cette instruction.
     */
    public String getOpCode() {
        return "SUB";
    }
}