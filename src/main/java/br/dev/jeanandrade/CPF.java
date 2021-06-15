package br.dev.jeanandrade;

public class CPF {
  private String value;

  public CPF(String value) {
    CPFValidator cpfValidation = new CPFValidator();
    if (!cpfValidation.validate(value)) {
      throw new RuntimeException("CPF de aluno inválido!");
    }
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
