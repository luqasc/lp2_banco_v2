package Banco;

// Exceção personalizada para erros de cadastro de agência
public class AgenciaIdJaExistenteException extends Exception {
    public AgenciaIdJaExistenteException(String mensagem) {
        super(mensagem);
    }
}