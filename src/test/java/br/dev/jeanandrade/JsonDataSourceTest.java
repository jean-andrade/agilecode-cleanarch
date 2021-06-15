package br.dev.jeanandrade;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class JsonDataSourceTest {
  @Test
  public void deveConsultarLevels(){
    List<Level> levels = JsonDataSource.getInstance().getLevels();
    assertEquals(3, levels.size());
  }

  @Test
  public void shouldHasValidClassRoomValidatePeriod(){
    List<ClassRoom> classes = JsonDataSource.getInstance().getClasses();
    classes.forEach(System.out::println);
     long count = classes.parallelStream().filter(classRoom -> classRoom.getStartDate().isBefore(classRoom.getEndDate())).count();
     assertEquals(classes.size(), count);
  }
}
