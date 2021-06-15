package br.dev.jeanandrade;

public class Level {
  private String code;
  private String description;

  public Level() {
  }

  public Level(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }
}
