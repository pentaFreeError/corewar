package redcode.instruction;

import memory.*;
import warior.*;

/*
 * Cette classe représente l'instruciton jump in, cette instruction n'est pas dans la documentation de redcode
 * mais nous l'avons crée nous même pour avoir un autre type de jump.
 * Les jump de redcode version 88 (JMP, JMZ, JMN) sont des sautes avec retour, mais nous voulons un saut intérieur,
 * c'est à dire un saut qui reste dans le bloc d'instruction attribué au warrior actuel.
 * C'est l'opérand A qui donne l'adresse à la cellule ou il faut sauter, puis l'éxectution continue à partir de 
 * cette cellule(l'opérand B ne sert à rien).
 * Cette commande traite tout les modes d'adressage comme un mode d'adressage direct.
 */
public class Jmi extends Instruction {
    /**
     * @param operandAMode Le mode d'opérande A
     * 
     * @param operandA     La valeur de l'opérande A
     * 
     * @param operandBMode Le mode d'opérande B
     * 
     * @param operandB     La valeur de l'opérande B
     */
    public Jmi(String operandAMode, int operandA, String operandBMode, int operandB) {
        super(operandAMode, operandA, operandBMode, operandB);
    }

    /**
     * 
     * @param warior L'objet Warior (guerrier) qui exécute l'instruction
     * @param board  L'objet InstructionBoard (plateau d'instructions) sur lequel
     *               l'instruction est exécutée
     * @see Warior
     * @see InstructionBoard
     * @see Processe
     * @see Cell
     */
    public void execute(Warior warior, InstructionBoard board) {
        Processe processToExecute = warior.getNextProcesses();
        Cell cellToExecute = processToExecute.getCellToExecute();
        int operandA = (cellToExecute.getInstruction()).getOperandA();
        processToExecute.addToExecutionPointer(operandA - 1);
    }

    /**
     * 
     * Cette méthode retourne le code opération de cette instruction, qui est "JMI"
     * pour l'instruction Jmi.
     * 
     * @return le code opération de cette instruction.
     */
    public String getOpCode() {
        return "JMI";
    }
}
