package redcode.instruction;

import memory.*;
import warior.*;

/**
 * 
 * La classe Spl représente l'instruction SPL qui permet de créer un nouveau
 * processus en utilisant le mode d'adressage spécifié pour l'opérande A. Le
 * nouveau processus est ajouté à la fin de la queue de processus de guerrier
 * qui exécute l'instruction.
 */
public class Spl extends Instruction {
    /**
     * 
     * Constructeur pour initialiser les valeurs des opérandes A et B et leurs modes
     * d'adressage respectifs.
     * 
     * @param operandAMode le mode d'adressage pour l'opérande A
     * @param operandA     la valeur de l'opérande A
     * @param operandBMode le mode d'adressage pour l'opérande B
     * @param operandB     la valeur de l'opérande B
     */
    public Spl(String operandAMode, int operandA, String operandBMode, int operandB) {
        super(operandAMode, operandA, operandBMode, operandB);
    }

    /**
     * 
     * La méthode execute prend en paramètre un objet Warior et un objet
     * InstructionBoard. Elle permet d'exécuter l'instruction SPL (spawn) d'un
     * processus dans le jeu Core War.
     * L'instruction SPL crée un nouveau processus qui est ajouté à la fin de la
     * file d'attente des processus de l'objet Warior. Le pointeur d'exécution du
     * nouveau processus est déterminé en fonction de l'opérande A de l'instruction:
     * Si l'opérande A est précédé d'un "$", le pointeur d'exécution du nouveau
     * processus est incrémenté de la valeur de l'opérande A.
     * Si l'opérande A est précédé d'un "@", le pointeur d'exécution du nouveau
     * processus est déterminé en utilisant une cellule indirecte dans le tableau
     * d'adresses du processus en cours d'exécution. La valeur de l'opérande B de
     * cette cellule indirecte est ajoutée à l'adresse de la cellule indirecte pour
     * déterminer le pointeur d'exécution final.
     * Si l'opérande A est précédé d'un "<", le pointeur d'exécution du nouveau
     * processus est déterminé en utilisant une cellule indirecte dans le tableau
     * d'adresses du processus en cours d'exécution, similaire à l'opérande "@",
     * mais la valeur de l'opérande B de la cellule indirecte est décrémentée avant
     * d'être utilisée pour déterminer le pointeur d'exécution final.
     */
    public void execute(Warior warior, InstructionBoard board) {
        Processe processToExecute = warior.getNextProcesses();
        Cell cellToExecute = processToExecute.getCellToExecute();

        String operandAMode = (cellToExecute.getInstruction()).getOperandAMode();
        int operandA = (cellToExecute.getInstruction()).getOperandA();
        Processe newProcesse = null;
        boolean create = true;

        try {
            newProcesse = processToExecute.copyForSpl();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            create = false;
        }
        if (create) {
            switch (operandAMode) {
                case "$":
                    newProcesse.addToExecutionPointer(operandA);
                    break;
                case "@":
                    Cell indirectCell = processToExecute
                            .getCellInAddressArray(operandA + processToExecute.getExecutionPointer());
                    int operandB_indirectCell = (indirectCell.getInstruction()).getOperandB();
                    newProcesse.setExecutionPointer(
                            operandA + processToExecute.getExecutionPointer() + operandB_indirectCell);
                    break;
                case "<":
                    Cell indirectDecrementCell = processToExecute
                            .getCellInAddressArray(operandA + processToExecute.getExecutionPointer());
                    int operandB_indirectDecrementCell = (indirectDecrementCell.getInstruction()).getOperandB();
                    (indirectDecrementCell.getInstruction()).setOperandB(operandB_indirectDecrementCell - 1);
                    newProcesse.setExecutionPointer(
                            operandA + processToExecute.getExecutionPointer() + operandB_indirectDecrementCell - 1);
                    break;
            }
            warior.addProcesse(newProcesse);
        }
    }

    /**
     * 
     * Cette méthode retourne le code opération de cette instruction, qui est "SPL"
     * pour l'instruction Spl.
     * 
     * @return le code opération de cette instruction.
     */
    public String getOpCode() {
        return "SPL";
    }
}