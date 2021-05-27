package br.dev.jeanandrade;

public class EnrollmentRequest {
    private Student student;

    EnrollmentRequest(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }
}
