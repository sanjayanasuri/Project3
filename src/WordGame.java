import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * @author sanjaysaianasuri \
 * @version @2022-07-25
 */
public class WordGame {

    public static String welcome = "Ready to play?";
    public static String yesNo = "1.Yes\n2.No";
    public static String noPlay = "Maybe next time!";
    public static String currentRoundLabel = "Current Round: ";
    public static String enterGuess = "Please enter a guess!";
    public static String winner = "You got the answer!";
    public static String outOfGuesses = "You ran out of guesses!";
    public static String solutionLabel = "Solution: ";
    public static String incorrect = "That's not it!";
    public static String keepPlaying = "Would you like to make another guess?";
    public static String fileNameInput = "Please enter a filename";

    public static void updateGameLog(String solution, String[] guesses, boolean solved) throws IOException {
        String answer = "";
        if (solved == true) {
            answer = "yes";
        } else {
            answer = "no";
        }
        ArrayList<String> listOfWords = new ArrayList<>();
        File file = new File("gamelog.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileReader fr = new FileReader(file);
        BufferedReader bfr = new BufferedReader(fr);
        try {
            String line = bfr.readLine();
            while (line != null) {
                listOfWords.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bfr != null) {
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        int gameNumber;
        if (listOfWords != null) {
            String[] gameOutput = listOfWords.get(0).split(" ");
            gameNumber = Integer.parseInt(gameOutput[2]);
            gameNumber++;
        } else {
            gameNumber = 1;
        }

        FileWriter fr2 = new FileWriter(file, true);
        BufferedWriter bwr = new BufferedWriter(fr2);
        bwr.write("Games Completed: " + gameNumber);
        for (String listOfWord : listOfWords) {
            bwr.write(listOfWord);
        }
        bwr.write("Game " + gameNumber);
        bwr.write("- Solution: " + solution);
        bwr.write("- Guesses: " + Arrays.toString(guesses));
        bwr.write("- Solved: " + answer);
        bwr.close();

    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        WordLibrary wordlibrary = null;
        boolean condition = true;

        while (condition) {
            try {
                System.out.println(fileNameInput);
                String fileInput = scanner.nextLine();
                try {
                    wordlibrary = new WordLibrary(fileInput);
                } catch (InvalidWordException e) {
                    System.out.println(e.getMessage());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        WordGuesser wordGuesser;
        String guess;
        String[] finalArray;
        int start;
        int guessCount = 0;

        do {
            System.out.println(welcome);
            System.out.println(yesNo);
            start = scanner.nextInt();
            scanner.nextLine();

            if (start == 2) {
                System.out.println(noPlay);
                break;
            }

            String answer = wordlibrary.chooseWord();
            wordGuesser = new WordGuesser(answer);

            System.out.println(currentRoundLabel + wordGuesser.getRound());
            wordGuesser.printField();

            System.out.println(enterGuess);
            guess = scanner.nextLine();

            try {
                condition = wordGuesser.checkGuess(guess);
            } catch (InvalidGuessException e) {
                System.out.println(e.getMessage());
                condition = false;
            }

            guessCount++;

            if (condition) {
                System.out.println(winner);
                wordGuesser.printField();
                finalArray = new String[1];
                finalArray[0] = guess;

                try {
                    updateGameLog(wordGuesser.getSolution(), finalArray, true);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {

                int startAgain;
                boolean conditionAgain = true;
                int counter = 0;
                String newGuess;
                ArrayList<String> allGuesses = new ArrayList<>();
                allGuesses.add(guess);

                do {
                    System.out.println(incorrect);
                    System.out.println(keepPlaying);
                    System.out.println(yesNo);
                    startAgain = scanner.nextInt();

                    if (startAgain == 2) {
                        break;
                    }

                    System.out.println(currentRoundLabel + wordGuesser.getRound());
                    wordGuesser.printField();
                    newGuess = scanner.nextLine();

                    try {
                        conditionAgain = wordGuesser.checkGuess(newGuess);
                    } catch (InvalidGuessException e) {
                        System.out.println(e.getMessage());
                    }

                    guessCount++;
                    allGuesses.add(newGuess);

                    if (conditionAgain == true) {

                        System.out.println(winner);
                        wordGuesser.printField();
                        finalArray = allGuesses.toArray(new String[guessCount]);

                        try {
                            updateGameLog(wordGuesser.getSolution(), finalArray, true);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    } else {
                        counter++;
                    }
                } while (startAgain == 1 && counter < 4);

                if (conditionAgain == false) {

                    System.out.println(outOfGuesses);
                    System.out.println(solutionLabel + wordGuesser.getSolution());
                    wordGuesser.printField();
                    finalArray = allGuesses.toArray(new String[guessCount - 1]);
                    try {
                        updateGameLog(wordGuesser.getSolution(), finalArray, true);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        while (start == 1);
        {
            System.out.println(noPlay);
        }
    }
}


