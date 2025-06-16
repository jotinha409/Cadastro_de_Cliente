package com.seuprojeto;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {
    public static void testarConexao() {
        System.out.println("Testando conexão com o Banco de Dados...");

        try (Connection conn = ConexaoBD.conectar()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexão com Banco de Dados realizada com sucesso!");
            } else {
                System.out.println("Conexão com o Banco de Dados falhou");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        testarConexao();
    }
}
