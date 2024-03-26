package warior.exception;

public class MaxAddressSizeException extends Exception {
    public MaxAddressSizeException() {
        super("\n___<=La taille maximal reservé pour un processus dans la mémoire est 25%, vous avez dépassé ceci=>___\n");
    }
}
