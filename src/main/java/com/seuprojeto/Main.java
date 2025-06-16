package com.seuprojeto;

import java.util.Scanner;
import java.util.List;

public class Main {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_ORANGE = "\u001B[38;5;208m";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ClienteDAO dao = new ClienteDAO();
        ClienteService clienteService = new ClienteService(dao);

        int opt;

        do {
            System.out.println(ANSI_PURPLE + "BEM-VINDO AO MENU INTERATIVO" + ANSI_RESET);

            System.out.println(ANSI_ORANGE + " \n SPA SPACE WELLNESS " + ANSI_RESET);

            System.out.println();
            System.out.println(ANSI_GREEN + "\t1. Cadastrar Cliente" + ANSI_RESET);
            System.out.println(ANSI_RED + "\t2. Remover Cliente" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "\t3. Atualizar Cliente" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "\t4. Listar Clientes" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "\t5. Sair" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "\t6. Testar conexão" + ANSI_RESET);

            System.out.println(ANSI_ORANGE + "\n\tSELECIONE UMA OPÇÃO:\n" + ANSI_RESET);

            opt = input.nextInt();
            input.nextLine();

        input.close();

            switch (opt) {
                case 1 -> {
                    try {
                        boolean sucesso = clienteService.cadastrarCliente(lerCliente(input, dao));
                        if (sucesso) {
                            System.out.println("✔ Cliente cadastrado com sucesso!");
                        }
                    } catch (Exception e) {
                        System.out.println("✖ Erro: " + e.getMessage());
                    }
                }
                case 2 -> dao.removerClienteInterativo(input);
                case 3 -> {
                    List<Cliente> todos = dao.listarClientes();
                    dao.imprimirClientes(todos);
                    System.out.print("Digite o ID do cliente a atualizar: ");
                    int idUpd = input.nextInt();
                    input.nextLine();
                    dao.atualizarClienteInterativo(input, idUpd);
                }
                case 4 -> {
                    List<Cliente> todos = dao.listarClientes();
                    dao.imprimirClientes(todos);
                }
                case 5 -> System.out.println("Saindo...");
                case 6 -> TesteConexao.testarConexao();
                default -> System.out.println("Opção inválida...");
            }

        } while (opt != 5);

        input.close();
    }

    private static Cliente lerCliente(Scanner input, ClienteDAO dao) {
        String nome = ValidadorInput.lerNome(input);
        String cpf = ValidadorInput.lerCpf(input, dao);
        java.sql.Date dataNascimento = ValidadorInput.lerData(input);
        String email = ValidadorInput.lerEmail(input);
        String telefone = ValidadorInput.lerTelefone(input);

        return new Cliente(nome, cpf, dataNascimento, email, telefone);
    }
}
