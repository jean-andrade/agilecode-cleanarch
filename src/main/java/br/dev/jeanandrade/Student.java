package br.dev.jeanandrade;

import java.time.LocalDate;

public class Student {
  private Name name;
  private CPF cpf;
  private LocalDate birthDate;

  public void setName(String name) {
    this.name = new Name(name);
  }

  public String getName() {
    return name.getValue();
  }

  public String getCpf() {
    return cpf.getValue();
  }

  public void setCpf(String cpf) {
    this.cpf = new CPF(cpf);
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Student other = (Student) obj;
    if (cpf == null) {
      if (other.cpf != null)
        return false;
    } else if (!cpf.equals(other.cpf))
      return false;
    return true;
  }
}
