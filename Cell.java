package minesweeper;

public class Cell {

    protected boolean containsMine;
    protected boolean checked = false;
    protected int noOfMines;
    protected boolean marked;
    protected boolean explored;
    private int posx;
    private int posy;

    public Cell(boolean containsMine) {
        this.containsMine = containsMine;
    }

    public boolean isContainsMine() {
        return containsMine;
    }

    public void setContainsMine(boolean containsMine) {
        this.containsMine = containsMine;
    }

    public boolean isExplored() {
        return explored;
    }


    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public void setPosX(int posx) {
        this.posx = posx;
    }

    public void setPosY(int posy) {
        this.posy = posy;
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public int getNoOfMines() {
        return noOfMines;
    }

    public void setNoOfMines(int noOfMines) {
        this.noOfMines = noOfMines;
    }
}
