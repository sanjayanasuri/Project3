import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

/**
 * @author sanjaysaianasuri \
 * @version 2022-07-25
 */

public class WordLibrary {
    private String[] library;
    private int seed;
    private Random random;
    private String fileName;

    public WordLibrary(String fileName) throws FileNotFoundException, InvalidWordException {
        this.fileName = fileName;
        File file = new File(fileName);
        FileReader fr = new FileReader(fileName);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<String> listOfWords = new ArrayList<>();

        try {
            if (file.createNewFile()) {
            } else {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            String newLine = bfr.readLine();
            while (newLine != null) {
                listOfWords.add(newLine);
                newLine = bfr.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (bfr != null) {
                try {
                    bfr.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        String[] result = listOfWords.get(0).split("");
        this.seed = Integer.parseInt(result[1]);
        this.random = new Random(seed);
        this.library = new String[listOfWords.size() - 1];
        for (int i = 1; i < listOfWords.size(); i++) {
            library[i - 1] = listOfWords.get(i);
        }
        processLibrary();
    }

    public void verifyWord(String word) throws InvalidWordException {
        if (word.length() != 5) {
            System.out.println("Invalid word!");
            throw new InvalidWordException("Invalid Word!");
        }
    }

    public String chooseWord() {
        return library[random.nextInt(library.length)];
    }

    public void processLibrary() {
        String[] newLib = null;
        for (String s : library) {
            try {
                verifyWord(s);
            } catch (InvalidWordException e) {
                int wordCounter = 0;
                newLib = new String[library.length - 1];
                for (String words : library) {
                    if (!words.equals(s)) {
                        newLib[wordCounter] = words;
                        wordCounter++;
                    }
                }
            } catch (NullPointerException e) {
                int wordCounter = 0;
                newLib = new String[library.length - 1];
                for (String words : library) {
                    if (!words.equals(s)) {
                        newLib[wordCounter] = words;
                        wordCounter++;
                    }
                }
            }
        }

        if (newLib != null) {
            library = newLib;
        }
    }

    // Given methods

    public String[] getLibrary() {
        return library;
    }

    public void setLibrary(String[] library) {
        this.library = library;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public String toString() {
        return Arrays.toString(library);
    }
}
