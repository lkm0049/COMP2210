import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class LiamsWordSearchGameTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


   /** A test that always fails. **/
   @Test public void testGetAllScorableWords_4() {
      LiamsWordSearchGame game = new LiamsWordSearchGame();
      game.loadLexicon("wordfiles/words_medium.txt");
      String[] board = {"T", "I", "G", "E", "R"};
      game.setBoard(board);
   
      SortedSet<String> scorableWords = game.getAllScorableWords(7);
   
    // Ensure that the set of scorable words is as expected.
      Set<String> expectedWords = new HashSet<>(Arrays.asList("TIGER"));
      assertEquals(expectedWords, new HashSet<>(scorableWords));
   }
   
}
