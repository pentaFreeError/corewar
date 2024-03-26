package redcode.parser;

import redcode.instruction.*;
import redcode.parser.exception.*;
import java.util.*;
import java.util.HashMap;
import java.io.*;

/*

La classe RedCodeParser permet de parser du code Redcode et de le transformer en instructions exécutables.

Elle contient des méthodes statiques pour enlever les commentaires et les espaces d'une ligne de code,

obtenir les modes d'adressage valides pour chaque opcode, vérifier la validité des commandes et transformer

les commandes en instructions exécutables.
*/
public class RedCodeParser {
    /*
     * La liste OperandModeListe contient tout les modes d'adressage possible, elle
     * nous sert à tester si un mode d'adressage donnée par l'utilisateur est valide
     * ou pas, s'il est dedans alors il est valide.
     */
    public static List<String> OperandModeListe = Arrays.asList(new String[] { "#", "$", "@", "<" });

    /*
     * Le dictionnaire AddressingModeDico contient en clé tout les opCode (nom de
     * commande possible) sous forme
     * de string et en valeur il contient un tableau de string avec la premiére case
     * du tableau les modes
     * d'adressage autorisé pour l'opérand A et la deuxième case les modes
     * d'adressage autorisé pour l'opérand B,
     * bien sur tout ceci en focntion de l'opCode.
     * 
     * Par exemple l'opCode ADD autorise les modes
     * pour l'opérand A ->
     * # : immédiat , $ : direct , @ : indirect, < : indirect pré-décrément
     * 
     * pour l'opérand B ->
     * $ , @, <
     * 
     * ceci veut dire que notre dictionnaire stock clé : "ADD" , valeur :
     * {"#$@<","$@<"}
     * 
     * En résumé, ce dictionnaire va nous aider pour vérifier si l'utilisateur a
     * choisit un type d'adressage valide selon l'opCode
     * de sa commande et selon l'opérand.
     */
    public static HashMap<String, String[]> AddressingModeDico = getAddressingMode();

    /**
     * La méthode removeComment permet de supprimer les commentaires d'une ligne de
     * code en supprimant tout ce qui se trouve après le premier caractère ";"
     * rencontré.
     * 
     * @param line La ligne de code à traiter
     * @return La ligne de code sans commentaires
     */
    public static String removeComment(String line) {
        if (line.contains(";")) {
            return line.substring(0, line.indexOf(";"));
        } else {
            return line;
        }
    }

    /**
     * 
     * La méthode removeSpace permet de supprimer les espaces d'une chaîne de
     * caractères donnée.
     * 
     * @param line la chaîne de caractères dont on veut supprimer les espaces.
     * @return la chaîne de caractères sans espaces.
     */
    public static String removeSpace(String line) {
        if (line.contains(" ")) {
            return line.replace(" ", "");
        } else {
            return line;
        }
    }

    /**
     * 
     * La méthode getAddressingMode retourne un dictionnaire contenant les modes
     * d'adressage pour chaque instruction de l'assembleur Redcode.
     * Les clés du dictionnaire sont les noms des instructions et les valeurs sont
     * des tableaux de chaînes contenant les modes d'adressage pour l'opérand A(1ère
     * case du tableau) et pour l'opérand B(2ème case du tableau).
     * Les valeurs possibles pour les modes d'adressage sont "#" (immédiat), "$"
     * (direct), "@" (indirect) et "<" (predecrement).
     * 
     * @return Une instance de HashMap contenant les modes d'adressage pour chaque
     *         instruction de l'assembleur Redcode.
     */
    public static HashMap<String, String[]> getAddressingMode() {
        HashMap<String, String[]> dictionary = new HashMap<>();
        String any = "#$@<";
        String mode2 = "$@<";
        dictionary.put("ADD", new String[] { any, mode2 });
        dictionary.put("CMP", new String[] { any, mode2 });
        dictionary.put("DAT", new String[] { any, any });
        dictionary.put("DJN", new String[] { mode2, any });
        dictionary.put("JMN", new String[] { mode2, any });
        dictionary.put("JMP", new String[] { mode2, any });
        dictionary.put("JMZ", new String[] { mode2, any });
        dictionary.put("MOV", new String[] { any, mode2 });
        dictionary.put("NOP", new String[] { any, any });
        dictionary.put("SLT", new String[] { any, mode2 });
        dictionary.put("SPL", new String[] { mode2, any });
        dictionary.put("SUB", new String[] { any, mode2 });
        dictionary.put("JMI", new String[] { mode2, any });
        return dictionary;
    }

