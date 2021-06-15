package br.dev.jeanandrade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URL;
import java.util.List;

public class JsonDataSource {
  private List<Level> levels;
  private List<Module> modules;
  private List<ClassRoom> classes;
  private static JsonDataSource instance;


  private JsonDataSource() {
  }

  public static JsonDataSource getInstance() {
    ClassLoader classLoader = JsonDataSource.class.getClassLoader();
    if (classLoader == null){
      throw new RuntimeException("ClassLoader não instanciado");
    }
    if (instance == null){
      URL resource = classLoader.getResource("json/data.json");
      ObjectMapper mapper =new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      try {
        instance = mapper.readValue(resource, JsonDataSource.class);
      }catch (Exception ex){
        throw new RuntimeException(ex);
      }

    }
    return instance;
  }

  public List<Level> getLevels() {
    return levels;
  }

  public List<Module> getModules() {
    return modules;
  }

  public List<ClassRoom> getClasses() {
    return classes;
  }
}
