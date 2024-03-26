package generateur;

import redcode.parser.*;
import java.util.HashMap;
import java.util.Random;
import redcode.battle.*;
import memory.InstructionBoard;
import warior.*;
/**
 * 
 * Cette classe contient des méthodes pour générer des lignes de code RedCode
 * aléatoires et des programmes complets
 * 
 * composés de plusieurs lignes.
 */
public class Generateur {
    
    private static Random random = new Random(System.currentTimeMillis());
    /**
     * 
     * Génère une ligne de code RedCode aléatoire.
     * 
     * @return une ligne de code RedCode générée aléatoirement
     */
    public static String generateLine() {
        HashMap<String, String[]> dico = RedCodeParser.getAddressingMode();
        // keySet donne l'ensemble de toutes les clés, on transforme ceci en tableau
        String cle_au_hasard = (String) (((dico.keySet()).toArray())[random.nextInt(dico.size())]);
        String[] operandsMode = dico.get(cle_au_hasard);
        // on récupere la String qui représente les différent mode d'adressage possible,
        // exemple : $@
        // puis on selectionne le charactere à l'index random entre 0 et la taille de la
        // string
        char operandAMode = (operandsMode[0]).charAt(random.nextInt((operandsMode[0]).length()));
        char operandBMode = (operandsMode[1]).charAt(random.nextInt((operandsMode[1]).length()));

        int operandA = (random).nextInt(InstructionBoard.SIZE_X * InstructionBoard.SIZE_Y);
        int operandB = (random).nextInt(InstructionBoard.SIZE_X * InstructionBoard.SIZE_Y);

        return cle_au_hasard + " " + operandAMode + operandA + " " + operandBMode + operandB;

    }

    /**
     * 
     * Génère un programme complet de RedCode composé de plusieurs lignes de code.
     * 
     * @param nLines le nombre de lignes à générer
     * @return un programme complet de RedCode composé de plusieurs lignes de code
     */
    public static String generateCode(int nLines) {
        String code = "";
        for (int i = 0; i < nLines; i++) {
            code += generateLine();
            code += "\n";
        }
        return code;
    }

    /**
     * 
     * Génère un programme complet de RedCode composé de plusieurs lignes de code.
     * Cette fonction renvoi un programme qui "marche", c'est-à-dire un programme qui ne se tue pas lui même. 
     * 
     * @param nLines le nombre de lignes à générer
     * @return un programme complet de RedCode composé de plusieurs lignes de code 
     */
    public static String generateValideCode(int nLines){
        boolean end = false;
        String code = "";
        while(!end){
            code = generateCode(nLines);
            Battle b = new Battle(code, "NOP $0 $0", CodeMode.ONLY_STRING);
            b.startBattle();
            if(b.getNCycle() == -1 || b.getNCycle() > Battle.MAX_CYCLE/4){
                end = true;
            }
        }
        return code;
    }

    public static String generateValideCode(){
        int nLines = 1 + (random.nextInt(Warior.MAX_INIT_WARIOR_SIZE - 1));
        return generateValideCode(nLines);
    }
}
