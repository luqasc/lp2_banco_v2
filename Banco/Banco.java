package Banco;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

//Classe principal; contém o método main
public class Banco
{
    //Lista de agências do banco
    List<Agencia> agencias;

    //Construtor
    public Banco()
    {
        agencias = new ArrayList<>();
    }
    
    //Imprime o menu inicial  
    public void exibirMenuInic()
    {
        System.out.println("MENU PRINCIPAL");
        System.out.println("Digite a opção desejada.");
        System.out.println("1 - Cadastrar nova agência");
        System.out.println("2 - Selecionar uma agência");
        System.out.println("3 - Listar todas as agências");
        System.out.println("4 - Sair");
    }

    //Cadastro de uma nova agência
    public void cadastraAgencia(Scanner scanner) 
    {
        String id, name;

        System.out.println("Digite um número de 3 dígitos para a nova agência:");
        id = scanner.nextLine();

        //Verificação de exceções
        try 
        {
            if (existeAgenciaId(id)) 
            { 
                //Método auxiliar para verificar duplicidade do código da agência
                throw new AgenciaIdJaExistenteException("O número da agência já existe: " + id);
            }
            if (formatoIdEhInadequado(id))
            {
                //Método auxiliar para verificar corretude do código da agência
                throw new AgenciaIdForaDeFormatoException("O número " + id + " da agência não está no formato correto.");
            }

            System.out.println("Digite um nome para a agência:");
            name = scanner.nextLine();

            if (existeAgenciaNome(name))
            {
                //Método auxiliar para verificar duplicidade do nome da agência
                throw new AgenciaNomeJaExistenteException("O nome da agência já está sendo usado: " + name);
            }

            Agencia agencia = new Agencia(id, name);
            agencias.add(agencia); // Adiciona a nova agência à lista
            System.out.println("Agência cadastrada com sucesso!");

        } 
        catch (AgenciaIdJaExistenteException | AgenciaNomeJaExistenteException | AgenciaIdForaDeFormatoException e) 
        {
            System.out.println("Erro ao cadastrar agência: " + e.getMessage());
        }
    }

    //Método auxiliar para verificar se uma agência já existe
    public boolean existeAgenciaId(String id) 
    {
        for (Agencia agencia : agencias) 
        {
            if (agencia.idAgencia.equals(id)) 
            {
                return true; //Retorna verdadeiro se o número já estiver na lista
            }
        }
        return false; //Não encontrou a agência
    }

    //Método auxiiliar para verificar se há agência com o mesmo nome
    public boolean existeAgenciaNome(String nome) 
    {
        for (Agencia agencia : agencias) 
        {
            if (agencia.nomeAgencia.equals(nome)) 
            {
                return true; //Retorna verdadeiro se o número já estiver na lista
            }
        }
        return false; //Não encontrou a agência
    }

    //Método auxiliar para verificar corretude do formato do ID da agência
    public boolean formatoIdEhInadequado(String id)
    {
        if (id.matches("\\d{3}"))
        {
            return false; //Formato ID é adequado
        }
        else
        {
            return true; //Formato ID é inadequado
        }
    }

    //Lista todas as agências atuais
    public void listaAgencias()
    {
        System.out.println("Lista de agências:");
        for (int i = 0; i < this.agencias.size(); i++)
        {
            System.out.println(agencias.get(i).nomeAgencia + " | Código: " + agencias.get(i).idAgencia);
        }
    }

    //Método para seleção da agência
    public int selecaoAgencia(Scanner scanner)
    {
        System.out.println("Digite o número da agência que deseja acessar.");
        String id = scanner.nextLine();
        boolean achou = false;
        int i;

        //Procura o número digitado entre as agências já cadastradas
        for (i = 0; i < this.agencias.size(); i++)
        {
            if (id.equals(agencias.get(i).idAgencia))
            {
                achou = true;
                break;
            }
        }

        //Se o número for encontrado, retorna seu índice na lista
        if (achou)
        {
            System.out.println("Agência acessada: " + agencias.get(i).nomeAgencia);
            return i;
        }
        //Se o número não foi encontrado, retorna ao menu inicial com -1.
        else
        {
            System.out.println("Agência não encontrada!");
            return -1;
        }
    }
    
    //Método principal (main), com o "switch" do menu principal
    public static void main(String[] args)
    {
        /*Instanciação de um objeto "banco" e do "scanner", que será
         *passado para todos os métodos que precisarem dele no futuro,
         *e será fechado somente ao final da execução, no corpo do main.*/

        Banco banco = new Banco();
        Scanner scanner = new Scanner(System.in);
        SistemaBanco sistemaBanco = new SistemaBanco();
        
        //Carrega os dados do arquivo CSV
        banco.agencias = sistemaBanco.carregarDados();
        
        //"op" é a opção digitada pelo usuário
        int op = 0;

        //O laço pára apenas quando o usuário digita "4" (Sair).
        while (op != 4)
        {
            banco.exibirMenuInic();
            op = scanner.nextInt();
            scanner.nextLine();
            
            switch (op)
            {
                //Chama o método de cadastro de agência
                case 1: banco.cadastraAgencia(scanner);
                        break;
                
                //Chama o método para seleção de agência
                case 2: int ind = banco.selecaoAgencia(scanner);
                        //Caso o número da agência seja encontrado, passa para o menu da agência
                        if (ind != -1)
                        {
                            banco.agencias.get(ind).menuAgencia(scanner, ind, banco.agencias);
                        }
                        break;
                
                //Chama o método para listagem de agências
                case 3: banco.listaAgencias();
                        break;
                
                //Sai do laço
                case 4: break;

                //Caso em que o usuário digita uma opção inválida
                default: System.out.println("Opção inválida. Tente novamente.");
            }
        }
        
        //Salva os dados
        sistemaBanco.salvarDados(banco.agencias);

        //Fechamento do scanner
        scanner.close();
    }
}