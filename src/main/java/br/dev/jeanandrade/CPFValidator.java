package br.dev.jeanandrade;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class CPFValidator {
  private final List<Integer> factors = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);

  private boolean hasSameNumberRecurring(String cpf) {
    char firstDigit = cpf.charAt(0);
    return cpf.chars().allMatch(digit -> digit == firstDigit);
  }

  private boolean hasInvalidLength(String cpf) {
    return cpf.length() != 11;
  }

  private String getOnlyNumbers(String cpf) {
    return cpf.replaceAll("\\D", "");
  }

  private int calculateDigitAt(final String CPF, final int position) {
    final String[] digits = CPF.substring(0, position - 1).split("");
    final Iterator<Integer> factorIterator = factors.iterator();
    int result = Arrays.stream(digits).mapToInt(Integer::parseInt)
                  .reduce(0,(total, digit) -> total + (digit * (position - factorIterator.next())));
    int digit = 11 - (result % 11);
    return digit >= 10 ? 0 : digit;
  }

  public boolean validate(String cpf) {
    String CPF = getOnlyNumbers(cpf);
    if (hasSameNumberRecurring(CPF)) return false;
    if (hasInvalidLength(CPF)) return false;
    try {
      int digit10 = calculateDigitAt(CPF, 10);
      int digit11 = calculateDigitAt(CPF, 11);
      String digitCalculated = "" + digit10 + digit11;
      String cpfDigit = CPF.substring(9);
      return cpfDigit.equals(digitCalculated);
    } catch (Exception error) {
      return false;
    }
  }
}
