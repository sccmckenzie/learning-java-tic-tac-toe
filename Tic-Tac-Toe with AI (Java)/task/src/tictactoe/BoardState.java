package tictactoe;

// ideally, this would be named "GameState", but hyperskill doesn't want me to rename it
public enum BoardState {

    UNFINISHED(null),
    DRAW("Draw"),
    X("X wins"),
    O("O wins");

    final String boardStateMsg;

    BoardState(String boardStateMsg) {
        this.boardStateMsg = boardStateMsg;
    }

    public String getBoardStateMsg() {
        return boardStateMsg;
    }
}
