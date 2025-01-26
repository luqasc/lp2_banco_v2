package Banco;

import java.util.Scanner;
import java.util.List;

//Classe ContaPoupanca herda da classe Conta
public class ContaPoupanca extends Conta 
{
    //Atributo para a taxa de rendimento mensal
    private double rendimento;

    //Construtor
    public ContaPoupanca(double _saldo, String _id, double _rendimento) 
    {
        //Chama o construtor da classe pai (Conta)
        super(_saldo, _id);
        this.rendimento = _rendimento;
    }

    //Consultar a taxa de rendimento
    public void consultarRendimento() 
    {
        System.out.printf("A taxa de rendimento mensal da conta é de %.2f%% a.m.%n", rendimento * 100);
    }

    //Simular o rendimento da conta
    public void simularRendimento(Scanner scanner) 
    {
        System.out.println("Digite a quantidade de meses para simulação:");
        int meses = scanner.nextInt();
        scanner.nextLine();

        //Validação para meses positivos
        if (meses > 0) 
        {
            //Fórmula para calcular o rendimento composto
            double saldoAtual = getSaldo();
            double saldoFuturo = saldoAtual * Math.pow((1 + rendimento), meses);

            System.out.printf("Saldo atual: R$ %.2f%n", saldoAtual);
            System.out.printf("Saldo após %d meses com rendimento de %.2f%% a.m.: R$ %.2f%n", meses, rendimento * 100, saldoFuturo);
        } 
        else 
        {
            System.out.println("Quantidade de meses inválida. Digite um número positivo.");
        }
    }

    //Sobrescreve o menu de transações; inclui opções relativas ao rendimento
    @Override
    public void menuTransacoes(Scanner scanner, List<Agencia> agencias) 
    {
        int op = 0;

        while (op != 7) 
        {
            System.out.println("MENU DA CONTA POUPANÇA");
            System.out.println("Digite a opção desejada.");
            System.out.println("1 - Consulta de saldo");
            System.out.println("2 - Saque");
            System.out.println("3 - Depósito");
            System.out.println("4 - Transferência");
            System.out.println("5 - Consultar taxa de rendimento");
            System.out.println("6 - Simular rendimento");
            System.out.println("7 - Voltar ao menu do usuário");

            op = scanner.nextInt();
            scanner.nextLine();

            switch (op) 
            {
                case 1:
                    consultaSaldo();
                    break;
                case 2:
                    Saque(scanner);
                    break;
                case 3:
                    Deposito(scanner);
                    break;
                case 4:
                    Transferencia(scanner, agencias);
                    break;
                case 5:
                    consultarRendimento();
                    break;
                case 6:
                    simularRendimento(scanner);
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
