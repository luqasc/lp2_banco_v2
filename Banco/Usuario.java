package Banco;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

//Classe do usuário
public class Usuario 
{
    //Dados privados do usuário: nome, CPF, senha e informações de suas contas.
    private String nome, cpf, senha;
    private List<Conta> contas;

    //Construtor
    public Usuario(String _nome, String _cpf, String _senha)
    {
        this.nome = _nome;
        this.cpf = _cpf;
        this.senha = _senha;
        this.contas = new ArrayList<>();
    }

    //Getters e setters
    public String getNome()
    {
        return nome;
    }

    public void setNome(String _nome)
    {
        this.nome = _nome;
    }

    public String getCpf()
    {
        return cpf;
    }

    public void setCpf(String _cpf)
    {
        this.cpf = _cpf;
    }

    public String getSenha()
    {
        return senha;
    }

    public void setSenha(String _senha)
    {
        this.senha = _senha;
    }

    public List<Conta> getContas()
    {
        return contas;
    }

    //Método para cadastro de uma nova conta.
    public void cadastrarConta(Scanner scanner, int ind, List<Agencia> agencias)
    {
        int op = 0;
        String id;

        System.out.println("Digite um número no formato x.xxx para sua conta.");
        id = scanner.nextLine();

        try
        {
            //Tratamento de exceção: Número de conta em formato inadequado
            if (formatoContaIdEhInadequado(id))
            {
                throw new ContaIdForaDeFormatoException("O número " + id + " não está no formato x.xxx. Tente novamente.");
            }
            //Tratamento de exceção: Número de conta já existente
            if (ContaJaExistente(id, agencias))
            {
                throw new ContaIdJaExistenteException("O número " + id + " já está sendo usado. Tente novamente.");
            }

            //Menu para seleção do tipo de conta a ser criada
            System.out.println("Digite a opção desejada.");
            System.out.println("1 - Criar conta corrente");
            System.out.println("2 - Criar conta poupança");
            System.out.println("3 - Criar conta salário");
            System.out.println("4 - Voltar ao menu do usuário");

            op = scanner.nextInt();
            scanner.nextLine();
            switch(op)
            {
                //Para contas correntes, é adicionado o dígito "1" ao final das contas;
                case 1:
                    id = id + "-1";
                    ContaCorrente contaCorrente = new ContaCorrente(0, id, 216);
                    contas.add(contaCorrente);
                    System.out.println("Sua nova conta tem o número " + id + ".");
                    break;
                //Para poupanças, o dígito "2" 
                case 2:
                    id = id + "-2";
                    ContaPoupanca contaPoupanca = new ContaPoupanca(0, id, 0.005);
                    contas.add(contaPoupanca);
                    System.out.println("Sua nova conta tem o número " + id + ".");
                    break;
                //Para contas salário, o dígito "3"
                case 3:
                    id = id + "-3";
                    ContaSalario contaSalario = new ContaSalario(0, id);
                    contas.add(contaSalario);
                    System.out.println("Sua nova conta tem o número " + id + ".");
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break; 
            }
        }
        catch (ContaIdForaDeFormatoException | ContaIdJaExistenteException e)
        {
            //Impressão de mensagem de erro para exceções
            System.out.println("Erro ao cadastrar conta: " + e.getMessage());
        }
    }

    //Método auxiliar para checar corretude do ID da conta
    public boolean formatoContaIdEhInadequado(String id)
    {
        if (id.matches("\\d\\.\\d{3}"))
        {
            return false; //Formato ID é adequado
        }
        else
        {
            return true; //Formato ID é inadequado
        }
    }

    //Método auxiliar para checar se ID da conta já foi utilizado
    public boolean ContaJaExistente(String id, List<Agencia> agencias)
    {
        for (int i = 0; i < agencias.size(); i++)
        {
            for (int j = 0; j < agencias.get(i).usuarios.size(); j++)
            {
                for (int k = 0; k < agencias.get(i).usuarios.get(j).contas.size(); k++)
                {
                    if (id.equals(agencias.get(i).usuarios.get(j).contas.get(k).getId().substring(0,5)))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Lista todas as contas do usuário
    public void listarContas()
    {
        System.out.println("Contas do usuário:");
        for (int i = 0; i < this.contas.size(); i++)
        {
            System.out.println("Conta " + this.contas.get(i).getId());
        }
    }

    //Método para acesso ao menu de transações de uma conta específica
    public int acessarConta(Scanner scanner)
    {
        System.out.println("Digite o número da conta.");
        String id = scanner.nextLine();
        boolean achou = false;
        int i;

        //Procura o número digitado pelo usuário entre as contas cadastradas
        for (i = 0; i < this.contas.size(); i++)
        {
            if (id.equals(contas.get(i).getId()))
            {
                achou = true;
                System.out.println("Acesso liberado.");
                break;
            }
        }
        
        //Se a conta for encontrada, retorna o índice da conta na lista de contas
        if (achou)
        {
            return i;
        }
        //Senão, retorna -1.
        else
        {
            System.out.println("Conta não encontrada!");
            return -1;
        }
    }

    /*Exibe o menu de opções de contas para o usuário
     *Recebe o scanner, o índice do usuário na lista de usuários
     *e a lista de agências (necessária para o método de
     *transferência entre contas).*/
    public void menuContas(Scanner scanner, int ind, List<Agencia> agencias)
    {
        //"op" é a opção digitada pelo usuário
        int op = 0;
        //Laço repete até que o usuário digite "4" (Sair)
        while (op != 4)
        {
            System.out.println("MENU DO USUÁRIO");
            System.out.println("Digite a opção desejada.");
            System.out.println("1 - Cadastrar nova conta");
            System.out.println("2 - Acessar uma conta");
            System.out.println("3 - Listar suas contas");
            System.out.println("4 - Voltar ao menu principal (logout)");
            
            op = scanner.nextInt();
            scanner.nextLine();
            switch (op)
            {
                //Chama o método para cadastro de conta
                case 1: cadastrarConta(scanner, ind, agencias);
                        break;
                
                /*Chama o método para acesso de conta; caso a conta digitada exista,
                 *acessa o método do menu de transações da conta, da classe "Conta"*/
                case 2: int index = acessarConta(scanner);
                        if (index != -1)
                        {
                            this.contas.get(index).menuTransacoes(scanner, agencias);
                        }
                        break;
                //Chama o método para listagem de contas do usuário
                case 3: listarContas();
                        break;
                //Quebra o laço (opção de saída)
                case 4: break;
                //Caso de opção inválida
                default: System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}