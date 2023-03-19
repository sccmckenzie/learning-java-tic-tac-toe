package tictactoe;

public enum BoardState {

    UNFINISHED(null),
    DRAW("Draw"),
    X("X wins"),
    O("O wins");

    String boardStateMsg;

    BoardState(String boardStateMsg) {
        this.boardStateMsg = boardStateMsg;
    }

    public String getBoardStateMsg() {
        return boardStateMsg;
    }
}
