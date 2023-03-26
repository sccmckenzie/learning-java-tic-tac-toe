package tictactoe;

import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void run() throws Exception {
        while (true) {
            String[] input = scanner.nextLine().split(" ");

            try {
                if (input[0].equals("exit")) {
                    break;
                } else if (input[0].equals("start") && input.length <= 3) {
                    PlayerType playerXType;
                    PlayerType playerOType;
                    try {
                        playerXType = PlayerType.valueOf(input[1].toUpperCase());
                        playerOType = PlayerType.valueOf(input[2].toUpperCase());
                    } catch (Exception e) {
                        throw new Exception("Bad parameters!");
                    }
                    Game game = new Game(playerXType, playerOType);
                    game.execute();
                } else {
                    throw new Exception("Bad parameters!");
                }
            } catch (Exception e) {
                String msg = e.getMessage();
                if (msg.equals("Bad parameters!")) {
                    System.out.println(msg);
                } else {
                    throw new Exception(e);
                }
            }
        }
    }
}
