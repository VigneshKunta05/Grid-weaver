package com.gridweaver.gridweaver.model;

public class GridCell {

    private int row;
    private int column;
    private boolean active;

    public GridCell() {
    }

    public GridCell(int row, int column, boolean active) {
        this.row = row;
        this.column = column;
        this.active = active;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}