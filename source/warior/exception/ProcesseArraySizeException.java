package warior.exception;

public class ProcesseArraySizeException extends Exception {
    public ProcesseArraySizeException(int warriorTeam) {
        super("\n___<= Le warrior de l'équipe + " + warriorTeam + " dépasse le nombre maximal de processus=>___\n");
    }
}
