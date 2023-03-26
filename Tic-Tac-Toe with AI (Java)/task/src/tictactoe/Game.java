package tictactoe;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    private static final Scanner scanner = new Scanner(System.in);

    private final PlayerType playerXType;
    private final PlayerType playerOType;

    private Board board;

    // ideally, this would be named "GameState", but hyperskill doesn't want me to rename it
    private BoardState boardState;

    public Game(PlayerType playerXType, PlayerType playerOType) {
        this.playerXType = playerXType;
        this.playerOType = playerOType;
    }

    public void execute() throws Exception {
        // set board
        this.board = new Board("_________");

        // initial print
        this.board.print();

        int countX = this.board.countPlayer(Player.X);
        int countO = this.board.countPlayer(Player.O);
        Player nextPlayer = Player.X;

        // conduct game
        game: for(int numMoves = 0; true; numMoves++) {
            // check state, break game if conclusive

            // check #1: three in row
            switch (this.board.findThreeAcross()) {
                case O:
                    this.boardState = BoardState.O;
                    break game;
                case X:
                    this.boardState = BoardState.X;
                    break game;
                default:
                    break;
            }

            // check #2: board full
            if (countX + countO == 9) {
                this.boardState = BoardState.DRAW;
                break;
            }

            // conduct turn
            if (nextPlayer.equals(Player.X)) {
                switch (playerXType) {
                    case USER -> this.userTurn(Player.X);
                    default -> this.easyTurn(Player.X);
                }
            } else {
                switch (playerOType) {
                    case USER -> this.userTurn(Player.O);
                    default -> this.easyTurn(Player.O);
                }
            }
            countX = board.countPlayer(Player.X);
            countO = board.countPlayer(Player.O);

            // print board
            board.print();

            // assign next player
            if (nextPlayer.equals(Player.X)) {
                nextPlayer = Player.O;
            } else {
                nextPlayer = Player.X;
            }
        }

        System.out.println(this.boardState.getBoardStateMsg());


    }

    public void userTurn(Player player) {
        askCoord: while (true) {
            System.out.print("Enter the coordinates: ");
            String[] inputCoord = scanner.nextLine().split(" ");
            int[] coord = {0, 0};

            for (int i = 0; i < 2; i++) {
                try {
                    coord[i] = Integer.parseInt(inputCoord[i]);
                } catch (NumberFormatException numberFormatException) {
                    System.out.println("You should enter numbers!");
                    continue askCoord;
                }

                if (coord[i] < 1 || coord[i] > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue askCoord;
                }
            }

            if (!this.board.getPlayerAtCell(coord).equals(Player.EMPTY)) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            this.board.setPlayerAtCell(coord, player);
            break;
        }
    }

    public void easyTurn(Player player) {
        List<Map<String, Object>> openCells = new ArrayList<>();

        for (Map<String, Object> element : this.board.getCellProjection()) {
            if (element.get("player").equals(Player.EMPTY)) {
                openCells.add(element);
            }
        }

        int index = (int) (Math.floor(Math.random() * openCells.size()));
        List<Integer> coord = ((List<Integer>) openCells.get(index).get("coord"));

        this.board.setPlayerAtCell(coord.stream().mapToInt(i -> i + 1).toArray(), player);

        System.out.println("Making move level \"easy\"");
    }

}