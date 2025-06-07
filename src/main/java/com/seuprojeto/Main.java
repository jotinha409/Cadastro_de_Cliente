package com.seuprojeto;

import java.util.*;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner (System.in);
        ClienteDAO dao = new ClienteDAO();
        int opt;

        do {

            System.out.println("\n ---- BEM VINDO AO MENU INTERATIVO ----\n");

            System.out.println("\t1. Cadastrar Cliente");
            System.out.println("\t2. Remover Cliente");
            System.out.println("\t3. Atualizar Cliente");
            System.out.println("\t4. Listar Clientes");
            System.out.println("\t5. Sair");
            System.out.println("\t6. Testar conexão");

            System.out.println("\n\tSELECIONE UMA OPÇÃO\n");

            opt = input.nextInt();
            input.nextLine();

            switch (opt){
                case 1 -> {
                    Cliente c = lerCliente(input);
                    System.out.println(
                            dao.cadastrarCliente(c)
                                    ? "✔ Cliente cadastrado com sucesso!"
                                    : "✖ Falha ao cadastrar cliente."
                    );
                }

                case 2 -> {  // Remover
                    System.out.print("Digite o ID do cliente a remover: ");
                    int idRem = input.nextInt();
                    input.nextLine();
                    System.out.println(
                            dao.removerCliente(idRem)
                                    ? "✔ Cliente removido."
                                    : "✖ Falha ao remover cliente."
                    );
                }
                case 3 -> {  // Atualizar
                    System.out.print("Digite o ID do cliente a atualizar: ");
                    int id = input.nextInt();
                    input.nextLine();
                    Cliente cUpd = lerCliente(input);
                    cUpd.setId(id);
                    System.out.println(
                            dao.atualizarCliente(cUpd)
                                    ? "✔ Cliente atualizado."
                                    : "✖ Falha ao atualizar cliente."
                    );
                }
                case 4 -> {  // Listar
                    List<Cliente> todos = dao.listarClientes();
                    dao.imprimirClientes(todos);
                }
                case 5 -> System.out.println("Saindo...");

                case 6 ->  TesteConexao.testarConexao();

                default -> System.out.println("Opção inválida...");
            }

        }while (opt != 5);
    }

    private static Cliente lerCliente(Scanner input) {
        Cliente c = new Cliente();
        System.out.print("Nome completo: ");
        c.setNomeCompleto(input.nextLine());
        System.out.print("CPF (11 dígitos): ");
        c.setCpf(input.nextLine());
        System.out.print("Data de nascimento (AAAA-MM-DD): ");
        c.setDataNascimento(Date.valueOf(input.nextLine()));  // funciona agora
        System.out.print("Email: ");
        c.setEmail(input.nextLine());
        System.out.print("Telefone: ");
        c.setTelefone(input.nextLine());
        return c;
    }
}
