package br.dev.jeanandrade.builder;

import br.dev.jeanandrade.ClassRoom;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class ClassRoomBuilder {
  private String level;
  private String module;
  private String code;
  private Integer capacity;
  private LocalDate startDate;
  private LocalDate endDate;

  private ClassRoomBuilder(){

  }

  public static ClassRoomBuilder newClassRoom(){
    ClassRoomBuilder builder = new ClassRoomBuilder();
    builder.capacity = 5;
    builder.code = "A";
    builder.level = "EM";
    builder.module = "3";
    builder.startDate  = LocalDate.now().plusMonths(1);
    builder.endDate = LocalDate.now().plusMonths(6);
    return builder;
  }
  public ClassRoomBuilder alreadyStarted(){
    Integer totalDays = 180;
    Integer lapseDays = 2+((ClassRoom.MAX_LAPSE_ENROLMENT_PERIOD*totalDays)/100);
    this.startDate = LocalDate.now().minusDays(lapseDays);
    this.endDate = startDate.plusDays(totalDays);
    return this;
  }
  public ClassRoomBuilder finished(){
    Integer totalDays = 180;
    this.startDate = LocalDate.now().minusDays(totalDays +1);
    this.endDate = startDate.plusDays(totalDays);
    return this;
  }
  public ClassRoomBuilder withLevel(String level){
    this.level = level;
    return this;
  }
  public ClassRoomBuilder withModule(String module){
    this.module = module;
    return this;
  }
  public ClassRoomBuilder withCode(String code){
    this.code = code;
    return this;
  }
  public ClassRoomBuilder withCapacity(Integer capacity){
    this.capacity = capacity;
    return this;
  }
  public ClassRoomBuilder withStartDate(LocalDate startDate){
    this.startDate = startDate;
    return this;
  }
  public ClassRoomBuilder withEndDate(LocalDate endDate){
    this.endDate = endDate;
    return this;
  }
  public ClassRoom build(){
    return new ClassRoom(
      level,module,code,capacity,startDate,endDate
    );
  }
}
