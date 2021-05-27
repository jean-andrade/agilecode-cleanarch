package br.dev.jeanandrade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnrollStudent {
    private Set<Student> students = new HashSet<>();

    public void execute(EnrollmentRequest enrollmentRequest) {
        CPFValidation cpfValidation = new CPFValidation();
        Student student = enrollmentRequest.getStudent();
        if (student.getName() == null || student.getName().isBlank() || student.getName().isEmpty()) {
            throw new RuntimeException("Nome é obrigatorio!");
        }
        if (!student.getName().matches("^([A-Za-z]+ )+([A-Za-z])+$")) {
            throw new RuntimeException("Nome de aluno inválido!");
        }
        if (!cpfValidation.validate(student.getCpf())) {
            throw new RuntimeException("CPF de aluno inválido!");
        }
        if (students.contains(student)) {
            throw new RuntimeException("Aluno já matriculado!");
        }
        students.add(student);
    }
}
