package redcode.parser.exception;

public class InvalidAddressingTypeException extends Exception {

    public InvalidAddressingTypeException(String operand, String opCode) {
        super("\nIl y a une erreur dans le mode d'adressage choisit pour l'operand " + operand
                + " !\n Pour l'opCode : \n" + opCode + "\n");
    }
}