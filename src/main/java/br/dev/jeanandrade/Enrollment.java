package br.dev.jeanandrade;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class Enrollment {
  private Integer sequence;
  private String code;
  private Student student;
  private String level;
  private String module;
  private String classRoom;
  private LocalDate enrollDate;

  public Enrollment(LocalDate date, Student student, String level, String module, String classRoom, Integer sequence) {

    this.enrollDate = date;
    this.student = student;
    this.level = level;
    this.module = module;
    this.classRoom = classRoom;
    this.sequence = sequence;
    buildEnrollmentCode();
  }

  private void buildEnrollmentCode(){
    this.code = "" + this.enrollDate.getYear() + this.level.trim() + this.module.trim() + this.classRoom.trim() + StringUtils.leftPad(String.valueOf(this.sequence),4, "0").trim();
  }

  public String getCode() { return code; }

  public Student getStudent() {
    return student;
  }

  public String getLevel() {
    return level;
  }

  public String getModule() {
    return module;
  }

  public String getClassRoom() {
    return classRoom;
  }
}
