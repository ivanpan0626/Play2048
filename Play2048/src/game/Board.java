package game;

import java.util.ArrayList;

public class Board {
    private int[][] gameBoard;
    private ArrayList<BoardSpot> openSpaces;
    private boolean add;

    public Board() {
        gameBoard = new int[4][4];
        openSpaces = new ArrayList<>();
    }

    public Board ( int[][] board ) {
        gameBoard = new int[board.length][board[0].length];
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                gameBoard[r][c] = board[r][c];
            }
        }
        openSpaces = new ArrayList<>();
    }

    public void updateOpenSpaces() {
        int i = 0;
        int j = 0;
        while (i<4){
            while (j<4){
                if (gameBoard[i][j] == 0){
                    openSpaces.add(new BoardSpot(i,j));
                }
                j++;
            }
            j=0;
            i++;
        }
    }

    public void addRandomTile() {
        // WRITE YOUR CODE HERE
        int randomSpot = 0;
        double numPicker = 0;
        randomSpot = StdRandom.uniform(0,openSpaces.size());
        numPicker = StdRandom.uniform(0.0,1.0);
        if (numPicker < .1){
            gameBoard[openSpaces.get(randomSpot).getRow()][openSpaces.get(randomSpot).getCol()] = 4;
            
        }
        else {
            gameBoard[openSpaces.get(randomSpot).getRow()][openSpaces.get(randomSpot).getCol()] = 2;
        }
    }

    public void swipeLeft() {
        // WRITE YOUR CODE HERE
        int i = 0;
        int j = 0;
        int space = 0;
        int holder = 0;
        while (i<4){
            while (j<4){
                if (gameBoard[i][j] == 0){
                    space++;
                }
                if (gameBoard[i][j] != 0){
                    holder = gameBoard[i][j-space];
                    gameBoard[i][j-space] = gameBoard[i][j];
                    gameBoard[i][j] = holder;
                }
                j++;
            }
            space = 0;
            j=0;
            i++;
        }
    }

    public void mergeLeft() {
        int i = 0;
        int j = 0;
        while (i<4){
            while (j<3){
                if (gameBoard[i][j] == gameBoard[i][j+1]){
                    gameBoard[i][j] = 2*gameBoard[i][j];
                    gameBoard[i][j+1] = 0;
                }
                j++;
            }
            j=0;
            i++;
        }
    }

    public void rotateBoard() {
        transpose();
        flipRows();
    }

    public void transpose() {
        int i = 0;
        int j = 0;
        int holder = 0;
        int counter = 0;
        while (i<4){
            while (j<4){
                holder = gameBoard[i][j];
                gameBoard[i][j] = gameBoard[j][i];
                gameBoard[j][i] = holder;
                j++;
            }
            counter++;
            j = counter;
            i++;
        }
    }

    public void flipRows() {
        int i = 0;
        int j = 0;
        int holder = 0;
        while (i<4){
            while (j<2){
                holder = gameBoard[i][j];
                gameBoard[i][j] = gameBoard[i][3-j];
                gameBoard[i][3-j] = holder;
                j++;
            }
            j = 0;
            i++;
        }
    }

    public void makeMove(char letter) {
        if (letter == 'L' || letter == 'U' || letter == 'R' || letter == 'D'){
            if (letter == 'L'){
                swipeLeft();
                mergeLeft();
                swipeLeft();   
            }
            else if (letter == 'U'){
                for (int i = 0; i < 3; i++){
                    rotateBoard();
                }
                swipeLeft();
                mergeLeft();
                swipeLeft();  
                for (int i = 0; i < 1; i++){
                    rotateBoard();
                } 
            }
            else if (letter == 'R'){
                for (int i = 0; i < 2; i++){
                    rotateBoard();
                }
                swipeLeft();
                mergeLeft();
                swipeLeft();   
                for (int i = 0; i < 2; i++){
                    rotateBoard();
                }
            }
            else {
                for (int i = 0; i < 1; i++){
                    rotateBoard();
                }
                swipeLeft();
                mergeLeft();
                swipeLeft();
                for (int i = 0; i < 3; i++){
                    rotateBoard();
                }

            }
        }
    }

    public boolean isGameLost() {
        return openSpaces.size() == 0;
    }

    public int showScore() {
        int score = 0;
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                score += gameBoard[r][c];
            }
        }
        return score;
    }

    public void print() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                String g = Integer.toString(gameBoard[r][c]);
                StdOut.print((g.equals("0")) ? "-" : g);
                for ( int o = 0; o < (5 - g.length()); o++ ) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }

    public void printOpenSpaces() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                String g = Integer.toString(gameBoard[r][c]);
                for ( BoardSpot bs : getOpenSpaces() ) {
                    if (r == bs.getRow() && c == bs.getCol()) {
                        g = "**";
                    }
                }
                StdOut.print((g.equals("0")) ? "-" : g);
                for ( int o = 0; o < (5 - g.length()); o++ ) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }

    public Board(long seed) {
        StdRandom.setSeed(seed);
        gameBoard = new int[4][4];
    }

    public ArrayList<BoardSpot> getOpenSpaces() {
        return openSpaces;
    }

    public int[][] getBoard() {
        return gameBoard;
    }
}
