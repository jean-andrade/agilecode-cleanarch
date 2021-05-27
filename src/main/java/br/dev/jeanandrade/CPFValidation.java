package br.dev.jeanandrade;

import java.util.List;

class CPFValidation {
    private boolean contemSequenciaDeNumerosIguais(String cpf) {
        char primeiroCaracter = cpf.charAt(0);
        return cpf.chars().allMatch(valor -> valor == primeiroCaracter);
    }

    private boolean naoContemOnzeDigitos(String cpf) {
        return cpf.length() != 11;
    }

    private String removerCaracteresNaoNumericos(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    public boolean validate(String cpf) {
        String CPF = removerCaracteresNaoNumericos(cpf);
        if (contemSequenciaDeNumerosIguais(CPF)) {
            return false;
        }
        if (naoContemOnzeDigitos(CPF)) {
            return false;
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return (true);
            else
                return (false);
        } catch (Exception erro) {
            return (false);
        }
    }
}
