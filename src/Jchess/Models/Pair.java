/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jchess.Models;

/**
 * A class that represents Pairs ie (x,y)
 * @author nehalpatel
 */
public class Pair {

    private final int rowIndex;
    private final int columnIndex;
    
    /**
     *
     * @param rowIndex
     * @param columnIndex
     */
    public Pair(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }
    
    /**
     * Retrieve the row index. (0 being the top row, 7 being the last row)
     * @return The Pair's row index.
     */
    public int getRow() {
        return this.rowIndex;
    }
    
    /**
     * Retrieve the column index. (0 being the leftmost Tile, 7 being the rightmost Tile)
     * @return The Pair's column index.
     */
    public int getColumn() {
        return this.columnIndex;
    }

    /**
     * Returns a copy of Pair with the row offsetted.
     * aka. new Pair(getRow() + rowOffset, getColumn())
     */
    public Pair offsettingRow(int rowOffset) {
        return new Pair(this.getRow() + rowOffset, this.getColumn());
    }

    /**
     * Returns a copy of Pair with the column offsetted. (getColumn() + columnOffset)
     * aka. new Pair(getRow(), getColumn() + columnOffset)
     */
    public Pair offsettingColumn(int columnOffset) {
        return new Pair(this.getRow(), this.getColumn() + columnOffset);
    }

    @Override
    public boolean equals(Object o) {
        // Self check.
        if (this == o) return true;

        // Null check.
        if (o == null) return false;

        // Type check and cast.
        if (getClass() != o.getClass()) return false;

        // Field comparison.
        Pair rhs = (Pair) o;
        return getRow() == rhs.getRow() && getColumn() == rhs.getColumn();
    }

}
