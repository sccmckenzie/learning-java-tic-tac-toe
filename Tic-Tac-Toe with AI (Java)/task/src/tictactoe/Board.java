package tictactoe;

import java.sql.Array;
import java.util.*;

public class Board extends BoardUtils {
    private static Scanner scanner = new Scanner(System.in);

    private final Cell[][] cells;

    public Board(String initState) throws Exception {
        this.cells = new Cell[3][3];

        Deque cellStates = parseStringArray(initState.split(""));

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

    public Player getPlayer(int[] coord) {
        return cells[coord[0] - 1][coord[1] - 1].getPlayer();
    }

    public void setPlayer(int[] coord, Player player) {
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

    public void humanPlayerTurn(Player player) {
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

            if (!this.getPlayer(coord).equals(Player.EMPTY)) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            this.setPlayer(coord, player);
            break;
        }
    }

    public void aiTurn(Player player) {
        List<Map<String, Object>> openCells = new ArrayList<>();

        for (Map<String, Object> element : this.getCellProjection()) {
            if (element.get("player").equals(Player.EMPTY)) {
                openCells.add(element);
            }
        }

        int index = (int) (Math.floor(Math.random() * openCells.size()));
        List<Integer> coord = ((List<Integer>) openCells.get(index).get("coord"));

        this.setPlayer(coord.stream().mapToInt(i -> i + 1).toArray(), player);

        System.out.println("Making move level \"easy\"");
    }
}
