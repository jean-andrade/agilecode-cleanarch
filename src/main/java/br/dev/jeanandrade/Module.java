package br.dev.jeanandrade;

import java.math.BigDecimal;

public class Module {
  private String level;
  private String code;
  private String description;
  private int minimumAge;
  private BigDecimal price;

  public Module() {
  }

  public Module(String level, String code, String description, int minimumAge, BigDecimal price) {
    this.level = level;
    this.code = code;
    this.description = description;
    this.minimumAge = minimumAge;
    this.price = price;
  }

  public String getLevel() {
    return level;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public int getMinimumAge() {
    return minimumAge;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
