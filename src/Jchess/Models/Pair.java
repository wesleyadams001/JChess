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
     * Constructor for pair coordinates.
     * @param rowIndex  Row index of pair.
     * @param columnIndex   Column index of pair.
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
     * Returns a copy of Pair with the row offsetted. aka new Pair(getRow() + rowOffset, getColumn())
     * @param rowOffset Integer value specifying distance to be offset by.
     * @return  Pair object located at offset position.
     */
    public Pair offsettingRow(int rowOffset) {
        return new Pair(this.getRow() + rowOffset, this.getColumn());
    }

    /**
     * Returns a copy of Pair with the column offsetted. (getColumn() + columnOffset)
 aka.new Pair(getRow(), getColumn() + columnOffset)
     * @param columnOffset  Integer value specifying distance to be offset by.
     * @return  Pair object located at offset position.
     */
    public Pair offsettingColumn(int columnOffset) {
        return new Pair(this.getRow(), this.getColumn() + columnOffset);
    }

}
