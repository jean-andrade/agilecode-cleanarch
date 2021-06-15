package br.dev.jeanandrade;

import java.util.Optional;

public interface ClassRoomRepository {
  Optional<ClassRoom> find(String level, String module, String code);
}
