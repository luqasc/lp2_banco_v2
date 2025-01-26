package Banco;

public class CpfJaExistenteException extends Exception {
    public CpfJaExistenteException(String message) {
        super(message);
    }
}