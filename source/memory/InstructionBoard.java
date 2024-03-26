package memory;

import redcode.instruction.*;
import warior.*;

/**
 * La classe `InstructionBoard` représente une grille de cellules contenant des
 * instructions.
 * Elle contient une grille circulaire de cellules de taille SIZE_X * SIZE_Y.
 */
public class InstructionBoard {
    public static final int SIZE = 90;
    public static final int SIZE_X = SIZE / 2;
    public static final int SIZE_Y = SIZE / 2;
    private Cell[][] grid;

    /**
     * Constructeur de la classe `InstructionBoard`.
     * Il initialise la grille de cellules de taille SIZE_X * SIZE_Y et instancie
     * des objets Cell pour chaque case de la grille.
     */
    public InstructionBoard() {
        this.grid = new Cell[SIZE_Y][SIZE_X];
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                this.grid[i][j] = new Cell(j, i);
            }
        }
    }

    /**
     * La fonction `displayGrid` de la classe `InstructionBoard` permet d'afficher
     * les informations(Instruction) de chaque cellule de la grille circulaire.
     * 
     */
    public void displayGrid() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                System.out
                        .print("[ " + (this.grid[i][j]).getPositionAsInt() + " | " + (this.grid[i][j]).getInstruction()
                                + "]     ");
            }
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }

    /**
     * La fonction `getCell` de la classe `InstructionBoard` permet de récupérer une
     * cellule de la grille circulaire à partir de son numéro de cellule.
     * 
     * @param nCell (int) : Le numéro de cellule souhaité. Ce paramètre peut être
     *              négatif.
     * @return Cell : La cellule correspondant au numéro de cellule souhaité.
     * 
     *         Fonctionnement :
     *         - Si le numéro de cellule est négatif, il est modifié pour être dans
     *         les limites de la grille.
     *         - Si le numéro est positif mais qu'il dépasse la taille de la grille
     *         alors on utilise le modulo.
     */
    public Cell getCell(int nCell) {
        if (nCell < 0) {
            nCell = nCell % (SIZE_Y * SIZE_X);
            nCell += SIZE_Y * SIZE_X;
        }
        int row = nCell / SIZE_Y;
        int column = nCell % SIZE_X;
        return this.grid[row % SIZE_Y][column % SIZE_X];
    }

    /**
     * La fonction `setCellInstruction` de la classe `InstructionBoard` permet de
     * définirl'instruction d'une cellule de la grille circulaire à partir de son
     * numéro de cellule.
     * 
     * @param nCell          (int) : Le numéro de cellule souhaité. Ce paramètre
     *                       peut être négatif.
     * @param newInstruction (Instruction) : La nouvelle instruction pour la
     *                       cellule.
     * 
     *                       Fonctionnement :
     *                       - Si le numéro de cellule est négatif, il est modifié
     *                       pour être dans les limites de la grille.
     *                       - Les indices de ligne et de colonne sont calculés à
     *                       partir du numéro de cellule.
     *                       - L'instruction de la cellule correspondante est
     *                       définie en accédant à la grille à l'aide des indices de
     *                       ligne et de colonne calculés.
     *
     */
    public void setCellInstruction(int nCell, Instruction newInstruction) {
        if (nCell < 0) {
            nCell = nCell % (SIZE_Y * SIZE_X);
            nCell += SIZE_Y * SIZE_X;
        }
        int row = nCell / SIZE_Y;
        int column = nCell % SIZE_X;
        (this.grid[row % SIZE_Y][column % SIZE_X]).setInstruction(newInstruction);
    }

    /**
     * 
     * Cette méthode retourne un tableau de deux cellules qui sont pointées par les
     * opérandes A et B de la cellule actuelle.
     * [case pointé par A, case pointé par B]
     * Dans le cas ou le mode d'adressage est immédiat, on met null dans la case du
     * tableau correspondante(car il n'y a pas d'adresse pointé).
     * 
     * @param actualCell La cellule actuelle dont les opérandes et les modes
     *                   d'adressage sont utilisées pour trouver les cellules
     *                   pointées.
     * 
     * @return Le tableau de cellules pointées par les opérandes A et B de la
     *         cellule actuelle.
     */
    public Cell[] getCellsPointed(Cell actualCell) {
        Cell[] cellsPointed = new Cell[2];
        int position = actualCell.getPositionAsInt();
        cellsPointed[0] = getPointedCells(actualCell.getInstruction().getOperandAMode(), position,
                actualCell.getInstruction().getOperandA());
        cellsPointed[1] = getPointedCells(actualCell.getInstruction().getOperandBMode(), position,
                actualCell.getInstruction().getOperandB());
        return cellsPointed;
    }

    /**
     * 
     * Cette méthode retourne la cellule pointée par un opérande donné.
     * 
     * @param operandMode Le mode de l'opérande ( "#", "$", "@" ou "<" )
     * 
     * @param position    La position actuelle de la cellule.
     * 
     * @param operand     La valeur de l'opérande.
     * 
     * @return La cellule pointée par l'opérande donné.
     */
    private Cell getPointedCells(String operandMode, int position, int operand) {
        switch (operandMode) {
            case "#":
                return null;
            case "$":
                return this.getCell(position + operand);
            case "@":
                Cell cellPointed = this.getCell(position + operand);
                int cellPointed_position = cellPointed.getPositionAsInt();
                int cellPointed_operandB = (cellPointed.getInstruction()).getOperandB();
                return this.getCell(cellPointed_position + cellPointed_operandB);
            case "<":
                Cell cellPointed2 = this.getCell(position + operand);
                int cellPointed_position2 = cellPointed2.getPositionAsInt();
                int cellPointed_operandB2 = (cellPointed2.getInstruction()).getOperandB();
                (cellPointed2.getInstruction()).setOperandB(cellPointed_operandB2 - 1);
                cellPointed_operandB2 = (cellPointed2.getInstruction()).getOperandB();
                return this.getCell(cellPointed_position2 + cellPointed_operandB2);
            default:
                return null;
        }
    }

    /**
     * 
     * Méthode qui permet d'exécuter le prochain processus d'un guerrier.
     * 
     * @param warior Le guerrier dont le prochain processus doit être exécuté
     */
    public void execute(Warior warior) {
        Processe processeToExecute = warior.getNextProcesses();
        Cell celltoExecute = processeToExecute.getCellToExecute();
        (celltoExecute.getInstruction()).execute(warior, this);
    }
}