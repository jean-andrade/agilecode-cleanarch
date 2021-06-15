package br.dev.jeanandrade;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class EnrollmentMemoryRepository implements EnrollmentRepository{
  private Set<Enrollment> enrollments = new HashSet<>();
  private static Integer count = 0;
  @Override
  public void save(Enrollment enrollment) {
    enrollments.add(enrollment);
  }

  @Override
  public Optional<Enrollment> findByStudentCPF(String cpf) {
    return enrollments.stream().filter(enrollment -> enrollment.getStudent().getCpf().equals(cpf)).findFirst();
  }

  @Override
  public synchronized Integer nextSequence() {
    return ++count;
  }

  @Override
  public Integer count(String level, String module, String classRoom) {
    long count = enrollments.stream().filter(enrollment ->
        enrollment.getLevel().equals(level)
        && enrollment.getModule().equals(module)
        && enrollment.getClassRoom().equals(classRoom)).count();
    return Long.valueOf(count).intValue();
  }

}
