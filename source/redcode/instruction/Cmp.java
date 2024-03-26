package redcode.instruction;

import memory.*;
import warior.*;

/**
 * 
 * Classe qui représente une instruction de comparaison de deux opérandes A et
 * B. Elle hérite de la classe Instruction et prend en compte les
 * modes d'opérandes A et B ainsi que leurs valeurs respectives.
 * 
 * comment ca marche ? :
 * CMP A B : Si l'opérande A est immédiat, il est comparé au champ B de la
 * localisation de la mémoire spécifiée par l'opérande B. Sinon, le contenu de
 * l'emplacement de la mémoire spécifié par l'opérande A est comparé au contenu
 * de l'emplacement de la mémoire spécifié par l'opérande B. Si les valeurs
 * comparées sont égales, l'instruction suivante est sautée (le compteur de
 * programme est incrémenté).
 * 
 */
public class Cmp extends Instruction {
    /**
     * 
     * Constructeur de la classe Cmp
     * 
     * @param operandAMode le mode de l'opérande A
     * @param operandA     la valeur de l'opérande A
     * @param operandBMode le mode de l'opérande B
     * @param operandB     la valeur de l'opérande B
     */
    public Cmp(String operandAMode, int operandA, String operandBMode, int operandB) {
        super(operandAMode, operandA, operandBMode, operandB);
    }

    /**
     * 
     * Méthode exécutant une instruction de comparaison de deux opérandes A et B sur
     * un guerrier et une planche d'instructions.
     * Si l'opérande A est immédiat, il est comparé au champ B de la mémoire
     * spécifiée par l'opérande B.
     * Sinon, le contenu de la mémoire spécifiée par l'opérande A est comparé au
     * contenu de la mémoire spécifiée par l'opérande B.
     * Si les valeurs comparées sont égales, l'instruction suivante est ignorée (le
     * compteur d'instruction est incrémenté).
     * attention deux instructions sont égaux s'il possédent les mêmes valeurs pour
     * les opérand, les mêmes modes d'adressage et le même opCode(voir méthode
     * equals dans la classe Instruction).
     * 
     * @param warior le guerrier exécutant l'instruction
     * @param board  la planche d'instructions sur laquelle l'instruction est
     *               exécutée
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
                if (operandA == operandB_cellPointedByB) {
                    skipNext = true;
                }
                break;
            default:
                Cell[] cellsPointed = board.getCellsPointed(cellToExecute);
                Cell pointedACell = cellsPointed[0];
                Cell pointedBCell = cellsPointed[1];
                Instruction cellAInstruction = pointedACell.getInstruction();
                Instruction cellBInstruction = pointedBCell.getInstruction();
                if (cellAInstruction.equals(cellBInstruction)) {
                    skipNext = true;
                }
        }
        if (skipNext) {
            processToExecute.addToExecutionPointer(1);
        }
    }

    /**
     * 
     * Cette méthode retourne le code opération de cette instruction, qui est "CMP"
     * pour l'instruction Cmp.
     * 
     * @return le code opération de cette instruction.
     */
    public String getOpCode() {
        return "CMP";
    }
}