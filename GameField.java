package minesweeper;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class GameField {
    public static Random rand = new Random();
    protected int numberOfMines;
    protected Cell[][] cells = new Cell[9][9];
    protected boolean firstMove = true;
    protected boolean gameOver = false;

    public GameField(int numberOfMines) {
        this.numberOfMines = numberOfMines;
        generateEmptyField();
        printGameField();
        generateMines(numberOfMines);
        countMines();
    }

    public void generateEmptyField() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(false);
                cells[i][j].setPosX(j);
                cells[i][j].setPosY(i);
            }
        }
    }

    public void generateMines(int numberOfMines) {

        for (int i = 0; i < numberOfMines; i++) {
            ArrayList<Cell> freeCells = returnFreeCellList();
            int randCell = rand.nextInt(freeCells.size() - 2);
            freeCells.get(randCell).setContainsMine(true);
        }
    }


    public ArrayList<Cell> returnFreeCellList() {

        ArrayList<Cell> freeCells = new ArrayList<>();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (!cells[i][j].isContainsMine()) freeCells.add(cells[i][j]);
            }
        }

        return freeCells;
    }

    public void countMines() {

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                int noOfMines = countMinesSquare(i, j);
                if (noOfMines > 0 && !cells[i][j].isContainsMine()) {
                    cells[i][j].setNoOfMines(noOfMines);

                } else {
                    cells[i][j].setNoOfMines(0);
                }

            }
        }

    }

    public int countMinesSquare(int i, int j) {
        int count = 0;

        if (i - 1 >= 0) {
            for (int k = -1; k < 2; k++) {
                if (j + k > 8 || j + k < 0) continue;
                if (cells[i - 1][j + k].isContainsMine()) {
                    count++;

                }

            }
        }

        for (int k = -1; k < 2; k++) {
            if (j + k > 8 || j + k < 0) continue;
            if (cells[i][j + k].isContainsMine()) {
                count++;
            }
        }

        if (i + 1 <= 8) {
            for (int k = -1; k < 2; k++) {
                if (j + k > 8 || j + k < 0) continue;
                if (cells[i + 1][j + k].isContainsMine()) {
                    count++;
                }
            }
        }
        return count;
    }


    public void makeMove(int a, int b, String c) {

        if (c.equals("free")) {
            if (!cells[a][b].isExplored() && !cells[a][b].isContainsMine()) {
                floodFill(a, b);
            } else if (cells[a][b].isContainsMine()) {
                if (firstMove) {
                    generateMines(1);
                    cells[a][b].setContainsMine(false);
                    countMines();
                    floodFill(a, b);
                } else {
                    gameOver = true;
                }
            } else {
                System.out.println("This cell has been explored");

            }

        } else if (c.equals("mine")) {
            if (!cells[a][b].isExplored() && !cells[a][b].isMarked()) {
                cells[a][b].setMarked(true);
            } else if (cells[a][b].isMarked()) {
                cells[a][b].setMarked(false);
            } else {
                System.out.println("This cell has been explored!");

            }
        }
        removeMarks();
        firstMove = false;
    }

    public void floodFill(int a, int b) {
        Queue<Cell> queue = new ArrayDeque<>();
        queue.add(cells[a][b]);

        while (!queue.isEmpty()) {
            Cell cellsTemp = queue.poll();
            cellsTemp.setExplored(true);
            if (checkInside(cellsTemp)) {
                cellsTemp.setChecked(true);
                if (cellsTemp.getPosy() - 1 >= 0) {
                    queue.offer(cells[cellsTemp.getPosy() - 1][cellsTemp.getPosx()]);
                }

                if (cellsTemp.getPosy() + 1 <= 8) {
                    queue.offer(cells[cellsTemp.getPosy() + 1][cellsTemp.getPosx()]);
                }


                if (cellsTemp.getPosx() - 1 >= 0) {
                    queue.offer(cells[cellsTemp.getPosy()][cellsTemp.getPosx() - 1]);
                }


                if (cellsTemp.getPosx() + 1 <= 8) {
                    queue.offer(cells[cellsTemp.getPosy()][cellsTemp.getPosx() + 1]);
                }

                if (cellsTemp.getPosx() + 1 <= 8 && cellsTemp.getPosy() + 1 <= 8) {
                    queue.offer(cells[cellsTemp.getPosy() + 1][cellsTemp.getPosx() + 1]);
                }

                if (cellsTemp.getPosx() + 1 <= 8 && cellsTemp.getPosy() - 1 >= 0) {
                    queue.offer(cells[cellsTemp.getPosy() - 1][cellsTemp.getPosx() + 1]);
                }

                if (cellsTemp.getPosx() - 1 >= 0 && cellsTemp.getPosy() + 1 <= 8) {
                    queue.offer(cells[cellsTemp.getPosy() + 1][cellsTemp.getPosx() - 1]);
                }

                if (cellsTemp.getPosx() - 1 >= 0 && cellsTemp.getPosy() - 1 >= 0) {
                    queue.offer(cells[cellsTemp.getPosy() - 1][cellsTemp.getPosx() - 1]);
                }
            }
        }


    }


    public boolean checkInside(Cell cell) {
        return !cell.isChecked() && !cell.isContainsMine() && cell.getNoOfMines() < 1;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean checkWin() {
        boolean markedWin = true;
        boolean exploreWin = true;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].isContainsMine() && !cells[i][j].isMarked()) {
                    markedWin = false;
                    break;
                }
            }
        }

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (!cells[i][j].isExplored() && !cells[i][j].isContainsMine()) {
                    exploreWin = false;
                    break;
                }
            }
        }

        return exploreWin || markedWin;

    }

    public void printGameField() {

        System.out.println("\n │123456789│\n" + "-|---------|");

        for (int i = 0; i < cells.length; i++) {
            System.out.print(i + 1 + "│");
            for (int j = 0; j < cells[i].length; j++) {


                if (cells[i][j].getNoOfMines() > 0 && cells[i][j].isExplored()) {
                    System.out.print(cells[i][j].getNoOfMines());
                } else if ((cells[i][j].isContainsMine() && cells[i][j].isExplored() && !cells[i][j].isMarked())
                        || cells[i][j].isContainsMine() && isGameOver()) {
                    System.out.print("X");
                } else if (cells[i][j].isChecked() && cells[i][j].isExplored()) {
                    System.out.print("/");
                } else if (cells[i][j].isMarked() && !cells[i][j].isExplored()) {
                    System.out.print("*");
                } else {
                    System.out.print(".");
                }

            }

            System.out.println("|");
        }
        System.out.println("-|---------|");
    }

    public void removeMarks() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].isExplored() && cells[i][j].isMarked()) {
                    cells[i][j].setMarked(false);
                }
            }
        }
    }
}

