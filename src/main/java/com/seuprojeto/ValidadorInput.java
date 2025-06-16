package com.seuprojeto;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidadorInput {

    public static boolean isNomeValido(String nome) {
        return nome != null && nome.matches("[A-Za-zÀ-ÿ ]+");
    }

    public static boolean isCpfValido(String cpf) {
        if (cpf == null) return false;
        cpf = cpf.replaceAll("\\D", "");
        return cpf.length() == 11;
    }

    public static boolean isEmailValido(String email) {
        if (email == null) return false;
        Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
        return emailPattern.matcher(email).matches();
    }

    public static boolean isTelefoneValido(String telefone) {
        if (telefone == null) return false;
        telefone = telefone.replaceAll("\\D", "");
        return telefone.length() >= 8 && telefone.length() <= 13;
    }


    public static String lerNome(Scanner input) {
        while (true) {
            System.out.print("Nome completo: ");
            String nome = input.nextLine().trim();
            if (isNomeValido(nome)) {
                return nome;
            } else {
                System.out.println("Nome inválido! Use somente letras e espaços.");
            }
        }
    }

    public static String lerCpf(Scanner input, ClienteDAO dao) {
        while (true) {
            System.out.print("CPF (11 dígitos): ");
            String cpf = input.nextLine().replaceAll("\\D", "");
            if (isCpfValido(cpf)) {
                if (!dao.cpfExiste(cpf)) {
                    return cpf;
                } else {
                    System.out.println("CPF já cadastrado! Insira outro.");
                }
            } else {
                System.out.println("CPF inválido! Deve ter 11 dígitos numéricos.");
            }
        }
    }

    public static java.sql.Date lerData(Scanner input) {
        String[] formatos = { "dd-MM-yyyy", "dd/MM/yyyy", "ddMMyyyy" };

        while (true) {
            System.out.print("Data de nascimento (ENTER para deixar vazio): ");
            String dataStr = input.nextLine().trim();

            if (dataStr.isEmpty()) {
                return null; // aceita vazio
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

    public static String lerEmail(Scanner input) {
        Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
        while (true) {
            System.out.print("Email (ENTER para deixar vazio): ");
            String email = input.nextLine().trim();
            if (email.isEmpty()) {
                return null; // aceita vazio
            }
            if (emailPattern.matcher(email).matches()) {
                return email;
            } else {
                System.out.println("Email inválido! Tente novamente.");
            }
        }
    }

    public static String lerTelefone(Scanner input) {
        while (true) {
            System.out.print("Telefone: ");
            String telefone = input.nextLine().replaceAll("\\D", "");
            if (isTelefoneValido(telefone)) {
                return telefone;
            } else {
                System.out.println("Telefone inválido! Use somente números, entre 8 e 13 dígitos.");
            }
        }
    }

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
