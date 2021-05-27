package br.dev.jeanandrade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnrollStudentTest {

    @Test
    public void naoDeveMatricularAlunoSemNome() {
        RuntimeException exception = assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Student student = new Student();
                EnrollmentRequest enrollmentRequest = new EnrollmentRequest(student);
                EnrollStudent enrollStudent = new EnrollStudent();
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
                Student student = new Student();
                student.setName("Ana");
                EnrollmentRequest enrollmentRequest = new EnrollmentRequest(student);
                EnrollStudent enrollStudent = new EnrollStudent();
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
                Student student = new Student();
                student.setName("Ana Silva");
                student.setCpf("00000000000");
                EnrollmentRequest enrollmentRequest = new EnrollmentRequest(student);
                EnrollStudent enrollStudent = new EnrollStudent();
                enrollStudent.execute(enrollmentRequest);
            }
        });
        assertEquals("CPF de aluno inválido!", exception.getMessage());
    }

    @Test
    public void naoDeveMatricularAlunoDuplicado() {
        EnrollStudent enrollStudent = new EnrollStudent();
        Student student1 = new Student();
        student1.setName("Ana Silva");
        student1.setCpf("323.026.857-17");
        EnrollmentRequest enrollmentRequest1 = new EnrollmentRequest(student1);

        Student student2 = new Student();
        student2.setName("Ana Silva");
        student2.setCpf("323.026.857-17");
        EnrollmentRequest enrollmentRequest2 = new EnrollmentRequest(student2);

        RuntimeException exception = assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                enrollStudent.execute(enrollmentRequest1);
                enrollStudent.execute(enrollmentRequest2);
            }
        });

        assertEquals("Aluno já matriculado!", exception.getMessage());
    }
}
