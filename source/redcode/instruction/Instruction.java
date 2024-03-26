package redcode.instruction;

import memory.*;
import warior.*;

/**
 * La classe abstraite `Instruction` représente une instruction pour un
 * programme Redcode.
 * Cette classe contient des informations sur les opérandes A et B, ainsi que
 * les modes d'opérandes pour un programme Redcode.
 */
public abstract class Instruction {
    protected int operandA;
    protected int operandB;
    protected String operandAMode;
    protected String operandBMode;

    /**
     * Constructeur de la classe `Instruction`.
     * 
     * @param operandAMode (String) : Le mode de l'opérande A pour un programme
     *                     Redcode.
     * @param operandA     (int) : La valeur de l'opérande A pour un programme
     *                     Redcode.
     * @param operandBMode (String) : Le mode de l'opérande B pour un programme
     *                     Redcode.
     * @param operandB     (int) : La valeur de l'opérande B pour un programme
     *                     Redcode.
     */
    public Instruction(String operandAMode, int operandA, String operandBMode, int operandB) {
        this.operandA = operandA;
        this.operandB = operandB;
        this.operandAMode = operandAMode;
        this.operandBMode = operandBMode;
    }

    /**
     * La fonction `getOperandA` de la classe `Instruction` permet de récupérer la
     * valeur de l'opérande A.
     * 
     * @return int : La valeur de l'opérande A.
     */
    public int getOperandA() {
        return this.operandA;
    }

    /**
     * La fonction `setOperandA` de la classe `Instruction` permet de mettre à jour
     * la valeur de l'opérande A.
     * 
     * @param newOperandA int : La nouvelle valeur de l'opérande A.
     */
    public void setOperandA(int newOperandA) {
        this.operandA = newOperandA;
    }

    /**
     * La fonction `getOperandB` de la classe `Instruction` permet de récupérer la
     * valeur de l'opérande B.
     * 
     * @return int : La valeur de l'opérande B.
     */
    public int getOperandB() {
        return this.operandB;
    }

    /**
     * La fonction `setOperandB` de la classe `Instruction` permet de mettre à jour
     * la valeur de l'opérande B.
     * 
     * @param newOperandB int : La nouvelle valeur de l'opérande B.
     */
    public void setOperandB(int newOperandB) {
        this.operandB = newOperandB;
    }

    public void setOperandBMode(String newOperandBMode) {
        this.operandBMode = newOperandBMode;
    }

    public void setOperandAMode(String newOperandAMode) {
        this.operandAMode = newOperandAMode;
    }

    /**
     * La fonction `getOperandAMode` de la classe `Instruction` permet de récupérer
     * le mode de l'opérande A.
     * 
     * @return String : Le mode de l'opérande A.
     */
    public String getOperandAMode() {
        return this.operandAMode;
    }

    /**
     * La fonction `getOperandBMode` de la classe `Instruction` permet de récupérer
     * le mode de l'opérande B.
     * 
     * @return String : Le mode de l'opérande B.
     */
    public String getOperandBMode() {
        return this.operandBMode;
    }

    /**
     * 
     * Méthode abstraite qui permet d'exécuter une instruction spécifique sur un
     * guerrier, en utilisant un plateau d'instructions.
     * 
     * @param warior Le guerrier sur lequel l'instruction doit être exécutée
     * @param board  Le plateau d'instructions qui contient les informations
     *               nécessaires pour exécuter l'instruction
     */
    public abstract void execute(Warior warior, InstructionBoard board);

    /**
     * La méthode abstraite `getOpCode` de la classe `Instruction` permet de
     * récupérer le code opération de l'instruction autrement dit, le nom de la
     * commande.
     * 
     * @return String : Le code opération de l'instruction.
     */
    public abstract String getOpCode();

    /**
     * Retourne une représentation sous forme de chaîne de cet objet
     * 
     * @return une chaîne contenant le code opérationnel, le mode de l'opérande A,
     *         l'opérande A, le mode de l'opérande B et l'opérande B
     */
    public String toString() {
        return this.getOpCode() + " " + this.getOperandAMode() + " " + this.getOperandA() + " " + this.getOperandBMode()
                + " " + this.getOperandB();
    }

    /**
     * Compare l'objet courant à un autre objet pour savoir s'ils sont égaux.
     *
     * @param unObjet l'objet à comparer
     * @return true si les objets sont égaux, false sinon
     */
    public boolean equals(Object unObjet) {
        if (unObjet == null) {
            return false;
        }
        if (!(unObjet instanceof Instruction)) {
            return false;
        }

        Instruction otherInstruction = (Instruction) unObjet;
        boolean ok = true;
        ok &= (otherInstruction.getOpCode()).equals(this.getOpCode());
        ok &= (otherInstruction.getOperandA()) == (this.getOperandA());
        ok &= (otherInstruction.getOperandB()) == (this.getOperandB());
        ok &= (otherInstruction.getOperandAMode()).equals(this.getOperandAMode());
        ok &= (otherInstruction.getOperandBMode()).equals(this.getOperandBMode());

        return ok;
    }

}
