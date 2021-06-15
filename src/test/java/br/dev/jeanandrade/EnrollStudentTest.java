package br.dev.jeanandrade;

import br.dev.jeanandrade.builder.StudentBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnrollStudentTest {
  private  EnrollStudent enrollStudent;

  @BeforeEach
  public void init(){
    this.enrollStudent = new EnrollStudent(
      new EnrollmentMemoryRepository(),
      new ModuleMemoryRepository(),
      new ClassRoomMemoryRepository()
    );
  }

  @Test
  public void naoDeveMatricularAlunoSemNome() {
    RuntimeException exception = assertThrows(RuntimeException.class, new Executable() {
      @Override
      public void execute() throws Throwable {
        Student student = StudentBuilder.newStudent().withoutName().build();
        String level = "EM";
        String module = "3";
        String classRoom = "A";
        EnrollmentRequest enrollmentRequest = new EnrollmentRequest(student, level, module, classRoom);
        enrollStudent.execute(enrollmentRequest);
      }
    });
    assertEquals("Nome é obrigatorio!", exception.getMessage());
  }

  @Test
  public void naoDeveMatricularAlunoSemNomeValido() {
    RuntimeException exception = assertThrows(RuntimeException.class, new Executable() {
      @Override
      public void execute() throws Throwable {
        Student student = StudentBuilder.newStudent().withName("Ana").build();
        String level = "EM";
        String module = "3";
        String classRoom = "A";
        EnrollmentRequest enrollmentRequest = new EnrollmentRequest(student, level, module, classRoom);
        enrollStudent.execute(enrollmentRequest);
      }
    });
    assertEquals("Nome de aluno inválido!", exception.getMessage());
  }

  @Test
  public void naoDeveMatricularAlunoSemCpfValido() {
    RuntimeException exception = assertThrows(RuntimeException.class, new Executable() {
      @Override
      public void execute() throws Throwable {
        Student student = StudentBuilder.newStudent().withCPF("00000000000").build();
        String level = "EM";
        String module = "3";
        String classRoom = "A";
        EnrollmentRequest enrollmentRequest = new EnrollmentRequest(student, level, module, classRoom);
        enrollStudent.execute(enrollmentRequest);
      }
    });
    assertEquals("CPF de aluno inválido!", exception.getMessage());
  }

  @Test
  public void naoDeveMatricularAlunoDuplicado() {
    String level = "EM";
    String module = "3";
    String classRoom = "A";
    Student student1 = StudentBuilder.newStudent().build();
    Student student2 = StudentBuilder.newStudent().build();
    EnrollmentRequest enrollmentRequest1 = new EnrollmentRequest(student1, level, module, classRoom);
    EnrollmentRequest enrollmentRequest2 = new EnrollmentRequest(student2, level, module, classRoom);
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        enrollStudent.execute(enrollmentRequest1);
        enrollStudent.execute(enrollmentRequest2);
    });

    assertEquals("Aluno já matriculado!", exception.getMessage());
  }

  @Test
  public void deveGerarNumeroDaMatricula() {
    Student student = StudentBuilder.newStudent().build();
    String level = "EM";
    String module = "3";
    String classRoom = "A";
    EnrollmentRequest enrollmentRequest = new EnrollmentRequest(student, level, module, classRoom);
    Enrollment enrollment = enrollStudent.execute(enrollmentRequest);
    assertEquals("2021EM3A0001", enrollment.getCode());

  }

  @Test
  public void naoDeveMatricularAlunoSemIdadeMinima(){
    Student student = StudentBuilder.newStudent().withBirthDate(LocalDate.now().minusYears(10)).build();
    String level = "EM";
    String module = "3";
    String classRoom = "A";
    EnrollmentRequest enrollmentRequest = new EnrollmentRequest(student, level, module, classRoom);
    RuntimeException exception = assertThrows(RuntimeException.class,() -> {
      Enrollment enrollment = enrollStudent.execute(enrollmentRequest);
    });
    assertEquals("Student below minimum age", exception.getMessage());
  }

  @Test
  @DisplayName("Não deve matricular aluno fora da capacidade da turma")
  public void naoDeveMatricularAlunoAcimaCapacidaddaTurma() {
    String level = "EM";
    String module = "3";
    String classRoom = "A";

    Student student1 = StudentBuilder.newStudent().build();
    Student student2 = StudentBuilder.newStudent().withCPF("182.871.780-02").build();
    Student student3 = StudentBuilder.newStudent().withCPF("104.461.400-58").build();
    Student student4 = StudentBuilder.newStudent().withCPF("573.469.970-50").build();
    Student student5 = StudentBuilder.newStudent().withCPF("286.546.360-50").build();
    Student student6 = StudentBuilder.newStudent().withCPF("877.835.260-63").build();
    Student student7 = StudentBuilder.newStudent().withCPF("151.799.050-52").build();

    EnrollmentRequest enrollmentRequest1 = new EnrollmentRequest(student1, level, module, classRoom);
    EnrollmentRequest enrollmentRequest2 = new EnrollmentRequest(student2, level, module, classRoom);
    EnrollmentRequest enrollmentRequest3 = new EnrollmentRequest(student3, level, module, classRoom);
    EnrollmentRequest enrollmentRequest4 = new EnrollmentRequest(student4, level, module, classRoom);
    EnrollmentRequest enrollmentRequest5 = new EnrollmentRequest(student5, level, module, classRoom);
    EnrollmentRequest enrollmentRequest6 = new EnrollmentRequest(student6, level, module, classRoom);
    EnrollmentRequest enrollmentRequest7 = new EnrollmentRequest(student7, level, module, classRoom);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        Enrollment enrollment1 = enrollStudent.execute(enrollmentRequest1);
        Enrollment enrollment2 = enrollStudent.execute(enrollmentRequest2);
        Enrollment enrollment3 = enrollStudent.execute(enrollmentRequest3);
        Enrollment enrollment4 = enrollStudent.execute(enrollmentRequest4);
        Enrollment enrollment5 = enrollStudent.execute(enrollmentRequest5);
        Enrollment enrollment6 = enrollStudent.execute(enrollmentRequest6);
        Enrollment enrollment7 = enrollStudent.execute(enrollmentRequest7);
    });
    assertEquals("Class is over capacity", exception.getMessage());
  }

  @Test
  @DisplayName("Não deve matricular depois do fim das aulas")
  public void naoDeveMatricularDepoisDoFimDasAulas() {
    String level = "EM";
    String module = "3";
    String classRoom = "B";
    Student student = StudentBuilder.newStudent().build();
    EnrollmentRequest enrollmentRequest = new EnrollmentRequest(student, level, module, classRoom);

    RuntimeException exception = assertThrows(RuntimeException.class, new Executable() {
      @Override
      public void execute() throws Throwable {
        Enrollment enrollment = enrollStudent.execute(enrollmentRequest);

      }
    });
    assertEquals("Class is already finished", exception.getMessage());
  }

  @Test
  public void naoDeveMatricularApos25PorCentoDoInicioDasAulas(){
    String level = "EM";
    String module = "3";
    String classRoom = "C";
    Student student = StudentBuilder.newStudent().build();
    EnrollmentRequest enrollmentRequest = new EnrollmentRequest(student, level, module, classRoom);
    RuntimeException exception = assertThrows(RuntimeException.class, () ->{
        Enrollment enrollment = enrollStudent.execute(enrollmentRequest);
    });
    assertEquals("Class is already started", exception.getMessage());

  }
}
