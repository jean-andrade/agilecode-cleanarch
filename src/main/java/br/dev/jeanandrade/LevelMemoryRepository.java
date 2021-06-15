package br.dev.jeanandrade;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class LevelMemoryRepository implements LevelRepository{
  private static List<Level> levels = JsonDataSource.getInstance().getLevels();



}
