package Banco;

import java.util.Scanner;
import java.util.List;

//Classe ContaCorrente herda da classe Conta
public class ContaCorrente extends Conta 
{
    //Taxa de manutenção da conta corrente
    private double taxa;

    //Construtor
    public ContaCorrente(double _saldo, String _id, double _taxa) 
    {
        //Chama o construtor da classe pai (Conta)
        super(_saldo, _id);
        this.taxa = _taxa;
    }

    //Getters e setters
    public double getTaxa() 
    {
        return taxa;
    }

    public void setTaxa(double _taxa) 
    {
        this.taxa = _taxa;
    }

    // Método para consultar a taxa de manutenção
    public void consultarTaxa() 
    {
        System.out.printf("A taxa de manutenção anual da conta é de R$ %.2f%n", this.taxa);
    }

    // Sobrescrevendo o método menuTransacoes para adicionar a nova opção
    @Override
    public void menuTransacoes(Scanner scanner, List<Agencia> agencias) 
    {
        int op = 0;
        while (op != 6) { // Novo menu com 6 opções
            System.out.println("MENU DA CONTA CORRENTE");
            System.out.println("Digite a opção desejada.");
            System.out.println("1 - Consulta de saldo");
            System.out.println("2 - Saque");
            System.out.println("3 - Depósito");
            System.out.println("4 - Transferência");
            System.out.println("5 - Consultar taxa de manutenção");
            System.out.println("6 - Voltar ao menu do usuário");
            
            op = scanner.nextInt();
            scanner.nextLine();
            switch (op) 
            {
                case 1: consultaSaldo();
                        break;
                case 2: Saque(scanner);
                        break;
                case 3: Deposito(scanner);
                        break;
                case 4: Transferencia(scanner, agencias);
                        break;
                case 5: consultarTaxa();
                        break;
                case 6: break;
                default: System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
