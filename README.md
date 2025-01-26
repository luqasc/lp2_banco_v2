# Sistema Bancário em Java - Versão 2.0
Autor: Lucas Cunha de Azevedo

## Descrição

Este é um sistema bancário simples desenvolvido em Java, que simula a gestão de contas bancárias de diferentes tipos (Conta Salário, Conta Poupança e Conta Corrente), com funcionalidades como depósito, saque, transferência e consulta de saldo. O sistema também inclui tratamentos de exceções personalizados para garantir a integridade dos dados e a consistência das operações, além de manter informações em arquivos CSV.

## Funcionalidades

- **Contas bancárias**: O sistema suporta três tipos de contas: 
  - **Conta Corrente**: Permite consultas de saldo, depósitos, transferências, e consulta de taxa de manutenção anual.
  - **Conta Poupança**: Permite consultas de saldo, depósitos, transferências e simulações de rendimento
  - **Conta Salário**: Limite de saques (máximo de 3 saques por execução); depósitos e transferências não são permitidos.

- **Agências bancárias**: O sistema permite que cada usuário possua diversas contas, de tipos diferentes, se assim desejado.

- **Agências bancárias**: O sistema permite a criação e gerenciamento de agências bancárias, com validações para evitar IDs duplicados ou inválidos.

- **Tratamento de Exceções**: O código implementa exceções personalizadas para garantir que o sistema trate erros relacionados a IDs de contas, CPF, e outros dados essenciais.

- **Registro em arquivo**: O sistema guarda os dados de suas agências, usuários e contas em um arquivo CSV. Portanto, não há perda de informações ao fim da execução.

## Estrutura do Projeto

O projeto é estruturado da seguinte forma:

### Classe `Banco`

Classe principal; contém o método "main" e o menu principal para acesso, cadastro e gerenciamento de agências.

### Classe `Agencia`

Classe relativa a cada agência. Contém o menu da agência, com opções para cadastro de usuários, login e listagem de usuários.

### Classe `Usuarios`

Classe referente a cada usuário. Contém o menu do usuário, com opções para cadastro, acesso e listagem de contas.

### Classe `Contas`

Classe base relativa às contas dos usuários. Conta com métodos para transações: saques, depósitos, transferências, consultas de saldo, etc.
Conta com três classes filhas, que sobrescrevem alguns de seus métodos e adicionam outros atributos e funções:

- **Classe `ContaCorrente`**: Herda de `Conta` e inclui uma taxa de manutenção anual (fixada em R$ 216,00) que pode ser consultada. Terminam sempre com dígito "-1".
- **Classe `ContaPoupanca`**: Herda de `Conta` e inclui funcionalidades específicas de rendimento e simulação de rendimento (fixado em 0,5% a.m.). Terminam sempre com dígito "-2".
- **Classe `ContaSalario`**: Herda de `Conta`, implementa a lógica de saque com limite (máximo de 3 saques por execução) e elimina a possibilidade de depósitos e transferências. Terminam sempre com dígito "-3".

### Classe `SistemaBanco`

Classe para gerenciamento do arquivo CSV, que contém todos os dados das agências, usuários e contas do banco.

### Exceções Personalizadas

- **AgenciaIdForaDeFormatoException**: Para quando o ID de uma agência não está no formato correto (três dígitos).
- **AgenciaIdJaExistenteException**: Para quando uma agência com o mesmo ID já existe.
- **AgenciaNomeJaExistenteException**: Para quando o nome da agência já está em uso.
- **ContaIdForaDeFormatoException**: Para quando o ID de uma conta não está no formato correto (x.xxx).
- **ContaIdJaExistenteException**: Para quando uma conta com o mesmo ID já existe.
- **CpfForaDeFormatoException**: Para quando o CPF fornecido não está no formato correto (xxx.xxx.xxx-xx).
- **CpfJaExistenteException**: Para quando o CPF já está cadastrado no sistema.
- **SenhaForaDeFormatoException**: Para quando a senha fornecida não está no formato correto (seis dígitos).

---

## Como Usar

### 1. Clonar o Repositório

Clone o repositório com a pasta "Banco" e o arquivo "dados_banco.csv" para sua pasta de trabalho.

### 2. Compilar e Executar

Para compilar e executar o projeto, use o seguinte comando no terminal:
```bash
javac Banco/*.java
java Banco.Banco
```
Certifique-se que o terminal está localizado no diretório em que a pasta "Banco" e o arquivo CSV foram instalados.

### 3. Interação com o Sistema
- **Menu Principal**

  Neste menu, é possível cadastrar novas agências, listar as agências existentes, ou acessar uma agência.
  Duas agências já foram criadas por padrão:
  
  - Agência Central | Código: 123
  - Agência Norte | Código: 456
  
  O usuário pode cadastrar uma nova agência ou acessar uma dessas duas, utilizando os códigos 123 ou 456.

