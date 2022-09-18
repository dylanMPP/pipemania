package model;

public class Pipeline {

    private String type;
    private Pipeline next;
    private Pipeline previous;
    private int row;
    private int column;

    public Pipeline(String type, int row, int column)
    {
        this.type = type;
        this.row = row;
        this.column = column;
    } // constructor

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Pipeline getNext() {
        return next;
    }

    public void setNext(Pipeline next) {
        this.next = next;
    }

    public Pipeline getPrevious() {
        return previous;
    }

    public void setPrevious(Pipeline previous) {
        this.previous = previous;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}
