package tictactoe;

public class CoordinateTuple {
    private int[] coord;
    private int score;


    public CoordinateTuple(int[] coord, int score) {
        this.coord = coord;
        this.score = score;
    }

    public CoordinateTuple() {

    }


    public int[] getCoord() {
        return coord;
    }

    public void setCoord(int[] coord) {
        this.coord = coord;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
