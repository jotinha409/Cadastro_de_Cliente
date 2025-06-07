package com.seuprojeto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // 1. Cadastrar Cliente (já tínhamos)
    public boolean cadastrarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome_completo, cpf, data_nascimento, email, telefone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNomeCompleto());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, cliente.getDataNascimento());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefone());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
            return false;
        }
    }

    // 2. Remover Cliente por ID
    public boolean removerCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao remover cliente: " + e.getMessage());
            return false;
        }
    }

    // 3. Atualizar Cliente (todos os campos menos criado_em/atualizado_em)
    public boolean atualizarCliente(Cliente cliente) {
        String sql = """
            UPDATE clientes
               SET nome_completo = ?,
                   cpf = ?,
                   data_nascimento = ?,
                   email = ?,
                   telefone = ?
             WHERE id = ?
            """;
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNomeCompleto());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, cliente.getDataNascimento());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefone());
            stmt.setInt(6, cliente.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // 4. Listar Clientes (retorna lista e/ou imprime)
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
            System.out.println("Erro ao listar clientes: " + e.getMessage());
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
}
