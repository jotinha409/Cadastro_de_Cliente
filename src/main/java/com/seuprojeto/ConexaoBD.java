package com.seuprojeto;

import java.sql.*;

public class ConexaoBD {

    private static final String URL = "jdbc:mysql://localhost:3306/sistema_clientes";
    private static final String USUARIO = "root";
    private static final String SENHA = "Joao409@";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
