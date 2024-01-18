package game;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class AnimatedDriver {
    //Used to create the animated version of 2048
    public static void main(String[] args) {
        String[] actions = { "Test individual methods", "Play full game" };
        Collage board = new Collage();
        Font font = new Font("Arial", Font.PLAIN, 30);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(font);

        int action = getActionChoice(actions);
        switch (action) {
            case 1: // Test individual methods
            testIndividualMethods(board);
            break;
            case 2: //Test full game
            playFullGame(board);
            break;
        }

        System.exit(0);
    }

    private static void testIndividualMethods(Collage boardCollage) {
        String[] methods = { "updateOpenSpaces", "addRandomTile", "swipeLeft", "mergeLeft", "transpose", "flipRows", "makeMove" };
        String[] options = { "Test a new input file", "Test a method using this input file", "Quit" };

        int controlChoice = 1;
        do {
            String filename = getFileChoice();
            do {
                int method = getMethodChoice(methods);
                int[][] boardArray = readBoard(filename);
                Board board = new Board(boardArray);
                boardCollage.updateBoard(board, 1);
                testMethod(method, board, boardCollage);
                StdDraw.setPenColor(Color.BLACK);
                Font font = new Font("Arial", Font.PLAIN, 30);
                StdDraw.setFont(font);
                controlChoice = getControlChoice(options);
            } while (controlChoice == 2);
        } while (controlChoice == 1);
    }

    private static int[][] readBoard(String filename) {
        StdIn.setFile(filename);
        int[][] boardArray = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                boardArray[i][j] = StdIn.readInt();
        }
        StdIn.resetFile();
        return boardArray;
    }

    private static int getControlChoice(String[] options) {
        StdDraw.clear();
        while (StdDraw.hasNextKeyTyped())
            StdDraw.nextKeyTyped();

        ArrayList<String> prompt = new ArrayList<>();
        prompt.add("What do you want to do now?");

        for (int i = 0; i < 3; i++) {
            prompt.add(String.format("%d. %s", i + 1, options[i]));
        }

        for (int line = 0; line < 4; line++) {
            StdDraw.textLeft(0.5, 3.5 - 0.3 * line, prompt.get(line));
        }

        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if ('1' <= key && key <= '3')
                    return key - '0';
            }

            StdDraw.pause(20);
        }
    }

    private static int getActionChoice(String[] actions) {
        StdDraw.clear();
        while (StdDraw.hasNextKeyTyped())
            StdDraw.nextKeyTyped();

        ArrayList<String> prompt = new ArrayList<>();
        prompt.add("What do you want to do?");

        for (int i = 0; i < 2; i++) {
            prompt.add(String.format("%d. %s", i + 1, actions[i]));
        }

        for (int line = 0; line < 3; line++) {
            StdDraw.textLeft(0.5, 3.5 - 0.3 * line, prompt.get(line));
        }

        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if ('1' <= key && key <= '2')
                    return key - '0';
            }

            StdDraw.pause(20);
        }
    }

    private static char getDirectionChoice() {
        HashMap<Character, String> keyMap = new HashMap<>() {
            {
                put('w', "U");
                put('a', "L");
                put('s', "D");
                put('d', "R");
                put('W', "U");
                put('A', "L");
                put('S', "D");
                put('D', "R");
            }
        };
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (keyMap.containsKey(key))
                    switch (keyMap.get(key)) {
                        case "U":
                        return 'U';
                        case "D":
                        return 'D';
                        case "L":
                        return 'L';
                        case "R":
                        return 'R';
                    }
            }

            StdDraw.pause(20);
        }
    }

    private static int getMethodChoice(String[] methods) {
        StdDraw.clear();

        ArrayList<String> prompt = new ArrayList<>();
        prompt.add("What method would you like to test?");

        for (int i = 0; i < 7; i++) {
            prompt.add(String.format("%d. %s", i + 1, methods[i]));
        }

        for (int line = 0; line < 8; line++) {
            StdDraw.textLeft(0.5, 3.5 - 0.3 * line, prompt.get(line));
        }

        StdDraw.show();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if ('1' <= key && key <= '7')
                    return key - '0';
            }

            StdDraw.pause(20);
        }
    }

    private static String getFileChoice() {
        StdDraw.clear();

        File[] allFiles = new File(".").listFiles();
        ArrayList<String> choices = new ArrayList<>();
        ArrayList<String> files = new ArrayList<>();
        choices.add("What file do you want to test on (enter = submit)?");
        choices.add("Type only ASCII characters, backspace and enter.");

        for (File f : allFiles) {
            if (f.getName().endsWith(".in")) {
                files.add(f.getName());
            }
        }
        Font font = new Font("Arial", Font.PLAIN, 24);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(font);
        double x = 0.25;
        for (int line = 0; line < choices.size(); line++) {
            StdDraw.textLeft(x, 3 - 0.21 * line, choices.get(line));
        }

        StdDraw.show();

        String selection = "";
        char key;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                key = StdDraw.nextKeyTyped();
                if (key == '\n') {
                    if (files.contains(selection)) {
                        font = new Font("Arial", Font.PLAIN, 30);
                        StdDraw.setPenColor(Color.BLACK);
                        StdDraw.setFont(font);
                        return selection;
                    }
                    else {
                        StdDraw.setPenColor(Color.BLACK);
                        StdDraw.filledRectangle(2, 1.9, 1.9, .4);
                        StdDraw.setPenColor(Color.RED);
                        font = new Font("Arial", Font.PLAIN, 40);
                        StdDraw.setFont(font);
                        StdDraw.text(2, 1.9, "Could not open " + selection);
                        StdDraw.show();
                        selection = "";
                        continue;
                    }
                }
                else if (key == '\b') {
                    if (selection.length() != 0)
                        selection = selection.substring(0, selection.length() - 1);
                }
                else if (key >= 32 && key <= 127) {
                    selection += key;
                }
                StdDraw.setPenColor(Color.BLACK);
                StdDraw.filledRectangle(2, 1.9, 1.9, .4);
                StdDraw.setPenColor(Color.WHITE);
                font = new Font("Arial", Font.PLAIN, 40);
                StdDraw.setFont(font);
                StdDraw.text(2, 1.9, selection);
                StdDraw.show();

            }

            StdDraw.pause(20);
        }
    }

    private static void testMethod(int methodNumber, Board board, Collage boardCollage) {
        while (StdDraw.hasNextKeyTyped())
            StdDraw.nextKeyTyped();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                break;
            }

            StdDraw.pause(20);
        }


        switch (methodNumber) {
            case 1:
                board.updateOpenSpaces();
                for (BoardSpot bs : board.getOpenSpaces()) {
                    boardCollage.replaceTile(bs.getRow(), bs.getCol(), -1);
                }
                break;

            case 2:
                StdRandom.setSeed(2022);
                board.updateOpenSpaces();
                board.addRandomTile();
                boardCollage.updateBoard(board, 1);
                break;

            case 3:
                board.swipeLeft();
                boardCollage.updateBoard(board, 1);
                break;

            case 4:
                board.mergeLeft();
                boardCollage.updateBoard(board, 1);
                break;

            case 5:
                board.transpose();
                boardCollage.updateBoard(board, 1);
                break;

            case 6:
                board.flipRows();
                boardCollage.updateBoard(board, 1);
                break;
            case 7:
                char letter = getDirectionChoice();
                board.makeMove(letter);
                boardCollage.updateBoard(board, 1);
                break;
        }

        StdDraw.show();
        while (StdDraw.hasNextKeyTyped())
            StdDraw.nextKeyTyped();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                break;
            }
            StdDraw.pause(20);
        }
    }

    private static void playFullGame(Collage boardCollage) {
        Board board = new Board();
        board.updateOpenSpaces();
        board.addRandomTile();
        board.updateOpenSpaces();
        board.addRandomTile();

        boardCollage.updateBoard(board, 2);
        HashMap<Character, String> keyMap = new HashMap<>() {
            {
                put('w', "U");
                put('a', "L");
                put('s', "D");
                put('d', "R");
                put('W', "U");
                put('A', "L");
                put('S', "D");
                put('D', "R");
                put('q', "Exit");
            }
        };

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String action = keyMap.getOrDefault(StdDraw.nextKeyTyped(), "Invalid");
                if (action.equals("Exit"))
                    break;
                if (!action.equals("Invalid")) {
                    int[][] oldBoard = new int[4][4];
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            oldBoard[i][j] = board.getBoard()[i][j];
                        }
                    }
                    board.makeMove(action.charAt(0));
                    board.updateOpenSpaces();
                    if (!board.isGameLost() && !boardsMatch(oldBoard, board.getBoard())) {
                        board.addRandomTile();
                    }
                    boardCollage.updateBoard(board, 2);
                }
            }

            StdDraw.show();
            StdDraw.pause(20);
        }
    }

    private static boolean boardsMatch(int[][] board1, int[][] board2) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board1[i][j] != board2[i][j])
                    return false;
            }
        }
        return true;
    }
}