/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import java.util.Vector;

/**
 *
 * @author nehalpatel
 */
public final class Board {

    /**
     *
     */
    public Grid grid;

    /**
     *
     */
    public Pair currentSelection;

    /**
     *
     */
    public int currentPlayer;

    /**
     *
     */
    public Board() {
        grid = new Grid();
        
        // TEMPORARY
        int rowIndex = grid.rowCount - 2; // Row index for Player 1's Pawns.
        int columnIndex = 6; // Random Pawn in the row.
        
        Tile tile = grid.getTile(new Pair(rowIndex, columnIndex));
        Vector<Pair> moves = tile.getPiece().getPossibleMoves(grid);
        
        moves.forEach((position) -> {
            grid.getTile(position).setHighlighted(true);
        });
        
        grid.print();
    }

    /**
     * Destroy the current Grid and create a new, ready-to-play Grid.
     */
    public void reset() {
    }

    /**
     * Load an previously saved session of the game.
     * @param fileName
     */
    public void load(String fileName) {
    }

    /**
     * Save this session of the game.
     * @param fileName
     */
    public void serialize(String fileName) {
    }

}
