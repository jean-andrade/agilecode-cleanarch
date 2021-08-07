package br.dev.jeanandrade;

import java.util.Optional;

public interface ClassRoomRepository {
  void save(ClassRoom classRoom);
  Optional<ClassRoom> find(String level, String module, String code);
}
