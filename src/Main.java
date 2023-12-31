// Odeya Sadoun 212380406 | Part 1
import java.util.Scanner;

public class Main {
  static final int BOARD_SIZE = 10;
  static final String PLAYER_1_NAME = "A";
  static final String PLAYER_2_NAME = "B";
  private static final String ILLEGAL_STEP_ERROR = "you cant go outside the board";
  private static final String INCORRECT_CHOOSE_OPTION_ERROR =
      "invalid option, please enter a number between 1 and 4";
  public static int playerALocationX;
  public static int playerALocationY;
  public static int playerBLocationX;
  public static int playerBLocationY;
  public static int winnersCarpetLocationX;
  public static int winnersCarpetLocationY;
  public static int winnersCarpetSideSize;
  public static boolean finishGame = false;

  public static void main(String[] args) {
    initializeValues();
    printBoard();
    int countGameTimes = 1;

    while (!finishGame) {

      if (countGameTimes % 2 != 0) {
        makeTurn(PLAYER_1_NAME);
      } else {
        makeTurn(PLAYER_2_NAME);
      }

      countGameTimes++;
    }
  }

  private static void initializeValues() {
    Scanner input = new Scanner(System.in);
    System.out.println("WELCOME \n" + "enter " + PLAYER_1_NAME + " player X location");
    playerALocationX = input.nextInt();
    System.out.println("enter " + PLAYER_1_NAME + " player Y location");
    playerALocationY = input.nextInt();
    System.out.println("enter " + PLAYER_2_NAME + " player X location");
    playerBLocationX = input.nextInt();
    System.out.println("enter " + PLAYER_2_NAME + " player Y location");
    playerBLocationY = input.nextInt();
    System.out.println("enter WINNERS CARPET top left location");
    winnersCarpetLocationX = input.nextInt();
    winnersCarpetLocationY = input.nextInt();
    System.out.println("enter WINNERS CARPET size");
    winnersCarpetSideSize = input.nextInt();
  }

  private static void printBoard() {
    for (int row = 0; row < BOARD_SIZE + 2; row++) {

      for (int column = 0; column < BOARD_SIZE + 2; column++) {

        if (row == 0 || column == 0 || row == BOARD_SIZE + 1 || column == BOARD_SIZE + 1) { // frame
          System.out.print("#");
        } else if (row == playerALocationX && column == playerALocationY) {
          System.out.print(PLAYER_1_NAME);
        } else if (row == playerBLocationX && column == playerBLocationY) {
          System.out.print(PLAYER_2_NAME);
        } else if (winnersCarpetLocationX <= row
            && row < winnersCarpetLocationX + winnersCarpetSideSize
            && winnersCarpetLocationY <= column
            && column < winnersCarpetLocationY + winnersCarpetSideSize) {
          System.out.print("*");
        } else {
          System.out.print(" ");
        }
      }

      System.out.println();
    }
  }

  // Help function to know which player is and return his x location
  private static int xLocationByPlayer(String player) {
    if (player.equals(PLAYER_1_NAME)) {
      return playerALocationX;
    }

    return playerBLocationX;
  }

  // Help function to know which player is and return his y location
  private static int yLocationByPlayer(String player) {
    if (player.equals(PLAYER_1_NAME)) {
      return playerALocationY;
    }

    return playerBLocationY;
  }

  private static void makeTurn(String player) {
    int move = getMoveDirection(player);
    /*
     * clarification:
     * In the case of input that is not correct - not 1-4,
     * the input will go through a function that checks the progress and checks if you exit the board,
     * of course such a situation will not happen because the value is not 1-4,
     * so we will reach default where we will print the desired error note,
     * but we will return with a value of true From the function that supposedly expresses a legal step,
     * in practice - in the game process itself there are only 4 options 1-4,
     * therefore the value that will be entered if it is not correct will not do anything -
     * and will only print the board, which is exactly the requested requirement.
     * */
    boolean legalStep =
        isMovingTowardsBorder(xLocationByPlayer(player), yLocationByPlayer(player), move);

    if (legalStep) {
      movePlayer(player, move);
      boolean isWinner = didPlayerWin(xLocationByPlayer(player), yLocationByPlayer(player));
      printBoard();

      if (isWinner) {
        finishGame = true;
        System.out.println("player " + player + " won this round!");
      }
    } else {
      System.out.println(ILLEGAL_STEP_ERROR);
    }
  }

  private static int getMoveDirection(String player) {
    System.out.println("player " + player + "'s move \n" + "1-up 2-down 3-right 4-left");
    Scanner input = new Scanner(System.in);

    return input.nextInt();
  }

  // A function to check if the step is legal or not. if legal, not go the border: return true, else, illegal step: return false
  private static boolean isMovingTowardsBorder(int playerXloc, int playerYloc, int playerMovement) {
    final int UP = 1;
    final int DOWN = 2;
    final int RIGHT = 3;
    final int LEFT = 4;

    switch (playerMovement) {
      case UP:
        if (playerXloc - 1 < 1) {
          return false;
        }
        break;
      case DOWN:
        if (playerXloc + 1 > BOARD_SIZE) {
          return false;
        }
        break;
      case RIGHT:
        if (playerYloc + 1 > BOARD_SIZE) {
          return false;
        }
        break;
      case LEFT:
        if (playerYloc - 1 < 1) {
          return false;
        }
        break;
      default:
        System.out.println(INCORRECT_CHOOSE_OPTION_ERROR);
        break;
    }

    return true;
  }

  private static void movePlayer(String player, int playerMovement) {
    final int UP = 1;
    final int DOWN = 2;
    final int RIGHT = 3;
    final int LEFT = 4;

    if (player.equals(PLAYER_1_NAME)) {
      switch (playerMovement) {
        case UP -> playerALocationX--;
        case DOWN -> playerALocationX++;
        case RIGHT -> playerALocationY++;
        case LEFT -> playerALocationY--;
      }
    } else {
      switch (playerMovement) {
        case UP -> playerBLocationX--;
        case DOWN -> playerBLocationX++;
        case RIGHT -> playerBLocationY++;
        case LEFT -> playerBLocationY--;
      }
    }
  }

  private static boolean didPlayerWin(int playerXloc, int playerYloc) {
    return playerXloc >= winnersCarpetLocationX
        && playerXloc < winnersCarpetLocationX + winnersCarpetSideSize
        && playerYloc >= winnersCarpetLocationY
        && playerYloc < winnersCarpetLocationY + winnersCarpetSideSize;
  }
}
