package redcode.instruction;

import memory.*;
import warior.*;

/**
 * La classe `NOP` représente une grille de cellules contenant des
 * instructions.
 * Elle contient une grille circulaire de cellules de taille SIZE_X * SIZE_Y.
 */
public class Nop extends Instruction {
    public Nop(String operandAMode, int operandA, String operandBMode, int operandB) {
        super(operandAMode, operandA, operandBMode, operandB);
    }

    public void execute(Warior warior, InstructionBoard board) {
        ;/* Car Nop comme son nom l'indique, elle ne fait rien ! . */
    }

    public String getOpCode() {
        return "NOP";
    }
}
