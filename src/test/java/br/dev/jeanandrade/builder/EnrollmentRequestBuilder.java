package br.dev.jeanandrade.builder;

import br.dev.jeanandrade.EnrollmentRequest;
import br.dev.jeanandrade.Student;

public class EnrollmentRequestBuilder {
  private Student student = StudentBuilder.newStudent().build();
  private String level = "EM";
  private String module = "3";
  private String classRoom = "A";
  private int installments = 12;

  private  EnrollmentRequestBuilder(){}

  public static EnrollmentRequestBuilder newEnrollmentRequest() {
    return new EnrollmentRequestBuilder();
  }
  public EnrollmentRequestBuilder withStudent(Student student){
    this.student = student;
    return this;
  }
  public EnrollmentRequestBuilder withModule(String module){
    this.module = module;
    return this;
  }

  public EnrollmentRequestBuilder withClassRoom(String classRoom){
    this.classRoom = classRoom;
    return this;
  }

  public EnrollmentRequestBuilder withLevel(String level){
    this.classRoom = classRoom;
    return this;
  }

  public EnrollmentRequestBuilder withInstallments(int installments){
    this.installments = installments;
    return this;
  }

  public EnrollmentRequest build(){
    return new EnrollmentRequest(student, level, module, classRoom,installments);
  }

}