    /**
     * 
     * Méthode qui prend en entrée une ligne de code assembleur Redcode(sans espace
     * ni commentaire) et retourne un tableau de chaînes de caractères
     * contenant les différentes parties de la commande Redcode.
     * case 0 du tableau : l'opCode
     * case 1 du tableau : le mode d'adressage de l'opérand A
     * case 2 du tableau : valeur de l'opérand A
     * case 3 du tableau : le mode d'adressage de l'opérand B
     * case 4 du tableau : valeur de l'opérand B
     * 
     * @param line : la ligne de code assembleur Redcode
     * @return : un tableau de chaînes de caractères contenant les différentes
     *         parties de la commande Redcode.
     * @throws NotEnoughArgumentsException    : exception lancée lorsque la ligne de
     *                                        code assembleur ne contient pas assez
     *                                        d'arguments
     * @throws InvalidAddressingTypeException : exception lancée lorsque le type
     *                                        d'adressage utilisé pour l'opérande B
     *                                        est invalide (celui de l'opérand A
     *                                        sera testé plus tard dans la méthode
     *                                        acceptableAddressingMode)
     */
    public static String[] getCommande(String line) throws NotEnoughArgumentsException, InvalidAddressingTypeException {
        /*
         * 3 caractères pour l'opCode, 1 pour le mode d'adressage de A, minimum 1 pour
         * la valeur de A,1 pour le mode d'adressage de B et minimum 1 pour la valeur de
         * B
         * 
         */
        if (line.length() < (3 + 1 + 1 + 1 + 1)) {
            throw new NotEnoughArgumentsException(line);
        }
        String opCode = line.substring(0, 3);
        String operandAMode = line.substring(3, 4);
        String secondePartOfline = line.substring(4);

        String operandBMode = "-1";
        for (String operandMode : OperandModeListe) {
            if (secondePartOfline.contains(operandMode)) {
                operandBMode = operandMode;
                break;
            }
        }
        if (operandBMode == "-1") {
            throw new InvalidAddressingTypeException("B", opCode);
        }
        String operandA = secondePartOfline.substring(0, secondePartOfline.indexOf(operandBMode));

        String operandB = secondePartOfline.substring(secondePartOfline.indexOf(operandBMode) + 1);
        String[] commande = { opCode, operandAMode, operandA, operandBMode, operandB };
        return commande;
    }

    /**
     * 
     * Cette méthode vérifie si le mode d'adressage spécifié pour un opérande est
     * acceptable pour l'opcode spécifié.
     * 
     * @param opCode      L'opcode pour lequel on vérifie la compatibilité du mode
     *                    d'adressage.
     * @param operandMode Le mode d'adressage spécifié pour l'opérande.
     * @param operand     L'opérande pour lequel on vérifie la compatibilité du mode
     *                    d'adressage.
     *                    Doit être "A" ou "B" pour indiquer si c'est l'opérande A
     *                    ou B qui est vérifié.
     * @return true si le mode d'adressage est acceptable pour l'opcode et
     *         l'opérande spécifié, false sinon.
     */
    public static boolean acceptableAddressingMode(String opCode, String operandMode, String operand) {
        if ((operand != "A" && operand != "B")
                || !(OperandModeListe.contains(operandMode) || AddressingModeDico.get(opCode) == null)) {
            return false;
        }
        String mode = "";
        if (operand == "A") {
            mode = (AddressingModeDico.get(opCode))[0];
        } else if (operand == "B") {
            mode = (AddressingModeDico.get(opCode))[1];
        }
        return mode.contains(operandMode);
    }

