package prog06;
import java.util.*;

public class TestGame {
  public static void main (String[] args) {
    WordStep game = new WordStep();
    try {
      game.loadDictionary("words.txt");
    } catch (Exception e) {
      System.out.println("test: " + e);
    }
    game.solve("fail", "pass");
  }
}
