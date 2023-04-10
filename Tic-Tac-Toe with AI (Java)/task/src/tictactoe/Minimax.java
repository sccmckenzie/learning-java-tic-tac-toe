package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Minimax {
    public static CoordinateTuple minimax(Board board, Player player) throws CloneNotSupportedException {

        CoordinateTuple out = new CoordinateTuple();

        // check board state
        BoardState currentState = board.checkState();

        // check if game finished + return appropriate score
        if (!currentState.equals(BoardState.UNFINISHED)) {
            if (currentState.equals(BoardState.DRAW)) {
                out.setScore(0);
            } else if (player.outputSymbol.equals(currentState.toString())) {
                out.setScore(1);
            } else {
                out.setScore(-1);
            }
            return out;
        }

        // get opponent
        Player opponent;
        if (player.equals(Player.X)) {
            opponent = Player.O;
        } else {
            opponent = Player.X;
        }

        // get EMPTY cells
        List<Map<String, Object>> openCells = new ArrayList<>();
        for (Map<String, Object> element : board.getCellProjection()) {
            if (element.get("player").equals(Player.EMPTY)) {
                openCells.add(element);
            }
        }

        out.setScore(-2);
        out.setCoord(new int[2]);


        // iterate through each EMPTY cell
        for (Map<String, Object> element : openCells) {

            // clone board
            Board hypotheticalBoard = board.clone();
            int[] coord = ((List<Integer>) element.get("coord")).stream().mapToInt(i -> i + 1).toArray();

            // mark cell as player
            hypotheticalBoard.setPlayerAtCell(coord, player);

            int hypotheticalScore = -minimax(hypotheticalBoard, opponent).getScore();

            if (hypotheticalScore > out.getScore()) {
                out.setScore(hypotheticalScore);
                out.setCoord(coord);
            }
        }

        return out;
    }
}