    /**
     * 
     * Méthode qui prend un tableau de chaînes de caractères représentant une
     * instruction dans un format spécifique et retourne une instruction exécutable.
     * 
     * @param commande Tableau de chaînes de caractères représentant une
     *                 instruction, avec les éléments suivants: opcode, mode
     *                 d'adressage pour l'opérande A, opérande A, mode d'adressage
     *                 pour l'opérande B, opérande B.
     * @return Une instruction exécutable correspondant à la commande donnée.
     * @throws NotEnoughArgumentsException    si le tableau de commande ne contient
     *                                        pas suffisamment d'éléments ou si un
     *                                        élément est vide.
     * @throws InvalidAddressingTypeException si le mode d'adressage spécifié pour
     *                                        l'opérande A ou B n'est pas valide
     *                                        pour l'opcode donné.
     * @throws InvalidOpCodeException         si l'opcode donné n'est pas valide.
     * @throws NumberFormatException          si l'opérande A ou B ne peut pas être
     *                                        converti en un entier.
     */
    public static Instruction getInstructionFromArray(String[] commande)
            throws NotEnoughArgumentsException, InvalidAddressingTypeException, InvalidOpCodeException,
            NumberFormatException {
        List<String> array = Arrays.asList(commande);
        if (array.size() != 5 || array.contains("")) {
            throw new NotEnoughArgumentsException("");
        }
        String opCode = (array.get(0)).toUpperCase();

        if (!(AddressingModeDico.containsKey(opCode))) {
            throw new InvalidOpCodeException();
        }
        String operandAMode = array.get(1);
        String operandBMode = array.get(3);
        if (!acceptableAddressingMode(opCode, operandAMode, "A")) {
            throw new InvalidAddressingTypeException("A", opCode);
        }
        if (!acceptableAddressingMode(opCode, operandBMode, "B")) {
            throw new InvalidAddressingTypeException("B", opCode);
        }

        // Ici NumberFormatException est lancé toute seule si les string ne peuvent pas
        // être transformé en int
        int operandA = Integer.parseInt(array.get(2));
        int operandB = Integer.parseInt(array.get(4));

        switch (opCode) {
            case "ADD":
                return new Add(operandAMode, operandA, operandBMode, operandB);
            case "SUB":
                return new Sub(operandAMode, operandA, operandBMode, operandB);
            case "DAT":
                return new Dat(operandAMode, operandA, operandBMode, operandB);
            case "NOP":
                return new Nop(operandAMode, operandA, operandBMode, operandB);
            case "CMP":
                return new Cmp(operandAMode, operandA, operandBMode, operandB);
            case "DJN":
                return new Djn(operandAMode, operandA, operandBMode, operandB);
            case "JMN":
                return new Jmn(operandAMode, operandA, operandBMode, operandB);
            case "JMP":
                return new Jmp(operandAMode, operandA, operandBMode, operandB);
            case "JMZ":
                return new Jmz(operandAMode, operandA, operandBMode, operandB);
            case "MOV":
                return new Mov(operandAMode, operandA, operandBMode, operandB);
            case "SLT":
                return new Slt(operandAMode, operandA, operandBMode, operandB);
            case "SPL":
                return new Spl(operandAMode, operandA, operandBMode, operandB);
            case "JMI":
                return new Jmi(operandAMode, operandA, operandBMode, operandB);

            default:
                throw new InvalidOpCodeException();
        }
    }

