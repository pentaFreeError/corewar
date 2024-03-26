package warior;

import memory.*;
import java.util.ArrayList;
import warior.exception.*;;

/**
 * 
 * La classe Processe représente un processus qui est exécuté par un guerrier
 * dans un jeu de guerre de l'espace.
 * 
 * Il contient une liste d'adresses qui indiquent les adresses des instructions
 * à exécuter,
 * et un pointeur d'exécution pour indiquer l'instruction en cours d'exécution,
 * et contient aussi une référence au plateau d'instructions qui contient les
 * informations nécessaires.
 * 
 * 
 * @version 88
 * 
 */
public class Processe {
    /*
     * nombre maximale d'adresses pour un processus.
     */
    public static final int MAX_ADDRESS_SIZE = (InstructionBoard.SIZE_X * InstructionBoard.SIZE_Y) / 4;
    /**
     * Liste des adresses d'instructions à exécuter.
     */
    private ArrayList<Integer> address;
    /**
     * Pointeur d'exécution pour la liste d'adresses.
     */
    private int executionPointer;
    /**
     * Plateau d'instructions pour l'exécution des instructions.
     */
    private InstructionBoard board;

    /**
     * 
     * Constructeur qui initialise une nouvelle instance de la classe Processe avec
     * une liste d'adresses,
     * 
     * un pointeur d'exécution et une référence au plateau d'instructions.
     * 
     * @param address          La liste d'adresses qui indiquent les instructions à
     *                         exécuter.
     * 
     * @param executionPointer Le pointeur d'exécution qui indique l'instruction en
     *                         cours d'exécution(ou celle qu'on devra exécuter au
     *                         prochin tour).
     * 
     * @param board            La référence au plateau d'instructions qui contient
     *                         les informations nécessaires.
     * 
     * 
     * @throws MaxAddressSizeException Si la taille de la liste d'adresses dépasse
     *                                 la taille maximale autorisée.
     */
    public Processe(ArrayList<Integer> address, int executionPointer, InstructionBoard board)
            throws MaxAddressSizeException {
        this.address = address;
        this.executionPointer = executionPointer;
        this.board = board;

        if ((this.address).size() > MAX_ADDRESS_SIZE) {
            throw new MaxAddressSizeException();
        }
    }

    /**
     * 
     * Constructeur qui initialise une nouvelle instance de la classe Processe avec
     * une liste d'adresses
     * et une référence au plateau d'instructions. Le pointeur d'exécution est
     * initialisé à 0.
     * 
     * @param address La liste d'adresses qui indiquent les instructions à exécuter.
     * @param board   La référence au plateau d'instructions qui contient les
     *                informations nécessaires.
     * 
     * @throws MaxAddressSizeException Si la taille de la liste d'adresses dépasse
     *                                 la taille maximale autorisée.
     */
    public Processe(ArrayList<Integer> address, InstructionBoard board) throws MaxAddressSizeException {
        this(address, 0, board);
    }

    /**
     * 
     * Retourne le pointeur d'exécution courant pour cet objet Processe.
     * 
     * @return le pointeur d'exécution courant en tant qu'entier
     */
    public int getExecutionPointer() {
        return this.executionPointer;
    }

    /**
     * 
     * Cette méthode permet de définir un nouveau pointeur d'exécution pour le
     * processus en cours.
     * 
     * @param newExecutionPointer La nouvelle position du pointeur d'exécution
     */
    public void setExecutionPointer(int newExecutionPointer) {
        if ((this.address.size()) > 0) {
            if (newExecutionPointer < 0) {
                newExecutionPointer = (this.address).size() + (newExecutionPointer % (this.address).size());
            }
            this.executionPointer = newExecutionPointer % (this.address).size();
        }
    }

    /**
     * 
     * Ajoute une adresse à la liste des adresses à exécuter pour ce Processe.
     * Si la taille de la liste dépasse la taille maximale autorisée, l'adresse
     * n'est pas ajouté
     * 
     * @param cellAddress l'adresse de la cellule à ajouter à la liste des adresses
     *                    à exécuter.
     */
    public void addAddress(int cellAddress) {
        if ((this.address).size() < MAX_ADDRESS_SIZE) {
            (this.address).add(cellAddress);
        }
    }

