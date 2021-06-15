package br.dev.jeanandrade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CPFValidatorTest {

  @Test
  public void deveValidarCPF() {
    String cpf = "039.961.979-85";
    CPFValidator validation = new CPFValidator();
    assertTrue(validation.validate(cpf));
  }

  @Test
  public void deveConterOnzeDigitosNumericos() {
    String cpf = "039.961.979";
    CPFValidator validation = new CPFValidator();
    assertFalse(validation.validate(cpf));
  }

  @Test
  public void naoDeveConterNumerosRepetidos() {
    String cpf = "111.111.111-11";
    CPFValidator validation = new CPFValidator();
    assertFalse(validation.validate(cpf));
  }
}
