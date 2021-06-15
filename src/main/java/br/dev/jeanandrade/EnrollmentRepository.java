package br.dev.jeanandrade;

import java.util.Optional;

public interface EnrollmentRepository {
  void save(Enrollment enrollment);
  Optional<Enrollment> findByStudentCPF(String cpf);
  Integer nextSequence();
  Integer count(String level, String module, String classRoom);
}
