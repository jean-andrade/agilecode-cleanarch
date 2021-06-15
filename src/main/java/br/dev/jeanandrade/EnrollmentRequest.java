package br.dev.jeanandrade;

public class EnrollmentRequest {
  private Student student;
  private String level;
  private String module;
  private String classRoom;

  EnrollmentRequest(Student student, String level, String module, String classRoom) {
    this.student = student;
    this.classRoom = classRoom;
    this.level = level;
    this.module = module;
  }

  public Student getStudent() {
    return student;
  }

  public String getClassRoom() {
    return classRoom;
  }

  public String getLevel() {
    return level;
  }

  public String getModule() {
    return module;
  }
}
