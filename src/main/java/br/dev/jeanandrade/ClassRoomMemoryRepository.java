package br.dev.jeanandrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassRoomMemoryRepository implements ClassRoomRepository{
  private static List<ClassRoom> classes = new ArrayList<>();

  @Override
  public void save(ClassRoom classRoom) {
    classes.add(classRoom);
  }

  @Override
  public Optional<ClassRoom> find(String level, String module, String code) {
    return classes.parallelStream().filter(classRoom ->
        classRoom.getLevel().equals(level)
        && classRoom.getModule().equals(module)
        && classRoom.getCode().equals(code))
      .findFirst();
  }
}
