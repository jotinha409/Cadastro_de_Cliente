package com.seuprojeto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoBD {

    private static final Logger logger = Logger.getLogger(ConexaoBD.class.getName());

    private static String url;
    private static String usuario;
    private static String senha;

    static {
        try (InputStream input = ConexaoBD.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.severe("Arquivo config.properties não encontrado no classpath.");
            } else {
                Properties prop = new Properties();
                prop.load(input);

                url = prop.getProperty("db.url");
                usuario = prop.getProperty("db.user");
                senha = prop.getProperty("db.password");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao carregar config.properties", e);
        }
    }

    public static Connection conectar() throws SQLException {
        if (url == null || usuario == null || senha == null) {
            throw new SQLException("Configuração do banco incompleta.");
        }
        try {
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao conectar ao banco de dados", e);
            throw e;  // relança para quem chamar tratar a exceção
        }
    }
}
