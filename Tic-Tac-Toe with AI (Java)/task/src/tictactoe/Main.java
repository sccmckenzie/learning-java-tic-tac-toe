package tictactoe;

import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.valueOf;

public class Main {
    public static void main(String[] args) throws Exception {
        // set the board
        Board board = new Board("_________");
        BoardState boardState;
        int countX = board.countPlayer(Player.X);
        int countO = board.countPlayer(Player.O);
        Player nextPlayer = Player.X;

        // initial print
        board.print();

        // who goes first?
        if (countX > countO) {
            nextPlayer = Player.O;
        }

        game: for(int numMoves = 0; true; numMoves++) {
            // check state, break game if conclusive

            // check #1: three in row
            switch (board.findThreeAcross()) {
                case O:
                    boardState = BoardState.O;
                    break game;
                case X:
                    boardState = BoardState.X;
                    break game;
                default:
                    break;
            }

            // check #2: board full
            if (countX + countO == 9) {
                boardState = BoardState.DRAW;
                break;
            }

            /*if (numMoves == 0) {
                System.out.println("Game not finished");
            }*/

            // conduct turn
            if (nextPlayer.equals(Player.X)) {
                board.humanPlayerTurn(nextPlayer);
            } else {
                board.aiTurn(nextPlayer);
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

        System.out.println(boardState.getBoardStateMsg());

    }
}
