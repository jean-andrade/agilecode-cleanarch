package br.dev.jeanandrade;

import br.dev.jeanandrade.builder.ClassRoomBuilder;
import br.dev.jeanandrade.builder.EnrollmentRequestBuilder;
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
    ClassRoomRepository classRoomRepository = new ClassRoomMemoryRepository();
    ClassRoom classRoomNotStarted = ClassRoomBuilder.newClassRoom().build();
    ClassRoom classRoomFinished = ClassRoomBuilder.newClassRoom()
      .withCode("B")
      .finished().build();
    ClassRoom classRoomAlreadyStarted = ClassRoomBuilder.newClassRoom()
      .withCode("C")
      .alreadyStarted().build();
    classRoomRepository.save(classRoomNotStarted);
    classRoomRepository.save(classRoomFinished);
    classRoomRepository.save(classRoomAlreadyStarted);
    this.enrollStudent = new EnrollStudent(
      new EnrollmentMemoryRepository(),
      new ModuleMemoryRepository(),
      classRoomRepository
    );
  }

  @Test
  public void naoDeveMatricularAlunoSemNome() {
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      Student student = StudentBuilder.newStudent().withoutName().build();
      EnrollmentRequest enrollmentRequest = EnrollmentRequestBuilder.newEnrollmentRequest()
        .withStudent(student)
        .build();
      enrollStudent.execute(enrollmentRequest);
    });
    assertEquals("Nome é obrigatorio!", exception.getMessage());
  }

  @Test
  public void naoDeveMatricularAlunoSemNomeValido() {
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      Student student = StudentBuilder.newStudent().withName("Ana").build();
      EnrollmentRequest enrollmentRequest = EnrollmentRequestBuilder.newEnrollmentRequest()
        .withStudent(student)
        .build();
      enrollStudent.execute(enrollmentRequest);
    });
    assertEquals("Nome de aluno inválido!", exception.getMessage());
  }

  @Test
  public void naoDeveMatricularAlunoSemCpfValido() {
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      Student student = StudentBuilder.newStudent().withCPF("00000000000").build();
      EnrollmentRequest enrollmentRequest = EnrollmentRequestBuilder.newEnrollmentRequest()
        .withStudent(student)
        .build();
      enrollStudent.execute(enrollmentRequest);
    });
    assertEquals("CPF de aluno inválido!", exception.getMessage());
  }

  @Test
  public void naoDeveMatricularAlunoDuplicado() {
    EnrollmentRequest enrollmentRequest1 = EnrollmentRequestBuilder.newEnrollmentRequest().build();
    EnrollmentRequest enrollmentRequest2 = EnrollmentRequestBuilder.newEnrollmentRequest().build();
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        enrollStudent.execute(enrollmentRequest1);
        enrollStudent.execute(enrollmentRequest2);
    });

    assertEquals("Aluno já matriculado!", exception.getMessage());
  }

  @Test
  public void deveGerarNumeroDaMatricula() {
    EnrollmentRequest enrollmentRequest = EnrollmentRequestBuilder.newEnrollmentRequest().build();
    Enrollment enrollment = enrollStudent.execute(enrollmentRequest);
    assertEquals("2021EM3A0001", enrollment.getCode());

  }

  @Test
  public void naoDeveMatricularAlunoSemIdadeMinima(){
    Student student = StudentBuilder.newStudent().withBirthDate(LocalDate.now().minusYears(10)).build();
    EnrollmentRequest enrollmentRequest = EnrollmentRequestBuilder.newEnrollmentRequest()
      .withStudent(student)
      .build();
    RuntimeException exception = assertThrows(RuntimeException.class,() -> {
      Enrollment enrollment = enrollStudent.execute(enrollmentRequest);
    });
    assertEquals("Student below minimum age", exception.getMessage());
  }

  @Test
  @DisplayName("Não deve matricular aluno fora da capacidade da turma")
  public void naoDeveMatricularAlunoAcimaCapacidaddaTurma() {
    Student student1 = StudentBuilder.newStudent().build();
    Student student2 = StudentBuilder.newStudent().withCPF("182.871.780-02").build();
    Student student3 = StudentBuilder.newStudent().withCPF("104.461.400-58").build();
    Student student4 = StudentBuilder.newStudent().withCPF("573.469.970-50").build();
    Student student5 = StudentBuilder.newStudent().withCPF("286.546.360-50").build();
    Student student6 = StudentBuilder.newStudent().withCPF("877.835.260-63").build();
    Student student7 = StudentBuilder.newStudent().withCPF("151.799.050-52").build();
    EnrollmentRequest enrollmentRequest1 = EnrollmentRequestBuilder.newEnrollmentRequest()
      .withStudent(student1)
      .build();
    EnrollmentRequest enrollmentRequest2 = EnrollmentRequestBuilder.newEnrollmentRequest()
      .withStudent(student2)
      .build();
    EnrollmentRequest enrollmentRequest3 = EnrollmentRequestBuilder.newEnrollmentRequest()
      .withStudent(student3)
      .build();
    EnrollmentRequest enrollmentRequest4 = EnrollmentRequestBuilder.newEnrollmentRequest()
      .withStudent(student4)
      .build();
    EnrollmentRequest enrollmentRequest5 = EnrollmentRequestBuilder.newEnrollmentRequest()
      .withStudent(student5)
      .build();
    EnrollmentRequest enrollmentRequest6 = EnrollmentRequestBuilder.newEnrollmentRequest()
      .withStudent(student6)
      .build();
    EnrollmentRequest enrollmentRequest7 = EnrollmentRequestBuilder.newEnrollmentRequest()
      .withStudent(student7)
      .build();

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
    String classRoom = "B";
    EnrollmentRequest enrollmentRequest = EnrollmentRequestBuilder.newEnrollmentRequest()
      .withClassRoom(classRoom)
      .build();
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
    String classRoom = "C";
    EnrollmentRequest enrollmentRequest = EnrollmentRequestBuilder.newEnrollmentRequest()
      .withClassRoom(classRoom)
      .build();
    RuntimeException exception = assertThrows(RuntimeException.class, () ->{
        Enrollment enrollment = enrollStudent.execute(enrollmentRequest);
    });
    assertEquals("Class is already started", exception.getMessage());

  }

  @Test
  @DisplayName("Deve gerar as faturas de acordo com o número de parcelas, arredondando o valor e aplicando o resto para a última fatura")
  public void teste_9(){

  }
}
