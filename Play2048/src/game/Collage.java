package game;

import java.awt.Color;
import java.awt.Font;

public class Collage {
    private int n;

    public Collage() {
        n = 4;
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setScale(0, 4);
        StdDraw.enableDoubleBuffering();
    }

    public Collage(int size) {
        n = size;
        StdDraw.setScale(0, n);
        StdDraw.enableDoubleBuffering();

    }

    public void updateBoard(Board gb, int mode) {
        int[][] board = gb.getBoard();
        StdDraw.clear();
        if (mode == 2 && gb.isGameLost()) {
            Font font = new Font("Arial", Font.BOLD, 64);
            StdDraw.setPenColor(Color.BLACK);
            String text = "Final score: " + gb.showScore();
            StdDraw.setFont(font);
            StdDraw.text((double) n / 2, (double) n / 2, text);
        } else {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    replaceTile(i, j, board[i][j]);
                }
            }
        }
        StdDraw.show();
    }

    public void replaceTile(int collageCol, int collageRow, int numToReplace) {
        String text = Integer.toString(numToReplace);
        if (numToReplace != 0) {
            StdDraw.setPenRadius();
            double centerX = collageRow + .5;
            double centerY = ((n - 1) - collageCol) + .5;
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.square(centerX, centerY, 0.5);
            if (numToReplace == -1) {
                StdDraw.setPenColor(Color.DARK_GRAY);
                StdDraw.filledSquare(centerX, centerY, 0.5);
                StdDraw.setPenColor(Color.WHITE);
                text = "-";
            }
            else if (numToReplace == 2) {
                StdDraw.setPenColor(new Color(238, 228, 218));
                StdDraw.filledSquare(centerX, centerY, 0.5);
                StdDraw.setPenColor(Color.BLACK);
            }
            else if (numToReplace == 4) {
                StdDraw.setPenColor(new Color(255, 224, 200));
                StdDraw.filledSquare(centerX, centerY, 0.5);
                StdDraw.setPenColor(Color.BLACK);
            }
            else if (numToReplace == 8) {
                StdDraw.setPenColor(new Color(242, 177, 121));
                StdDraw.filledSquare(centerX, centerY, 0.5);
                StdDraw.setPenColor(Color.WHITE);
            }
            else if (numToReplace == 16) {
                StdDraw.setPenColor(new Color(245, 149, 99));
                StdDraw.filledSquare(centerX, centerY, 0.5);
                StdDraw.setPenColor(Color.WHITE);           
             }
             else if (numToReplace == 32) {
                StdDraw.setPenColor(new Color(255, 160, 96));
                StdDraw.filledSquare(centerX, centerY, 0.5);
                StdDraw.setPenColor(Color.WHITE);           
             }
             else if (numToReplace == 64) {
                StdDraw.setPenColor(new Color(246, 124, 96));
                StdDraw.filledSquare(centerX, centerY, 0.5);
                StdDraw.setPenColor(Color.WHITE);           
             }
            else {
                StdDraw.setPenColor(new Color(237, 197, 63));
                StdDraw.filledSquare(centerX, centerY, 0.5);
                StdDraw.setPenColor(Color.WHITE);      
            }
            Font font = new Font("Arial", Font.BOLD, 40);
            StdDraw.setFont(font);
            StdDraw.text(centerX, centerY, text);
        } else {
            double centerX = collageRow + .5;
            double centerY = ((n - 1) - collageCol) + .5;
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.001);
            StdDraw.square(centerX, centerY, 0.5);
        }

    }
}