    /**
     * 
     * Méthode permettant de déplacer le pointeur d'exécution du processus.
     * Le pointeur d'exécution est utilisé pour savoir quelle instruction de la
     * liste d'adresses doit être exécutée.
     * 
     * @param n Le nombre de cases vers lequel le pointeur d'exécution doit être
     *          déplacé.
     *          Si n est négatif, le pointeur d'exécution sera déplacé vers
     *          l'arrière.
     *          Lorsque le pointeur d'exécution dépasse la fin de la liste
     *          d'adresses, il revient à 0.
     */
    public void addToExecutionPointer(int n) {
        setExecutionPointer(this.executionPointer + n);
    }

    /**
     * 
     * Méthode qui permet l'exécution d'une instruction par un guerrier.
     * Cette méthode appelle la méthode "execute" de l'InstructionBoard associé au
     * processus,
     * puis incrémente le pointeur d'exécution.
     * 
     * @param warior Le guerrier qui exécute l'instruction.
     */
    public void execute(Warior warior) {
        (this.board).execute(warior);
        addToExecutionPointer(1);
    }

    /**
     * 
     * Cette méthode retourne la cellule qui doit être exécutée par le processus.
     * La cellule est déterminée à partir de l'index de l'adresse stockée dans la
     * liste d'adresses du processus.
     * 
     * @return la cellule qui doit être exécutée
     */
    public Cell getCellToExecute() {
        return (this.board).getCell((this.address).get(this.executionPointer));
    }

    /**
     * 
     * Méthode toString : retourne une chaîne de caractère représentant l'état
     * actuel de l'objet Processe.
     * 
     * Cette chaîne contient la liste des adresses pointées par le Processe, ainsi
     * que les instructions correspondantes sur la planche d'instruction.
     * 
     * @return La chaîne de caractère représentant l'état actuel de l'objet
     *         Processe.
     */
    public String toString() {

        String display = "Adresses pointé :" + this.getExecutionPointer() + "\n";

        for (Integer i : this.address) {
            display += "[ " + i + " | ";
            display += (((this.board).getCell(i)).getInstruction()).toString();
            display += " ]";
        }
        return display;
    }

    /**
     * 
     * Cette méthode permet de créer une copie du processus en cours pour le SPL.
     * Elle crée une nouvelle liste d'adresses en copiant les valeurs de l'ancienne
     * liste, puis crée un nouveau processus avec cette liste d'adresses et la
     * référence du plateau de jeu. Le pointeur d'exécution est également copié
     * depuis l'ancien processus vers le nouveau.
     * 
     * @return Le nouveau processus créé par la copie.
     * @throws MaxAddressSizeException si la taille de la liste d'adresses dépasse
     *                                 la limite autorisée.
     */
    public Processe copyForSpl() throws MaxAddressSizeException {
        ArrayList<Integer> newAddress = new ArrayList<>();
        for (Integer i : this.address) {
            newAddress.add(i);
        }
        Processe newProcesse = new Processe(newAddress, this.board);
        newProcesse.setExecutionPointer(this.executionPointer);
        return newProcesse;
    }

    /**
     * 
     * Récupère la cellule dans le tableau d'adresses à l'index spécifié.
     * Ce tableau est circulaire, donc si l'index est inférieur à 0, il est
     * considéré comme étant la dernière case du tableau.
     * Si l'index est supérieur à la taille du tableau, il est considéré comme étant
     * la première case.
     * 
     * @param index l'index de la cellule à récupérer dans le tableau d'adresses
     * @return la cellule correspondante, ou null si le tableau d'adresses est vide
     */
    public Cell getCellInAddressArray(int index) {
        if ((this.address.size()) > 0) {
            if (index < 0) {
                index = (this.address).size() + (index % (this.address).size());
            }
            int cellAddress = (this.address).get(index % ((this.address).size()));
            return (this.board).getCell(cellAddress);
        }
        return null;
    }

    /**
     * Renvoie la liste d'adresses de l'objet courant.
     * 
     * @return La liste d'adresses sous forme d'ArrayList d'entiers.
     */
    public ArrayList<Integer> getAddress() {
        return this.address;
    }

    /**
     * 
     * Récupère l'adresse de la cellule à exécuter pour ce processus.
     * 
     * @return L'adresse de la cellule à exécuter sous forme d'entier.
     */
    public int getAddressCellToExecute() {
        return ((this.board).getCell((this.address).get(this.executionPointer))).getPositionAsInt();
    }
}
