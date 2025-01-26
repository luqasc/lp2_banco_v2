package Banco;

public class ContaIdJaExistenteException extends Exception {
    public ContaIdJaExistenteException(String message) {
        super(message);
    }
}