package br.dev.jeanandrade;

import java.util.List;
import java.util.Optional;

public class ModuleMemoryRepository implements ModuleRepository{
  private static List<Module> modules = JsonDataSource.getInstance().getModules();

  @Override
  public Optional<Module> find(String level, String code) {
    return modules.parallelStream().filter(module -> module.getLevel().equals(level) && module.getCode().equals(code)).findFirst();
  }
}
