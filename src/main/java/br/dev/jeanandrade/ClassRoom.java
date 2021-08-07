package br.dev.jeanandrade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

public class ClassRoom {
  public static final Integer MAX_LAPSE_ENROLMENT_PERIOD = 25;
  private String level;
  private String module;
  private String code;
  private Integer capacity;
  @JsonProperty("start_date")
  private LocalDate startDate;
  @JsonProperty("end_date")
  private LocalDate endDate;

  public ClassRoom() {
  }

  public ClassRoom(String level, String module, String code, Integer capacity,LocalDate startDate,LocalDate endDate) {
    this.level = level;
    this.module = module;
    this.code = code;
    this.capacity = capacity;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public String getLevel() {
    return level;
  }

  public String getModule() {
    return module;
  }

  public String getCode() {
    return code;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public boolean isFinished(LocalDate currentDate){
    return  currentDate.isAfter(this.getEndDate());
  }

  public boolean isLapseEnrollmentPeriod(LocalDate currantDate){
    long totalDays = Duration.between(this.startDate.atStartOfDay(), this.endDate.atStartOfDay()).toDays();
    long lapseDays = Duration.between(this.startDate.atStartOfDay(), currantDate.atStartOfDay()).toDays();
    return (lapseDays*100)/totalDays > MAX_LAPSE_ENROLMENT_PERIOD;
  }

  @Override
  public String toString() {
    return "ClassRoom{" +
      "level='" + level + '\'' +
      ", module='" + module + '\'' +
      ", code='" + code + '\'' +
      ", capacity=" + capacity +
      ", startDate=" + startDate +
      ", endDate=" + endDate +
      '}';
  }
}
