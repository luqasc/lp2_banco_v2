package Banco;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class SistemaBanco {

    //Método para salvar as agências e seus dados no CSV
    public void salvarDados(List<Agencia> agencias) 
    {
        System.out.println("Salvando dados...");
        try (FileWriter writer = new FileWriter("dados_banco.csv")) 
        {
            //Escrever as agências, usuários, contas e saldos no CSV
            for (Agencia agencia : agencias) 
            {
                for (Usuario usuario : agencia.usuarios) 
                {
                    for (Conta conta : usuario.getContas()) 
                    {
                        //Escreve as informações no formato CSV
                        String linha = agencia.idAgencia + "," + agencia.nomeAgencia + "," + usuario.getCpf() + "," + usuario.getNome() + "," + usuario.getSenha() + "," + conta.getId() + "," + conta.getSaldo() + "\n";
                        writer.write(linha);
                    }
                }
            }
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    //Método para carregar os dados do arquivo CSV
    public List<Agencia> carregarDados() 
    {
        System.out.println("Carregando dados...");
        List<Agencia> agencias = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dados_banco.csv"))) 
        {
            String linha;
            while ((linha = reader.readLine()) != null) 
            {
                //Ignora linhas em branco
                if(linha.trim().isEmpty())
                {
                    continue;
                }

                //Dividir cada linha pelos campos do CSV
                String[] campos = linha.split(",");

                //Verifica se a linha tem o número esperado de campos
                if (campos.length < 7)
                {
                    System.out.println("Formato inválido na linha: " + linha);
                    continue;
                }

                //Associa cada campo a uma informação
                String idAgencia = campos[0];
                String nomeAgencia = campos[1];
                String cpfUsuario = campos[2];
                String nomeUsuario = campos[3];
                String senhaUsuario = campos[4];
                String idConta = campos[5];
                float saldo = Float.parseFloat(campos[6]);

                //Procura pela agência correspondente
                Agencia agencia = encontrarAgencia(agencias, idAgencia);
                if (agencia == null) 
                {
                    agencia = new Agencia(idAgencia, nomeAgencia);
                    agencias.add(agencia);
                }

                //Procura usuário e adiciona-o se ele já não estiver no registro
                Usuario usuario = encontrarUsuario(agencia.usuarios, cpfUsuario);
                if (usuario == null) 
                {
                    usuario = new Usuario(nomeUsuario, cpfUsuario, senhaUsuario);
                    agencia.usuarios.add(usuario);
                }

                //Adiciona a conta à sua devida classe, a depender do seu tipo
                if (idConta.charAt(idConta.length() - 1) == '1')
                {
                    ContaCorrente conta = new ContaCorrente(saldo, idConta, 216);
                    usuario.getContas().add(conta);
                }
                else if (idConta.charAt(idConta.length() - 1) == '2')
                {
                    ContaPoupanca conta = new ContaPoupanca(saldo, idConta, 0.005);
                    usuario.getContas().add(conta);
                }
                else
                {
                    ContaSalario conta = new ContaSalario(saldo, idConta);
                    usuario.getContas().add(conta);
                }
            }
            System.out.println("Dados carregados com sucesso!");
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        return agencias;
    }

    // Método auxiliar para encontrar a agência pelo ID
    private Agencia encontrarAgencia(List<Agencia> agencias, String id) 
    {
        for (Agencia agencia : agencias) 
        {
            if (agencia.idAgencia.equals(id)) 
            {
                return agencia;
            }
        }
        return null;
    }

    // Método auxiliar para encontrar um usuário por seu CPF
    private Usuario encontrarUsuario(List<Usuario> usuarios, String cpf) 
    {
        for (Usuario usuario : usuarios) 
        {
            if (usuario.getCpf().equals(cpf)) 
            {
                return usuario;
            }
        }
        return null;
    }
}