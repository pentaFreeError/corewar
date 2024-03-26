package redcode.instruction;

import memory.*;
import warior.*;

/**
 * 
 * La classe Dat représente une instruction de type DAT dans le langage Redcode.
 * Elle hérite de la classe Instruction et implémente les méthodes execute et
 * getOpCode.
 * Lorsque l'instruction DAT est exécutée par un guerrier (Warior), elle
 * entraîne la suppression
 * du processus suivant dans la liste des processus du guerrier.r
 */
public class Dat extends Instruction {
    /**
     * 
     * Constructeur de la classe Dat. Il prend en entrée les modes et valeurs des
     * opérandes A et B.
     * Ces informations sont ensuite passées au constructeur parent (Instruction)
     * pour initialisation.
     * 
     * @param operandAMode Le mode de l'opérande A (ex: #, $, @, etc.)
     * @param operandA     La valeur de l'opérande A
     * @param operandBMode Le mode de l'opérande B (ex: #, $, @, etc.)
     * @param operandB     La valeur de l'opérande B
     */
    public Dat(String operandAMode, int operandA, String operandBMode, int operandB) {
        super(operandAMode, operandA, operandBMode, operandB);
    }

    /**
     * 
     * Méthode d'exécution de l'instruction DAT. Elle prend en entrée un objet
     * Warior (guerrier)
     * et un objet InstructionBoard (plateau d'instructions).
     * Elle supprime le processus suivant dans la liste des processus du guerrier
     * passé en entrée.
     * 
     * @param warior L'objet Warior (guerrier) qui exécute l'instruction
     * @param board  L'objet InstructionBoard (plateau d'instructions) sur lequel
     *               l'instruction est exécutée
     * @see Warior
     * @see InstructionBoard
     */
    public void execute(Warior warior, InstructionBoard board) {
        Processe processToExecute = warior.getNextProcesses();
        Cell cellToExecute = processToExecute.getCellToExecute();
        // même si le processus est tué on doit quand même chercher les cases pointé
        // avant, exemple DAT < 5 $ 3, on doit retirer 1 à l'opérand B 5 cases après
        board.getCellsPointed(cellToExecute);
        warior.removeProcesse(warior.getNextProcesses());
    }

    /**
     * 
     * Méthode qui retourne le code opération de l'instruction DAT.
     * 
     * @return Le code opération de l'instruction DAT : "DAT"
     */
    public String getOpCode() {
        return "DAT";
    }
}
