package redcode.battle;

import memory.*;
import redcode.instruction.*;
import redcode.parser.*;
import warior.*;
import java.util.*;

/**
 * 
 * La classe Loader gère le chargement des instructions pour les guerriers dans
 * le jeu Corewar.
 * Elle utilise la classe RedCodeParser pour traiter les codes Redcode des
 * guerriers et
 * construit ensuite des objets Warior à partir de ces instructions.
 * 
 */
public class Loader {
    /*
     * La séparation minimale entre les guerriers dans la mémoire.
     */
    public static final int MINIMUM_SEPARATION = (InstructionBoard.SIZE_X * InstructionBoard.SIZE_Y) / 8;

    /**
     * Charge un guerrier à partir de la liste d'instructions donnée et de la
     * position de départ sur la planche d'instruction.
     * 
     * @param InstructionArray la liste d'instructions pour le guerrier
     * @param startPosition    la position de départ sur la planche d'instruction
     * @param team             l'équipe du guerrier
     * @param board            la planche d'instruction
     * @return le guerrier construit
     */
    private static Warior loadWarior(ArrayList<Instruction> InstructionArray, int startPosition, int team,
            InstructionBoard board) {

        int instructionPointer = 0;
        ArrayList<Integer> addressInstructions = new ArrayList<>();

        // Boucle à travers toutes les instructions du guerrier et les ajouter à la
        // planche d'instruction
        for (int i = startPosition; i < startPosition + InstructionArray.size(); i++) {
            ((board).getCell(i)).setInstruction(InstructionArray.get(instructionPointer));
            addressInstructions.add(((board).getCell(i)).getPositionAsInt());
            instructionPointer++;
        }
        Warior warior = null;
        try {
            Processe processe = new Processe(addressInstructions, board);
            ArrayList<Processe> processeArray = new ArrayList<Processe>();
            processeArray.add(processe);
            warior = new Warior(board, processeArray, team);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(2);
        }

        return warior;
    }

    /**
     * Charge les guerriers à partir des instructions données pour chaque guerrier
     * et de la planche d'instruction.
     * 
     * @param warrior1InstructionArray instructions pour le premier guerrier
     * @param warrior2InstructionArray instructions pour le second guerrier
     * @param board                    la planche d'instruction
     * @return la liste des guerriers construits
     */
    public static ArrayList<Warior> loadWariors(ArrayList<Instruction> warrior1InstructionArray,
            ArrayList<Instruction> warrior2InstructionArray, InstructionBoard board) {
        int warior1Position = (int) (Math.random() * (InstructionBoard.SIZE_X * InstructionBoard.SIZE_Y));
        int warior2Start = warior1Position + warrior1InstructionArray.size() + MINIMUM_SEPARATION;
        int warior2End = warior1Position - warrior2InstructionArray.size() - MINIMUM_SEPARATION;
        int warior2Position = warior2Start + (int) (Math.random() * warior2End);

        ArrayList<Warior> wariorArray = new ArrayList<>();

        wariorArray.add(loadWarior(warrior1InstructionArray, warior1Position, 1, board));
        wariorArray.add(loadWarior(warrior2InstructionArray, warior2Position, 2, board));

        return wariorArray;
    }

    /**
     * 
     * Cette méthode permet de charger le code des deux warriors dans l'objet
     * InstructionBoard.
     * 
     * @param codeWarrior1 Le code du premier warrior en tant que chaîne de
     *                     caractères.
     * @param codeWarrior2 Le code du second warrior en tant que chaîne de
     *                     caractères.
     * @param board        L'objet InstructionBoard dans lequel seront chargés les
     *                     instructions des deux warriors.
     * @return La liste de deux objets Warior correspondants aux deux warriors.
     */
    public static ArrayList<Warior> loadCode(String codeWarior1, String codeWarior2, InstructionBoard board) {
        ArrayList<Warior> wariorArray = Loader.loadWariors(RedCodeParser.treatCode(codeWarior1),
                RedCodeParser.treatCode(codeWarior2), board);
        return wariorArray;
    }

    /**
     * 
     * La méthode loadScript permet de charger les instructions pour les deux
     * guerriers à partir de deux fichiers de script.
     * 
     * @param fileName1 Le nom du fichier de script pour le premier guerrier.
     * @param fileName2 Le nom du fichier de script pour le deuxième guerrier.
     * @param board     La planche d'instructions sur laquelle les guerriers sont
     *                  chargés.
     * @return une liste de guerriers chargés.
     */
    public static ArrayList<Warior> loadScript(String fileName1, String fileName2, InstructionBoard board) {
        ArrayList<Instruction> warior1Instruction = RedCodeParser.treatCodeFromScript(fileName1);
        ArrayList<Instruction> warior2Instruction = RedCodeParser.treatCodeFromScript(fileName2);
        return Loader.loadWariors(warior1Instruction, warior2Instruction, board);
    }

    public static ArrayList<Warior> loadScriptAndCode(String file1Name, String code, InstructionBoard board){
        ArrayList<Instruction> warior1Instruction = RedCodeParser.treatCodeFromScript(file1Name);
        ArrayList<Instruction> warior2Instruction = RedCodeParser.treatCode(code);
        return Loader.loadWariors(warior1Instruction, warior2Instruction, board);
    }

    /**
     * Constructeur par défaut qui empêche d'instancier la classe.
     */
    private Loader() {
    }

}