    /**
     * 
     * Cette méthode prend en entrée un code sous forme de chaîne de caractères et
     * le découpe en lignes en utilisant le caractère de saut de ligne ("\n") comme
     * séparateur.
     * Elle retourne ensuite un tableau de chaînes de caractères contenant chaque
     * ligne de code.
     * 
     * @param code Le code à découper en lignes sous forme de chaîne de caractères
     * @return Un tableau de chaînes de caractères contenant chaque ligne de code
     */
    public static String[] splitLinesIntoArray(String code) {
        String[] lines = code.split("\n");
        return lines;
    }

    /**
     * 
     * Traite une ligne de code pour en extraire les informations nécessaires à
     * l'exécution de l'instruction.
     * 
     * Cette méthode supprime les espaces et les commentaires de la ligne, puis
     * extrait les informations de l'instruction
     * 
     * à l'aide des méthodes {@link #getCommande(String)} et
     * {@link #getInstructionFromArray(String[])}.
     * 
     * Elle retourne ensuite l'instruction créée.
     * 
     * @param line la ligne de code à traiter
     * 
     * @return l'instruction créée à partir de la ligne de code donnée
     */

    public static Instruction treatLine(String line) {
        line = removeSpace(line);
        line = removeComment(line);

        String[] commande = null;
        // Le cas ou la ligne traité est juste un commentaire
        if (line.isEmpty() || line.length() < 2) {
            return null;
        }
        try {
            commande = getCommande(line);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        Instruction instruction = null;
        try {
            instruction = getInstructionFromArray(commande);
        } catch (NotEnoughArgumentsException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (InvalidAddressingTypeException e) {
            for (int i = 0; i < e.getStackTrace().length; i++) {
                System.out.println(e.getStackTrace()[i]);

            }
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (InvalidOpCodeException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return instruction;
    }

    /**
     * 
     * treatCode est une méthode qui prend en entrée un String "code" qui représente
     * le code source. Cette méthode va séparer le code source en lignes en
     * utilisant la méthode splitLinesIntoArray, puis pour chaque ligne elle va
     * utiliser la méthode treatLine pour traiter cette ligne, c'est-à-dire pour en
     * extraire les informations nécessaires pour créer une instruction (opCode,
     * operandAMode, operandA, operandBMode, operandB) et pour créer cette
     * instruction en utilisant la méthode getInstructionFromArray. Enfin, elle va
     * ajouter cette instruction à une liste d'instructions qui est retournée en fin
     * de traitement.
     * on choisit de partir sur une liste car nous ne savons combien d'instruction
     * nous aurons dans chaque code, le nombre de ligne n'aide pas car on peut avoir
     * des commentaires.
     * 
     * @param code le code source à traiter
     * @return liste d'instructions qui ont été extraites du code source
     */
    public static ArrayList<Instruction> treatCode(String code) {
        ArrayList<Instruction> instructionListe = new ArrayList<Instruction>();
        String[] codeArray = splitLinesIntoArray(code);
        for (String line : codeArray) {
            Instruction instruction = treatLine(line);
            if (instruction != null) {
                instructionListe.add(instruction);
            }
        }

        return instructionListe;
    }

    /**
     * Traite le code à partir d'un script.
     * 
     * @param fileName Le nom du fichier script à traiter.
     * @return Une liste d'instructions à partir du code du script.
     */
    public static ArrayList<Instruction> treatCodeFromScript(String fileName) {
        return treatCode(scriptToString(fileName));
    }

    public static String scriptToString(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
                content.append("\n");
            }
        } catch (IOException e) {
            return "Erreur dans le fichier";
        }
        return content.toString();
    }

    public static String instructionToString(ArrayList<Instruction> instructions) {
        String resultat = "";
        for (Instruction instruction : instructions) {
            resultat += instruction.toString();
            resultat += "\n";
        }
        return resultat;
    }

    /**
     * Constructeur par défaut qui empêche d'instancier la classe.
     */
    private RedCodeParser() {
    }

}
