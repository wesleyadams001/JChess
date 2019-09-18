/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Pieces.Pawn;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author nehalpatel
 */
public class Grid {

    /**
     * The number of rows for this Grid.
     */
    public final int rowCount = 8;

    /**
     * The number of columns for this Grid.
     */
    public final int columnCount = 8;

    private final ArrayList<ArrayList<Tile>> matrix;
    
    /**
     *
     */
    public Grid() {
        matrix = new ArrayList<>();
        
        // Populate matrix with empty Tiles.
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            matrix.add(new ArrayList<>());
            
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                Tile tile = new Tile(new Pair(rowIndex, columnIndex));
                
                matrix.get(rowIndex).add(tile);
            }
        }
        
        // Add enemy Pawns.
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            matrix.get(1).get(columnIndex).setPiece(createPawn(1, new Pair(0, columnIndex)));
        }
        
        // Add home Pawns.
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            matrix.get(rowCount - 2).get(columnIndex).setPiece(createPawn(0, new Pair(rowCount - 2, columnIndex)));
        }
    }
    
    /**
     * Search for and retrieve a Tile.
     * @param position A pair of coordinates with which to search for a Tile.
     * @return The desired Tile if found, else null.
     */
    public Tile getTile(Pair position) {
        try {
            return matrix.get(position.getRow()).get(position.getColumn());
        } catch (IndexOutOfBoundsException error) {
            System.out.println("Error while accessing Tile.");
            System.out.println(error);
            return null;
        }
    }
    
    private Pawn createPawn(int player, Pair position) {
        Pawn pawn = new Pawn();       
        pawn.setPlayer(player);
        pawn.setCurrentPosition(position);
        
        return pawn;
    }
    
    /**
     * Temporary method to debug and test the board before the GUI is implemented.
     */
    public void print() {
        this.matrix.forEach((row) -> {
            row.forEach((tile) -> {            
                if (tile.getHighlighted()) {
                    System.out.print("(X) ");
                } else if (!tile.getOccupied()) {
                    System.out.print("( ) ");
                } else {
                    System.out.print("(P) ");
                }
            });

            System.out.println();
        });
    }

}
