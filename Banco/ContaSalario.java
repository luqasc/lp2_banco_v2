package Banco;

import java.util.Scanner;
import java.util.List;

//Classe ContaSalario herda da classe Conta
public class ContaSalario extends Conta 
{
    //Número de saques realizados
    private int numSaques;

    //Construtor
    public ContaSalario(double _saldo, String _id) 
    {
        // Chama o construtor da classe pai (Conta)
        super(_saldo, _id);
        this.numSaques = 0;
    }

    //Sobrescreve o método de saque; limite de 3 saques por execução do programa
    @Override
    public void Saque(Scanner scanner) 
    {
        if (numSaques >= 3) 
        {
            System.out.println("Limite de saques atingido para esta execução do programa.");
            return;
        }

        double saq = getSaldo() + 1.0;

        //Laço para garantir que o valor a ser sacado será menor que o saldo, e maior que 0
        while (saq > getSaldo() || saq < 0) 
        {
            System.out.println("Digite a quantia a ser sacada.");
            saq = scanner.nextDouble();
            scanner.nextLine();

            //Caso as condições sejam atendidas, o método Movimentacao é chamado, com o valor em negativo
            if (saq <= getSaldo() && saq >= 0) 
            {
                Movimentacao(-saq);
                numSaques++;
                System.out.printf("Saque efetuado com sucesso. Saldo remanescente: R$ %.2f%n\n", getSaldo());
                System.out.printf("Você ainda tem %d saques disponíveis nesta execução.%n\n", 3 - numSaques);
                break;
            } 
            else if (saq > getSaldo()) 
            {
                System.out.println("Saldo insuficiente. Tente novamente.");
            } 
            else 
            {
                System.out.println("Quantia inválida. Tente novamente.");
            }
        }
    }

    //Sobrescreve o menu de transações, para excluir as operações de depósito e transferência
    @Override
    public void menuTransacoes(Scanner scanner, List<Agencia> agencias) 
    {
        int op = 0;
        while (op != 3) 
        { 
            //Menu da conta salário permite apenas saque e consulta de saldo; máximo de 3 saques
            System.out.println("MENU DA CONTA SALÁRIO");
            System.out.println("Digite a opção desejada.");
            System.out.println("1 - Consulta de saldo");
            System.out.println("2 - Saque");
            System.out.println("3 - Voltar ao menu do usuário");

            op = scanner.nextInt();
            scanner.nextLine();
            switch (op) {
                case 1:
                    consultaSaldo();
                    break;
                case 2:
                    Saque(scanner);
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
