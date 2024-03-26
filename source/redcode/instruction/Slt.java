package redcode.instruction;

import memory.*;
import warior.*;

/*
Classe Slt qui étend la classe Instruction.
Cette classe représente une instruction de comparaison qui permet de vérifier 
si la valeur de l'opérande A est inférieure à celle de l'opérande B.Si c'est le cas, 
l'instruction suivante sera sautée (le compteur d'instructions sera incrémenté).

comment ca marche ?

SLT A B : Si l'opérande A n'est pas immédiat, le champ B de l'emplacement de mémoire spécifié par 
l'opérande A est comparé au champ B de l'opérande B. Sinon, l'opérande A lui-même est utilisé dans la comparaison.
Si la valeur A est inférieure à la valeur B, l'instruction suivante est sautée 
(le compteur de programme est incrémenté).
*/
public class Slt extends Instruction {
    /**
     * 
     * Constructeur de la classe Slt
     * 
     * @param operandAMode le mode de l'opérande A
     * @param operandA     la valeur de l'opérande A
     * @param operandBMode le mode de l'opérande B
     * @param operandB     la valeur de l'opérande B
     */

    public Slt(String operandAMode, int operandA, String operandBMode, int operandB) {
        super(operandAMode, operandA, operandBMode, operandB);
    }

    /**
     * 
     * La méthode execute() prend en entrée un objet guerrier (Warrior) et un objet
     * InstructionBoard.
     * Elle exécute l'instruction SLT (set if less than) en comparant la valeur de
     * l'opérande A avec celle de l'opérande B.
     * Si la valeur de l'opérande A est inférieure à celle de l'opérande B, alors
     * l'instruction suivante sera ignorée (le compteur d'instruction sera
     * incrémenté).
     * 
     * @param warior L'objet Warior (guerrier) qui exécute l'instruction
     * 
     * @param board  L'objet InstructionBoard (plateau d'instructions) sur lequel
     *               l'instruction est exécutée
     */
    public void execute(Warior warior, InstructionBoard board) {
        Processe processToExecute = warior.getNextProcesses();
        Cell cellToExecute = processToExecute.getCellToExecute();

        String operandAMode = (cellToExecute.getInstruction()).getOperandAMode();
        int operandA = (cellToExecute.getInstruction()).getOperandA();

        boolean skipNext = false;

        switch (operandAMode) {
            case "#":
                Cell cellPointedByB = board.getCellsPointed(cellToExecute)[1];
                int operandB_cellPointedByB = (cellPointedByB.getInstruction()).getOperandB();
                if (operandA < operandB_cellPointedByB) {
                    skipNext = true;
                }
                break;
            default:
                Cell[] cellsPointed = board.getCellsPointed(cellToExecute);
                Cell pointedACell = cellsPointed[0];
                Cell pointedBCell = cellsPointed[1];
                Instruction cellAInstruction = pointedACell.getInstruction();
                Instruction cellBInstruction = pointedBCell.getInstruction();
                if (cellAInstruction.getOperandB() < cellBInstruction.getOperandB()) {
                    skipNext = true;
                }
        }
        if (skipNext) {
            processToExecute.addToExecutionPointer(1);
        }

    }

    /**
     * 
     * Cette méthode retourne le code opération de cette instruction, qui est "SLT"
     * pour l'instruction Slt.
     * 
     * @return le code opération de cette instruction.
     */
    public String getOpCode() {
        return "SLT";
    }
}