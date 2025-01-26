package Banco;

import java.util.Scanner;
import java.util.List;

//Classe "Conta", referente a uma conta em específico
public class Conta
{
    //Atributos de uma conta: seu número (id) e seu saldo.
    private double saldo;
    private String id;

    //Construtor
    public Conta(double _saldo, String _id)
    {
        this.saldo = _saldo;
        this.id = _id;
    }

    //Getters e setters
    public double getSaldo()
    {
        return saldo;
    }

    public void setSaldo(double _saldo)
    {
        this.saldo = _saldo;
    }

    public String getId()
    {
        return id;
    }

    public void setCpf(String _id)
    {
        this.id = _id;
    }

    //Método de consulta de saldo
    public void consultaSaldo()
    {
        System.out.printf("O saldo da conta é de R$ %.2f%n", this.saldo);
    }

    //Método para movimentação da conta no geral (acréscimos e decréscimos)
    public void Movimentacao(double quant)
    {
        this.saldo = this.saldo + quant;
    }

    //Método para saque
    public void Saque(Scanner scanner)
    {
        double saq = saldo + 1.00;
        //Laço para garantir que o valor a ser sacado será menor que o saldo, e maior que 0
        while (saq > saldo || saq < 0)
        {
            System.out.println("Digite a quantia a ser sacada.");
            saq = scanner.nextDouble();
            scanner.nextLine();
            //Caso as condições sejam atendidas, o método Movimentacao é chamado, com o valor em negativo
            if (saq <= this.saldo && saq >= 0)
            {
                Movimentacao(-saq);
                System.out.printf("Saque efetuado com sucesso. Saldo remanescente: R$ %.2f%n", saldo);
                break;
            }
            //Se o saldo for insuficiente, o usuário é informado
            else if (saq > saldo)
            {
                System.out.println("Saldo insuficiente. Tente novamente.");
            }
            //Se o valor for menor que 0, o usuário é informado
            else
            {
                System.out.println("Quantia inválida. Tente novamente.");
            }
        }
    }

    //Método para depósito de uma quantia
    public void Deposito(Scanner scanner)
    {
        double dep = -1.0;

        //Aceita apenas valores de depósito maiores ou iguais a 0
        while (dep < 0)
        {
            System.out.println("Digite a quantia a ser depositada.");
            dep = scanner.nextDouble();
            scanner.nextLine();

            //Se a condição for atendida, o método Movimentacao é chamado
            if (dep >= 0)
            {
                Movimentacao(dep);
                System.out.printf("Depósito efetuado com sucesso. Novo saldo: R$ %.2f%n", saldo);
            }
            //Senão, o usuário é informado
            else
            {
                System.out.println("Quantia inválida. Tente novamente.");
            }
        }
    }

    //Método para transferência entre contas
    public void Transferencia(Scanner scanner, List<Agencia> agencias)
    {
        System.out.println("Digite o número da agência para que deseja transferir.");
        String agenciaDestino = scanner.nextLine();
        boolean achouConta = false, achouAgencia = false;
        int i = 0, j = 0, k = 0;
        String conta = "";
        Conta destino = new Conta(0, "");

        //Laço que percorre todas as agências para procurar a digitada pelo usuário
        for (i = 0; i < agencias.size(); i++)
        {   
            //Caso em que o ID da agência procurada coincide com algum da lista de agências
            if (agenciaDestino.equals(agencias.get(i).idAgencia))
            {
                achouAgencia = true;
                System.out.println("Digite o número da conta para que deseja transferir.");
                conta = scanner.nextLine();

                //Laço que percorre a lista de usuários da agência
                for (j = 0; j < agencias.get(i).usuarios.size(); j++)
                {
                    //Laço que pecorre a lista de contas de cada usuário
                    for (k = 0; k < agencias.get(i).usuarios.get(j).getContas().size(); k++)
                    {
                        /*Se a conta digitada pelo usuário for encontrada,
                        *"achouConta" é verdadeiro, e a conta de destino é armazenada,
                        *e ambos os laços são quebrados.*/
                        if (conta.equals(agencias.get(i).usuarios.get(j).getContas().get(k).getId()))
                        {
                            achouConta = true;
                            destino = agencias.get(i).usuarios.get(j).getContas().get(k);
                            break;
                        }
                    }
                    if (achouConta)
                    {
                        break;
                    }
                }
            }
            if (achouConta)
            {
                break;
            }
        }
        
        //Se a conta foi achada, informa-se seu dono ao usuário, e pede-se a quantia.
        if (achouConta)
        {
            System.out.println("Conta: " + conta + "; Usuário: " + agencias.get(i).usuarios.get(j).getNome());
            System.out.println("Digite a quantia a ser transferida.");
            double transf = scanner.nextDouble();
            
            //Apenas aceita valores maiores ou iguais a zero, e menores ou iguais ao saldo
            if (transf >= 0 && transf <= this.saldo)
            {
                /*Caso as condições sejam atendidas, o método Movimentacao é chamado
                 *negativamente para a conta de origem, e positivamente para a conta
                 *de destino.*/
                this.Movimentacao(-transf);
                destino.Movimentacao(transf);
                System.out.printf("Transferência efetuada com sucesso. Novo saldo: R$ %.2f%n", saldo);
            }
            //Se o saldo for insuficiente, o usuário é informado.
            else if (transf > this.saldo)
            {
                System.out.println("Saldo insuficiente. Tente novamente.");
            }
            //Se o valor for menor que 0, o usuário é informado.
            else
            {
                System.out.println("Valor inválido. Tente novamente.");
            }
        }
        //Caso a conta não seja encontrada, o usuário é informado.
        else if (achouAgencia)
        {
            System.out.println("Conta não encontrada. Tente novamente");
        }
        else
        {
            System.out.println("Agência não encontrada. Tente novamente");
        }
    }

    //Exibe o menu de transações da conta para o usuário
    public void menuTransacoes(Scanner scanner, List<Agencia> agencias)
    {
        //"op" é a opção digitada pelo usuário  
        int op = 0;
        //O laço persiste até que o usuário digite "5" (Sair)
        while (op != 5)
        {
            System.out.println("MENU DA CONTA");
            System.out.println("Digite a opção desejada.");
            System.out.println("1 - Consulta de saldo");
            System.out.println("2 - Saque");
            System.out.println("3 - Depósito");
            System.out.println("4 - Transferência");
            System.out.println("5 - Voltar ao menu do usuário");
            
            op = scanner.nextInt();
            scanner.nextLine();
            switch (op)
            {
                //Chama o método de consulta de saldo
                case 1: consultaSaldo();
                        break;
                //Chama o método de saque, passando o scanner
                case 2: Saque(scanner);
                        break;
                //Chama o método de depósito, passando o scanner
                case 3: Deposito(scanner);
                        break;
                //Chama o método de transferência, passando o scanner e a lista de usuários
                case 4: Transferencia(scanner, agencias);
                        break;
                //Quebra o laço (opção de saída)
                case 5: break;
                //Caso de opção inválida
                default: System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}