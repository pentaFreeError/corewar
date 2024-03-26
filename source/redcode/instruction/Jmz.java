package redcode.instruction;

import memory.*;
import warior.*;

/**
 * 
 * La classe Jmz représente l'instruction Redcode "JMZ".
 * Elle hérite de la classe abstraite {@link Instruction}
 * et implémente les méthodes spécifiques à cette instruction.
 * 
 * voici la définition donnée sur la documentation donnée sur ecampus(version
 * 88) :
 * JMZ A B : Si le champ B de l'opérande B est égal à zéro, alors l'adresse
 * de la localisation de la mémoire spécifiée par l'opérande A est placée à la
 * fin de la file d'attente des processus associée au programme en cours
 * d'exécution.
 * 
 * @see Instruction
 * @see Warior
 * @see Memory
 */
public class Jmz extends Instruction {
    /**
     * 
     * Constructeur de la classe Jmz.
     * Il prend en paramètres les informations relatives aux opérandes A et B :
     * le mode d'adressage et la valeur (ou l'adresse pointée).
     * 
     * @param operandAMode le mode d'adressage de l'opérande A
     * @param operandA     la valeur ou l'adresse pointée par l'opérande A
     * @param operandBMode le mode d'adressage de l'opérande B
     * @param operandB     la valeur ou l'adresse pointée par l'opérande B
     */
    public Jmz(String operandAMode, int operandA, String operandBMode, int operandB) {
        super(operandAMode, operandA, operandBMode, operandB);
    }

    /**
     * 
     * Méthode d'exécution de l'instruction JMZ.
     * 
     * Elle vérifie si la valeur pointée par l'opérande B est égale à zéro.
     * 
     * Si c'est le cas, l'adresse pointée par l'opérande A est ajoutée à la fin de
     * la file d'exécution
     * 
     * du programme en cours d'exécution.
     * 
     * @param warior l'objet représentant le programme en cours d'exécution
     * 
     * @param board  l'objet représentant la mémoire de la machine virtuelle
     * 
     * @see Warior
     * 
     * @see Memory
     */
    public void execute(Warior warior, InstructionBoard board) {
        Processe processToExecute = warior.getNextProcesses();
        Cell cellToExecute = processToExecute.getCellToExecute();

        boolean isZero = false;

        String operandBMode = (cellToExecute.getInstruction()).getOperandBMode();
        int operandB = (cellToExecute.getInstruction()).getOperandB();
        Cell[] cellsPointed = (board.getCellsPointed(cellToExecute));
        Cell cellPointedByA = cellsPointed[0];
        Cell cellPointedByB = cellsPointed[1];

        switch (operandBMode) {
            case "#":
                if (operandB == 0) {
                    isZero = true;
                }
                break;
            default:
                if ((cellPointedByB.getInstruction()).getOperandB() == 0) {
                    isZero = true;
                }

        }
        if (isZero) {
            processToExecute.addAddress(cellPointedByA.getPositionAsInt());
        }
    }

    /**
     * 
     * Cette méthode retourne le code opération de cette instruction, qui est "JMZ"
     * pour l'instruction Jmz.
     * 
     * @return le code opération de cette instruction.
     */
    public String getOpCode() {
        return "JMZ";
    }
}