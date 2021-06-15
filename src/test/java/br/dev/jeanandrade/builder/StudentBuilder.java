package br.dev.jeanandrade.builder;

import br.dev.jeanandrade.Student;

import java.time.LocalDate;

public class StudentBuilder {
  private Student student;

  private StudentBuilder() {

  }

  public static StudentBuilder newStudent(){
    StudentBuilder studentBuilder = new StudentBuilder();
    studentBuilder.student = new Student();
    studentBuilder.student.setName("Ana Silva");
    studentBuilder.student.setCpf("323.026.857-17");
    studentBuilder.student.setBirthDate(LocalDate.now().minusYears(20));
    return studentBuilder;
  }
  public StudentBuilder withoutName(){
    this.student.setName("");
    return this;
  }

  public StudentBuilder withName(String name){
    this.student.setName(name);
    return this;
  }

  public StudentBuilder withCPF(String cpf){
    this.student.setCpf(cpf);
    return this;
  }

  public StudentBuilder withBirthDate(LocalDate birthDate){
    this.student.setBirthDate(birthDate);
    return this;
  }

  public Student build(){
    return student;
  }
}
