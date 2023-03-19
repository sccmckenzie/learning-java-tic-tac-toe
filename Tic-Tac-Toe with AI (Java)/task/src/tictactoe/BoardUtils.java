package tictactoe;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class BoardUtils {

    public static Deque<Player> parseStringArray(String[] inputStringArray) throws Exception {
        Deque out = new ArrayDeque<Player>();

        for (String str : inputStringArray) {
            out.add(parseCellPlayer(str));
        }

        return out;
    }

    public static Player parseCellPlayer(String str) throws Exception {
        Player out = Player.findBySymbol(str);

        if (out == null) {
            throw new Exception("Unable to parse input string into CellState.");
        }

        return out;
    }

}
