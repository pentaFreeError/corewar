package warior;

import memory.*;
import java.util.ArrayList;
import warior.exception.*;

/**
 * 
 * La classe Guerrier représente un joueur dans le jeu Core War. Elle contient
 * des informations sur les processus utilisés par le joueur, ainsi que sur son
 * équipe. Elle permet également de gérer les processus, de les exécuter
 * et de les afficher.
 */
public class Warior {
    public static final int MAX_INIT_WARIOR_SIZE = (InstructionBoard.SIZE_X * InstructionBoard.SIZE_Y) / 4;
    public static final int MAX_PROCESSES_SIZE = 16;
    private InstructionBoard board;
    private int nProcesses;
    private ArrayList<Processe> processesArray;
    private int team;
    private int processePointer;
    private boolean isAlive;

    /**
     * 
     * Constructeur pour la classe Guerrier. Il prend en entrée un plateau
     * d'instructions, une liste de processus et
     * l'équipe à laquelle appartient le joueur.
     * 
     * @param board          Le plateau d'instructions
     * @param processesArray La liste de processus utilisés par le joueur
     * @param team           L'équipe à laquelle appartient le joueur
     * @throws TailleProcessusArrayException Si la taille de la liste de processus
     *                                       dépasse la taille maximale
     */
    public Warior(InstructionBoard board, ArrayList<Processe> processesArray, int team)
            throws ProcesseArraySizeException {
        this.team = team;
        this.board = board;
        this.processesArray = processesArray;
        this.nProcesses = (this.processesArray).size();
        this.isAlive = true;
        if (this.nProcesses > MAX_PROCESSES_SIZE) {
            throw new ProcesseArraySizeException(this.team);
        }
        this.processePointer = 0;
    }

    /**
     * 
     * Retourne le numéro du processus courant.
     * 
     * @return Le numéro du processus courant
     */
    public int getProcessePointer() {
        return this.processePointer;
    }

    /*
     * @return Le plateau d'instructions
     */
    public InstructionBoard getInstructionBoard() {
        return this.board;
    }

    /**
     * 
     * Retourne le numéro de l'équipe du joueur.
     * 
     * @return Le numéro de l'équipe du joueur(1 ou 2 vu que nous pouvons avoir que
     *         2 codes)
     */
    public int getTeam() {
        return this.team;
    }

    /**
     * 
     * Retourne le nombre de processus utilisés par le joueur.
     * 
     * @return Le nombre de processus utilisés par le joueur
     */
    public int getNProcesses() {
        return this.nProcesses;
    }

    /**
     * 
     * Retourne la liste de processus utilisés par le joueur.
     * 
     * @return La liste de processus utilisés par le joueur
     */
    public ArrayList<Processe> getProcessesArray() {
        return this.processesArray;
    }

    /**
     * 
     * Ajoute un nouveau processus à la liste des processus du warrior.
     * Si le nombre de processus dépasse la taille maximale autorisée
     * (MAX_PROCESSES_SIZE),
     * le warrior est considéré comme mort(on évite les boucle infini de spl).
     * 
     * @param newProcesse le nouveau processus à ajouter
     */
    public void addProcesse(Processe newProcesse) {
        (this.processesArray).add(newProcesse);
        this.nProcesses += 1;
        if (this.nProcesses > MAX_PROCESSES_SIZE) {
            this.isAlive = false;
        }
    }

    /**
     * 
     * Supprime un processus de la liste des processus du warrior.
     * Si le nombre de processus devient inférieur ou égal à 0, le warrior est
     * considéré comme mort.
     * 
     * @param processeToRemove le processus à supprimer
     */
    public void removeProcesse(Processe processeToRemove) {
        (this.processesArray).remove(processeToRemove);
        this.nProcesses -= 1;
        this.addToProcessePointer(-1);
        if (this.nProcesses <= 0) {
            this.isAlive = false;
        }
    }

    /**
     * 
     * La méthode getNextProcesses() renvoie le processe suivant dans la liste de
     * processus de la classe Warior.
     * Elle utilise l'attribut processePointer pour déterminer quel processus est le
     * suivant dans la liste.
     * 
     * @return Le processe suivant dans la liste de processus de la classe Warior.
     */
    public Processe getNextProcesses() {
        return (this.processesArray).get(this.processePointer);
    }

    /**
     * Exécute l'instruction suivante du processus pointé.
     */
    public void execute() {
        (this.getNextProcesses()).execute(this);
        this.addToProcessePointer(1);
    }

    /**
     * 
     * Cette méthode permet d'ajouter une valeur donnée à l'indice de l'élément en
     * cours dans le tableau de processus.
     * Si l'indice dépasse la taille du tableau, il sera réinitialisé en utilisant
     * la taille du tableau pour obtenir un indice valide.
     * Cela permet de parcourir de manière circulaire le tableau de processus.
     * 
     * @param n Entier indiquant le nombre d'éléments à ajouter à l'indice actuel.
     *          Peut être positif ou négatif.
     */
    public void addToProcessePointer(int n) {
        if (!((this.processesArray).size() == 0)) {
            this.processePointer += n;
            if (this.processePointer < 0) {
                this.processePointer += (this.processesArray).size();
            }
            this.processePointer %= (this.processesArray).size();
        }
    }

    /**
     * 
     * Méthode toString() qui permet d'afficher les informations du Warrior.
     * 
     * @return display : une chaîne de caractères contenant les informations du
     *         Warrior (nombre de processus, numéro de l'équipe, numéro du processus
     *         pointé, et les informations de chaque processus)
     */
    public String toString() {
        String display = "Nombre de processus : " + this.getNProcesses() + "\n";
        display += "numéro de l'équipe : " + this.getTeam() + "\n";
        display += "numéro du processus pointé : " + this.getProcessePointer() + "\n\n";
        int i = 0;
        for (Processe p : this.processesArray) {
            display += "processus " + i + " -> " + p.toString() + "\n";
            i++;
            display += "\n";
        }
        return display;
    }

    /**
     * 
     * Cette méthode permet de déterminer si un warrior est encore en vie ou non.
     * Elle retourne un booléen qui indique si le warrior est encore en vie ou non.
     * Pour qu'un warrior soit considéré comme encore en vie, les deux conditions
     * suivantes doivent être remplies :
     * La propriété isAlive doit être vraie
     * La liste de processus associée au warrior ne doit pas être vide
     * 
     * @return un booléen qui indique si le warrior est encore en vie ou non.
     */
    public boolean isAlive() {
        return this.isAlive && !((this.processesArray).isEmpty());
    }

    /**
     * Récupère l'ensemble des cellules pointées par tous les processus de la liste
     * `processesArray`.
     * 
     * @return La liste d'adresses sous forme d'ArrayList d'entiers, représentant
     *         toutes les cellules pointées par tous les processus.
     */
    public ArrayList<Integer> getAllCellPointed() {
        ArrayList<Integer> allCell = new ArrayList<>();
        for (Processe p : this.processesArray) {
            for (Integer i : p.getAddress()) {
                if (!(allCell.contains(i))) {
                    allCell.add(i);
                }
            }
        }
        return allCell;
    }

    /**
     * 
     * Cette méthode retourne une liste contenant toutes les cellules pointées par
     * les processus.
     * 
     * @return La liste des adresses des prochins cellules pointées par les
     *         processus.
     */
    public ArrayList<Integer> getAllNextCellPointed() {
        ArrayList<Integer> allCell = new ArrayList<>();
        for (Processe p : this.processesArray) {
            int i = p.getAddressCellToExecute();
            if (!(allCell.contains(i))) {
                allCell.add(i);
            }
        }
        return allCell;
    }

}
