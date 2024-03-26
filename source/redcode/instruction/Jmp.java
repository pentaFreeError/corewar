package redcode.instruction;

import memory.*;
import warior.*;

/*
 * JMP A B : L'adresse de la zone mémoire spécifiée par l'opérande A est placée à la fin de la file 
 * d'attente de processus associée au programme en cours d'exécution. 
 * L'opérande B ne participe pas nécessairement à l'exécution de cette instruction.
 * D'après cette définition donnée sur le site de documentation(sur ecampus) pour le corewar version 88,
 * le jump en redcode est juste le faite d'ajouter l'adresse de la cellule pointé par l'opérand A 
 * à la fin de de la liste des adresse du processus qui l'exécute le jump.
 * attention, si la liste des adresse a dépassé la taille maximal alors jump ne fait rien.
 * 
 */
public class Jmp extends Instruction {
    /**
     * @param operandAMode Le mode d'opérande A
     * 
     * @param operandA     La valeur de l'opérande A
     * 
     * @param operandBMode Le mode d'opérande B
     * 
     * @param operandB     La valeur de l'opérande B
     */
    public Jmp(String operandAMode, int operandA, String operandBMode, int operandB) {
        super(operandAMode, operandA, operandBMode, operandB);
    }

    /**
     * 
     * Méthode d'exécution de l'instruction JMP. Elle prend en entrée un objet
     * Warior (guerrier)
     * et un objet InstructionBoard (plateau d'instructions).
     * Elle récupère le prochain processus à exécuter dans la liste des processus du
     * guerrier,
     * puis récupère la cellule où ce processus doit s'exécuter.
     * Ensuite, elle récupère la cellule qui est pointée par l'opérande A,
     * et ajoute l'adresse de cette cellule à la fin de la file d'attente de
     * processus du guerrier.
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

        Cell cellWhoJmpTo = (board.getCellsPointed(cellToExecute))[0];

        processToExecute.addAddress(cellWhoJmpTo.getPositionAsInt());

    }

    /**
     * 
     * Cette méthode retourne le code opération de cette instruction, qui est "JMP"
     * pour l'instruction Jmp.
     * 
     * @return le code opération de cette instruction.
     */
    public String getOpCode() {
        return "JMP";
    }
}