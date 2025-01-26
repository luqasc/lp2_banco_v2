package Banco;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

//Classe principal; contém o método main
public class Agencia 
{
    //Lista de usuários da agência e atributos da agência
    List<Usuario> usuarios;
    String idAgencia, nomeAgencia;

    //Construtor
    public Agencia(String _idAgencia, String _nomeAgencia)
    {
        usuarios = new ArrayList<>();
        idAgencia = _idAgencia;
        nomeAgencia = _nomeAgencia;
    }
    
    //Imprime o menu inicial  
    public void exibirMenuInic()
    {
        System.out.println("MENU DA AGÊNCIA");
        System.out.println("Digite a opção desejada.");
        System.out.println("1 - Cadastrar novo usuário");
        System.out.println("2 - Fazer login");
        System.out.println("3 - Listar todos os usuários");
        System.out.println("4 - Sair");
    }

    //Cadastro de um novo usuário, com nome, CPF e senha
    public void cadastraUsuario(Scanner scanner, List<Agencia> agencias)
    {
        String name, id, pswrd;

        System.out.println("Digite o nome do usuário.");
        name = scanner.nextLine();
        
        try
        {
            System.out.println("Digite o CPF do usuário (formato xxx.xxx.xxx-xx).");
            id = scanner.nextLine();

            //Tratamento de exceção: Formato inadequado de CPF
            if (formatoCpfEhInadequado(id))
            {
                throw new CpfForaDeFormatoException("O CPF digitado, " + id + ", não está no formato xxx.xxx.xxx-xx. Tente novamente.");
            }
            //Tratamento de exceção: CPF já cadastrado
            if (CpfJaExistente(id, agencias))
            {
                throw new CpfJaExistenteException("O CPF digitado, " + id + ", já está cadastrado. Tente novamente.");
            }

            System.out.println("Digite uma senha de 6 dígitos para o usuário.");
            pswrd = scanner.nextLine();
            
            //Tratamento de exceção: Formato inadequado de senha
            if (formatoSenhaEhInadequado(pswrd))
            {
                throw new SenhaForaDeFormatoException("A senha digitada não tem 6 dígitos.");
            }

            //Adiciona-se o usuário à lista da agência
            Usuario user = new Usuario(name, id ,pswrd);
            usuarios.add(user);
            System.out.println("Usuário cadastrado com sucesso!");
        }
        catch (CpfForaDeFormatoException | CpfJaExistenteException | SenhaForaDeFormatoException e)
        {
            //Mensagem de erro para exceções
            System.out.println("Erro ao cadastrar agência: " + e.getMessage());
        }
    }

    //Método auxiliar para checar corretude do CPF do usuário
    public boolean formatoCpfEhInadequado(String cpf)
    {
        if (cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"))
        {
            return false; //Formato CPF é adequado
        }
        else
        {
            return true; //Formato CPF é inadequado
        }
    }

    //Método auxiliar para checar se CPF do usuário já foi utilizado
    public boolean CpfJaExistente(String cpf, List<Agencia> agencias)
    {
        for (int i = 0; i < agencias.size(); i++)
        {
            for (int j = 0; j < agencias.get(i).usuarios.size(); j++)
            {
                if (cpf.equals(agencias.get(i).usuarios.get(j).getCpf()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    //Método auxiliar para checar corretude da senha do usuário
    public boolean formatoSenhaEhInadequado(String senha)
    {
        if (senha.matches("\\d{6}"))
        {
            return false; //Formato da senha é adequado
        }
        else
        {
            return true; //Formato da senha é inadequado
        }
    }

    //Lista todos os usuários atuais
    public void listaUsuarios()
    {
        System.out.println("Lista de usuários:");
        for (int i = 0; i < this.usuarios.size(); i++)
        {
            System.out.println(usuarios.get(i).getNome() + " | CPF: " + usuarios.get(i).getCpf());
        }
    }

    //Faz o login do usuário, pedindo CPF e senha, e checando com os dados armazenados na memória
    public int fazLogin(Scanner scanner)
    {
        System.out.println("Digite o CPF do usuário.");
        String cpf = scanner.nextLine();
        boolean achou = false;
        int i;

        //Procura o CPF digitado entre os usuários já cadastrados
        for (i = 0; i < this.usuarios.size(); i++)
        {
            if (cpf.equals(usuarios.get(i).getCpf()))
            {
                achou = true;
                break;
            }
        }

        //Se o CPF for encontrado, pede a senha
        if (achou)
        {
            System.out.println("Digite a senha.");
            String senha = scanner.nextLine();

            //Checa se a senha é igual à cadastrada pelo usuário
            if (senha.equals(usuarios.get(i).getSenha()))
            {
                System.out.println("Bem-vindo(a), " + usuarios.get(i).getNome());
                return i;
            }
            //Se não for, retorna ao menu incial.
            else
            {
                System.out.println("Senha incorreta!");
                return -1;
            }
        }
        //Se o CPF não foi encontrado, retorna ao menu inicial.
        else
        {
            System.out.println("Usuário não encontrado!");
            return -1;
        }
    }
    
    //Método principal (main), com o "switch" do menu principal
    public void menuAgencia(Scanner scanner, int ind, List<Agencia> agencias)
    {        
        //"op" é a opção digitada pelo usuário
        int op = 0;

        //O laço pára apenas quando o usuário digita "4" (Sair).
        while (op != 4)
        {
            exibirMenuInic();
            op = scanner.nextInt();
            scanner.nextLine();
            
            switch (op)
            {
                //Chama o método de cadastro de usuário
                case 1: cadastraUsuario(scanner, agencias);
                        break;
                
                //Chama o método para login 
                case 2: int index = fazLogin(scanner);
                        //Caso o login tenha sucesso, passa para o menu de contas do usuário
                        if (index != -1)
                        {
                            this.usuarios.get(index).menuContas(scanner, index, agencias);
                        }
                        break;
                
                //Chama o método para listagem de usuários
                case 3: listaUsuarios();
                        break;
                
                //Sai do laço
                case 4: break;

                //Caso em que o usuário digita uma opção inválida
                default: System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}