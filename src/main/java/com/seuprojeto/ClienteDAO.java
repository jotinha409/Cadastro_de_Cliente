package com.seuprojeto;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO {

    private static final Logger logger = Logger.getLogger(ClienteDAO.class.getName());

    public boolean cadastrarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome_completo, cpf, data_nascimento, email, telefone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNomeCompleto());
            stmt.setString(2, cliente.getCpf());

            if (cliente.getDataNascimento() != null) {
                stmt.setDate(3, cliente.getDataNascimento());
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }

            if (cliente.getEmail() != null) {
                stmt.setString(4, cliente.getEmail());
            } else {
                stmt.setNull(4, java.sql.Types.VARCHAR);
            }

            stmt.setString(5, cliente.getTelefone());

            if (emailExiste(cliente.getEmail())) {
                System.out.println("E-mail já cadastrado.");
                return false;
            }

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao inserir cliente", e);
            return false;
        }
    }

    public boolean emailExiste(String email) {
        String sql = "SELECT id FROM clientes WHERE email = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true se encontrou

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao verificar existência do e-mail: " + email, e);
            return false;
        }
    }

    public boolean removerCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao remover cliente", e);
            return false;
        }
    }

    public void removerClienteInterativo(Scanner input) {
        System.out.print("Digite o ID do cliente a remover: ");
        String linha = input.nextLine().trim();

        int id;
        try {
            id = Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            System.out.println("✖ ID inválido.");
            return;
        }

        Cliente cliente = buscarClientePorId(id);
        if (cliente == null) {
            System.out.println("✖ Cliente não encontrado.");
            return;
        }

        System.out.println("Você tem certeza que quer remover o cliente: " + cliente.getNomeCompleto() + " ? (S/N)");
        String resposta = input.nextLine().trim().toUpperCase();

        if (resposta.equals("S") || resposta.equals("SIM")) {
            if (removerCliente(id)) {
                System.out.println("✔ Cliente removido com sucesso!");
            } else {
                System.out.println("✖ Erro ao remover cliente.");
            }
        } else {
            System.out.println("Remoção cancelada.");
        }
    }


    public Cliente buscarClientePorId(int id) {
        String sql = "SELECT id, nome_completo, cpf, data_nascimento, email, telefone FROM clientes WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNomeCompleto(rs.getString("nome_completo"));
                c.setCpf(rs.getString("cpf"));
                c.setDataNascimento(rs.getDate("data_nascimento"));
                c.setEmail(rs.getString("email"));
                c.setTelefone(rs.getString("telefone"));
                return c;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao buscar cliente por ID: " + id, e);
        }
        return null;
    }

    public boolean atualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nome_completo = ?, cpf = ?, data_nascimento = ?, email = ?, telefone = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNomeCompleto());
            stmt.setString(2, cliente.getCpf());

            if (cliente.getDataNascimento() != null) {
                stmt.setDate(3, cliente.getDataNascimento());
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }

            if (cliente.getEmail() != null) {
                stmt.setString(4, cliente.getEmail());
            } else {
                stmt.setNull(4, java.sql.Types.VARCHAR);
            }

            stmt.setString(5, cliente.getTelefone());
            stmt.setInt(6, cliente.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar cliente ID: " + cliente.getId(), e);
            return false;
        }
    }

    public void atualizarClienteInterativo(Scanner input, int id) {
        Cliente cliente = buscarClientePorId(id);

        if (cliente == null) {
            System.out.println("✖ Cliente não encontrado.");
            return;
        }

        System.out.println("\nAtualizando cliente: " + cliente.getNomeCompleto());
        System.out.println("Para manter o campo atual, pressione ENTER sem digitar nada.");

        // Atualiza nome
        String nome = lerNomeValido(input, cliente.getNomeCompleto());
        cliente.setNomeCompleto(nome);

        // Atualiza CPF
        String cpf = lerCpfValido(input, cliente.getCpf());
        cliente.setCpf(cpf);

        // Atualiza data de nascimento
        java.sql.Date dataNascimento = lerDataValida(input, cliente.getDataNascimento());
        cliente.setDataNascimento(dataNascimento);

        // Atualiza email
        String email = lerEmailValido(input, cliente.getEmail());
        cliente.setEmail(email);

        // Atualiza telefone
        String telefone = lerTelefoneValido(input, cliente.getTelefone());
        cliente.setTelefone(telefone);

        if (atualizarCliente(cliente)) {
            System.out.println("✔ Cliente atualizado com sucesso!");
        } else {
            System.out.println("✖ Erro ao atualizar cliente.");
        }
    }

    // Métodos auxiliares para validação interativa

    private String lerNomeValido(Scanner input, String valorAtual) {
        while (true) {
            System.out.print("Nome (" + valorAtual + "): ");
            String entrada = input.nextLine().trim();
            if (entrada.isEmpty()) return valorAtual;
            if (ValidadorInput.isNomeValido(entrada)) return entrada;
            System.out.println("Nome inválido! Tente novamente.");
        }
    }

    private String lerCpfValido(Scanner input, String valorAtual) {
        while (true) {
            System.out.print("CPF (" + valorAtual + "): ");
            String entrada = input.nextLine().replaceAll("\\D", "");
            if (entrada.isEmpty()) return valorAtual;
            if (!ValidadorInput.isCpfValido(entrada)) {
                System.out.println("CPF inválido! Deve conter 11 dígitos. Tente novamente.");
                continue;
            }
            if (!entrada.equals(valorAtual) && cpfExiste(entrada)) {
                System.out.println("CPF já cadastrado. Tente outro.");
                continue;
            }
            return entrada;
        }
    }

    private java.sql.Date lerDataValida(Scanner input, java.sql.Date valorAtual) {
        while (true) {
            System.out.print("Data de nascimento (" + (valorAtual != null ? valorAtual.toString() : "vazio") + "): ");
            String entrada = input.nextLine().trim();
            if (entrada.isEmpty()) return valorAtual;
            java.sql.Date data = ValidadorInput.parseData(entrada);
            if (data != null) return data;
            System.out.println("Data inválida! Use formato YYYY-MM-DD. Tente novamente.");
        }
    }

    private String lerEmailValido(Scanner input, String valorAtual) {
        while (true) {
            System.out.print("Email (" + (valorAtual != null ? valorAtual : "vazio") + "): ");
            String entrada = input.nextLine().trim();
            if (entrada.isEmpty()) return valorAtual;
            if (!ValidadorInput.isEmailValido(entrada)) {
                System.out.println("Email inválido! Tente novamente.");
                continue;
            }
            if (!entrada.equals(valorAtual) && emailExiste(entrada)) {
                System.out.println("Email já cadastrado. Tente outro.");
                continue;
            }
            return entrada;
        }
    }

    private String lerTelefoneValido(Scanner input, String valorAtual) {
        while (true) {
            System.out.print("Telefone (" + valorAtual + "): ");
            String entrada = input.nextLine().replaceAll("\\D", "");
            if (entrada.isEmpty()) return valorAtual;
            if (ValidadorInput.isTelefoneValido(entrada)) return entrada;
            System.out.println("Telefone inválido! Deve conter apenas números, entre 8 e 13 dígitos. Tente novamente.");
        }
    }


    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, nome_completo, cpf, data_nascimento, email, telefone FROM clientes";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNomeCompleto(rs.getString("nome_completo"));
                c.setCpf(rs.getString("cpf"));
                c.setDataNascimento(rs.getDate("data_nascimento"));
                c.setEmail(rs.getString("email"));
                c.setTelefone(rs.getString("telefone"));
                lista.add(c);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar clientes", e);
        }
        return lista;
    }

    public void imprimirClientes(List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            System.out.println("\n--- Lista de Clientes ---");
            for (Cliente c : clientes) {
                System.out.printf(
                        "ID: %d | Nome: %s | CPF: %s | Nascimento: %s | Email: %s | Fone: %s%n",
                        c.getId(),
                        c.getNomeCompleto(),
                        c.getCpf(),
                        c.getDataNascimento(),
                        c.getEmail(),
                        c.getTelefone()
                );
            }
        }
    }

    public boolean cpfExiste(String cpf) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE cpf = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao verificar existência do CPF: " + cpf, e);
        }
        return false;
    }

}
