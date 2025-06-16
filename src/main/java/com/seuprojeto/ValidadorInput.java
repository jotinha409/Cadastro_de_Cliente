package com.seuprojeto;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidadorInput {

    // Nome pode ter letras, acentos, hífen e espaço (apóstrofo sem escape)
    public static boolean isNomeValido(String nome) {
        return nome != null && nome.matches("[A-Za-zÀ-ÿ\\-' ]+");
    }

    // CPF só números, 11 dígitos
    public static boolean isCpfValido(String cpf) {
        if (cpf == null) return false;
        cpf = cpf.replaceAll("\\D", "");
        return cpf.length() == 11;
    }

    // Email válido por regex simples
    public static boolean isEmailValido(String email) {
        if (email == null) return false;
        Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
        return emailPattern.matcher(email).matches();
    }

    // Telefone números, entre 8 e 13 dígitos
    public static boolean isTelefoneValido(String telefone) {
        if (telefone == null) return false;
        telefone = telefone.replaceAll("\\D", "");
        return telefone.length() >= 8 && telefone.length() <= 13;
    }

    // Leitura de nome com validação e repetição até válido
    public static String lerNome(Scanner input) {
        while (true) {
            System.out.print("Nome completo: ");
            String nome = input.nextLine().trim();
            if (isNomeValido(nome)) {
                return nome;
            } else {
                System.out.println("Nome inválido! Use somente letras, espaços, hífen ou apóstrofo.");
            }
        }
    }

    // Leitura de CPF, checando duplicidade via DAO
    public static String lerCpf(Scanner input, ClienteDAO dao) {
        while (true) {
            System.out.print("CPF (11 dígitos, somente números): ");
            String cpf = input.nextLine().replaceAll("\\D", "");
            if (isCpfValido(cpf)) {
                if (!dao.cpfExiste(cpf)) {
                    return cpf;
                } else {
                    System.out.println("CPF já cadastrado! Insira outro.");
                }
            } else {
                System.out.println("CPF inválido! Deve conter exatamente 11 dígitos numéricos.");
            }
        }
    }

    // Leitura de data, tentando formatos comuns
    public static java.sql.Date lerData(Scanner input) {
        String[] formatos = { "dd-MM-yyyy", "dd/MM/yyyy", "ddMMyyyy" };

        while (true) {
            System.out.print("Data de nascimento (DD-MM-AAAA, DD/MM/AAAA ou DDMMAAAA): ");
            String dataStr = input.nextLine().trim();

            if (dataStr.isEmpty()) {
                System.out.println("Data de nascimento é obrigatória!");
                continue;
            }

            if (dataStr.contains(" ")) {
                System.out.println("Data não pode conter espaços. Tente novamente.");
                continue;
            }

            for (String formato : formatos) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
                    LocalDate localDate = LocalDate.parse(dataStr, formatter);
                    return java.sql.Date.valueOf(localDate);
                } catch (Exception e) {
                    // tenta próximo formato
                }
            }

            System.out.println("Formato inválido! Use DD-MM-AAAA, DD/MM/AAAA ou DDMMAAAA. Tente novamente.");
        }
    }

    // Leitura de email, checando duplicidade e formato
    public static String lerEmail(Scanner input, ClienteDAO dao) {
        Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");

        while (true) {
            System.out.print("Email: ");
            String email = input.nextLine().trim();

            if (email.isEmpty()) {
                System.out.println("Email é obrigatório!");
                continue;
            }

            if (email.contains(" ")) {
                System.out.println("Email não pode conter espaços. Tente novamente.");
                continue;
            }

            if (!emailPattern.matcher(email).matches()) {
                System.out.println("Email inválido! Tente novamente.");
                continue;
            }

            if (dao.emailExiste(email)) {
                System.out.println("Email já cadastrado! Insira outro.");
                continue;
            }

            return email;
        }
    }

    // Leitura de telefone
    public static String lerTelefone(Scanner input) {
        while (true) {
            System.out.print("Telefone (somente números, 8 a 13 dígitos): ");
            String telefone = input.nextLine().replaceAll("\\D", "");
            if (isTelefoneValido(telefone)) {
                return telefone;
            } else {
                System.out.println("Telefone inválido! Use somente números, entre 8 e 13 dígitos.");
            }
        }
    }

    // Parse data a partir de string limpa (usado para update)
    public static java.sql.Date parseData(String input) {
        try {
            input = input.replaceAll("[^0-9]", "");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            LocalDate localDate = LocalDate.parse(input, formatter);
            return java.sql.Date.valueOf(localDate);
        } catch (Exception e) {
            System.out.println("✖ Data inválida. Mantendo original.");
            return null;
        }
    }
}
