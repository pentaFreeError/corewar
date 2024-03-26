package redcode.parser.exception;

public class InvalidOpCodeException extends Exception {
    public InvalidOpCodeException() {
        super("\nAttention l'opCode n'est pas valide !\n");
    }
}
