package br.dev.jeanandrade;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class EnrollStudent {
  private EnrollmentRepository enrollmentRepository;
  private ModuleRepository moduleRepository;
  private ClassRoomRepository classRoomRepository;

  public EnrollStudent(EnrollmentRepository enrollmentRepository, ModuleRepository moduleRepository, ClassRoomRepository classRoomRepository) {
    this.enrollmentRepository = enrollmentRepository;
    this.moduleRepository = moduleRepository;
    this.classRoomRepository = classRoomRepository;
  }

  public Enrollment execute(EnrollmentRequest enrollmentRequest) {
    Student student = enrollmentRequest.getStudent();
    Module module = this.moduleRepository
      .find(enrollmentRequest.getLevel(), enrollmentRequest.getModule())
      .orElseThrow(() -> new RuntimeException("Module not Found"));
    ClassRoom classRoom = this.classRoomRepository
        .find(enrollmentRequest.getLevel(), enrollmentRequest.getModule(), enrollmentRequest.getClassRoom())
        .orElseThrow(() -> new RuntimeException("Class Room not Found"));
    int studentAge = Period.between(student.getBirthDate(), LocalDate.now()).getYears();
    if (studentAge < module.getMinimumAge()){
      throw new RuntimeException("Student below minimum age");
    }
    Optional<Enrollment> existentEnrollment = this.enrollmentRepository
      .findByStudentCPF(student.getCpf());
    if (existentEnrollment.isPresent()) {
      throw new RuntimeException("Aluno já matriculado!");
    }
    Integer totalEnrollments = this.enrollmentRepository.count(enrollmentRequest.getLevel(),enrollmentRequest.getModule(), enrollmentRequest.getClassRoom());
    if (totalEnrollments.equals(classRoom.getCapacity())){
      throw new RuntimeException("Class is over capacity");
    }
    if (classRoom.isFinished(LocalDate.now())){
      throw new RuntimeException("Class is already finished");
    }
    if(classRoom.isLapseEnrollmentPeriod(LocalDate.now())){
      throw new RuntimeException("Class is already started");
    }

    Integer sequence = this.enrollmentRepository.nextSequence();
    Enrollment enrollment = new Enrollment(LocalDate.now(), enrollmentRequest.getStudent(),
       enrollmentRequest.getLevel(),enrollmentRequest.getModule(),enrollmentRequest.getClassRoom(), sequence);

    this.enrollmentRepository.save(enrollment);
    return enrollment;
  }
}
