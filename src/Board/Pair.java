/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

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

}
