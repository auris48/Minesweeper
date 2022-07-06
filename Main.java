package minesweeper;

import java.util.Scanner;

public class Main {
    public static boolean gameRunning = true;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("How many mines do you want on the field? ");
        GameField gameField = new GameField(scanner.nextInt());
        scanner.nextLine();
        while (gameRunning) {
            System.out.println("Set/unset mines marks or claim a cell as free:");
            String input = scanner.nextLine();
            String[] inputArray = input.split(" ");
            int x = Integer.parseInt(inputArray[1]) - 1;
            int y = Integer.parseInt(inputArray[0]) - 1;
            String command = inputArray[2];

            gameField.makeMove(x, y, command);
            if (gameField.checkWin()) {
                System.out.println("Congratulations you found all the mines!");
                gameRunning = false;
            } else if (gameField.isGameOver()) {
                System.out.println("You stepped on a mine and failed");
                gameRunning = false;
            }

            gameField.printGameField();

        }
    }
}



