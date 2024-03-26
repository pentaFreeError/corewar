package redcode.parser.exception;

public class RedCodeSyntaxException extends Exception {
    public RedCodeSyntaxException() {
        super("\nAttention il y a une erreur de syntax dans votre code\n");
    }
}