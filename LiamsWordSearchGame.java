

             
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
/**
 *Class that implements WordSearchGame for assignment 5.
 */

public class LiamsWordSearchGame implements WordSearchGame {
   private String[][] board = {{"E", "E", "C", "A"}, {"A", "L", "E", "P"},
          {"H", "N", "B", "O"}, {"Q", "T", "T", "Y"}};
   TreeSet<String> tree = new TreeSet<>();
   private boolean lexLoaded = false;
   protected int square = 4;
   private String[] boardSingleArray = new String[]{"E", "E", "C", "A",
          "A", "L", "E", "P", "H", "N", "B", "O", "Q", "T", "T", "Y"};
   private int firstLetterIndex;
   private boolean[][] visited;
   private int wordCount = 0;
   private ArrayList<Position> visitedPositions;
   private Integer[] firstLetters;
   private int wholeWordIndex;

   public LiamsWordSearchGame() {
      firstLetters = new Integer[square * square];
   }

   protected String getTreeString() {
      return tree.toString();
   }

   public void loadLexicon(String fileName) {
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      ArrayList<String> fileArray = new ArrayList<>();
      try {
         Scanner input = new Scanner(new File(fileName));
         while (input.hasNext()) {
            String splitInput = input.next().split(" ")[0];
            fileArray.add(splitInput.toUpperCase());
         }
         input.close();
      } catch (FileNotFoundException e) {
         throw new IllegalArgumentException();
      }
      tree.addAll(fileArray);
      lexLoaded = true;
   }

   public void setBoard(String[] letterArray) {
      if (letterArray == null || !checkPerfectSquare(letterArray.length)) {
         throw new IllegalArgumentException();
      }
   
      int sqrt = (int) Math.sqrt(letterArray.length);
      String[][] newBoard = new String[sqrt][sqrt];
   
      for (int i = 0; i < sqrt; i++) {
         for (int j = 0; j < sqrt; j++) {
            newBoard[i][j] = letterArray[i * sqrt + j];
         }
      }
   
      square = sqrt;
      board = newBoard;
   
      boardSingleArray = letterArray;
   }

   protected boolean checkPerfectSquare(double x) {
      double sqrt = Math.sqrt(x);
      return ((sqrt - Math.floor(sqrt)) == 0);
   }

   public String getBoard() {
      final StringBuilder sb = new StringBuilder();
      for (int i = 0; i < board.length; i++) {
         final String[] a = board[i];
         if (i > 0) {
            sb.append("\n");
         }
         if (a != null) {
            for (int j = 0; j < a.length; j++) {
               final String b = a[j];
               if (j > 0) {
                  sb.append(" ");
               }
               sb.append(b);
            }
         }
      }
      return sb.toString();
   }


   public SortedSet<String> getAllScorableWords(int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (!lexLoaded) {
         throw new IllegalStateException();
      }
      SortedSet<String> stringSortedSet = new TreeSet<>();
      if (square == 1) {
         if (tree.contains(board[0][0])) {
            stringSortedSet.add(board[0][0]);
         }
         return stringSortedSet;
      }
      if (board[0][0] == null) {
         return stringSortedSet;
      }
      int n = board.length;
      visited = new boolean[n][n];
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
            findWords(i, j, board, visited, "", minimumWordLength);
            visitedPositions = new ArrayList<>();
         }
      }
      for (String s : tree) {
         if (s.length() >= minimumWordLength) {
            stringSortedSet.add(s);
         }
      }
      return stringSortedSet;
   }

   private void findWords(int i, int j, String[][] boggle, boolean[][] visited,
                          String str, int length) {
      if (length == 0) {
         return;
      }
      visited[i][j] = true;
      str = str + boggle[i][j];
      if (isValidWord(str)) {
         tree.add(str);
         visitedPositions.add(new Position(i, j));
         firstLetters[wordCount] = firstLetterIndex;
         wordCount++;
      }
      if (isValidPrefix(str)) {
         int n = boggle.length;
         for (int row = Math.max(0, i - 1); row <= Math.min(i + 1, n - 1); row++) {
            for (int col = Math.max(0, j - 1); col <= Math.min(j + 1, n - 1); col++) {
               if (!visited[row][col]) {
                  findWords(row, col, boggle, visited, str, length - 1);
               }
            }
         }
      }
      visited[i][j] = false;
   }



   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (!lexLoaded) {
         throw new IllegalStateException();
      }
      int score = 0;
      for (String s : words) {
         int length = s.length();
         if (length >= minimumWordLength) {
            score += length - minimumWordLength + 1;
         }
      }
      return score;
   }

   public boolean isValidWord(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      return tree.contains(wordToCheck.toUpperCase());
   }

   public boolean isValidPrefix(String prefixToCheck) {
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      String wordInLex = "";
      int n = prefixToCheck.length();
      for (String s : tree) {
         if (s.length() >= n) {
            wordInLex = s.substring(0, n);
            if (wordInLex.equals(prefixToCheck.toUpperCase())) {
               return true;
            }
         }
      }
      return false;
   }


   public List<Integer> isOnBoard(String wordToCheck) {
      if (wordToCheck == null || wordToCheck.isEmpty()) {
         throw new IllegalArgumentException();
      }
   
      int sqrt = (int) Math.sqrt(boardSingleArray.length);
      if (sqrt != square) {
         return new ArrayList<>();
      }
   
      boolean found = false;
      for (int i = 0; i < boardSingleArray.length; i++) {
         if (boardSingleArray[i].equals(String.valueOf(wordToCheck.charAt(0))) && i / sqrt < square && i % sqrt < square) {
            firstLetterIndex = i;
            found = true;
            break;
         }
      }
   
      if (!found) {
         return new ArrayList<>();
      }
   
      int row = firstLetterIndex / sqrt;
      int col = firstLetterIndex % sqrt;
      String temp = wordToCheck.substring(1);
      visited = new boolean[square][square];
      visitedPositions = new ArrayList<>();
      wholeWordIndex = 0;
      findWord(row, col, temp, 0);
   
      if (wholeWordIndex == temp.length()) {
         List<Integer> list = new ArrayList<>();
         for (Position a : visitedPositions) {
            list.add(a.getX() * sqrt + a.getY());
         }
         return list;
      }
   
      return new ArrayList<>();
   }

   private void findWord(int row, int col, String str, int index) {
      int sqrt = (int) Math.sqrt(boardSingleArray.length);
      if (row < 0 || row >= sqrt || col < 0 || col >= sqrt) {
         return;
      }
      if (visited[row][col] || !board[row][col].equals(String.valueOf(str.charAt(index)))) {
         return;
      }
      visited[row][col] = true;
      visitedPositions.add(new Position(row, col));
      if (index == str.length() - 1) {
         wholeWordIndex = index;
         return;
      }
      findWord(row + 1, col, str, index + 1);
      findWord(row - 1, col, str, index + 1);
      findWord(row, col + 1, str, index + 1);
      findWord(row, col - 1, str, index + 1);
      findWord(row + 1, col + 1, str, index + 1);
      findWord(row - 1, col - 1, str, index + 1);
      findWord(row + 1, col - 1, str, index + 1);
      findWord(row - 1, col + 1, str, index + 1);
      visited[row][col] = false;
      visitedPositions.remove(visitedPositions.size() - 1);
   }


   public int getScore() {
      if (!lexLoaded) {
         throw new IllegalStateException();
      }
      return tree.size();
   }
   private class Position {
      int x;
      int y;
   
      public Position(int x, int y) {
         this.x = x;
         this.y = y;
      }
   
      public int getX() {
         return x;
      }
   
      public int getY() {
         return y;
      }
   }
}