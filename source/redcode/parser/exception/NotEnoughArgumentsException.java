package redcode.parser.exception;

public class NotEnoughArgumentsException extends Exception {

    public NotEnoughArgumentsException(String line) {
        super("\nAttention il manque des argument(rappel : opCode modeAdressageA operandA modeAdressageB operandB) \n");
    }
}
