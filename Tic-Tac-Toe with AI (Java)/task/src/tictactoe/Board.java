package tictactoe;

import java.util.*;

public class Board implements Cloneable {
    private static final Scanner scanner = new Scanner(System.in);

    private final Cell[][] cells;

    public Board(String initCells) throws Exception {
        this.cells = new Cell[3][3];

        Deque<Player> cellStates = new ArrayDeque<>();;

        for (String str : initCells.split("")) {
            Player player = Player.findBySymbol(str);

            if (player == null) {
                throw new Exception("Unable to parse input string into CellState.");
            }

            cellStates.add(player);
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 3; j > 0; j--) {
                cells[i][3 - j] = new Cell((Player) cellStates.removeFirst());
            }
        }
    }

    public void print() {
        System.out.println("---------");
        System.out.print("| ");
        System.out.print(cells[0][0].getPlayer().getSymbol());
        System.out.print(" ");
        System.out.print(cells[0][1].getPlayer().getSymbol());
        System.out.print(" ");
        System.out.print(cells[0][2].getPlayer().getSymbol());
        System.out.println(" |");

        System.out.print("| ");
        System.out.print(cells[1][0].getPlayer().getSymbol());
        System.out.print(" ");
        System.out.print(cells[1][1].getPlayer().getSymbol());
        System.out.print(" ");
        System.out.print(cells[1][2].getPlayer().getSymbol());
        System.out.println(" |");

        System.out.print("| ");
        System.out.print(cells[2][0].getPlayer().getSymbol());
        System.out.print(" ");
        System.out.print(cells[2][1].getPlayer().getSymbol());
        System.out.print(" ");
        System.out.print(cells[2][2].getPlayer().getSymbol());
        System.out.println(" |");
        System.out.println("---------");
    }

    public Player findThreeAcross () {
        Player player = Player.EMPTY;

        List<List<int[]>> threeAcrossIndices =
                List.of(
                        // flat
                        List.of(new int[]{0, 0}, new int[]{0, 1}, new int[]{0, 2}),
                        List.of(new int[]{1, 0}, new int[]{1, 1}, new int[]{1, 2}),
                        List.of(new int[]{2, 0}, new int[]{2, 1}, new int[]{2, 2}),
                        List.of(new int[]{0, 0}, new int[]{1, 0}, new int[]{2, 0}),
                        List.of(new int[]{0, 1}, new int[]{1, 1}, new int[]{2, 1}),
                        List.of(new int[]{0, 2}, new int[]{1, 2}, new int[]{2, 2}),

                        // diag
                        List.of(new int[]{0, 0}, new int[]{1, 1}, new int[]{2, 2}),
                        List.of(new int[]{0, 2}, new int[]{1, 1}, new int[]{2, 0})
                );

        for (List<int[]> specificRowIndices : threeAcrossIndices) {
            int[] c1 = specificRowIndices.get(0);
            int[] c2 = specificRowIndices.get(1);
            int[] c3 = specificRowIndices.get(2);

            Cell cell1 = cells[c1[0]][c1[1]];
            Cell cell2 = cells[c2[0]][c2[1]];
            Cell cell3 = cells[c3[0]][c3[1]];

            if (cell1.getPlayer().equals(cell2.getPlayer()) &&
                    cell2.getPlayer().equals(cell3.getPlayer())) {
                player = cell1.getPlayer();
            }
        }


        return player;
    }

    public int countPlayer(Player player) {
        int count = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 3; j > 0; j--) {
                if (cells[i][3 - j].getPlayer().equals(player)) {
                    count++;
                }
            }
        }

        return count;
    }

    public Player getPlayerAtCell(int[] coord) {
        return cells[coord[0] - 1][coord[1] - 1].getPlayer();
    }

    public void setPlayerAtCell(int[] coord, Player player) {
        cells[coord[0] - 1][coord[1] - 1].setPlayer(player);
    }

    public List<Map<String, Object>> getCellProjection() {
        List<Map<String, Object>> cellProjection = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Map<String, Object> currentCell = new HashMap<>();
                currentCell.put("coord", List.of(i, j));
                currentCell.put("player", cells[i][j].getPlayer());

                cellProjection.add(currentCell);
            }
        }

        return cellProjection;
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
        StringBuilder initCells = new StringBuilder();
        for (Map<String, Object> element : this.getCellProjection()) {
            initCells.append(((Player) element.get("player")).inputSymbol);
        }

        try {
            return new Board (initCells.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BoardState checkState() {
        // check #1: three in row
        switch (this.findThreeAcross()) {
            case O:
                return BoardState.O;
            case X:
                return BoardState.X;
            default:
                break;
        }

        // check #2: board full
        int countX = this.countPlayer(Player.X);
        int countO = this.countPlayer(Player.O);
        if (countX + countO == 9) {
            return BoardState.DRAW;
        }

        return BoardState.UNFINISHED;
    }
}
