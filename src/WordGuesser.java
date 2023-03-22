/**
 * @author sanjaysaianasuri \
 * @version 2022-07-25
 */

public class WordGuesser {
    private String[][] playingField;
    private int round;
    private String solution;

    public WordGuesser(String solution) {
        this.solution = solution;
        this.round = round;
        this.playingField = new String[][]{{"", "", "", ""}, {"", "", "", ""}, {"", "", "", ""}, {"", "", "", ""}};
    }

    public String[][] getPlayingField() {
        return playingField;
    }

    public void setPlayingField(String[][] playingField) {
        this.playingField = playingField;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public boolean checkGuess(String guess) throws InvalidGuessException {
        if (guess.length() != 5) {
            throw new InvalidGuessException("Invalid guess!");
        }

        boolean solved = false;

        if (guess.equals(solution)) {
            playingField[round - 1][0] = "'" + guess.charAt(0) + "'";
            playingField[round - 1][1] = "'" + guess.charAt(1) + "'";
            playingField[round - 1][2] = "'" + guess.charAt(2) + "'";
            playingField[round - 1][3] = "'" + guess.charAt(3) + "'";
            playingField[round - 1][4] = "'" + guess.charAt(4) + "'";

            solved = true;
        } else {
            for (int i = 0; i < 5; i++) {
                if (guess.substring(i, i + 1).equals(solution.substring(i, i + 1))) {
                    playingField[round - 1][i] = "'" + guess.charAt(i) + "'";
                } else if (solution.contains(guess.substring(i, i + 1))) {
                    playingField[round - 1][i] = "*" + guess.charAt(i) + "*";
                } else {
                    playingField[round - 1][i] = "{" + guess.charAt(i) + "}";
                }
            }
            round++;
        }
        return solved;
    }

    public void printField() {
        System.out.println(playingField[0][0] + "|" + playingField[0][1] + "|" +
                playingField[0][2] + "|" + playingField[0][3] + "|" + playingField[0][4]);
        System.out.println(playingField[1][0] + "|" + playingField[1][1] + "|" + playingField[1][2]
                + "|" + playingField[1][3] + "|" + playingField[1][4]);
        System.out.println(playingField[2][0] + "|" + playingField[2][1] + "|" + playingField[2][2]
                + "|" + playingField[2][3] + "|" + playingField[2][4]);
        System.out.println(playingField[3][0] + "|" + playingField[3][1] + "|" + playingField[3][2]
                + "|" + playingField[3][3] + "|" + playingField[3][4]);
    }
}