- **Menu da Agência**
  
  Esse menu é exibido ao acessar uma das agências; ele permite cadastrar novos usuários, listar os usuários de cada agência, e fazer login com o CPF e a senha de um usuário.
  Alguns usuários foram cadastrados de antemão, e estão no arquivo CSV.
  
  Para a agência Central (123):
  
  - Lucas | CPF: 000.000.000-01 | Senha: 111111
  - Jorge | CPF: 000.000.000-02 | Senha: 222222
  
  Para a agência Central (456):
  
  - Arthur | CPF: 000.000.000-03 | Senha: 333333
  - Matheus | CPF: 000.000.000-04 | Senha: 444444
  
  Para testar as funcionalidades do sistema, esses usuários podem ser utilizados, ou podem ser criados novos.

- **Menu do Usuário**
  
  Exibido após o processo de login, o menu do usuário permite que o cliente crie novas contas, liste-as ou acesse uma delas.
  
  Cada um dos usuários pré-cadastrados tem, já, suas contas:
  
  - Lucas
    - Conta 1.000-1 (Corrente) | Saldo: R$ 300,00
    - Conta 1.002-3 (Salário) | Saldo: R$ 100,00
  - Jorge
    - Conta 2.000-1 (Corrente) | Saldo: R$ 450,00
    - Conta 2.001-3 (Salário) | Saldo: R$ 40,00
  - Arthur
    - Conta 3.000-1 (Corrente) | Saldo: R$ 427,50
    - Conta 3.001-2 (Poupança) | Saldo: R$ 0,50
  - Matheus
    - Conta 4.000-1 (Corrente) | Saldo: R$ 572,50
    - Conta 4.001-2 (Poupança) | Saldo: R$ 4.000,00
  
  Essas contas podem ser utilizadas pelo usuário para testar as transações possíveis no sistema.

- **Menu da Conta**
  
  Depois que o código de uma conta é digitado e seu acesso é liberado, o usuário tem acesso a esse menu.
  O menu da conta difere segundo o tipo de cada conta (corrente, poupança ou salário), como anteriormente descrito.
  
  Obs.: É importante que valores decimais sejam digitados utilizando vírgula, em vez de ponto. Por exemplo, "49,50".

- **Arquivo dados_banco.csv**
  
  O arquivo dados_banco.csv mantém todas as informações do banco, para além do tempo de execução do programa.
  
  Seu formato obedece o seguinte padrão:
  - CÓDIGO_AGÊNCIA,NOME_AGÊNCIA,CPF_USUARIO,NOME_USUARIO,SENHA_USUARIO,ID_CONTA,SALDO_CONTA
  - 123,Central,000.000.000-01,Lucas,111111,1.000-1,300.0
  - 123,Central,000.000.000-01,Lucas,111111,1.002-3,100.0
  - 123,Central,000.000.000-02,Jorge,222222,2.000-1,450.0
  - 123,Central,000.000.000-02,Jorge,222222,2.001-3,40.0
  - 456,Norte,000.000.000-03,Arthur,333333,3.000-1,427.5
  - 456,Norte,000.000.000-03,Arthur,333333,3.001-2,0.5
  - 456,Norte,000.000.000-04,Matheus,444444,4.000-1,572.5
  - 456,Norte,000.000.000-04,Matheus,444444,4.001-2,4000.0
  
  Essas informações são lidas na inicialização do programa, e após sua finalização os novos dados são salvos.

### 4. Exceções
As exceções personalizadas são lançadas automaticamente em caso de erros, como IDs ou CPFs inválidos ou repetidos, e o sistema informa o erro de forma clara.

---

## Melhorias em relação à Versão 1.0
- Implementação da funcionalidade de agências;
- Adição de diferentes tipos de contas (corrente, poupança, salário), com novas funcionalidades;
- Armazenamento permanente de dados através de arquivo CSV;
- Tratamento personalizado e mais restrito de exceções;

---

## Perspectivas futuras

1. Por enquanto, o código armazena em arquivo apenas agências e usuários que têm contas cadastradas. Pode-se pensar em formas de cadastrar agências e usuários que sejam mantidos no arquivo CSV, mesmo que não tenham contas ainda.
2. Pode-se implementar tratamento de exceções na digitação de valores para saque, saldo, transferência, etc., de forma a garantir que os usuários digitem números em formato válido.
3. A remoção de usuários, agências ou contas, ou a mudança de nomes e senhas, ainda não é possível através do programa em si. Por enquanto, para isso, pode-se manipular o arquivo CSV.

## Obrigado!
