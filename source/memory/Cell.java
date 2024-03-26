package memory;

import redcode.instruction.*;

/**
 * La classe `Cell` représente une cellule de la grille d'instructions.
 * Elle contient les coordonnées x et y, et l'instruction de la cellule
 */
public class Cell {
    private final int x;
    private final int y;
    private Instruction instruction;

    /**
     * Constructeur de la classe `Cell`.
     * Il initialise les coordonnées x et y, et l'instruction de la
     * cellule.
     *
     * @param x           (int) : Coordonnée x de la cellule.
     * @param y           (int) : Coordonnée y de la cellule.
     * @param instruction (Instruction) : Instruction contenue dans la cellule.
     *
     */
    public Cell(int x, int y, Instruction instruction) {
        this.x = x;
        this.y = y;
        this.instruction = instruction;
    }

    /**
     * Constructeur de la classe `Cell` avec instruction par défaut.
     * Il initialise les coordonnées x et y, et l'instruction par défaut NOP
     * de la cellule.
     *
     * @param x (int) : Coordonnée x de la cellule.
     * @param y (int) : Coordonnée y de la cellule.
     */
    public Cell(int x, int y) {
        this(x, y, new Nop(" ", 0, " ", 0));
    }

    /**
     * La fonction `getPositionAsInt` de la classe `Cell` permet de récupérer la
     * position de la cellule en format int.
     * 
     * @return int : La position de la cellule actuelle.
     */
    public int getPositionAsInt() {
        return this.y * InstructionBoard.SIZE_X + this.x;
    }

    /**
     * La fonction `getPositionAsTab` de la classe `Cell` permet de récupérer les
     * coordonnées de la cellule en format tableau.
     * 
     * @return int[] : Tableau contenant les coordonnées x et y de la cellule.
     */
    public int[] getPositionAsTab() {
        int[] tab = { this.x, this.y };
        return tab;
    }

    /**
     * La fonction `setInstruction` de la classe `Cell` permet de définir
     * l'instruction de la cellule.
     * 
     * @param newInstruction (Instruction) : La nouvelle instruction pour la
     *                       cellule.
     */
    public void setInstruction(Instruction newInstruction) {
        this.instruction = newInstruction;
    }

    /**
     * La fonction `getInstruction` de la classe `Cell` permet de récupérer
     * l'instruction de la cellule.
     * 
     * @return Instruction : L'instruction de la cellule.
     */
    public Instruction getInstruction() {
        return this.instruction;
    }

    /**
     * La fonction `toString` de la classe `Cell` permet de récupérer les
     * informations de la cellule sous forme de chaîne de caractères.
     * 
     * @return String : La chaîne de caractères contenant l'adresse de la cellule,
     *         les coordonnées x et y de la cellule.
     */
    public String toString() {
        return "Cell Address : " + this.getPositionAsInt() + "\nCell position : [ " + this.getPositionAsTab()[0] + ", "
                + this.getPositionAsTab()[1] + " ]";
    }
}