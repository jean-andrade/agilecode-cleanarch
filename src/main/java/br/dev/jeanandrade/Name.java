package br.dev.jeanandrade;

public class Name {
  private String value;

  public Name(String value) {
    if (value == null || value.isBlank() || value.isEmpty()) {
      throw new RuntimeException("Nome é obrigatorio!");
    }
    if (!value.matches("^([A-Za-z]+ )+([A-Za-z])+$")) {
      throw new RuntimeException("Nome de aluno inválido!");
    }
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
