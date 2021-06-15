package br.dev.jeanandrade;

import java.util.Optional;

public interface ModuleRepository {
  Optional<Module> find(String leve, String code);
}
