package Banco;

public class AgenciaNomeJaExistenteException extends Exception {
    public AgenciaNomeJaExistenteException(String message) {
        super(message);
    }
}