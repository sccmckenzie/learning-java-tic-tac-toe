package tictactoe;

public enum Player {

    X("X", "X"),
    O("O", "O"),
    EMPTY("_", " ");

    final String inputSymbol;
    final String outputSymbol;

    Player(String inputSymbol, String outputSymbol) {
        this.inputSymbol = inputSymbol;
        this.outputSymbol = outputSymbol;
    }

    public String getSymbol() {
        return outputSymbol;
    }

    public static Player findBySymbol(String symbol) {
        for (Player value: values()) {
            if (value.inputSymbol.equals(symbol)) {
                return value;
            }
        }
        return null;
    }
}
